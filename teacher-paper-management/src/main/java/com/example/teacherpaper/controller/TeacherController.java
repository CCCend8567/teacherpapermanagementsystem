package com.example.teacherpaper.controller;

import com.example.teacherpaper.domain.entity.Teacher;
import com.example.teacherpaper.dto.request.PaperUploadRequest;
import com.example.teacherpaper.service.PaperService;
import com.example.teacherpaper.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final PaperService paperService;

    public TeacherController(TeacherService teacherService, PaperService paperService) {
        this.teacherService = teacherService;
        this.paperService = paperService;
    }

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Teacher> teachers;
        if (keyword != null && !keyword.isEmpty()) {
            teachers = teacherService.searchByKeyword(keyword);
        } else {
            teachers = teacherService.findAll();
        }
        model.addAttribute("teachers", teachers);
        model.addAttribute("keyword", keyword);
        return "teacher/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        try {
            Optional<Teacher> teacherOpt = teacherService.findById(id);
            
            if (teacherOpt.isEmpty()) {
                model.addAttribute("errorMessage", "教师信息不存在或已被删除。");
                return "error";
            }
            
            Teacher teacher = teacherOpt.get();
            // 强制加载论文列表，避免懒加载问题
            if (teacher.getPapers() != null) {
                teacher.getPapers().size();
            }
            model.addAttribute("teacher", teacher);
            return "teacher/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "获取教师信息时发生错误：" + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/upload-paper")
    public String uploadPaper(@ModelAttribute PaperUploadRequest request,
                             RedirectAttributes redirectAttributes) {
        try {
            paperService.uploadPaper(
                    request.getFile(),
                    request.getTeacherId(),
                    request.getTitle(),
                    request.getDescription(),
                    request.getPublishDate()
            );
            
            redirectAttributes.addFlashAttribute("success", "论文上传成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "论文上传失败: " + e.getMessage());
        }
        
        return "redirect:/teachers/" + request.getTeacherId();
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teacher/create";
    }

    @PostMapping
    public String create(@ModelAttribute Teacher teacher, RedirectAttributes redirectAttributes) {
        try {
            teacherService.save(teacher);
            redirectAttributes.addFlashAttribute("success", "教师信息创建成功");
            return "redirect:/teachers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "教师信息创建失败: " + e.getMessage());
            return "redirect:/teachers/create";
        }
    }
}