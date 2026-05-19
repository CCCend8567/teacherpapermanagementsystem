package com.example.teacherpaper.controller;

import com.example.teacherpaper.domain.entity.User;
import com.example.teacherpaper.domain.entity.Teacher;
import com.example.teacherpaper.service.UserService;
import com.example.teacherpaper.service.TeacherService;
import com.example.teacherpaper.service.PaperService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {
    private final UserService userService;
    private final TeacherService teacherService;
    private final PaperService paperService;

    public ProfileController(UserService userService, TeacherService teacherService, PaperService paperService) {
        this.userService = userService;
        this.teacherService = teacherService;
        this.paperService = paperService;
    }

    @GetMapping("/profile")
    public String profile(@RequestParam(value = "tab", required = false) String tab, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                String username = auth.getName();
                User user = userService.findByUsername(username);
                if (user != null) {
                    userId = user.getId();
                    session.setAttribute("userId", userId);
                } else {
                    return "redirect:/login";
                }
            } else {
                return "redirect:/login";
            }
        }
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("activeTab", tab);
        // 如果是教师且tab为info、edit或upload，查找teacher信息
        if (tab != null && (tab.equals("info") || tab.equals("edit") || tab.equals("upload")) && user.getRole() != null && user.getRole().name().equals("ROLE_TEACHER")) {
            try {
                Teacher teacher = teacherService.findById(user.getId()).orElse(null);
                if (teacher != null) {
                    model.addAttribute("teacher", teacher);
                } else {
                    model.addAttribute("errorMessage", "教师信息不存在，请联系管理员。");
                    return "error";
                }
            } catch (Exception e) {
                model.addAttribute("errorMessage", "获取教师信息时发生错误：" + e.getMessage());
                return "error";
            }
        }
        // 管理员教师用户管理
        if (tab != null && tab.equals("user") && user.getRole() != null && user.getRole().name().equals("ROLE_ADMIN")) {
            String keyword = null;
            // 获取搜索关键字
            Object keywordObj = null;
            try {
                keywordObj = ((org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.getRequestAttributes()).getRequest().getParameter("keyword");
            } catch (Exception ignore) {}
            if (keywordObj != null) keyword = keywordObj.toString();
            java.util.List<User> teacherUsers;
            if (keyword != null && !keyword.isEmpty()) {
                teacherUsers = userService.searchTeachers(keyword);
            } else {
                teacherUsers = userService.findAllTeachers();
            }
            model.addAttribute("teacherUsers", teacherUsers);
            model.addAttribute("keyword", keyword);
        }
        // 管理员论文管理
        if (tab != null && tab.equals("paper") && user.getRole() != null && user.getRole().name().equals("ROLE_ADMIN")) {
            String paperKeyword = null;
            Object paperKeywordObj = null;
            try {
                paperKeywordObj = ((org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.getRequestAttributes()).getRequest().getParameter("keyword");
            } catch (Exception ignore) {}
            if (paperKeywordObj != null) paperKeyword = paperKeywordObj.toString();
            if (paperKeyword != null && !paperKeyword.isEmpty()) {
                model.addAttribute("allPapers", paperService.searchAll(paperKeyword));
            } else {
                model.addAttribute("allPapers", paperService.findAll());
            }
            model.addAttribute("paperKeyword", paperKeyword);
        }
        return "profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam Long teacherId,
                              @RequestParam(required = false) String password,
                              @RequestParam String email,
                              @RequestParam String school,
                              @RequestParam String department,
                              @RequestParam String intro,
                              HttpSession session,
                              Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        User user = userService.findById(userId);
        // 修改邮箱
        user.setEmail(email);
        // 修改密码（如有填写）
        if (password != null && !password.isEmpty()) {
            user.setPassword(userService.encodePassword(password));
        }
        // 修改教师信息
        Teacher teacher = user.getTeacher();
        if (teacher != null) {
            teacher.setSchool(school);
            teacher.setDepartment(department);
            teacher.setIntro(intro);
        }
        user.setTeacher(teacher);
        userService.save(user); // 级联保存teacher
        model.addAttribute("successMessage", "修改成功！");
        return "redirect:/profile?tab=edit";
    }

    @PostMapping("/admin/users/{id}/reset-password")
    public String resetTeacherPassword(@PathVariable Long id, HttpSession session, Model model) {
        User user = userService.findById(id);
        user.setPassword(userService.encodePassword("123456"));
        userService.save(user);
        session.setAttribute("resetPasswordSuccess", "密码已重置为123456，请通知用户及时修改密码。");
        return "redirect:/profile?tab=user";
    }

    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, HttpSession session, Model model) {
        try {
            userService.deleteById(id);
            session.setAttribute("deleteUserSuccess", "用户已成功删除。");
            return "redirect:/profile?tab=user";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "删除用户失败：" + e.getMessage());
            return "error";
        }
    }
} 