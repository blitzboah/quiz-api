package com.quiz.api.repository;

import com.quiz.api.entity.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSessionRepo extends JpaRepository<QuizSession, Long> {
    List<QuizSession> findByUserId(Long userId);
}
