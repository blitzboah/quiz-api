package com.quiz.api.service;

import com.quiz.api.dto.QuizSessionResultDTO;
import com.quiz.api.entity.Questions;
import com.quiz.api.entity.QuizSession;
import com.quiz.api.repository.QuestionsRepo;
import com.quiz.api.repository.QuizSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizSessionService {
    @Autowired private QuizSessionRepo quizSessionRepo;
    @Autowired private QuestionsRepo questionsRepo;

    //save the answer to session repo
    public void submitAnswer(Long userId, int questionId, String answer){

        Questions question = questionsRepo.findById(questionId).orElseThrow( () -> new RuntimeException("question not found"));
        String correctAnswer = question.getCorrectAnswer();
        boolean isRight = correctAnswer.equals(answer);

        QuizSession submittedAnswer = new QuizSession();
        submittedAnswer.setUserId(userId);
        submittedAnswer.setQuestionId(questionId);
        submittedAnswer.setCorrect(isRight);
        submittedAnswer.setUserAnswer(answer);

        System.out.println("saved");
        //save the submitted answer in the repo
        quizSessionRepo.save(submittedAnswer);
    }

    //returns necessary data for results
    public List<QuizSessionResultDTO> getResults(Long userId){
       List<QuizSession> results = quizSessionRepo.findByUserId(userId);

        System.out.println("Total results found for user " + userId + ": " + results.size());
       return results.stream().map(quizSession -> {
           Questions questions = questionsRepo.findById(quizSession.getQuestionId())
                   .orElseThrow(() -> new RuntimeException("question not found"));
           return new QuizSessionResultDTO(
                   quizSession.getUserId(),
                   quizSession.getQuestionId(),
                   questions.getQuestion(),
                   quizSession.getUserAnswer(),
                   quizSession.isCorrect()
           );
       }).collect(Collectors.toList());
    }

    //deletes every que mapped with user id
    public void deleteSession(Long userId){
        List<QuizSession> quizSession = quizSessionRepo.findByUserId(userId);
        for(QuizSession q: quizSession){
            quizSessionRepo.delete(q);
        }
    }
}
