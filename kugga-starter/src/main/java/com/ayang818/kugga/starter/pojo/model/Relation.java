package com.ayang818.kugga.starter.pojo.model;

public class Relation {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column friend_info.id
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column friend_info.owner_id
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    private Long ownerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column friend_info.friend_id
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    private Long friendId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column friend_info.pending_status
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    private Byte pendingStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column friend_info.last_message
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    private String lastMessage;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column friend_info.id
     *
     * @return the value of friend_info.id
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column friend_info.id
     *
     * @param id the value for friend_info.id
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column friend_info.owner_id
     *
     * @return the value of friend_info.owner_id
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column friend_info.owner_id
     *
     * @param ownerId the value for friend_info.owner_id
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column friend_info.friend_id
     *
     * @return the value of friend_info.friend_id
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    public Long getFriendId() {
        return friendId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column friend_info.friend_id
     *
     * @param friendId the value for friend_info.friend_id
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column friend_info.pending_status
     *
     * @return the value of friend_info.pending_status
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    public Byte getPendingStatus() {
        return pendingStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column friend_info.pending_status
     *
     * @param pendingStatus the value for friend_info.pending_status
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    public void setPendingStatus(Byte pendingStatus) {
        this.pendingStatus = pendingStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column friend_info.last_message
     *
     * @return the value of friend_info.last_message
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column friend_info.last_message
     *
     * @param lastMessage the value for friend_info.last_message
     *
     * @mbg.generated Sun Jan 19 13:44:48 CST 2020
     */
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage == null ? null : lastMessage.trim();
    }
}