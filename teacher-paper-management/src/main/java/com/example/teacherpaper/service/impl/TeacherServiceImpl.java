package com.example.teacherpaper.service.impl;

import com.example.teacherpaper.domain.entity.Teacher;
import com.example.teacherpaper.repository.TeacherRepository;
import com.example.teacherpaper.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> searchByKeyword(String keyword) {
        return teacherRepository.findByNameContainingIgnoreCaseOrSchoolContainingIgnoreCase(keyword, keyword);
    }
}