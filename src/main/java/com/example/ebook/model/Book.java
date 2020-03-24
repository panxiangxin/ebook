package com.example.ebook.model;

public class Book {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.book_name
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String bookName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.img_url
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String imgUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.author
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String author;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.tags
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String tags;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.book_url
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String bookUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.book_stamps
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Double bookStamps;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.book_size
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long bookSize;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.date
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long date;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.bio
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String bio;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.id
     *
     * @return the value of book.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.id
     *
     * @param id the value for book.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.book_name
     *
     * @return the value of book.book_name
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.book_name
     *
     * @param bookName the value for book.book_name
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.img_url
     *
     * @return the value of book.img_url
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.img_url
     *
     * @param imgUrl the value for book.img_url
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.author
     *
     * @return the value of book.author
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getAuthor() {
        return author;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.author
     *
     * @param author the value for book.author
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.tags
     *
     * @return the value of book.tags
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getTags() {
        return tags;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.tags
     *
     * @param tags the value for book.tags
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.book_url
     *
     * @return the value of book.book_url
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getBookUrl() {
        return bookUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.book_url
     *
     * @param bookUrl the value for book.book_url
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl == null ? null : bookUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.book_stamps
     *
     * @return the value of book.book_stamps
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Double getBookStamps() {
        return bookStamps;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.book_stamps
     *
     * @param bookStamps the value for book.book_stamps
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setBookStamps(Double bookStamps) {
        this.bookStamps = bookStamps;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.book_size
     *
     * @return the value of book.book_size
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getBookSize() {
        return bookSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.book_size
     *
     * @param bookSize the value for book.book_size
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setBookSize(Long bookSize) {
        this.bookSize = bookSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.date
     *
     * @return the value of book.date
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.date
     *
     * @param date the value for book.date
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setDate(Long date) {
        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.bio
     *
     * @return the value of book.bio
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getBio() {
        return bio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.bio
     *
     * @param bio the value for book.bio
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setBio(String bio) {
        this.bio = bio == null ? null : bio.trim();
    }
}