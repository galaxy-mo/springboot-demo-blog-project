package com.mohe.blog.web.admin;

import com.mohe.blog.po.Tag;
import com.mohe.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * tag controller
 *
 * @author mo
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService service;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model){ //给pageable对象赋予属性，5条一分页，根据id倒叙显示
        //将数据传递给前端,此处传递分页对象
        model.addAttribute("page", service.listTag(pageable));
        service.listTag(pageable);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String tagsInput(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        if (service.getTagByName(tag.getName()) != null){
            result.rejectValue("name", "nameError", "当前标签已存在");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = service.saveTag(tag);
        if (t == null){
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        if (service.getTagByName(tag.getName()) != null){
            result.rejectValue("name", "nameError", "当前标签已存在");
        }
        if (result.hasErrors()){    //如果name为空
            return "admin/tags-input";
        }
        Tag t = service.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 给列表里的编辑按钮给定api
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", service.getTag(id));
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes) {
        service.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
