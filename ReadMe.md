# springboot入门项目

> 之前大二学springboot时在b站上随便跟做的入门项目（一个博客系统），代码逻辑非常简单，今天整理磁盘文件夹的时候发现了这个项目的源码文件夹我没丢，顺手放在GitHub上吧。希望能给初学springboot的同学们一点参考。（当时还是初学所以愣是一点代码规范的意识都没有，不要吐槽我hhh，以后有时间会重构的）

## 简介

- 此博客系统是学习springboot和springdataJPA之后拿来练手的小项目，代码逻辑比较简单。希望能给初学springboot或者做类似项目的同学们一点参考。
- 技术栈：
    - 前端：
      - semantic-ui框架
      - jquery
      - thymeleaf模板渲染
    - 后端：
      - spring boot
      - spring data JPA
      - mysql
- 后台管理界面没有做登录按钮，直接访问admin路径即可

## 基本功能点
- 集成markdown格式文章编写，后端有对应的markdown渲染处理，文章目前仅支持markdown格式文章。
- 文章支持评论区的关闭开启，支持分类标签管理。
- 前端页面采用栅格系统组合页面布局，完美适配移动端的响应。
- 全局日志统一管理

## 使用IntelliJ IDEA本地运行项目步骤:

* `clone`项目源码到本地
* 在本地数据库新建一个名为blog的数据库（本项目采用jpa作为ORM层框架，
所以新建数据库后运行项目时即可创建数据库结构，故源码中未提供sql语句）
* 使用IDEA打开项目文件夹
* 使用Maven下载相关依赖
* 在"Project Structure"中设置"Project SDK"为1.8，"Project language level"为8-Lambdas...
* 运行`mvn clean`命令清除maven编译记录，然后`Build-->Build Project`本地编译
* 点击BlogApplication.java，右键点击"Run BlogApplication..."，日志信息无错误即成功运行

ps：因为作者距离写这个项目的时间比较久了，是最近整理文件夹的时候才发现所以顺手上传一份到GitHub上来。**所以还请提issue的同学还请将问题描述的详细些**。（虽然这些代码基本不会有什么复杂逻辑，全是CRUD，hhh，本人当时也刚刚接触springboot，以后有机会了会把这个项目重构一下多加点功能进去）。

## 当前项目重构计划（代办）
* 完善各类注释信息
* 修改整理代码风格