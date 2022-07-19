package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredential(int credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, userName, key,password,userId) VALUES(#{url}, #{userName}, #{key}, #{password},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int delete(int credentialId);
    //@Update("UPDATE NOTES SET noteTitle=#{noteTitle}, noteDescription=#{noteDescription}, userId=#{userId} where noteId=#{noteId}")
    @Update("UPDATE CREDENTIALS SET url=#{url}, userName=#{userName} ,key=#{key}, password=#{password}, userId=#{userId} where credentialId=#{credentialId}")
    int update(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getListCredential(int userId);
}
