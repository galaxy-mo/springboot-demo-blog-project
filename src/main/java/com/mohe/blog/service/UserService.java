package com.mohe.blog.service;

import com.mohe.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
