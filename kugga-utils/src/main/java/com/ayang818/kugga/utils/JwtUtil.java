package com.ayang818.kugga.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jsonwebtoken.secret.key}")
    String secretKey;

    String base64String = null;

    /**
     * 创建jwt
     * @param id
     * @param subject   内容主体 jsonString
     * @param ttlMillis
     * @return
     */
    public String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) {
        SecretKey key = generalKey();
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
    }

    /**
     * 由字符串生成加密key
     * @return
     */
    private SecretKey generalKey() {
        if (base64String == null) {
            base64String = new Base64().encodeToString(secretKey.getBytes());
        }
        byte[] encodedKey = Base64.decodeBase64(base64String);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}