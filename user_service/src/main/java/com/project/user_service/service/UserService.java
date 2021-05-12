package com.project.user_service.service;

import com.project.user_service.model.User;
import com.project.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User insertUser(User newUser) {
    return userRepository.save(newUser);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
