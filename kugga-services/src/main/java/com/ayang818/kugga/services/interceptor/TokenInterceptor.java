package com.ayang818.kugga.services.interceptor;

import com.ayang818.kugga.services.pojo.JwtSubject;
import com.ayang818.kugga.utils.JsonUtil;
import com.ayang818.kugga.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 通过拦截有两种方式，第一种是jwt在redis缓存中，第二种是解析出来的jwt符合规定
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authentication");
        if (token != null && !token.isEmpty()) {
            // 获取redis缓存
            String uid = redisTemplate.opsForValue().get(token);
            if (uid != null) {
                logger.info("用户 {} 的 jwt 击中缓存", uid);
                request.setAttribute("uid", Long.parseLong(uid));
                return true;
            } else {
                // 缓存中没有就解析jwt
                Claims claims = jwtUtil.parseJWT(token);
                if (claims == null) {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write("{\"status\":\"401\", \"data\": {\"message\": \"无效的身份验证\"}}");
                    return false;
                }
                Date expireDate = claims.getExpiration();
                // 若 jwt 已经过期，则重新登录
                if (expireDate.before(new Date())) {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write("{\"status\":\"401\", \"data\": {\"message\": \"身份验证过期\"}}");
                    return false;
                }
                String jsonString = claims.getSubject();
                JwtSubject jwtSubject = JsonUtil.fromJson(jsonString, JwtSubject.class);
                if (jwtSubject != null) {
                    request.setAttribute("uid", jwtSubject.getUID());
                    return true;
                }
            }
        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"status\":\"401\", \"data\": {\"message\": \"未身份验证\"}}");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
