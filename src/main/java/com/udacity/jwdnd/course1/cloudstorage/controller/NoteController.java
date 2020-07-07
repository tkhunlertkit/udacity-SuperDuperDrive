package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.security.Principal;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/note/**")
public class NoteController {

    @Autowired private NoteService noteService;

    @PostMapping("/note")
    public RedirectView addNotes(@ModelAttribute Note note, Principal principal) {
        noteService.insertNote(note, principal.getName());
        return new RedirectView("/home#nav-notes");
    }

    @GetMapping("/note/delete/{noteId:.+}")
    public RedirectView deleteNote(@PathVariable Integer noteId) {
        noteService.deleteNote(noteId);
        return new RedirectView("/home#nav-notes");
    }
}