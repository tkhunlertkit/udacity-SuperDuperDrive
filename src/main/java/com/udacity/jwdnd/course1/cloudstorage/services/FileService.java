package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired private FileMapper fileMapper;
    @Autowired private UserService userService;

    public boolean store(MultipartFile file, String username) {
        byte[] filedata;
        try {
            List<String> fileNames = fileMapper.getFileNames(userService.getUserId(username));
            if (fileNames.contains(file.getOriginalFilename())) return false;
            filedata = file.getBytes();
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();
            String filesize = Long.toString(file.getSize());
            Integer userid = userService.getUserId(username);

            File fileDb = new File(null, filename, contentType, filesize, userid, filedata);
            fileMapper.storeFile(fileDb);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<File> getAllFiles(String username) {
        return fileMapper.getAllFiles(userService.getUserId(username));
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }
}
