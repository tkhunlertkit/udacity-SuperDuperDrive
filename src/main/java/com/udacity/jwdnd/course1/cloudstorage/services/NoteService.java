package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired private NoteMapper noteMapper;
    @Autowired private UserService userService;

    public void insertNote(Note note, String username) {
        // if note already exist
        note.setUserid(userService.getUserId(username));
        if (note.getNoteid() == null)
            noteMapper.insertNote(note);
        else
            noteMapper.updateNote(note);
    }

    public List<Note> getAllNotes(String username) {
        return this.noteMapper.getAllNotes(userService.getUserId(username));
    }

    public void deleteNote(Integer noteId) {
        this.noteMapper.deleteNote(noteId);
    }
}