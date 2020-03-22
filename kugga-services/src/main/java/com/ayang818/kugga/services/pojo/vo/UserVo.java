package com.ayang818.kugga.services.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/3/22 14:08
 **/
@Data
@Builder
public class UserVo implements Vo {
    Integer state;
    String userName;
    String displayName;
    String avatar;
    String email;
}
