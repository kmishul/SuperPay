package com.project.user_service.controller;

import com.project.user_service.exception.UserNotFoundException;
import com.project.user_service.model.User;
import com.project.user_service.service.UserService;
import com.project.user_service.util.UserValidator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class UserController {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private UserService userService;

    // Find
    @ApiOperation(value = "Find all the Users")
    @GetMapping("/users")
    List<User> findAll() {
        return userService.findAll();
    }

    //Create a new user
    @ApiOperation(value = "Register New User")
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@RequestBody User newUser) throws Exception {
        if(UserValidator.isValidUser(newUser))
            return userService.insertUser(newUser);
        else throw new Exception();
    }

    // Find a given user
    @ApiOperation(value = "Find User by Id ")
    @GetMapping("/users/{id}")
    User findOne(@PathVariable int id) {
        LOGGER.info("/users/{id} called with id "+ id);
        return userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
