package com.ayang818.kugga.starter.pojo.model;

public class ChatMessage {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chat_info.id
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chat_info.sender_id
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    private Long senderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chat_info.receiver_id
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    private Long receiverId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chat_info.message_content
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    private String messageContent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chat_info.is_signed
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    private Boolean isSigned;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chat_info.id
     *
     * @return the value of chat_info.id
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chat_info.id
     *
     * @param id the value for chat_info.id
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chat_info.sender_id
     *
     * @return the value of chat_info.sender_id
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    public Long getSenderId() {
        return senderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chat_info.sender_id
     *
     * @param senderId the value for chat_info.sender_id
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chat_info.receiver_id
     *
     * @return the value of chat_info.receiver_id
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    public Long getReceiverId() {
        return receiverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chat_info.receiver_id
     *
     * @param receiverId the value for chat_info.receiver_id
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chat_info.message_content
     *
     * @return the value of chat_info.message_content
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chat_info.message_content
     *
     * @param messageContent the value for chat_info.message_content
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chat_info.is_signed
     *
     * @return the value of chat_info.is_signed
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    public Boolean getIsSigned() {
        return isSigned;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chat_info.is_signed
     *
     * @param isSigned the value for chat_info.is_signed
     *
     * @mbg.generated Wed Jan 15 15:01:44 CST 2020
     */
    public void setIsSigned(Boolean isSigned) {
        this.isSigned = isSigned;
    }
}