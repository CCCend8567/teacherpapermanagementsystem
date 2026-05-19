package com.example.teacherpaper.repository;

import com.example.teacherpaper.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    // 查询所有教师用户
    List<User> findByRole(com.example.teacherpaper.domain.enums.RoleType role);

    // 按用户名或邮箱模糊搜索教师用户
    List<User> findByRoleAndUsernameContainingIgnoreCaseOrRoleAndEmailContainingIgnoreCase(com.example.teacherpaper.domain.enums.RoleType role1, String username, com.example.teacherpaper.domain.enums.RoleType role2, String email);
}