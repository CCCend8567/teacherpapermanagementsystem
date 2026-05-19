package com.example.teacherpaper.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaperUploadRequest {

    @NotNull
    private MultipartFile file;

    @NotNull
    private Long teacherId;

    @NotBlank
    private String title;

    private String description;

    private String publishDate;
}