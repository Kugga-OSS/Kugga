package com.ayang818.kugga.services.mapper;

import com.ayang818.kugga.services.pojo.model.UserRelation;
import com.ayang818.kugga.services.pojo.model.UserRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user_relation
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    long countByExample(UserRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user_relation
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    int deleteByExample(UserRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user_relation
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    int insert(UserRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user_relation
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    int insertSelective(UserRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user_relation
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    List<UserRelation> selectByExampleWithRowbounds(UserRelationExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user_relation
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    List<UserRelation> selectByExample(UserRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user_relation
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    int updateByExampleSelective(@Param("record") UserRelation record, @Param("example") UserRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user_relation
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    int updateByExample(@Param("record") UserRelation record, @Param("example") UserRelationExample example);
}