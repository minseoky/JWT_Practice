package com.example.jwtpractice.common.repository;

import com.example.jwtpractice.common.entity.UserEntity;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(name = "user_table")
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

}
