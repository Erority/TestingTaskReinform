package com.example.testbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testbot.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}
