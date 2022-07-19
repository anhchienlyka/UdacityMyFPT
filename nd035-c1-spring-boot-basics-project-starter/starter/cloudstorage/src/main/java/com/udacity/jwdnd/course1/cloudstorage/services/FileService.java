package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    public int addFile(MultipartFile file, int userId) throws IOException {
        File fileInput = new File();

        fileInput.setFileName(file.getOriginalFilename());
        fileInput.setContentType(file.getContentType());
        fileInput.setFileSize(String.valueOf(file.getSize()));
        fileInput.setFileData(file.getBytes());
        fileInput.setUserId(userId);
        return fileMapper.insert(fileInput);

    }

    public File getFileById(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public List<File> getFileListingByUserId(int userId) {
        return fileMapper.getFileByUserId(userId);
    }

    public int updateFile(File file) {
        return fileMapper.update(file);
    }

    public int deleteFile(int fileId) {
        return fileMapper.delete(fileId);
    }

    public boolean isExistedFile(String originalFilename, Integer userId) {
        return fileMapper.getFileByFileNameAndUserId(originalFilename, userId) != null;
    }
    public boolean isExistedFile(int fileId) {
        return fileMapper.getFile(fileId) != null;
    }
}
