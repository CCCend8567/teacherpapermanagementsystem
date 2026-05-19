package com.example.teacherpaper.service;

import com.example.teacherpaper.domain.entity.Paper;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PaperService {

    Paper uploadPaper(MultipartFile file, Long teacherId, String title, String description, String publishDate) throws IOException;

    List<Paper> findByTeacherId(Long teacherId);

    Optional<Paper> findById(Long id);

    Resource getFileAsResource(String fileUrl);

    List<Paper> findAll();

    List<Paper> searchAll(String keyword);

    void deleteById(Long id);
}