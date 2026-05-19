package com.example.teacherpaper.controller;

import com.example.teacherpaper.dto.request.LoginRequest;
import com.example.teacherpaper.dto.request.TeacherCreateRequest;
import com.example.teacherpaper.dto.response.ApiResponse;
import com.example.teacherpaper.dto.response.JwtResponse;
import com.example.teacherpaper.security.JwtUtils;
import com.example.teacherpaper.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, 
                         UserService userService, 
                         JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public String authenticateUser(@Valid LoginRequest loginRequest, HttpSession session) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 获取用户对象并放入session
        com.example.teacherpaper.domain.entity.User user = userService.findByUsername(loginRequest.getUsername());
        session.setAttribute("user", user);
        // 登录成功后重定向到首页
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute TeacherCreateRequest request, Model model) {
        try {
            if (userService.existsByUsername(request.getUsername())) {
                model.addAttribute("errorMessage", "用户名已存在");
                return "register";
            }
            if (userService.existsByEmail(request.getEmail())) {
                model.addAttribute("errorMessage", "邮箱已存在");
                return "register";
            }
            userService.registerTeacher(request);
            model.addAttribute("successMessage", "注册成功，请登录");
            return "login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "注册失败：" + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "用户名或密码错误，请重试。");
        }
        return "login";
    }

    @GetMapping("/admin-register")
    public String showAdminRegisterPage() {
        return "admin-register";
    }

    @PostMapping("/admin-register")
    public String registerAdmin(@ModelAttribute TeacherCreateRequest request, Model model) {
        try {
            if (userService.existsByUsername(request.getUsername())) {
                model.addAttribute("errorMessage", "用户名已存在");
                return "admin-register";
            }
            if (userService.existsByEmail(request.getEmail())) {
                model.addAttribute("errorMessage", "邮箱已存在");
                return "admin-register";
            }
            userService.registerAdminUser(request);
            model.addAttribute("successMessage", "管理员注册成功，请登录");
            return "login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "注册失败：" + e.getMessage());
            return "admin-register";
        }
    }
}