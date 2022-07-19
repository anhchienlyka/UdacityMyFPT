package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.management.Notification;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @RequestMapping("/insert-file")
    @PostMapping()
    public String insertFile(Authentication authentication, MultipartFile fileUpload, RedirectAttributes redirectAttributes) throws IOException {

        String userName = authentication.getName();
        User user = userService.getUserByUserName(userName);
        Integer userId = user.getUserId();
        if (fileService.isExistedFile(fileUpload.getOriginalFilename(), userId)) {
            redirectAttributes.addFlashAttribute("message", " your file was Existed !");
            return "redirect:/home";
        }
        try {
            if (fileService.addFile(fileUpload, userId) > 0) {
                redirectAttributes.addFlashAttribute("message", "your file was uploaded!");
                return "redirect:/home";
            } else {
                redirectAttributes.addFlashAttribute("message", "Oops! your file was upload failure!");
                return "redirect:/home";
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Some error occurred in upload process! " + e.getMessage());
        }
        redirectAttributes.addFlashAttribute("message", "Oops! This file was uploaded!");
        return "redirect:/home";
    }


    @RequestMapping("/delete-file")
    public String deleteFile(@RequestParam("id") Integer fileId, RedirectAttributes redirectAttributes) {
        Notification notification;
        if (StringUtils.isEmpty(fileId) || !fileService.isExistedFile(fileId)) {
            redirectAttributes.addFlashAttribute("message", "File delete is invalid!");
            return "redirect:/home";
        }

        if (fileService.deleteFile(fileId) > 0) {
            redirectAttributes.addFlashAttribute("message", "file deleted successfully!");
            return "redirect:/home";
        }
        redirectAttributes.addFlashAttribute("message", "Oops! File was deleted failure!");
        return "redirect:/home";
    }

    @RequestMapping("/view-file")
    public String viewFile(@RequestParam("id") Integer fileId, RedirectAttributes redirectAttributes, HttpServletResponse response) {

        if(StringUtils.isEmpty(fileId) || !fileService.isExistedFile(fileId)) {
            redirectAttributes.addFlashAttribute("message", "File download is not existed!");
            return "redirect:/home";
        }
        File fileDownload = fileService.getFileById(fileId);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = "+fileDownload.getFileName();
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(fileDownload.getFileData());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Oops! Some error occurred when download processed!");
            return "redirect:/home";
        } finally {
            try {
                if(outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/home";
    }


}
