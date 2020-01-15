package com.ayang818.kugga.starter.mapper;

import com.ayang818.kugga.starter.pojo.model.ChatMessage;
import com.ayang818.kugga.starter.pojo.model.ChatMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ChatMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    long countByExample(ChatMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    int deleteByExample(ChatMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    int insert(ChatMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    int insertSelective(ChatMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    List<ChatMessage> selectByExampleWithRowbounds(ChatMessageExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    List<ChatMessage> selectByExample(ChatMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    ChatMessage selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    int updateByExampleSelective(@Param("record") ChatMessage record, @Param("example") ChatMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    int updateByExample(@Param("record") ChatMessage record, @Param("example") ChatMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    int updateByPrimaryKeySelective(ChatMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_info
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    int updateByPrimaryKey(ChatMessage record);
}