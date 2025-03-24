## 系统模块

~~~
cn.staitech     
├── staitech-ui              // 前端框架 [80]
├── staitech-gateway         // 网关模块 [8080]
├── staitech-auth            // 认证中心 [9200]
├── staitech-api             // 接口模块
│       └── staitech-api-system                          // 系统接口
├── staitech-common          // 通用模块
│       └── staitech-common-core                         // 核心模块
│       └── staitech-common-datascope                    // 权限范围
│       └── staitech-common-datasource                   // 多数据源
│       └── staitech-common-log                          // 日志记录
│       └── staitech-common-redis                        // 缓存服务
│       └── staitech-common-security                     // 安全模块
│       └── staitech-common-swagger                      // 系统接口
├── staitech-modules         // 业务模块
│       └── staitech-system                              // 系统模块 [9201]
│       └── staitech-gen                                 // 代码生成 [9202]
│       └── staitech-job                                 // 定时任务 [9203]
│       └── staitech-file                                // 文件服务 [9300]
│       └── staitech-openslide                           // 文件服务 [9990]
├── staitech-visual          // 图形化管理模块
│       └── staitech-visual-monitor                      // 监控中心 [9100]
├──pom.xml                // 公共依赖
~~~


## 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 在线构建器：拖动表单元素生成相应的HTML代码。
17. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。


admin/admin123

网关：
http://127.0.0.1:8080/

重启Nacos
sudo systemctl start nacos.service

防火墙相关
https://blog.csdn.net/weixin_45869725/article/details/114670943

重启防火墙
sudo service firewalld restart

防火墙开放以下端口
sudo firewall-cmd --zone=public --add-port=80/tcp --permanent
sudo firewall-cmd --zone=public --add-port=8080/tcp --permanent
sudo firewall-cmd --zone=public --add-port=8848/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9848/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9849/tcp --permanent
sudo firewall-cmd --zone=public --add-port=6379/tcp --permanent
sudo firewall-cmd --zone=public --add-port=3306/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9100/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9200/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9201/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9202/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9203/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9300/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9000/tcp --permanent
sudo firewall-cmd --zone=public --add-port=6379/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9300/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9990/tcp --permanent
sudo firewall-cmd --zone=public --add-port=3306/tcp --permanent
sudo firewall-cmd --zone=public --add-port=3307/tcp --permanent
sudo firewall-cmd --zone=public --add-port=1514/tcp --permanent
sudo firewall-cmd --zone=public --add-port=5000/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9002/tcp --permanent
sudo firewall-cmd --zone=public --add-port=9003/tcp --permanent
sudo firewall-cmd --zone=public --add-port=15672/tcp --permanent
sudo firewall-cmd --zone=public --add-port=5672/tcp --permanent


查看开放端口
`sudo firewall-cmd --permanent --list-port`



##在IDEA上使用git命令删除GitHub上的文件或文件夹
https://blog.csdn.net/weixin_45789266/article/details/122753411

```shell
在IDEA的终端输入如下代码：

# 删除名字为.idea的文件夹
git rm -r --cached .idea

# 提交到git
git commit -m '删除.idea文件夹'

# 推送到GitHub
git push -u origin master

# 删除所有以sql为后缀的文件
git rm -r --cached *.sql

# 提交到git
git commit -m '删除所有以sql为后缀的文件'

# 推送到GitHub
git push -u origin master

# 删除名为ONE.iml的文件
git rm -r --cached ONE.iml

# 提交到git
git commit -m '删除名为ONE.iml的文件'

# 推送到GitHub
git push -u origin master
```


docker启动rabbitmq
```shell

docker pull 192.168.52.112/library/rabbitmq:3-managemen
 

docker run -d --hostname localhost --name rabbitmq -p 15672:15672 -p 5672:5672  192.168.52.112/library/rabbitmq:3-management

docker run -d --hostname rabbit --name rabbit -p 15672:15672 -p 5672:5672 


docker run -d --hostname localhost --name rabbitmq -p 15672:15672 -p 5672:5672  rabbitmq:latest

rabbitmq-plugins enable rabbitmq_management

```
