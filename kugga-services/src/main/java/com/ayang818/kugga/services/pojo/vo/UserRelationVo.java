package com.ayang818.kugga.services.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/3/26 15:44
 **/
@Data
@Builder
public class UserRelationVo {
    UserVo other;
    Date createTime;
    Byte status;
}
