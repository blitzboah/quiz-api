package com.quiz.api.controller;

import com.quiz.api.dto.QuizSessionResultDTO;
import com.quiz.api.entity.Questions;
import com.quiz.api.entity.QuizSession;
import com.quiz.api.service.QuizService;
import com.quiz.api.service.QuizSessionService;
import com.quiz.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173",methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RequestMapping("/api")
public class QuizController {
    @Autowired private QuizService quizService;
    @Autowired private QuizSessionService quizSessionService;
    @Autowired private UserService userService;

    @GetMapping("/validate-user")
    public ResponseEntity<?> startQuiz(@RequestParam Long userId){
        if(userService.userExists(userId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/question")
    public ResponseEntity<Questions> question(){
        try {
            Questions question = quizService.getRandomQuestion();
            return ResponseEntity.ok(question);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswer(@RequestParam Long userId,@RequestParam int questionId,
                                          @RequestParam String answer){
        try {
            quizSessionService.submitAnswer(userId, questionId, answer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/results")
    public ResponseEntity<List<QuizSessionResultDTO>> getResults(@RequestParam Long userId){
        try {
            return ResponseEntity.ok(quizSessionService.getResults(userId));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSession(@RequestParam Long userId){
        try {
            quizSessionService.deleteSession(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
