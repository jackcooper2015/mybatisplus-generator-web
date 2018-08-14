# mybatisplus-generator-web
基于mybatisplus的web代码生成工具

##项目特色
- 该项目运行无需任何数据库链接
- 数据源配置记录在属性文件中，项目重启也不会丢失
- 基于mybatispluls的代码工具编写，同公司人员无需在本机运行main方法，可以方便统一使用该web项目
- 漂亮的登录页面（amaze云适配）

##数据库支持
* mysql
* oracle

##登录信息
* 用户名：admin
* 密  码：admin


##使用说明
1. 从doc目录找到jar文件，运行命令 nohup java -jar code-*.jar &
![image.png](https://upload-images.jianshu.io/upload_images/3710706-23fbd71878ec41fe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2. 访问ip:8088
![image.png](https://upload-images.jianshu.io/upload_images/3710706-6cec5c85b906637c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2. 登录之后默认进入数据库列表页面，如果没有你需要的，请新增数据源，测试并保存
![image.png](https://upload-images.jianshu.io/upload_images/3710706-617d979cd469b4b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

4. 找到你表所在的数据库，点击“选中”，此时可以看到你所有的表信息
![image.png](https://upload-images.jianshu.io/upload_images/3710706-54390840abcc4dfb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

6. 选则你需要生成的表
7. 添加包名、字段注释后点击保存（写库，不会丢失，下次可以继续使用）
![image.png](https://upload-images.jianshu.io/upload_images/3710706-4c5a74f4ecba782f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

8. 点击生成代码
![image.png](https://upload-images.jianshu.io/upload_images/3710706-a3ed843e9ec73e15.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

---
作者相关：
    
    简书：https://www.jianshu.com/u/8523e601fa4e
    
    github: https://github.com/jackcooper2015