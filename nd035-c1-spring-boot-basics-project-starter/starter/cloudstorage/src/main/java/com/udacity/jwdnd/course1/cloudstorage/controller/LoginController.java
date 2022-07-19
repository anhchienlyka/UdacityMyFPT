package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class LoginController {



    @GetMapping()
    @RequestMapping("/login")
    public String loginView(@ModelAttribute("user") User user) {
        return "login";
    }

    @GetMapping()
    @RequestMapping("/logout")
    public String logout() {
        return "login";
    }
}
