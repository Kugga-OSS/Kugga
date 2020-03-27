package com.ayang818.kugga.services.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/3/27 19:43
 **/
@Data
@Builder
public class FriendListVo implements Vo {
    Integer state;
    List<UserVo> friendList;
}
