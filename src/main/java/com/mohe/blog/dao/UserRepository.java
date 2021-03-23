package com.mohe.blog.dao;

import com.mohe.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户实例dao层
 *
 * @author mo
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}
