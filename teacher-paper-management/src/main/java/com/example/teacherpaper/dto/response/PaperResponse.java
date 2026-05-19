package com.example.teacherpaper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PaperResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime publishDate;
    private String fileName;
    private long fileSize;
    private String fileType;
}