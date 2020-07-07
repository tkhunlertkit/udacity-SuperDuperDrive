package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.security.Principal;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired private NoteService noteService;
    @Autowired private FileService fileService;
    @Autowired private CredentialService credentialService;
    @Autowired private EncryptionService encryptionService;

    @GetMapping()
    public String HomeView() {
        return "home";
    }

    @ModelAttribute("allNotes")
    public List<Note> getAllNotes(Principal principal) {
        return noteService.getAllNotes(principal.getName());
    }

    @ModelAttribute("allFiles")
    public List<File> getAllFiles(Principal principal) {
        return this.fileService.getAllFiles(principal.getName());
    }

    @ModelAttribute("allCredentials")
    public List<Credential> getAllCredentials(Principal principal) {
        List<Credential> credentials = this.credentialService.getAllCredentials(principal.getName());
        credentials.forEach(c -> {
            c.setDecryptedPassword(encryptionService.decryptValue(c.getPassword(), c.getKey()));
        });
        return credentials;
    }
}
