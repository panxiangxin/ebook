package com.example.ebook.model;

public class Post {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.title
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.gmt_create
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.gmt_modified
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.creator
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.comment_count
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Integer commentCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.like_count
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Integer likeCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.view_count
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Integer viewCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.tag
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String tag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.description
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.id
     *
     * @return the value of post.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.id
     *
     * @param id the value for post.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.title
     *
     * @return the value of post.title
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.title
     *
     * @param title the value for post.title
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.gmt_create
     *
     * @return the value of post.gmt_create
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.gmt_create
     *
     * @param gmtCreate the value for post.gmt_create
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.gmt_modified
     *
     * @return the value of post.gmt_modified
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.gmt_modified
     *
     * @param gmtModified the value for post.gmt_modified
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.creator
     *
     * @return the value of post.creator
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.creator
     *
     * @param creator the value for post.creator
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.comment_count
     *
     * @return the value of post.comment_count
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.comment_count
     *
     * @param commentCount the value for post.comment_count
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.like_count
     *
     * @return the value of post.like_count
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.like_count
     *
     * @param likeCount the value for post.like_count
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.view_count
     *
     * @return the value of post.view_count
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.view_count
     *
     * @param viewCount the value for post.view_count
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.tag
     *
     * @return the value of post.tag
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.tag
     *
     * @param tag the value for post.tag
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.description
     *
     * @return the value of post.description
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.description
     *
     * @param description the value for post.description
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}