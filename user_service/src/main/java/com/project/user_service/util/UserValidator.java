package com.project.user_service.util;

import com.project.user_service.model.User;

public class UserValidator {

    public static boolean isValidUser(User user) {
        if(user.getEmail()==null || user.getEmail()=="" || user.getName()==null || user.getName()=="" || user.getMobile()==null || user.getMobile()=="")
            return false;
        else return true;
    }
}

