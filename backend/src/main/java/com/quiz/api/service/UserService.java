package com.quiz.api.service;

import com.quiz.api.entity.Users;
import com.quiz.api.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired private UserRepo userRepo;

    public boolean userExists(Long userId){
        return userRepo.findById(userId).isPresent();
    }
}
