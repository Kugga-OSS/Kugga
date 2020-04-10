package com.ayang818.kugga.services.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/4/9 22:21
 **/
@Data
@Builder
@AllArgsConstructor
public class JwtSubject {
    Long UID;
}
