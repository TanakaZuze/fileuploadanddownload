package com.fileuploadanddownload.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.Files.copy;



@RestController
public class FileResource {
//    define a location
    public static final String DIRECTORY=System.getProperty("user.home") +"Downloads/uploads";

//    method for uploading files
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles) throws IOException {
        List<String> fileNames=new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String files= StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage =get(DIRECTORY,files).toAbsolutePath().normalize();
            copy(file.getInputStream(),fileStorage,REPLACE_EXISTING);
            fileNames.add(files);
        }
        return ResponseEntity.ok().body(fileNames);
    }
}
