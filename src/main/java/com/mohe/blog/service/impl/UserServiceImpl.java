package com.mohe.blog.service.impl;

import com.mohe.blog.dao.UserRepository;
import com.mohe.blog.po.User;
import com.mohe.blog.service.UserService;
import com.mohe.blog.utils.MD5utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5utils.code(password));
        return user;
    }
}
