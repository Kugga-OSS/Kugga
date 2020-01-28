package com.ayang818.kugga.services.mapper;

import com.ayang818.kugga.services.pojo.model.User;
import com.ayang818.kugga.services.pojo.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    long countByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    int deleteByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    List<User> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    List<User> selectByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    User selectByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kugga_user
     *
     * @mbg.generated Thu Jan 23 22:35:22 CST 2020
     */
    int updateByPrimaryKey(User record);
}