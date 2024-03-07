package com.example.Typeface.service;

import com.example.Typeface.entities.File;
import com.example.Typeface.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;

    public ResponseEntity<String> uploadFile(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return new ResponseEntity<>("Please provide a file", HttpStatus.BAD_REQUEST);
        }
        File newFile = new File();
        newFile.setFileName(file.getOriginalFilename());
        newFile.setCreatedAt(LocalDateTime.now());
        newFile.setSize(file.getSize());
        newFile.setFileType(file.getContentType());
        newFile.setFileData(file.getBytes());
        return new ResponseEntity<>(fileRepository.save(newFile).getId().toString(),HttpStatus.OK);

    }

    public ResponseEntity<String> deleteFile(Long id) {
        if(fileRepository.existsById(id)){
            fileRepository.deleteById(id);
            return new ResponseEntity<>("Deleted file: " + id, HttpStatus.OK);
        }
        else return new ResponseEntity<>("File with id : " + id + " does not exist", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<File>> getAllFiles(){
        return new ResponseEntity<>(fileRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<String> updateFile(Long fileId, MultipartFile newFile) {
        File file = fileRepository.findById(fileId).orElse(null);
        if(file != null){
            file.setFileName(newFile.getOriginalFilename());
            file.setFileType(newFile.getContentType());
            file.setSize(newFile.getSize());
            fileRepository.save(file);
            return new ResponseEntity<>("Updated file " + fileId, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("File with id : " + fileId + " does not exist", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<byte[]> readFileById(Long fileId) {
        Optional<File> optionalFile = fileRepository.findById(fileId);
        if (optionalFile.isPresent()) {
            File file = optionalFile.get();
            return new ResponseEntity<>(file.getFileData(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
