package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TodoItem;
import com.example.demo.entity.User;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findByUser(User user);
}
