package com.exam.recipeplatform.service.impl;

import com.exam.recipeplatform.exception.ResourceNotFoundException;
import com.exam.recipeplatform.model.entity.User;
import com.exam.recipeplatform.repository.UserRepository;
import com.exam.recipeplatform.service.UserService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        // Тук може да се добави допълнителна бизнес проверка (напр. за дублиран email)
        return userRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }
}