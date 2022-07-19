package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(int fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFileByUserId(int userId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize,userId,fileData) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int delete(int fileId);

    @Update("UPDATE FILES SET fileName=#{fileName}, contentType=#{contentType}, fileSize=#{fileSize}, userId=#{userId}, fileData=#{fileData} where fileId=#{fileId}")
    int update(File file);
    @Select("SELECT * FROM FILES WHERE userId = #{userId} AND fileName = #{fileName} ")
     File getFileByFileNameAndUserId(String fileName, Integer userId);
}
