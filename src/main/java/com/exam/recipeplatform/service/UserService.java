package com.exam.recipeplatform.service;

import com.exam.recipeplatform.model.entity.User;
import java.util.UUID;

public interface UserService {
    User registerUser(User user);
    User findById(UUID id);
}