package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {


    private FileService fileService;
    private NoteService  noteService;
    private UserService userService;
    private CredentialService credentialService;
    public HomeController(FileService fileService, UserService userService,NoteService noteService,CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService  = credentialService;
    }

    @GetMapping
    @RequestMapping("/home")
    public String homePage(Authentication authentication, Model model) {
        String userName = authentication.getName();
        User user = this.userService.getUserByUserName(userName);
        Integer userId = user.getUserId();
        List<File> files = this.fileService.getFileListingByUserId(userId);
        model.addAttribute("listFile", files);
        List<Note>  notes = this.noteService.getNoteByUserId(userId);
        model.addAttribute("listNote", notes);
        List<Credential>   credentials = this.credentialService.getCredentialByUserId(userId);

        model.addAttribute("listCredential", credentials);
        return "home";
    }



}
