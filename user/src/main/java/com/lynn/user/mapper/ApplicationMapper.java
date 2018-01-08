package com.lynn.user.mapper;

import com.lynn.user.model.out.ApplicationOut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ApplicationMapper {

    @Select("select client_id,secret from application where client_id = #{clientId}")
    ApplicationOut findByClientId(@Param("clientId") String clientId);
}
