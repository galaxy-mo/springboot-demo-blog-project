package com.mohe.blog.web;

import com.mohe.blog.po.User;
import com.mohe.blog.po.Comment;
import com.mohe.blog.service.BlogService;
import com.mohe.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class commentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null){
            //当前有用户登录
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
