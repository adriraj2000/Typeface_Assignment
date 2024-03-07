package com.example.Typeface.entities;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class File
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private LocalDateTime createdAt;
    private Long size;
    private String fileType;
    @Lob
    private byte[] fileData;
}