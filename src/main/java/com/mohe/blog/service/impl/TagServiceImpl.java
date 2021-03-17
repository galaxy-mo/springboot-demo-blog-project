package com.mohe.blog.service.impl;

import com.mohe.blog.service.TagService;
import com.mohe.blog.NotFoundException;
import com.mohe.blog.dao.TagRepository;
import com.mohe.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findById(id).get();
        if (t == null){
            throw new NotFoundException("标签不存在");
        }
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String names) {
        List<String> list = convertToList(names);
        List<Long> longs = new ArrayList<>();
        for (String name : list) {
            Tag tag = tagRepository.findByName(name);
            if (tag == null){
                Tag t = new Tag();
                t.setName(name);
                tagRepository.save(t);
                longs.add(t.getId());
            } else {
                longs.add(tag.getId());
            }
        }
        return tagRepository.findAllById(longs);
    }

    private List<String> convertToList(String names){
        List<String> list = new ArrayList<>();
        if (!"".equals(names) && names !=null){
            String[] idArray = names.split(",");
            for (int i = 0; i < idArray.length; i++) {
                list.add(new String(idArray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
