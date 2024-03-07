package com.example.Typeface.controller;

import com.example.Typeface.entities.File;
import com.example.Typeface.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.uploadFile(file);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> readFile(@PathVariable Long fileId) {
        return fileService.readFileById(fileId);
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<String> updateFile(@PathVariable Long fileId, @RequestParam("file") MultipartFile file) {
        return fileService.updateFile(fileId, file);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId) {
        return fileService.deleteFile(fileId);
    }

    @GetMapping
    public ResponseEntity<List<File>> getAllFiles(){
        return fileService.getAllFiles();
    }
}