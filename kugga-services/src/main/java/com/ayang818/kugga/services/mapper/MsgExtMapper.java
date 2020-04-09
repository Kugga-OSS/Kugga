package com.ayang818.kugga.services.mapper;

import com.ayang818.kugga.services.pojo.vo.MsgTmp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/4/3 16:57
 **/
public interface MsgExtMapper {

    @Select("select a.owner_uid, a.other_uid, a.mid, b.content, a.is_sender, a.create_time from " +
            "kugga_message_relation a, " +
            "kugga_message_content b " +
            "where a.owner_uid = #{ownerUid} " +
            "and a.other_uid = #{otherUid} " +
            "and a.mid = b.mid")
    @Results({
            @Result(property="ownerUid",column="owner_uid"),
            @Result(property="otherUid",column="other_uid"),
            @Result(property="sender",column="is_sender"),
            @Result(property="createTime",column="create_time")
    })
    List<MsgTmp> fetchMsg(@Param("ownerUid") Long ownerUid,@Param("otherUid") Long otherUid);
}
