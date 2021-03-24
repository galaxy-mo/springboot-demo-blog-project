package com.mohe.blog.web;

import com.mohe.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 文章展示页面controller层
 *
 * @author mo
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        //拿到年份与所有博客
        model.addAttribute("archiveMap", blogService.archiveBlog());
        //拿到博客总数
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }
}
