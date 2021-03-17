package com.mohe.blog.web;

import com.mohe.blog.vo.BlogQuery;
import com.mohe.blog.po.Type;
import com.mohe.blog.service.BlogService;
import com.mohe.blog.service.TypeService;
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
public class TypeShowControlller {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,
                        @PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
            Pageable pageable, Model model){
        //拿到所有的分类
        List<Type> types = typeService.listTypeTop(100);
        //如果是导航栏点击产生的跳转则默认跳转到第一个
        if (id == -1){
            id = types.get(0).getId();
        }
        BlogQuery query = new BlogQuery();
        query.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, query));
        //将id回传给前端让这个id的分类为选中状态
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
