package com.ayang818.kugga.services.interceptor;

import com.ayang818.kugga.services.pojo.JwtSubject;
import com.ayang818.kugga.utils.JsonUtil;
import com.ayang818.kugga.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 通过拦截有两种方式，第一种是jwt在redis缓存中，第二种是解析出来的jwt符合规定
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
                request.setAttribute("uid", Long.parseLong(uid));
                return true;
            } else {
                // 缓存中没有就解析jwt
                Claims claims = jwtUtil.parseJWT(token);
                String jsonString = claims.getSubject();
                JwtSubject jwtSubject = JsonUtil.fromJson(jsonString, JwtSubject.class);
                if (jwtSubject != null) {
                    request.setAttribute("uid", jwtSubject.getUID());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
