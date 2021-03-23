package com.mohe.blog.dao;

import com.mohe.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 博客分类实例dao层
 *
 * @author mo
 */
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);

    @Query("select t from Type t")      //自定义一些jpql语句
    List<Type> findTop(Pageable pageable);
}
