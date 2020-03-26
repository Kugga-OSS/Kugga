package com.ayang818.kugga.services.mapper;

import com.ayang818.kugga.services.pojo.model.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/3/26 20:37
 **/
public interface UserExtMapper {

    @Select({"<script>",
            "select *",
            "from kugga_user",
            "where uid in",
            "<foreach item='item' index='index' collection='list'",
            "open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    @Results({
            @Result(property="displayName",column="display_name")
    })
    List<User> selectAllByUid(List<Long> list);
}
