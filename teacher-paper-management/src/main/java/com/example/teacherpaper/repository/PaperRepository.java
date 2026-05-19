package com.example.teacherpaper.repository;

import com.example.teacherpaper.domain.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {

    List<Paper> findByTeacherId(Long teacherId);

    @Query("SELECT p FROM Paper p LEFT JOIN FETCH p.teacher WHERE p.id = :id")
    Optional<Paper> findByIdWithTeacher(Long id);

    @Query("SELECT p FROM Paper p LEFT JOIN p.teacher t LEFT JOIN t.user u WHERE " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Paper> searchAll(String keyword);
}