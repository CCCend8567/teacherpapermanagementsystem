package com.example.teacherpaper.service.impl;

import com.example.teacherpaper.domain.entity.Teacher;
import com.example.teacherpaper.domain.entity.User;
import com.example.teacherpaper.domain.enums.RoleType;
import com.example.teacherpaper.dto.request.TeacherCreateRequest;
import com.example.teacherpaper.repository.UserRepository;
import com.example.teacherpaper.repository.TeacherRepository;
import com.example.teacherpaper.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TeacherRepository teacherRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void registerTeacher(TeacherCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(RoleType.ROLE_TEACHER);
        user = userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setId(user.getId());
        teacher.setName(request.getName());
        teacher.setIntro(request.getIntro());
        teacher.setDepartment(request.getDepartment());
        teacher.setSchool(request.getSchool());
        teacher.setUser(user);
        teacherRepository.save(teacher);
    }

    @Override
    public void registerTeacherWithRole(TeacherCreateRequest request, RoleType role) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(role);
        user = userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setId(user.getId());
        teacher.setName(request.getName());
        teacher.setIntro(request.getIntro());
        teacher.setDepartment(request.getDepartment());
        teacher.setSchool(request.getSchool());
        teacher.setUser(user);
        teacherRepository.save(teacher);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + username));
    }

    @Override
    public void registerAdminUser(TeacherCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(RoleType.ROLE_ADMIN);
        userRepository.save(user);
    }

    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("用户不存在: " + id));
    }

    @Override
    public java.util.List<User> findAllTeachers() {
        return userRepository.findByRole(com.example.teacherpaper.domain.enums.RoleType.ROLE_TEACHER);
    }

    @Override
    public java.util.List<User> searchTeachers(String keyword) {
        return userRepository.findByRoleAndUsernameContainingIgnoreCaseOrRoleAndEmailContainingIgnoreCase(
            com.example.teacherpaper.domain.enums.RoleType.ROLE_TEACHER, keyword,
            com.example.teacherpaper.domain.enums.RoleType.ROLE_TEACHER, keyword);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}