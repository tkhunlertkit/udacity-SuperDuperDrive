package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.security.Principal;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/file/**")
public class FileController {

    @Autowired private FileService fileService;

    @PostMapping("/file/upload")
    public RedirectView uploadFile(@RequestParam("fileUpload") MultipartFile file, Principal principal) {
        fileService.store(file, principal.getName());
        return new RedirectView("/home#nav-files");
    }

    @GetMapping("/file/{fileid:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileid) {
        File f = fileService.getFile(fileid);
        ByteArrayResource res = new ByteArrayResource(f.getFiledata());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getFilename() + "\"")
            .contentLength(Long.parseLong(f.getFilesize()))
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(res);
    }

    @GetMapping("/file/delete/{fileid:.+}")
    public RedirectView deleteFile(@PathVariable Integer fileid) {
        this.fileService.deleteFile(fileid);
        return new RedirectView("/home#nav-files");
    }
}