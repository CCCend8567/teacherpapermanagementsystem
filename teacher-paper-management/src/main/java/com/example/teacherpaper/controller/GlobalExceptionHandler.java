package com.example.teacherpaper.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public String handleValidationException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "注册失败：参数错误或缺少必填项，请检查所有字段是否填写完整。");
        return "register";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, Model model) {
        model.addAttribute("errorMessage", "文件上传失败：文件大小超过限制（最大10MB）");
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        // 检查是否是文件相关的错误
        if (ex.getMessage().contains("文件大小") || ex.getMessage().contains("PDF格式")) {
            model.addAttribute("errorMessage", "文件上传失败：" + ex.getMessage());
            return "error";
        }
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleOtherException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
} 