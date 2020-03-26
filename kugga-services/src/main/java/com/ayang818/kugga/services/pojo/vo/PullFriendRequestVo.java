package com.ayang818.kugga.services.pojo.vo;

import com.ayang818.kugga.services.pojo.model.UserRelation;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/3/26 15:23
 **/
@Data
@Builder
public class PullFriendRequestVo implements Vo {
    Integer state;
    List<UserRelationVo> relations;
}
