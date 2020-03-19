package com.example.ebook.mapper;

import com.example.ebook.model.BookOrder;
import com.example.ebook.model.BookOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BookOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    long countByExample(BookOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    int deleteByExample(BookOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    int insert(BookOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    int insertSelective(BookOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    List<BookOrder> selectByExampleWithRowbounds(BookOrderExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    List<BookOrder> selectByExample(BookOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    BookOrder selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    int updateByExampleSelective(@Param("record") BookOrder record, @Param("example") BookOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    int updateByExample(@Param("record") BookOrder record, @Param("example") BookOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    int updateByPrimaryKeySelective(BookOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_order
     *
     * @mbg.generated Thu Mar 19 16:34:36 CST 2020
     */
    int updateByPrimaryKey(BookOrder record);
}