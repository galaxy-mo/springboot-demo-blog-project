package com.mohe.blog.service.impl;

import com.mohe.blog.NotFoundException;
import com.mohe.blog.po.Blog;
import com.mohe.blog.service.BlogService;
import com.mohe.blog.utils.MyBeanUtils;
import com.mohe.blog.vo.BlogQuery;
import com.mohe.blog.dao.BlogRepository;
import com.mohe.blog.po.Type;
import com.mohe.blog.utils.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * 博客实体业务类
 *
 * @author mo
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository repository;

    @Override
    public Blog getBlog(Long id) {

        return repository.findById(id).get();
    }

    /**
     * 博客的查询和渲染
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = repository.findById(id).get();
        if (blog == null){
            throw new NotFoundException("该博客不存在");
        }
        /*这里利用一个代理对象进行markdown到HTML的转换，
            如若不这样处理直接用blog处理那么程序会操作数据库而修改blog的内容*/
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        repository.updateViews(id);
        return b;
    }

    /**
     * 查询所有的博客总数
     *
     * @return
     */
    @Override
    public Long countBlog() {
        return repository.count();
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return repository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    //模糊匹配查询
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null){
                    //相等查询
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    predicates.add(cb.equal(root.get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return repository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return repository.findByQuery(query, pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        //先拿所有年份
        List<String> years = repository.findGroupYear();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        //再根据年份拿博客
        for (String year : years) {
            map.put(year, repository.findByYear(year));
        }
        return map;
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return repository.findTop(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){
            //新增博客
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            //博客有id，说明操作是修改博客
            blog.setUpdateTime(new Date());
        }

        return repository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = repository.findById(id).get();
        if (b == null){
            throw new NotFoundException("该博客不存在");
        }
        //过滤掉属性值为空的属性，将blog对象里面有值的属性copy到b
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return repository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        repository.deleteById(id);
    }
}
