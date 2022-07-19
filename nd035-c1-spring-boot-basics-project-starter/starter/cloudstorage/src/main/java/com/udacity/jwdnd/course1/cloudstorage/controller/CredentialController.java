package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    public CredentialController(CredentialService credentialService,UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }



    @RequestMapping("/insert-credential")
    @PostMapping()
    public  String savePage(Authentication authentication, Credential credential , RedirectAttributes redirectAttributes){
        var userName = authentication.getName();
        User user = userService.getUserByUserName(userName);
        int userId = user.getUserId();

        if (this.userService.getUserByUserName(credential.getUserName())!=null){
            redirectAttributes.addFlashAttribute("message", "userName does existed");
            return "redirect:/home";
        }
        if (credential.getCredentialId()==null)
        {
            //create new
            if (this.credentialService.addCredential(credential,userId)>0){
                redirectAttributes.addFlashAttribute("message", "your credential was added!");
                return "redirect:/home";
            }

        }else{
            //update
            credential.setUserId(userId);
            if (this.credentialService.updateCredential(credential)>0){
                redirectAttributes.addFlashAttribute("message", "your credential was updated!");
                return "redirect:/home";
            }
        }

        return "redirect:/home";
    }
    @RequestMapping("/delete-credential")
    public String deleteCredential(@RequestParam("id") Integer credentialId, RedirectAttributes redirectAttributes){
        Credential credential = this.credentialService.getCredentialById(credentialId);
        if (credential==null){
            redirectAttributes.addFlashAttribute("message", "Credential does not existed");
            return "redirect:/home";
        }else{
            if (this.credentialService.deleteCredential(credentialId)>0){
                redirectAttributes.addFlashAttribute("message", "Delete credential success");
                return "redirect:/home";
            }
        }
        redirectAttributes.addFlashAttribute("message", "Delete credential fail");
        return "redirect:/home";
    }

}
