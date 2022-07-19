package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteMapper noteMapper;
    public int addNote(Note note,int userId){
        note.setUserId(userId);
        return noteMapper.insert(note);
    }

    public Note getNoteById(int noteId){return noteMapper.getNote(noteId);}
    public List<Note> getNoteByUserId(int userId){return noteMapper.getNoteByUerId(userId);}

    public int updateNote(Note note){return noteMapper.update(note);}

    public int deleteNote(int noteId){return noteMapper.delete(noteId); }
}
