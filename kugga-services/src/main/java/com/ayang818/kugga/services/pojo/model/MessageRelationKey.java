package com.ayang818.kugga.services.pojo.model;

public class MessageRelationKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kugga_message_relation.owner_uid
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    private Long ownerUid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kugga_message_relation.mid
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    private Long mid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kugga_message_relation.owner_uid
     *
     * @return the value of kugga_message_relation.owner_uid
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    public Long getOwnerUid() {
        return ownerUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kugga_message_relation.owner_uid
     *
     * @param ownerUid the value for kugga_message_relation.owner_uid
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    public void setOwnerUid(Long ownerUid) {
        this.ownerUid = ownerUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kugga_message_relation.mid
     *
     * @return the value of kugga_message_relation.mid
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    public Long getMid() {
        return mid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kugga_message_relation.mid
     *
     * @param mid the value for kugga_message_relation.mid
     *
     * @mbg.generated Thu Mar 26 14:54:36 CST 2020
     */
    public void setMid(Long mid) {
        this.mid = mid;
    }
}