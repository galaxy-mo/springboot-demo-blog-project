package com.mohe.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客的实体类
 *
 * @author mo
 */
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;
    private String title;               //博客标题

    @Basic(fetch = FetchType.LAZY)      //懒加载
    @Lob    //大字段类型，博客正文
    private String content;             //博客内容
    private String firstPicture;        //博客配图
    private String flag;                //博客标志（原创还是转载）
    private Integer views;              //博客访问次数
    private boolean appreciation;       //博客赞赏功能是否开启
    private boolean shareStatement;      //博客声明
    private boolean commentable;         //评论功能是否开启
    private boolean published;           //博客是否发布
    private boolean recommend;           //博客是否推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;             //博客发布时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;             //博客更新时间
    private String description;          //博客描述

    @ManyToOne
    private Type type;                   //多个分类可以对应一个博客

    @ManyToMany(cascade = {CascadeType.PERSIST})            //级联操作，新增博客是可以定义标签，标签会自动增加到数据库
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

//    @Transient      //注解该属性值非字段，不要将其注入数据库
//    private String tagIds;

    @Transient      //注解该属性值非字段，不要将其注入数据库
    private String tagNames;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    public void init() {
        this.tagNames = tagsToNames(this.getTags());
    }

    /**
     * 把标签对象转成标签的id，以方便前端的参数传值和校验
     * 例如： 1, 2, 3
     */
    private String tagsToNames(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                //最后一个不加,
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getName());
            }
            return ids.toString();
        } else {
            return tagNames;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagNames() {
        return tagNames;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentable=" + commentable +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", tagNames='" + tagNames + '\'' +
                ", comments=" + comments +
                '}';
    }
}
