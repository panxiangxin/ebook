package com.example.ebook.model;

public class StampOrderKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stamps_order.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stamps_order.user_id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stamps_order.id
     *
     * @return the value of stamps_order.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stamps_order.id
     *
     * @param id the value for stamps_order.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stamps_order.user_id
     *
     * @return the value of stamps_order.user_id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stamps_order.user_id
     *
     * @param userId the value for stamps_order.user_id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}