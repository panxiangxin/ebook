package com.example.ebook.model;

public class Announcement {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column announcement.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column announcement.title
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column announcement.gmt_create
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column announcement.content
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column announcement.id
     *
     * @return the value of announcement.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column announcement.id
     *
     * @param id the value for announcement.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column announcement.title
     *
     * @return the value of announcement.title
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column announcement.title
     *
     * @param title the value for announcement.title
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column announcement.gmt_create
     *
     * @return the value of announcement.gmt_create
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column announcement.gmt_create
     *
     * @param gmtCreate the value for announcement.gmt_create
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column announcement.content
     *
     * @return the value of announcement.content
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column announcement.content
     *
     * @param content the value for announcement.content
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}