package com.example.ebook.model;

public class Chapter {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chapter.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chapter.book_id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long bookId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chapter.chapter_name
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String chapterName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chapter.number
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private Long number;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chapter.chapter_content
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    private String chapterContent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chapter.id
     *
     * @return the value of chapter.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chapter.id
     *
     * @param id the value for chapter.id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chapter.book_id
     *
     * @return the value of chapter.book_id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chapter.book_id
     *
     * @param bookId the value for chapter.book_id
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chapter.chapter_name
     *
     * @return the value of chapter.chapter_name
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getChapterName() {
        return chapterName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chapter.chapter_name
     *
     * @param chapterName the value for chapter.chapter_name
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName == null ? null : chapterName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chapter.number
     *
     * @return the value of chapter.number
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public Long getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chapter.number
     *
     * @param number the value for chapter.number
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chapter.chapter_content
     *
     * @return the value of chapter.chapter_content
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public String getChapterContent() {
        return chapterContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chapter.chapter_content
     *
     * @param chapterContent the value for chapter.chapter_content
     *
     * @mbg.generated Tue Mar 24 16:13:05 CST 2020
     */
    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent == null ? null : chapterContent.trim();
    }
}