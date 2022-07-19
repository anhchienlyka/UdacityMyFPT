package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @PostMapping()
    @RequestMapping("/insert-note")
    public String addNote(Authentication authentication,  Note note, RedirectAttributes redirectAttributes, Model model) {
        String userName = authentication.getName();
        User user = userService.getUserByUserName(userName);
        int userId = user.getUserId();

        if (StringUtils.isEmpty(note.getNoteTitle())) {
            redirectAttributes.addFlashAttribute("message", "tile is Empty");
            return "redirect:/home";
        }

        if (note.getNoteId()!=null){
            //update
            note.setUserId(userId);
            if (noteService.updateNote(note) > 0) {
                redirectAttributes.addFlashAttribute("message", "update Note Success");
                return "redirect:/home";
            }
        }else{
            //create
            if (noteService.addNote(note, userId) > 0) {
                redirectAttributes.addFlashAttribute("message", "add Note Success");
                return "redirect:/home";
            }
        }
        redirectAttributes.addFlashAttribute("message", "add Note Fail");
        return "redirect:/home";
    }

    @RequestMapping("/delete-note")
    public String deleteFile(@RequestParam("id") Integer noteId, RedirectAttributes redirectAttributes) {
        if (this.noteService.getNoteById(noteId)==null){
            redirectAttributes.addFlashAttribute("message", "Note does not exist");
            return "redirect:/home";
        }
        if (this.noteService.deleteNote(noteId)>0){
            redirectAttributes.addFlashAttribute("message", "Note is Deleted");
            return "redirect:/home";
        }
        redirectAttributes.addFlashAttribute("message", "delete Note Fail");
        return "redirect:/home";

    }
}
