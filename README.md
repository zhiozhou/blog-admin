# 介绍
scaffold-admin是一个管理端脚手架，前端使用layui进行渲染
# 套用
1.执行tables.sql创建基础数据表
2. yml文件维护数据库信息
```
spring:
  datasource:
    username: 
    password: 
    url: jdbc:sqlserver://127.0.0.1:3306;DatabaseName=test_server
    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
```
3. 启动项目，登陆管理员账号 zhou/111
4. 利用系统管理中的模块生成，快速创建mvc代码进行构建

