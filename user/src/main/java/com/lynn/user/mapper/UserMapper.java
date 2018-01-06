package com.lynn.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select openid from oauth_user where mobile = #{mobile} and password = #{password}")
    String findOpenidByMobileAndPassword(@Param("mobile") String mobile,@Param("password") String password);
}
