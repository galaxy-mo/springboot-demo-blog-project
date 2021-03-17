package com.mohe.blog.web;

import com.mohe.blog.service.TagService;
import com.mohe.blog.po.Tag;
import com.mohe.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowControlller {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id,
                        @PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
            Pageable pageable, Model model){
        //拿到所有的分类
        List<Tag> tags = tagService.listTagTop(100);
        //如果是导航栏点击产生的跳转则默认跳转到第一个
        if (id == -1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        //将id回传给前端让这个id的分类为选中状态
        model.addAttribute("activetagId", id);
        return "tags";
    }
}
