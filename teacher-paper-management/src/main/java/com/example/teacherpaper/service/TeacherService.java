package com.example.teacherpaper.service;

import com.example.teacherpaper.domain.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    List<Teacher> findAll();

    Optional<Teacher> findById(Long id);

    Teacher save(Teacher teacher);

    List<Teacher> searchByKeyword(String keyword);
}