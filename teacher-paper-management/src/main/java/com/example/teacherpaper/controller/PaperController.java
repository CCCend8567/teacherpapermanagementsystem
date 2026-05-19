package com.example.teacherpaper.controller;

import com.example.teacherpaper.domain.entity.Paper;
import com.example.teacherpaper.service.PaperService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
@RequestMapping("/papers")
public class PaperController {

    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id, 
                                            HttpServletRequest request) {
        
        Optional<Paper> paperOpt = paperService.findById(id);
        
        if (paperOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Paper paper = paperOpt.get();
        Resource resource = paperService.getFileAsResource(paper.getFileUrl());
        
        // 设置响应头
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(
                    resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            contentType = "application/octet-stream";
        }
        
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        
        // 设置下载文件名为 论文标题+原始扩展名
        String fileName = paper.getFileName(); // 例如 123abc_论文名称.pdf
        String ext = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            ext = fileName.substring(dotIndex); // .pdf
        }
        String downloadFileName = paper.getTitle() + ext;
        String encodedFileName = downloadFileName;
        try {
            encodedFileName = URLEncoder.encode(downloadFileName, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            // 忽略编码异常，使用原始文件名
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + encodedFileName + "\"")
                .body(resource);
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        try {
            Optional<Paper> paperOpt = paperService.findById(id);
            System.out.println("查到的paper: " + paperOpt);
            if (paperOpt.isEmpty()) {
                model.addAttribute("errorMessage", "论文不存在或已被删除。");
                return "error";
            }
            model.addAttribute("paper", paperOpt.get());
            return "paper/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "获取论文信息时发生错误：" + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{id}/delete")
    public String deletePaper(@PathVariable Long id, Model model) {
        try {
            paperService.deleteById(id);
            return "redirect:/profile?tab=paper";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "删除论文失败：" + e.getMessage());
            return "error";
        }
    }
}