package com.example.teacherpaper.service;

import com.example.teacherpaper.domain.entity.User;
import com.example.teacherpaper.dto.request.TeacherCreateRequest;

public interface UserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void registerTeacher(TeacherCreateRequest request);

    void registerTeacherWithRole(TeacherCreateRequest request, com.example.teacherpaper.domain.enums.RoleType role);

    User findByUsername(String username);

    void registerAdminUser(TeacherCreateRequest request);

    String encodePassword(String rawPassword);

    void save(User user);

    User findById(Long id);

    java.util.List<User> findAllTeachers();

    java.util.List<User> searchTeachers(String keyword);

    void deleteById(Long id);
}