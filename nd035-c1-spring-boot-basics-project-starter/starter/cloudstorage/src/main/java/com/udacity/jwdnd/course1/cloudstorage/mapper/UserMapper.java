package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE userName = #{userName}")
    User getUserByUserName(String userName);

    @Select("SELECT * FROM USERS WHERE userName = #{userName}")
    User isUsernameAvailable(String userName);
    @Select("SELECT * FROM USERS WHERE userId = #{userId}")
    User getUserById(int userId);
    @Insert("INSERT INTO USERS (userName, salt, password, firstName, lastName) VALUES(#{userName}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);

    @Delete("DELETE FROM USERS WHERE userId = #{userId}")
    int delete(int userid);

    @Update("UPDATE USERS SET userName=#{userName}, salt=#{salt}, password=#{password},firstName = #{firstName},lastName = #{lastName} where userId=#{userId}")
    public int update(User user);
}
