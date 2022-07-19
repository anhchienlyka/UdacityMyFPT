package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(int noteId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNoteByUerId(int userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int delete(int noteId);

    @Update("UPDATE NOTES SET noteTitle=#{noteTitle}, noteDescription=#{noteDescription}, userId=#{userId} where noteId=#{noteId}")
    int update(Note note);
}
