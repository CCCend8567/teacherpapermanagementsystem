package com.example.teacherpaper.service.impl;

import com.example.teacherpaper.domain.entity.Paper;
import com.example.teacherpaper.domain.entity.Teacher;
import com.example.teacherpaper.repository.PaperRepository;
import com.example.teacherpaper.repository.TeacherRepository;
import com.example.teacherpaper.service.PaperService;
import com.example.teacherpaper.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaperServiceImpl implements PaperService {

    private static final Logger logger = LoggerFactory.getLogger(PaperServiceImpl.class);

    private final PaperRepository paperRepository;
    private final TeacherRepository teacherRepository;
    private final FileUtil fileUtil;

    public PaperServiceImpl(PaperRepository paperRepository, 
                           TeacherRepository teacherRepository, 
                           FileUtil fileUtil) {
        this.paperRepository = paperRepository;
        this.teacherRepository = teacherRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Paper uploadPaper(MultipartFile file, Long teacherId, String title, String description, String publishDate) throws IOException {
        // 验证文件大小（10MB = 10 * 1024 * 1024 bytes）
        long maxFileSize = 10 * 1024 * 1024; // 10MB
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("文件大小不能超过10MB，当前文件大小: " + 
                String.format("%.2f", file.getSize() / (1024.0 * 1024.0)) + "MB");
        }
        
        // 验证文件类型
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("只支持上传PDF格式的文件");
        }
        
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("教师不存在，ID: " + teacherId));

        String fileUrl = fileUtil.storeFile(file);

        Paper paper = new Paper();
        paper.setTitle(title);
        paper.setDescription(description);
        // 解析yyyy-MM格式为LocalDateTime的1号0点
        if (publishDate != null && !publishDate.isEmpty()) {
            try {
                java.time.YearMonth ym = java.time.YearMonth.parse(publishDate);
                paper.setPublishDate(ym.atDay(1).atStartOfDay());
            } catch (Exception e) {
                paper.setPublishDate(java.time.LocalDateTime.now());
            }
        } else {
            paper.setPublishDate(java.time.LocalDateTime.now());
        }
        paper.setFileUrl(fileUrl);
        paper.setFileName(file.getOriginalFilename());
        paper.setFileSize(file.getSize());
        paper.setFileType(file.getContentType());
        paper.setTeacher(teacher);

        teacher.getPapers().add(paper);
        return paperRepository.save(paper);
    }

    @Override
    public List<Paper> findByTeacherId(Long teacherId) {
        return paperRepository.findByTeacherId(teacherId);
    }

    @Override
    public Optional<Paper> findById(Long id) {
        return paperRepository.findByIdWithTeacher(id);
    }

    @Override
    public org.springframework.core.io.Resource getFileAsResource(String fileUrl) {
        try {
            return fileUtil.loadFileAsResource(fileUrl);
        } catch (Exception e) {
            logger.error("获取文件失败: {}", fileUrl, e);
            throw new RuntimeException("获取文件失败", e);
        }
    }

    @Override
    public List<Paper> findAll() {
        return paperRepository.findAll();
    }

    @Override
    public List<Paper> searchAll(String keyword) {
        return paperRepository.searchAll(keyword);
    }

    @Override
    public void deleteById(Long id) {
        paperRepository.deleteById(id);
    }
}