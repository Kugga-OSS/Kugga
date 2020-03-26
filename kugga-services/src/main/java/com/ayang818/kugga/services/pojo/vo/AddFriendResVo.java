package com.ayang818.kugga.services.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/3/26 11:33
 **/
@Data
@Builder
public class AddFriendResVo implements Vo {
    Integer state;
    String message;
}
