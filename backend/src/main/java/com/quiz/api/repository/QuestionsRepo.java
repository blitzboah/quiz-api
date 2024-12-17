package com.quiz.api.repository;

import com.quiz.api.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionsRepo extends JpaRepository<Questions, Integer> {
    @Query(value = "SELECT * from Questions ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Questions findRandomQuestion();

    @Query("SELECT q.correctAnswer FROM Questions q WHERE q.id = :questionId")
    Optional<String> getCorrectAnswer(@Param("questionId") int questionId);
}
