package com.ayang818.kugga.services.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/4/3 16:10
 **/
public interface UserRelationExtMapper {

    @Select("select other_uid from kugga_user_relation where owner_uid=#{uid} and last_mid is not" +
            " " +
            "null")
    List<Long> selectRecentChatUid(Long uid);
}
