package com.ayang818.kugga.utils.enums;

public interface JwtConstant {
    String JWT_ID = "jwt";
    String JWT_SECRET = "v2rayshado.wsocksr";
    int JWT_TTL = 60*60*1000;
    int JWT_REFRESH_INTERVAL = 55*60*1000;
    int JWT_REFRESH_TTL = 12*60*60*1000;
}
