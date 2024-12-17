package com.quiz.api.service;

import com.quiz.api.entity.Questions;
import com.quiz.api.repository.QuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    @Autowired
    private QuestionsRepo questionsRepo;

    public Questions getRandomQuestion() {
        return questionsRepo.findRandomQuestion();
    }

}
