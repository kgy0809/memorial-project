package com.memorial.mapper;

import com.memorial.pojo.Memorial;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/15 17:05
 */
public interface MemorialMapper extends Mapper<Memorial> {

    @Select("SELECT\n" +
            " m.* \n" +
            "FROM\n" +
            " memorial_ancestral_details m \n" +
            "WHERE\n" +
            " LEAST( m.trend1, m.trend2 ) != '' \n" +
            " AND LEAST( m.trend1, m.trend2 ) != '不详' \n" +
            " AND date_format( LEAST( m.trend1, m.trend2 ), '%m-%d' ) >= #{time1} \n" +
            " AND date_format( LEAST( m.trend1, m.trend2 ), '%m-%d' ) <= #{time2} \n" +
            "ORDER BY\n" +
            " LEAST( m.trend1, m.trend2 )")
    List<Memorial> selectByExampleCustomize(@Param("time1") String time1,@Param("time2") String time2);

    @Select("SELECT\n" +
            " m.* \n" +
            "FROM\n" +
            " memorial_ancestral_details m \n" +
            "WHERE\n" +
            " LEAST( m.trend1, m.trend2 ) != '' \n" +
            " AND LEAST( m.trend1, m.trend2 ) != '不详' \n" +
            " AND date_format( LEAST( m.trend1, m.trend2 ), '%m-%d' ) >= #{time1} \n" +
            " or date_format( LEAST( m.trend1, m.trend2 ), '%m-%d' ) <= #{time2} \n" +
            "ORDER BY\n" +
            " LEAST( m.trend1, m.trend2 )")
    List<Memorial> selectByExampleCustomizeYear(@Param("time1") String time1,@Param("time2") String time2);

    @Select("SELECT\n" +
            " m.* \n" +
            "FROM\n" +
            " memorial_ancestral_details m \n" +
            "WHERE\n" +
            " LEAST( m.trend1, m.trend2 ) != '' \n" +
            " AND LEAST( m.trend1, m.trend2 ) != '不详' \n" +
            " AND date_format( LEAST( m.trend1, m.trend2 ), '%m-%d' ) >= #{time1} \n" +
            " or date_format( LEAST( m.trend1, m.trend2 ), '%m-%d' ) <= #{time2} \n" +
            "ORDER BY\n" +
            " LEAST( m.trend1, m.trend2 )")
    List<Memorial> selectByExampleCustomizeAllYear(@Param("time1") String time1,@Param("time2") String time2);
}
