/*
 Navicat Premium Data Transfer

 Source Server         : local_conn
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 127.0.0.1:3306
 Source Schema         : test_db

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 08/03/2021 02:32:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog_access_log
-- ----------------------------
DROP TABLE IF EXISTS `blog_access_log`;
CREATE TABLE `blog_access_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主机',
  `mfrs` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '厂商',
  `dev` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设备',
  `os` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
  `api` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求接口',
  `param` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
  `ua` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户代理',
  `visitor_id` int(11) NULL DEFAULT NULL COMMENT '访客id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '访问日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_access_log
-- ----------------------------

-- ----------------------------
-- Table structure for blog_block
-- ----------------------------
DROP TABLE IF EXISTS `blog_block`;
CREATE TABLE `blog_block`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型',
  `reason` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原因',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_freed` datetime(0) NULL DEFAULT NULL COMMENT '释放时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_index`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1806 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '阻塞' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_block
-- ----------------------------
INSERT INTO `blog_block` VALUES (1805, '127.0.0.1', 0, '灌水', '119', 1000, '2020-09-22 10:39:13', NULL);

-- ----------------------------
-- Table structure for blog_blog
-- ----------------------------
DROP TABLE IF EXISTS `blog_blog`;
CREATE TABLE `blog_blog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `preview` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预览',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `abs` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '摘要',
  `pv` bigint(20) NULL DEFAULT NULL COMMENT '页面访问量',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_by` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(255) NULL DEFAULT NULL COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_index`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_blog
-- ----------------------------
INSERT INTO `blog_blog` VALUES (38, '11', '111', '', 0, '', '111', 0, 1000, '2020-09-16 09:11:30', NULL, '2020-09-16 09:32:47', NULL);
INSERT INTO `blog_blog` VALUES (39, 'Ubuntu安装', '不是第一次装Ubuntu，但有些配置不常用就忘了，导致坑踩了一遍又一遍。这次长记性的我把配置过程记下来，防止再次入坑。Ubuntu的安装可以参考[18.04安装教程](https://blog.csdn.net/baidu_36602427/article/details/86548203)讲得很清楚\n\n# 更换软件源\n\nUbuntu官方源在国外，国内访问很慢，甚至有的可能被墙。所以需要把软件源更换为国内的镜像源\n\n在`sources.list`配置阿里云镜像源，修改前先备份\n\n``` bash\nsudo cp /etc/apt/sources.list /etc/apt/sources.list.bak\nsudo gedit /etc/apt/sources.list\n```\n\n内容清空，粘贴阿里云镜像源\n\n``` \ndeb http://mirrors.aliyun.com/ubuntu/ bionic main restricted universe multiverse\ndeb-src http://mirrors.aliyun.com/ubuntu/ bionic main restricted universe multiversesou\ndeb http://mirrors.aliyun.com/ubuntu/ bionic-security main restricted universe multiverse\ndeb-src http://mirrors.aliyun.com/ubuntu/ bionic-security main restricted universe multiverse\ndeb http://mirrors.aliyun.com/ubuntu/ bionic-updates main restricted universe multiverse\ndeb-src http://mirrors.aliyun.com/ubuntu/ bionic-updates main restricted universe multiverse\ndeb http://mirrors.aliyun.com/ubuntu/ bionic-backports main restricted universe multiverse\ndeb-src http://mirrors.aliyun.com/ubuntu/ bionic-backports main restricted universe multiverse\n```\n\n同步软件源信息并更新\n\n``` bash\nsudo apt update && sudo apt upgrade\n```\n\n# 安装常用软件\n\n## 搜狗输入法\n\n搜狗输入法依赖于`Fcitx`，嗖先装`Fcitx`\n\n``` bash\nsudo apt install fcitx\n```\n\n打开语言支持，如需安装完整语言支持则安装\n\n![安装完整语言支持](https://zhousb.cn/upload/zhousb-admin/sougou1.png)\n\n将键盘输入法系统改为`Fcitx`\n\n![键盘输入法系统改为Fcitx](https://zhousb.cn/upload/zhousb-admin/sougou2.png)\n\n[进入搜狗输入法官网](https://pinyin.sogou.com/linux/)下载相应安装包后，使用`dpkg`进行安装\n\n``` bash\nsudo dpkg -i sogoupinyin_2.3.1.0112_amd64.deb\n```\n\n如出现依赖错误执行\n\n```bash\nsudo apt --fix-broken install\n```\n\n安装完成后重启系统\n\n``` bash\nreboot\n```\n\n打开 Fictx配置，点击加号添加`搜狗拼音`和`键盘-英语`\n\n![添加搜狗拼音和键盘-英语](https://zhousb.cn/upload/zhousb-admin/sougou3.png)\n\n## Chrome\n\n[进入Chrome官网](https://www.google.cn/chrome/)下载相应安装包后，使用`dpkg`进行安装\n\n``` bash\nsudo dpkg -i google-chrome-stable_current_amd64.deb\n```\n\n有了新的删了旧的，卸载预装的火狐浏览器\n\n``` bash\nsudo apt purge lfirefox?\n```\n\n## Java\n\n[进入Oracle官网](https://www.oracle.com/java/technologies/javase-downloads.html)下载相应的压缩包解压\n\n``` bash\ntar -zxvf jdk-11.0.7_linux-x64_bin.tar.gz\n```\n\n将解压后的文件移动到`/usr/local/`下（可能之后还会安`8`，所以外面包裹了一层）\n\n``` bash\nsudo mkdir /usr/local/jdk && sudo mv jdk-11.0.7 /usr/local/jdk/11\n```\n\n配置环境变量，配置前先备份\n\n``` bash\nsudo cp /etc/profile /etc/profile.bak\nsudo gedit /etc/profile\n```\n\n在底部追加Java环境配置，我下载的是11，根据下载的版本自行选择或修改\n\n``` \n# java 11\nexport JAVA_HOME=/usr/local/jdk/11\nexport PATH=$JAVA_HOME/bin:$PATH\n```\n\n``` \n# java 8\nexport JAVA_HOME=/usr/local/jdk/8\nexport JRE_HOME=$JAVA_HOME/jre\nexport CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH\nexport PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$PATH\n```\n\n保存后，使修改的环境在当前Bash生效（profile文件会在注销重登后才对所有用户生效）\n``` bash\nsource /etc/profile\n```\n检查Java环境\n``` bash\njava -version\n```\n\n修改`/etc/profile`后重启，如果出现循环登录或黑屏无法进桌面说明`profile`文件存在错误，`crt+alt+2 `进入命令行，手动导入命令\n\n``` bash\nexport PATH=/usr/bin:/usr/sbin:/bin:/sbin:/usr/X11R6/bin,\n```\n\n还原备份（没备份的使用vim将配置的环境变量删除即可）\n\n```bash\nsudo mv  /etc/profile.bak /etc/profile\n```\n\n## Maven\n\n[进入Maven官网](http://maven.apache.org/download.cgi)下载相应压缩包解压\n\n``` bash\ntar -zxvf apache-maven-3.6.3-bin.tar.gz\n```\n\n将解压后的文件移动`/usr/local`下\n\n```bash\nsudo mv apache-maven-3.6.3 /usr/local/maven\n```\n\n配置环境变量\n\n```bash\nsudo gedit /etc/profile\n```\n\n在底部追加Maven环境配置\n\n``` bash\nexport MAVEN_HOME=/usr/local/maven\nexport PATH=${PATH}:${MAVEN_HOME}/bin\n```\n\n保存后，使修改的环境在当前Bash生效\n\n``` bash\nsource /etc/profile\n```\n\n检查Maven环境\n\n``` bash\nmvn -v\n```\n\n创建Maven仓库目录\n\n```bash\nmkdir /usr/local/maven/repository\n```\n\n修改Maven配置文件\n\n```bash\ngedit /usr/local/maven/config/setting.xml\n```\n\n`localRepository`标签内指定本地仓库路径\n\n``` xml\n<!--本地仓库 -->\n<localRepository>/usr/local/maven/repository</localRepository>\n```\n\n## IntelliJ IDEA\n\n[进入**IntelliJ**官网](https://www.jetbrains.com/idea/download/other.html)下载相应的tar包后，进行解压缩（我下载的是`2020.1.2`版本，过高可能破解不了）\n\n``` bash\ntar -zxvf ideaIU-2020.1.2.tar.gz\n```\n\n将解压后的文件移动到`/opt`下\n\n```bash\nsudo mv idea-IU-201.7846.76/ /opt/idea\n```\n\n测试启动Idea\n\n```\n/opt/idea/bin/idea.sh\n```\n\n[下载Jetbrains-Agent破解工具](https://pan.baidu.com/s/1x0MG2CPucWOcIS_khe8n0g )，提取码为: g64m，按`Readme`文件流程破解\n\n### 性能优化\n\nIntelliJ IDEA 本身是个 Java 应用，所以运行在JVM上。默认配置IDEA虚拟机内存给的太小了，运行多个项目跟拖拉机一样，而且现在电脑内存动不动就8G、16G的，完全可以把IDEA的虚拟机内存提高，以此获得丝滑流畅\n\n修改配置文件（32位的修改`idea.properties`，64位改`idea64.properties`，不确定都改了也成）\n\n``` bash\ngedit /opt/idea/bin/idea64.vmoptions\n```\n\n配置参数的作用（参数是IDEA虚拟机的配置，不是项目运行）\n\n- **Xms**：初始内存大小，可提高程序启动速度\n- **Xmx**：最大内存大小，可减少内存回收频率，提高性能\n- **ReservedCodeCacheSize**：代码缓存区，JIT编译的代码都放代码缓存区。当缓存区空间不足时，JIT无法继续编译，会去进行优化，比如编译执行改为解释执行，性能则会降低\n\n将对应配置修改为（根据自身内存大小修改，我是16G内存）\n\n``` bash\n-server\n-Xms1024m\n-Xmx2048m\n-XX:ReservedCodeCacheSize=1024m\n```\n\n## Git\n\n使用`apt`安装git\n\n```bash\nsudo apt install git\n```\n\n配置全局用户名与邮箱\n\n```bash\ngit config --global user.name \"你的名字\"\ngit config --global user.email \"你的邮箱\"\n```\n\n配置记住密码\n\n```bash\ngit config --global credential.helper store\n```\n\n检查配置列表\n\n```bash\ngit config --list\n```\n\n## NodeJS\n\n[进入NodeJS官网]( http://nodejs.cn/)下载相应压缩包后，进行解压缩\n\n``` bash\ntar -xvf node-v10.15.3-linux-x64.tar.xz\n```\n\n将解压后的文件移动到`/usr/local/`下\n\n```bash\nsudo mv node-v12.18.2-linux-x64/ /usr/local/nodejs\n```\n\n配置环境变量，配置前先备份\n\n```bash\nsudo cp /etc/profile /etc/profile.bak\nsudo vim /etc/profile\n```\n\n底部追加Node环境配置\n\n```bash\nexport NODE_HOME=/usr/local/nodejs\nexport NODE_PATH=$NODE_HOME/lib/node_modules\nexport PATH=$PATH:$NODE_HOME/bin\n```\n\n保存后，使修改的环境在当前Bash生效\n\n``` bash\nsource /etc/profile\n```\n\n检查Node环境\n\n```bash\nnode -v\n```\n\nnpm设置淘宝镜像\n\n``` bash\nnpm config set registry https://registry.npm.taobao.org\n```\n\n设置全局模块路径\n\n``` bash\nmkdir /usr/local/nodejs/node_global\nnpm config set prefix \"/usr/local/nodejs/node_global\"\n```\n\n设置缓存路径\n\n``` bash\nmkdir /usr/local/nodejs/node_cache\nnpm config set cache \"/usr/local/nodejs/node_cache\"\n```\n\n检查npm配置\n\n```bash\nnpm config list\n```\n\n安装yran\n\n```bash\nnpm install yarn -g\n```\n\n在node环境中创建yarn软连接命令，使其可以全局使用\n\n```bash\ncd /usr/local/nodejs/bin\nln -s  ../node_global/lib/node_modules/yarn/bin/yarn.js yarn\n```\n\n检查yarn环境\n\n```bash\nyarn -v \n```\n\n## Redis\n\n安装Redis服务\n\n```bash\nsudo apt install redis-server\n```\n\n检查Redis服务状态\n\n```bash\nservice redis status\n```\n\n配置redis密码，`requirepass foobared`一行取消注释，将`foobared`改为你要设置的密码\n\n```bash\nsudo gedit /etc/redis/redis.conf\n```\n\n### 客户端\n\n`Another-Redis-Desktop-Manager`是我用起来比较好使的Redis客户端，界面也很友好。\n\n[进入官网](https://github.com/qishibo/AnotherRedisDesktopManager/releases)下载`.AppImage`软件包，赋予执行权限\n\n``` bash\nchmod +x Another-Redis-Desktop-Manager.1.3.7.AppImage\n```\n\n将软件包移动到`/opt`下\n\n```bash\nsudo mkdir /opt/redis-manager && sudo mv Another-Redis-Desktop-Manager.1.3.7.AppImage /opt/redis-manager/\n```\n\n启动测试\n\n``` bash\n./opt/redis-manager/Another-Redis-Desktop-Manager.1.3.7.AppImage\n```\n\n在用户共享应用中创建快捷方式\n\n``` bash\nsudo vim /usr/share/applications/redis-desktop-manager.desktop\n```\n\n```\n[Desktop Entry]\nType=Application\nName=Redis Desktop Manager\nIcon=/opt/redis-manager/logo.png\nExec=/opt/redis-manager/Another-Redis-Desktop-Manager.1.3.7.AppImage\nCategories=Development;\n```\n\n## MySql\n\n[进入MySql官网](https://dev.mysql.com/downloads/repo/apt/)下载相应配置包后，使用`dpkg`安装配置\n\n``` bash\nsudo dpkg -i mysql-apt-config_0.8.15-1_all.deb \n```\n\n配置包会添加Mysql软件源，所以先同步软件源信息\n\n``` bash\nsudo apt update\n```\n\n安装Mysql服务\n\n```bash\nsudo apt install mysql-server\n```\n\n查看Mysql服务状态\n\n```bash\nservice mysql status\n```\n\n连接Mysql服务\n\n```bash\nmysql -u root -p\n```\n\n当使用Navicat进行连接时会报权限错误，因为只在本地开发使用，所以直接赋予权限\n\n``` bash\nsudo chmod go+rx /var/lib/mysql/\n```\n\n## Navicat\n\n创建临时目录用于存储要用的破解工具和安装包，进入该目录\n\n```bash\nmkdir tmp\ncd tmp\n```\n\n[点击下载相关工具](https://pan.baidu.com/s/1u01pL0Fz7A0L1sfvU6_ZHg)，提取码为：jjtr\n\n- **navicat15-premium-cs.AppImage**：Navicat 15 premium 简体中文试用版\n- **navicat-patcher**：补丁工具\n- **navicat-keygen** ：注册机\n- **appimagetool-x86_64.AppImage**：Linux 独立运行软件打包工具\n\n安装capstone\n\n``` bash\nsudo apt install libcapstone-dev\n```\n\n安装rapidjson\n\n```bash\nsudo apt-get install rapidjson-dev\n```\n\n安装Keystone，拉取源代码\n\n``` bash\ngit clone https://github.com/keystone-engine/keystone.git\n```\n\n编译源代码并安装\n\n``` bash\nmkdir keystone/build && cd keystone/build\n../make-share.sh\nsudo make install\n```\n\n如编译出现`No CMAKE_CXX_COMPILER could be found`错误，安装g++编译器\n\n``` bash\nsudo apt install g++\n```\n\n安装出现`g++ : Depends: g++-7 (>= 7.4.0-1~) but it is not going to be installed`的版本错误，是因为软件源信息不匹配，换回官方源再安装就可以了。配置时我对官方源进行了备份，这里直接备份还原就可以了；如果没备份复制下面的源信息复制粘贴\n\n```bash\nsudo mv /etc/apt/sources.list /etc/apt/sources.list.home.bak\nsudo mv /etc/apt/sources.list.bak /etc/apt/sources.lis\n```\n\n```\n# ubuntu软件源 20版本\ndeb http://cn.archive.ubuntu.com/ubuntu/ focal main restricted\ndeb http://cn.archive.ubuntu.com/ubuntu/ focal-updates main restricted\ndeb http://cn.archive.ubuntu.com/ubuntu/ focal universe\ndeb http://cn.archive.ubuntu.com/ubuntu/ focal-updates universe\ndeb http://cn.archive.ubuntu.com/ubuntu/ focal multiverse\ndeb http://cn.archive.ubuntu.com/ubuntu/ focal-updates multiverse\ndeb http://cn.archive.ubuntu.com/ubuntu/ focal-backports main restricted universe multiverse\ndeb http://security.ubuntu.com/ubuntu focal-security main restricted\ndeb http://security.ubuntu.com/ubuntu focal-security universe\ndeb http://security.ubuntu.com/ubuntu focal-security multiverse\n```\n\n同步软件源并更新后，安装g++编译器，再进行编译安装即可\n\n```bash\nsudo apt update && sudo apt upgrade\nsudo apt install g++\n```\n\n回到tmp目录下，将下载好的工具包赋予执行权限\n\n``` bash\nchmod +x navicat-patcher navicat-keygen appimagetool-x86_64.AppImage\n```\n\n解包navicat软件\n\n``` bash\nmkdir navicat15\nsudo mount -o loop navicat15-premium-cs.AppImage navicat15\n```\n\n给解包的文件打上补丁，运行后会在当前目录生产一个`RegPrivateKey.pem`文件\n\n```bash\ncp -r navicat15 navicat15-patched\n./navicat-patcher navicat15-patched\n```\n\n将有补丁的文件打包成运行软件并运行\n\n``` bash\n./appimagetool-x86_64.AppImage navicat15-patched navicat15-premium-cs-pathed.AppImage\nchmod +x navicat15-premium-cs-pathed.AppImage\n./navicat15-premium-cs-pathed.AppImage\n```\n\n**关闭网络**后运行注册机\n\n``` bash\n./navicat-keygen --text ./RegPrivateKey.pem\n```\n\n**选择产品类型**：1\n\n```bash\n[*] Select Navicat product:\n 0. DataModeler\n 1. Premium\n 2. MySQL\n 3. PostgreSQL\n 4. Oracle\n 5. SQLServer\n 6. SQLite\n 7. MariaDB\n 8. MongoDB\n 9. ReportViewer\n```\n\n**选择语言**：1\n\n```bash\n[*] Select product language:\n 0. English\n 1. Simplified Chinese\n 2. Traditional Chinese\n 3. Japanese\n 4. Polish\n 5. Spanish\n 6. French\n 7. German\n 8. Korean\n 9. Russian\n 10. Portuguese\n```\n\n**选择版本**：15\n\n```bash\n[*] Input major version number:\n(range: 0 ~ 15, default: 12)> \n```\n\n**生成序列号**：将生成的序列号粘贴到Navicat注册页面，并选择手动激活\n\n**填写信息**\n\n```bash\n[*] Your name: zhou\n[*] Your organization: bilibili\n```\n\n**输入注册码：** 将Navicat激活界面注册码粘贴此处后，按两次回车确认\n\n```bash\n[*] Input request code in Base64: (Double press ENTER to end)\nlaogongshuowdnmd\n```\n\n**生成激活码**：将激活码粘贴到Navicat界面中完成激活\n\n```bash\n[*] Activation Code:\njiegeshuowdnmmb\n```\n\n将破解后的软件包和解包后的Navicat图标放到`/opt/navicat`下\n\n```bash\nsudo mkdir /opt/navicat && \nsudo cp navicat15-premium-cs-pathed.AppImage /opt/navicat/\nsudo cp navicat15/navicat-icon.png /opt/navicat/logo.png\n```\n\n在用户共享应用中创建快捷方式\n\n```bash\nsudo vim /usr/share/applications/navicat.desktop\n```\n\n```\n[Desktop Entry]\nType=Application\nName=Navicat Premium 15\nGenericName=Database Development Tool\nIcon=/opt/navicat/logo.png\nExec=/opt/navicat/navicat15-premium-pathed.AppImage\nCategories=Development;\nKeywords=database;sql;\n```\n\n最后对navicat15取消挂载后，把tmp文件夹全部删除就收工了\n\n## Typora\n\n博客是用markdown编写，所以下载Markdown编辑器Typora\n\n[进入Typora官网](https://www.typora.io/#linux)下载相应的tar包后，进行解压缩\n\n``` bash\ntar -zxvf Typora-linux-x64.tar.gz\n```\n\n将解压后的文件移动到`/opt`下\n\n``` bash\nsudo mv bin/Typora-linux-x64/ /opt/typora\n```\n\n测试启动Typora\n\n``` bash\n/opt/typora/Typora\n```\n\n在用户共享应用中创建快捷方式\n\n```bash\nsudo vim /usr/share/applications/typora.desktop\n```\n\n```\n[Desktop Entry]\nName=Typora\nType=Application\nExec=/opt/typora/Typora %U\nIcon=/opt/typora/resources/app/asserts/icon/icon_512x512.png\nTerminal=false\nCategories=Utility;TextEditor;\nMimeType=text/x-makefile;text/markdown;\n```\n\n校验桌面文件是否正确\n\n```bash\ndesktop-file-validate /usr/share/applications/typora.desktop\n```\n\n现在就可以在应用程序中看到Typora了。如果想放在用户桌面，把`desktop`拷贝到用户桌面即可\n\n``` bash\ncp /usr/share/applications/typora.desktop ~/Desktop\n```\n\n## WPS\n\n[进入WPS官网](https://www.wps.cn/product/wpslinux)下载相应安装包后，使用`dpkg`进行安装\n\n``` bash\nsudo dpkg -i wps-office_11.1.0.9522_amd64.deb\n```\n\n喜新厌旧的我，又卸载了预装的`libreoffice`\n\n``` bash\nsudo apt purge libreoffice?\n```\n\n# 配置调优\n\n## 卸载预装软件\n\n预装了蛮多软件，好多没有用看着也乱，卸载掉\n\n``` bash\nsudo apt purge cheese simple-scan  thunderbird aisleriot transmission-common deja-dup gnome-sudoku gnome-mahjongg\n```\n\n## 新建常用模板\n\n鼠标右键新建是获取的`~/Template`下的文件，所以只要把重新使用的文件模板放在此处就可以\n\n```bash\nsudo touch ~/Template/文本文档.txt\n```\n\nTemplate下的文件都有个小锁，拖入的新文件就没有，看着很不顺眼。修改文件权限同步小锁\n\n``` bash\nsudo chown root Word.doc\nsudo chmod g-w Word.doc\n```\n\n## 免密登录\n\n生成RSA钥匙对\n\n```bash\ncd ~/.ssh\nssh-keygen -t rsa\n```\n\n复制公钥，将公钥内容追加粘贴到要免密登录主机的`authorized_keys`中\n\n```bash\nssh root@11.11.11.11\nvim ~/.ssh/authorized_keys\n```\n\n当远程连接这台主机后就可以不用输入密码了，但IP太长不好记，用config对主机进行配置\n\n回到原主机，创建配置文件\n\n```bash\nvim ~/.ssh/config\n```\n\n```bash\nHost wangleimaiyu\n    HostName 11.11.11.11\n    User root\n```\n\n有多个主机依次追加配置就可以了，远程连接使用别名即可\n\n```bash\nssh wangleimaiyu\n```\n\n# 参考文章\n\n- [Ubuntu下Navicat15无限期试用](https://blog.csdn.net/u014585456/article/details/104464269)\n- [Jetbrains系列产品2020.1.x最新激活方法](https://zhile.io/2018/08/25/jetbrains-license-server-crack.html)\n', '', 0, '', '不是第一次装Ubuntu，但有些配置不常用就忘了，导致坑踩了一遍又一遍。这次长记性的我把配置过程记下来，防止再次入坑。Ubuntu的安装可以参考讲得很清楚Ubuntu官方源在国外，国内访问很慢，甚至有的可能被墙。所以需要把软件源更换为国内的镜像源在配置阿里云镜像源，修改前先备份内容清空，粘贴阿里云镜', 0, 1000, '2020-09-16 09:33:25', NULL, '2021-02-10 17:08:16', NULL);

-- ----------------------------
-- Table structure for blog_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_blog_tag`;
CREATE TABLE `blog_blog_tag`  (
  `tag_id` int(11) NULL DEFAULT NULL COMMENT '标签id',
  `blog_id` int(11) NULL DEFAULT NULL COMMENT '文章id',
  `is_deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除位'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_blog_tag
-- ----------------------------
INSERT INTO `blog_blog_tag` VALUES (26, 38, NULL);
INSERT INTO `blog_blog_tag` VALUES (27, 38, NULL);
INSERT INTO `blog_blog_tag` VALUES (28, 39, NULL);

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blog_id` int(11) NULL DEFAULT NULL COMMENT '博客id',
  `topic_id` int(11) NULL DEFAULT 0 COMMENT '主题id',
  `replied_id` int(11) NULL DEFAULT NULL COMMENT '回复评论id',
  `to_vid` int(11) NULL DEFAULT NULL COMMENT '目标访客id',
  `from_vid` int(11) NULL DEFAULT NULL COMMENT '访客id',
  `content` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态',
  `ip` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `ua` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户代理',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `topic_index`(`topic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 120 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_comment
-- ----------------------------
INSERT INTO `blog_comment` VALUES (118, 39, 0, NULL, NULL, 1497, 'test', 11, '127.0.0.1', '1111111111', '2020-07-28 15:48:05', '2020-09-22 10:23:40');
INSERT INTO `blog_comment` VALUES (119, 39, 0, NULL, NULL, 1497, 'test', 10, '127.0.0.1', '1111111111', '2020-07-28 15:48:05', '2020-09-22 10:39:13');

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `count` int(11) NULL DEFAULT NULL COMMENT '计数',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
INSERT INTO `blog_tag` VALUES (26, 'java', 1, 1000, '2020-09-16 09:11:32');
INSERT INTO `blog_tag` VALUES (27, '黑龙江', 1, 1000, '2020-09-16 09:11:33');
INSERT INTO `blog_tag` VALUES (28, 'ubuntu', 3, 1000, '2020-09-16 09:33:25');

-- ----------------------------
-- Table structure for blog_visitor
-- ----------------------------
DROP TABLE IF EXISTS `blog_visitor`;
CREATE TABLE `blog_visitor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `website` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '站点',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态',
  `notify` int(11) NULL DEFAULT NULL COMMENT '通知',
  `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `last_access_time` datetime(0) NULL DEFAULT NULL COMMENT '最后访问时间',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_index`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1799 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '访客' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_visitor
-- ----------------------------
INSERT INTO `blog_visitor` VALUES (1497, 'tester', 'test@qq.com', NULL, 'https://s.gravatar.com/avatar/bf58432148b643a8b4c41c3901b81d1b?s=48&r=g&d=identicon', 0, NULL, '0', NULL, '2020-07-28 15:48:05', '2020-07-28 14:44:58');
INSERT INTO `blog_visitor` VALUES (1798, 'zhou', NULL, NULL, NULL, 0, NULL, '0', NULL, '2020-09-17 16:14:38', '2020-09-17 16:14:40');

-- ----------------------------
-- Table structure for system_dict
-- ----------------------------
DROP TABLE IF EXISTS `system_dict`;
CREATE TABLE `system_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典标识',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态 0正常 11停用',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `modified_by` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1025 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_dict
-- ----------------------------
INSERT INTO `system_dict` VALUES (1005, '菜单类型', 'system_menu_type', 0, '系统菜单类型', 1000, 1000, '2020-06-08 14:40:22', '2021-02-12 17:05:56', 0);
INSERT INTO `system_dict` VALUES (1006, '菜单状态', 'system_menu_state', 0, '系统菜单状态', 1000, 1000, '2020-04-20 15:08:09', '2020-04-21 11:28:16', 0);
INSERT INTO `system_dict` VALUES (1007, '角色状态', 'system_role_state', 0, '系统角色状态', 1000, 1000, '2020-04-20 15:38:51', '2020-04-21 11:28:45', 0);
INSERT INTO `system_dict` VALUES (1008, '用户状态', 'system_user_state', 0, '系统用户状态', 1000, 1000, '2020-04-20 16:41:32', '2021-02-12 22:53:59', 0);
INSERT INTO `system_dict` VALUES (1009, '模块生成', 'system_extend_config', 0, '模块生成所需参数', 1000, 1000, '2020-04-22 14:35:35', '2021-02-18 18:50:01', 0);
INSERT INTO `system_dict` VALUES (1013, '博客状态', 'blog_state', 0, '博客状态', 1000, 1000, '2020-05-15 16:48:06', '2020-06-04 14:07:38', 0);
INSERT INTO `system_dict` VALUES (1015, '联系方式', 'zhou_sns', 0, NULL, 1000, 1000, '2020-05-29 16:49:54', '2020-06-30 10:53:04', 0);
INSERT INTO `system_dict` VALUES (1018, '访客状态', 'visitor_state', 0, '访客状态', 1000, 1000, '2020-06-08 14:40:22', '2020-06-25 17:05:48', 0);
INSERT INTO `system_dict` VALUES (1019, '评论状态', 'comment_state', 0, NULL, 1000, 1000, '2020-06-25 16:56:32', '2020-09-22 10:26:57', 0);
INSERT INTO `system_dict` VALUES (1021, '阻塞类型', 'block_type', 0, NULL, 1000, 1000, '2020-09-18 09:35:55', '2020-09-18 09:35:55', 0);
INSERT INTO `system_dict` VALUES (1022, '评论禁言类型', 'comment_block_type', 0, '评论禁言的预设类型', 1000, 1000, '2020-09-21 17:16:55', '2020-09-21 17:18:03', 0);
INSERT INTO `system_dict` VALUES (1023, '123', '123123', 0, '31231', 1000, 1000, '2021-02-10 17:22:23', '2021-02-10 17:22:38', 1);
INSERT INTO `system_dict` VALUES (1024, 'test', 'test', 0, NULL, 1000, NULL, '2021-02-25 22:27:23', NULL, 1);

-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_data`;
CREATE TABLE `system_dict_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标识码',
  `label` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `dict_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典标识',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型 0 正常 1 预留 11系统',
  `is_perf` tinyint(1) NULL DEFAULT NULL COMMENT '首选标识',
  `spare` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用',
  `is_deleted` tinyint(1) NOT NULL COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1459 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_dict_data
-- ----------------------------
INSERT INTO `system_dict_data` VALUES (1123, '0', '正常', 'system_menu_state', 0, 0, 'layui-bg-blue', 0);
INSERT INTO `system_dict_data` VALUES (1124, '1', '隐藏', 'system_menu_state', 0, 0, 'layui-bg-orange', 0);
INSERT INTO `system_dict_data` VALUES (1125, '0', '正常', 'system_role_state', 0, 0, 'layui-bg-blue', 0);
INSERT INTO `system_dict_data` VALUES (1126, '11', '停用', 'system_role_state', 0, 0, 'layui-bg-orange', 0);
INSERT INTO `system_dict_data` VALUES (1171, '0', '正常', 'blog_state', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1172, '11', '已删除', 'blog_state', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1225, '0', '正常', 'visitor_state', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1226, '11', '阻塞', 'visitor_state', 0, 0, 'layui-bg-red', 0);
INSERT INTO `system_dict_data` VALUES (1243, 'email', 'flyingnoob@qq.com', 'zhou_sns', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1244, 'wechat', 'https://zhousb.cn/resource/wechatQR.jpg', 'zhou_sns', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1245, 'github', 'https://github.com/zhousb1999', 'zhou_sns', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1263, '0', '阻塞评论', 'block_type', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1264, '10', '阻塞访问', 'block_type', 0, 0, NULL, 0);
INSERT INTO `system_dict_data` VALUES (1281, '0', '灌水', 'comment_block_type', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1282, '1', '广告', 'comment_block_type', 0, 0, NULL, 0);
INSERT INTO `system_dict_data` VALUES (1283, '2', '引战', 'comment_block_type', 0, 0, NULL, 0);
INSERT INTO `system_dict_data` VALUES (1284, '3', '辱骂', 'comment_block_type', 0, 0, NULL, 0);
INSERT INTO `system_dict_data` VALUES (1319, '0', '待审核', 'comment_state', 0, 0, 'layui-bg-orange', 0);
INSERT INTO `system_dict_data` VALUES (1320, '1', '正常', 'comment_state', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1321, '9', '已屏蔽', 'comment_state', 0, 0, 'layui-bg-gray', 0);
INSERT INTO `system_dict_data` VALUES (1322, '10', '已禁言', 'comment_state', 0, 0, 'layui-bg-black', 0);
INSERT INTO `system_dict_data` VALUES (1323, '11', '自动屏蔽', 'comment_state', 0, 0, 'layui-bg-gray', 0);
INSERT INTO `system_dict_data` VALUES (1431, '0', '正常', '123123', 0, 1, '12312', 1);
INSERT INTO `system_dict_data` VALUES (1444, '0', '目录', 'system_menu_type', 0, 0, 'layui-bg-orange', 0);
INSERT INTO `system_dict_data` VALUES (1445, '1', '菜单', 'system_menu_type', 0, 0, 'layui-bg-green', 0);
INSERT INTO `system_dict_data` VALUES (1446, '2', '按钮', 'system_menu_type', 0, 0, 'layui-bg-blue', 0);
INSERT INTO `system_dict_data` VALUES (1447, '0', '正常', 'system_user_state', 0, 1, 'layui-bg-blue', 0);
INSERT INTO `system_dict_data` VALUES (1448, '9', '注销', 'system_user_state', 11, 0, 'layui-btn-disabled', 0);
INSERT INTO `system_dict_data` VALUES (1449, '11', '冻结', 'system_user_state', 0, 0, 'layui-btn-disabled', 0);
INSERT INTO `system_dict_data` VALUES (1454, 'author', 'zhou', 'system_extend_config', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1455, 'packet', 'priv.zhou', 'system_extend_config', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1456, 'keepPrefix', 'false', 'system_extend_config', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1457, 'module', 'system', 'system_extend_config', 0, 0, '', 0);
INSERT INTO `system_dict_data` VALUES (1458, '0', '正常', 'test', 0, 1, NULL, 1);

-- ----------------------------
-- Table structure for system_image
-- ----------------------------
DROP TABLE IF EXISTS `system_image`;
CREATE TABLE `system_image`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1251 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_image
-- ----------------------------
INSERT INTO `system_image` VALUES (1232, 'https://zhousb.cn/upload/zhousb-admin/39413428802899968.jpg', '', 1000, '2020-07-03 09:39:11', '2020-07-03 09:39:11');
INSERT INTO `system_image` VALUES (1233, 'https://zhousb.cn/upload/zhousb-admin/43521150738206720.jpg', '一封情书', 1000, '2020-07-14 17:44:30', '2020-07-14 17:44:30');
INSERT INTO `system_image` VALUES (1234, 'https://zhousb.cn/upload/zhousb-admin/43522782888693760.jpg', '一封情书', 1000, '2020-07-14 17:48:20', '2020-07-14 17:48:20');
INSERT INTO `system_image` VALUES (1235, 'https://zhousb.cn/upload/zhousb-admin/43522782943219714.jpg', '一封情书', 1000, '2020-07-14 17:48:20', '2020-07-14 17:48:20');
INSERT INTO `system_image` VALUES (1236, 'https://zhousb.cn/upload/zhousb-admin/43522782943219712.jpg', '一封情书', 1000, '2020-07-14 17:48:20', '2020-07-14 17:48:20');
INSERT INTO `system_image` VALUES (1237, 'https://zhousb.cn/upload/zhousb-admin/43522782943219713.jpg', '一封情书', 1000, '2020-07-14 17:48:20', '2020-07-14 17:48:20');
INSERT INTO `system_image` VALUES (1238, 'https://zhousb.cn/upload/zhousb-admin/43522782943219715.jpg', '一封情书', 1000, '2020-07-14 17:48:20', '2020-07-14 17:48:20');
INSERT INTO `system_image` VALUES (1239, 'https://zhousb.cn/upload/zhousb-admin/43522782943219716.jpg', '一封情书', 1000, '2020-07-14 17:48:20', '2020-07-14 17:48:20');
INSERT INTO `system_image` VALUES (1240, 'https://zhousb.cn/upload/zhousb-admin/43522790639767552.jpg', '一封情书', 1000, '2020-07-14 17:48:20', '2020-07-14 17:48:20');
INSERT INTO `system_image` VALUES (1241, 'https://zhousb.cn/upload/zhousb-admin/43522790677516288.jpg', '一封情书', 1000, '2020-07-14 17:48:20', '2020-07-14 17:48:20');
INSERT INTO `system_image` VALUES (1242, 'https://zhousb.cn/upload/zhousb-admin/43522790614601728.jpg', '一封情书', 1000, '2020-07-14 17:48:20', '2020-07-14 17:48:20');
INSERT INTO `system_image` VALUES (1243, 'https://zhousb.cn/upload/zhousb-admin/43882670131220480.jpg', '评论模块', 1000, '2020-07-15 17:38:23', '2020-07-15 17:38:23');
INSERT INTO `system_image` VALUES (1244, 'https://zhousb.cn/upload/zhousb-admin/43882670135414785.png', '评论模块', 1000, '2020-07-15 17:38:23', '2020-07-15 17:38:23');
INSERT INTO `system_image` VALUES (1245, 'https://zhousb.cn/upload/zhousb-admin/43882670131220481.png', '评论模块', 1000, '2020-07-15 17:38:23', '2020-07-15 17:38:23');
INSERT INTO `system_image` VALUES (1246, 'https://zhousb.cn/upload/zhousb-admin/43882670135414784.png', '评论模块', 1000, '2020-07-15 17:38:23', '2020-07-15 17:38:23');
INSERT INTO `system_image` VALUES (1247, 'https://zhousb.cn/upload/zhousb-admin/43882670131220483.png', '评论模块', 1000, '2020-07-15 17:38:23', '2020-07-15 17:38:23');
INSERT INTO `system_image` VALUES (1248, 'https://zhousb.cn/upload/zhousb-admin/43882670131220482.png', '评论模块', 1000, '2020-07-15 17:38:23', '2020-07-15 17:38:23');
INSERT INTO `system_image` VALUES (1249, 'https://zhousb.cn/upload/zhousb-admin/65970466769571840.jpg', '', 1000, '2020-09-14 16:26:45', '2020-09-14 16:26:45');
INSERT INTO `system_image` VALUES (1250, 'https://zhousb.cn/upload/zhousb-admin/65970875940704256.jpg', '', 1000, '2020-09-14 16:28:22', '2020-09-14 16:28:22');
INSERT INTO `system_image` VALUES (1251, 'https://zhousb.cn/upload/zhousb-admin/65971331597307904.jpg', '', 1000, '2020-09-14 16:30:11', '2020-09-14 16:30:11');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `icon` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `path` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型 关联字典',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态 关联字典',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序 从小直大',
  `key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `flag` int(11) NULL DEFAULT NULL COMMENT '旗帜 0为后台 1为前台',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_by` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1170 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES (1003, 1035, '菜单列表', 'fa-gear', 'system/menu', 1, 0, 0, 'system:menu:view', 1, 0, '2020-04-08 14:58:59', 1000, '2020-06-01 11:43:51');
INSERT INTO `system_menu` VALUES (1011, 1003, '菜单添加', 'fa-gear', 'system/menu/add', 2, 0, NULL, 'system:menu:add', 1, 1000, '2020-04-10 14:32:32', 1000, '2020-06-01 11:32:20');
INSERT INTO `system_menu` VALUES (1035, 0, '系统管理', 'fa fa-gear', '', 0, 0, 1, NULL, 1, 1000, '2020-04-14 19:33:42', 1000, '2021-03-05 19:52:32');
INSERT INTO `system_menu` VALUES (1061, 1035, '角色列表', '', 'system/role', 1, 0, 1, 'system:role:view', 1, 1000, '2020-04-15 13:43:59', 1000, '2020-04-15 13:43:59');
INSERT INTO `system_menu` VALUES (1066, 1003, '菜单修改', '', '', 2, 0, NULL, 'system:menu:update', 1, 1000, '2020-04-16 10:55:52', 1000, '2020-06-01 11:38:51');
INSERT INTO `system_menu` VALUES (1067, 1035, '模块生成', '', 'system/extend', 1, 0, 3, 'system:extend:index', 1, 1000, '2020-04-16 16:24:49', 1000, '2020-04-16 16:24:49');
INSERT INTO `system_menu` VALUES (1068, 1035, '字典列表', '', 'system/dict', 1, 0, 3, 'system:dict:view', 1, 1000, '2020-04-17 13:08:36', 1000, '2020-04-17 13:08:36');
INSERT INTO `system_menu` VALUES (1069, 1003, '菜单移除', '', '', 2, 0, NULL, 'system:menu:remove', 1, 1000, '2020-04-17 13:27:38', 1000, '2020-06-01 11:37:04');
INSERT INTO `system_menu` VALUES (1070, 1061, '角色修改', NULL, NULL, 2, 0, NULL, 'system:role:update', 1, 1000, '2020-04-17 13:08:36', 1000, '2020-04-17 13:08:36');
INSERT INTO `system_menu` VALUES (1072, 1061, '角色详情', '', '', 2, 0, NULL, 'system:role:detail', 1, 1000, '2020-04-17 13:41:03', 1000, '2020-04-17 13:41:03');
INSERT INTO `system_menu` VALUES (1073, 1061, '角色删除', '', '', 2, 0, NULL, 'system:role:remove', 1, 1000, '2020-04-17 13:41:41', 1000, '2020-04-17 13:41:41');
INSERT INTO `system_menu` VALUES (1074, 1067, '批量生成', '', '', 2, 0, NULL, 'system:extend:extend', 1, 1000, '2020-04-17 13:43:10', 1000, '2020-06-01 13:35:22');
INSERT INTO `system_menu` VALUES (1075, 1068, '添加字典', '', '', 2, 0, NULL, 'system:dict:add', 1, 1000, '2020-04-17 14:13:19', 1000, '2020-04-17 14:13:19');
INSERT INTO `system_menu` VALUES (1076, 1068, '移除字典', '', '', 2, 0, NULL, 'system:dict:remove', 1, 1000, '2020-04-17 14:14:32', 1000, '2020-04-17 14:14:32');
INSERT INTO `system_menu` VALUES (1077, 1068, '字典修改', '', '', 2, 0, NULL, 'system:dict:update', 1, 1000, '2020-04-17 14:14:54', 1000, '2020-04-17 14:14:54');
INSERT INTO `system_menu` VALUES (1078, 1068, '字典详情', '', '', 2, 0, NULL, 'system:dict:detail', 1, 1000, '2020-04-17 14:37:52', 1000, '2020-04-17 14:37:52');
INSERT INTO `system_menu` VALUES (1080, 1035, '用户列表', '', 'system/user', 1, 0, 3, 'system:user:view', 1, 1000, '2020-04-20 16:46:05', 1000, '2020-04-20 16:46:05');
INSERT INTO `system_menu` VALUES (1081, 1080, '添加用户', '', '', 2, 0, NULL, 'system:user:add', 1, 1000, '2020-04-20 16:47:45', 1000, '2020-04-20 16:47:45');
INSERT INTO `system_menu` VALUES (1082, 1080, '修改用户', '', '', 2, 0, NULL, 'system:user:update', 1, 1000, '2020-04-20 16:48:06', 1000, '2020-04-20 16:48:06');
INSERT INTO `system_menu` VALUES (1083, 1080, '用户详情', '', '', 2, 0, NULL, 'system:user:detail', 1, 1000, '2020-04-20 16:48:29', 1000, '2020-04-20 16:48:29');
INSERT INTO `system_menu` VALUES (1084, 1080, '移除用户', '', '', 2, 0, NULL, 'system:user:remove', 1, 1000, '2020-04-20 16:51:34', 1000, '2020-04-20 16:51:34');
INSERT INTO `system_menu` VALUES (1085, 1035, '在线监控', '', 'system/monitor', 1, 0, 6, 'system:monitor:view', 1, 1000, '2020-04-22 11:20:27', 1000, '2020-04-22 11:20:27');
INSERT INTO `system_menu` VALUES (1086, 1085, '强退', '', '', 2, 0, NULL, 'system:monitor:offline', 1, 1000, '2020-04-22 14:03:41', 1000, '2020-09-17 11:39:57');
INSERT INTO `system_menu` VALUES (1087, 1061, '添加角色', '', '', 2, 0, NULL, 'system:role:add', 1, 1000, '2020-04-22 14:56:55', 1000, '2020-04-22 14:56:55');
INSERT INTO `system_menu` VALUES (1099, 1003, '刷新权限', '', '', 2, 0, NULL, 'system:menu:refresh', 1, 1000, '2020-05-21 10:23:46', 1000, '2020-06-01 11:37:07');
INSERT INTO `system_menu` VALUES (1110, 1142, 'JAVA', 'CoffeeOutlined', '/?type=java', 1, 0, 1, 'java', 2, 1000, '2020-05-15 16:18:02', 1000, '2020-07-02 15:00:36');
INSERT INTO `system_menu` VALUES (1122, 1035, '在线图床', '', 'system/image', 1, 0, 8, 'system:image:view', 1, 1000, '2020-06-01 13:54:11', 1000, '2020-09-17 13:38:57');
INSERT INTO `system_menu` VALUES (1123, 1122, '上传图片', '', '', 2, 0, NULL, 'system:image:save', 1, 1000, '2020-06-01 13:54:52', 1000, '2020-06-02 15:53:36');
INSERT INTO `system_menu` VALUES (1124, 1122, '移除图片', '', '', 2, 0, NULL, 'system:image:remove', 1, 1000, '2020-06-03 08:39:47', 1000, '2020-06-03 08:39:47');
INSERT INTO `system_menu` VALUES (1126, 0, '首页', 'HomeOutlined', '/', 1, 0, 0, '/', 2, 1000, '2020-05-28 16:18:02', 1000, '2020-06-04 15:08:00');
INSERT INTO `system_menu` VALUES (1141, 0, '生活', 'ContactsOutlined', '/?type=life', 1, 0, 1, 'life', 2, 1000, '2020-06-25 20:16:48', 1000, '2020-07-03 15:53:22');
INSERT INTO `system_menu` VALUES (1142, 0, '编程', 'CodeOutlined', NULL, 0, 0, 2, 'program', 2, 1000, '2020-08-06 20:18:52', 1000, '2020-07-03 15:47:22');
INSERT INTO `system_menu` VALUES (1143, 0, '备忘', 'ContainerOutlined', '/?type=notes', 1, 0, 3, 'notes', 2, 1000, '2020-06-25 20:19:52', 1000, '2020-07-03 15:56:12');
INSERT INTO `system_menu` VALUES (1144, 0, '关于', 'UserOutlined', '/detail?id=0', 1, 0, 4, 'about', 2, 1000, '2020-06-29 14:01:16', 1000, '2020-07-03 15:57:06');
INSERT INTO `system_menu` VALUES (1145, 1142, '计算机基础', 'DesktopOutlined', '/?type=computer-basics', 1, 0, 2, 'computer-basics', 2, 1000, '2020-08-06 15:00:30', 1000, '2020-07-03 15:53:11');
INSERT INTO `system_menu` VALUES (1146, 1142, '建站分享', '', '/?type=build-blog', 1, 0, 3, 'build-blog', 2, 1000, '2020-08-06 09:23:14', 1000, '2020-07-16 09:23:26');
INSERT INTO `system_menu` VALUES (1148, 1080, '冻结用户', '', '', 2, 0, NULL, 'system:user:freeze', 1, 1000, '2020-08-17 09:29:12', 1000, '2020-08-17 09:29:12');
INSERT INTO `system_menu` VALUES (1149, 1080, '修改密码', '', '', 2, 0, NULL, 'system:user:reset:pwd', 1, 1000, '2020-08-17 09:29:34', 1000, '2020-10-14 09:58:18');
INSERT INTO `system_menu` VALUES (1153, 0, '博客列表', 'fa fa-newspaper-o', 'blog', 1, 0, 1, 'blog:view', 1, 1000, '2020-09-14 17:36:00', 1000, '2020-09-14 17:36:00');
INSERT INTO `system_menu` VALUES (1154, 1153, '添加博客', '', '', 2, 0, NULL, 'blog:add', 1, 1000, '2020-09-14 17:43:02', 1000, '2020-09-14 17:43:02');
INSERT INTO `system_menu` VALUES (1155, 1153, '修改博客', '', '', 2, 0, NULL, 'blog:update', 1, 1000, '2020-09-14 17:43:14', 1000, '2020-09-14 17:43:14');
INSERT INTO `system_menu` VALUES (1156, 1153, '移除博客', '', '', 2, 0, NULL, 'blog:remove', 1, 1000, '2020-09-14 17:43:26', 1000, '2020-09-14 17:43:26');
INSERT INTO `system_menu` VALUES (1157, 0, '评论管理', 'fa fa-comment-o', 'comment', 1, 0, 3, 'comment:view', 1, 1000, '2020-09-17 08:34:53', 1000, '2020-09-17 08:34:53');
INSERT INTO `system_menu` VALUES (1158, 1157, '评论详情', '', '', 2, 0, NULL, 'comment:detail', 1, 1000, '2020-09-17 08:35:13', 1000, '2020-09-17 08:35:13');
INSERT INTO `system_menu` VALUES (1159, 1157, '回复评论', '', '', 2, 0, NULL, 'comment:reply', 1, 1000, '2020-09-17 08:35:33', 1000, '2020-09-17 08:35:33');
INSERT INTO `system_menu` VALUES (1160, 1157, '审核评论', '', '', 2, 0, NULL, 'comment:check', 1, 1000, '2020-09-17 10:21:40', 1000, '2020-09-17 10:21:40');
INSERT INTO `system_menu` VALUES (1161, 1157, '移除评论', '', '', 2, 0, NULL, 'comment:remove', 1, 1000, '2020-09-17 16:27:22', 1000, '2020-09-17 16:28:53');
INSERT INTO `system_menu` VALUES (1162, 1153, '博客详情', '', '', 2, 0, NULL, 'blog:detail', 1, 1000, '2020-09-17 16:34:22', 1000, '2020-09-17 16:34:22');
INSERT INTO `system_menu` VALUES (1164, 0, '阻塞列表', 'fa fa-address-book', 'block', 1, 0, 4, 'block:view', 1, 1000, '2020-09-18 09:25:27', 1000, '2020-09-18 09:36:08');
INSERT INTO `system_menu` VALUES (1165, 1164, '取消阻塞', '', '', 2, 0, NULL, 'block:remove', 1, 1000, '2020-09-18 09:52:59', 1000, '2020-09-18 09:52:59');
INSERT INTO `system_menu` VALUES (1167, 1157, '禁言', '', '', 2, 0, NULL, 'comment:block', 1, 1000, '2020-09-21 17:07:07', 1000, '2020-09-21 17:07:07');
INSERT INTO `system_menu` VALUES (1169, 0, 'test', 'fa fa-address-book-o', '', 0, 0, NULL, '', 1, 1000, '2021-02-25 21:00:39', 1000, '2021-03-05 19:52:56');

-- ----------------------------
-- Table structure for system_msg
-- ----------------------------
DROP TABLE IF EXISTS `system_msg`;
CREATE TABLE `system_msg`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `from_id` int(11) NULL DEFAULT NULL COMMENT '发送人',
  `to_id` int(11) NULL DEFAULT NULL COMMENT '接收人',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_msg
-- ----------------------------

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'key',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态 关联字典',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_by` int(11) NULL DEFAULT NULL COMMENT '修改人id',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2029 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES (1001, 'root', '超级管理员', 0, '这角色嘛都能操作', 0, '2020-04-08 14:58:59', 1000, '2020-09-21 17:29:06', 0);
INSERT INTO `system_role` VALUES (2026, 'awake', 'awake', 0, '', 1000, '2020-06-08 08:37:04', 1000, '2021-03-06 16:00:20', 0);
INSERT INTO `system_role` VALUES (2027, 'goubi', '狗比', 0, '', 1000, '2021-02-24 22:24:52', 1000, '2021-03-06 15:26:13', 1);
INSERT INTO `system_role` VALUES (2028, 'test', '测试', 0, '', 1000, '2021-03-07 00:05:09', 1000, '2021-03-07 00:34:58', 1);

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
  `role_id` int(11) NULL DEFAULT NULL,
  `menu_id` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色关联菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
INSERT INTO `system_role_menu` VALUES (2026, 1035);
INSERT INTO `system_role_menu` VALUES (2026, 1003);
INSERT INTO `system_role_menu` VALUES (2026, 1011);
INSERT INTO `system_role_menu` VALUES (2026, 1099);
INSERT INTO `system_role_menu` VALUES (2026, 1066);
INSERT INTO `system_role_menu` VALUES (2026, 1069);
INSERT INTO `system_role_menu` VALUES (2026, 1061);
INSERT INTO `system_role_menu` VALUES (2026, 1070);
INSERT INTO `system_role_menu` VALUES (2026, 1072);
INSERT INTO `system_role_menu` VALUES (2026, 1073);
INSERT INTO `system_role_menu` VALUES (2026, 1087);
INSERT INTO `system_role_menu` VALUES (2026, 1067);
INSERT INTO `system_role_menu` VALUES (2026, 1074);
INSERT INTO `system_role_menu` VALUES (2026, 1068);
INSERT INTO `system_role_menu` VALUES (2026, 1075);
INSERT INTO `system_role_menu` VALUES (2026, 1076);
INSERT INTO `system_role_menu` VALUES (2026, 1077);
INSERT INTO `system_role_menu` VALUES (2026, 1078);
INSERT INTO `system_role_menu` VALUES (2026, 1080);
INSERT INTO `system_role_menu` VALUES (2026, 1084);
INSERT INTO `system_role_menu` VALUES (2026, 1083);
INSERT INTO `system_role_menu` VALUES (2026, 1081);
INSERT INTO `system_role_menu` VALUES (2026, 1082);
INSERT INTO `system_role_menu` VALUES (2026, 1085);
INSERT INTO `system_role_menu` VALUES (2026, 1086);
INSERT INTO `system_role_menu` VALUES (2026, 1122);
INSERT INTO `system_role_menu` VALUES (2026, 1123);
INSERT INTO `system_role_menu` VALUES (2026, 1124);
INSERT INTO `system_role_menu` VALUES (2027, 1035);
INSERT INTO `system_role_menu` VALUES (2027, 1003);
INSERT INTO `system_role_menu` VALUES (2027, 1011);
INSERT INTO `system_role_menu` VALUES (2027, 1066);
INSERT INTO `system_role_menu` VALUES (2027, 1069);
INSERT INTO `system_role_menu` VALUES (2027, 1099);
INSERT INTO `system_role_menu` VALUES (2027, 1061);
INSERT INTO `system_role_menu` VALUES (2027, 1070);
INSERT INTO `system_role_menu` VALUES (2027, 1072);
INSERT INTO `system_role_menu` VALUES (2027, 1073);
INSERT INTO `system_role_menu` VALUES (2027, 1087);
INSERT INTO `system_role_menu` VALUES (2027, 1067);
INSERT INTO `system_role_menu` VALUES (2027, 1074);
INSERT INTO `system_role_menu` VALUES (2027, 1068);
INSERT INTO `system_role_menu` VALUES (2027, 1075);
INSERT INTO `system_role_menu` VALUES (2027, 1076);
INSERT INTO `system_role_menu` VALUES (2027, 1077);
INSERT INTO `system_role_menu` VALUES (2027, 1078);
INSERT INTO `system_role_menu` VALUES (2027, 1080);
INSERT INTO `system_role_menu` VALUES (2027, 1081);
INSERT INTO `system_role_menu` VALUES (2027, 1082);
INSERT INTO `system_role_menu` VALUES (2027, 1083);
INSERT INTO `system_role_menu` VALUES (2027, 1084);
INSERT INTO `system_role_menu` VALUES (2027, 1148);
INSERT INTO `system_role_menu` VALUES (2027, 1149);
INSERT INTO `system_role_menu` VALUES (2027, 1085);
INSERT INTO `system_role_menu` VALUES (2027, 1086);
INSERT INTO `system_role_menu` VALUES (2027, 1122);
INSERT INTO `system_role_menu` VALUES (2027, 1123);
INSERT INTO `system_role_menu` VALUES (2027, 1153);
INSERT INTO `system_role_menu` VALUES (2027, 1154);
INSERT INTO `system_role_menu` VALUES (2027, 1155);
INSERT INTO `system_role_menu` VALUES (2027, 1156);
INSERT INTO `system_role_menu` VALUES (2027, 1162);
INSERT INTO `system_role_menu` VALUES (2027, 1157);
INSERT INTO `system_role_menu` VALUES (2027, 1158);
INSERT INTO `system_role_menu` VALUES (2027, 1159);
INSERT INTO `system_role_menu` VALUES (2027, 1160);
INSERT INTO `system_role_menu` VALUES (2027, 1161);
INSERT INTO `system_role_menu` VALUES (2027, 1167);
INSERT INTO `system_role_menu` VALUES (2027, 1164);
INSERT INTO `system_role_menu` VALUES (2027, 1165);
INSERT INTO `system_role_menu` VALUES (2028, 1035);
INSERT INTO `system_role_menu` VALUES (2028, 1003);
INSERT INTO `system_role_menu` VALUES (2028, 1011);
INSERT INTO `system_role_menu` VALUES (2028, 1066);
INSERT INTO `system_role_menu` VALUES (2028, 1069);
INSERT INTO `system_role_menu` VALUES (2028, 1099);
INSERT INTO `system_role_menu` VALUES (2028, 1061);
INSERT INTO `system_role_menu` VALUES (2028, 1070);
INSERT INTO `system_role_menu` VALUES (2028, 1072);
INSERT INTO `system_role_menu` VALUES (2028, 1073);
INSERT INTO `system_role_menu` VALUES (2028, 1087);
INSERT INTO `system_role_menu` VALUES (2028, 1067);
INSERT INTO `system_role_menu` VALUES (2028, 1074);
INSERT INTO `system_role_menu` VALUES (2028, 1068);
INSERT INTO `system_role_menu` VALUES (2028, 1075);
INSERT INTO `system_role_menu` VALUES (2028, 1076);
INSERT INTO `system_role_menu` VALUES (2028, 1077);
INSERT INTO `system_role_menu` VALUES (2028, 1078);
INSERT INTO `system_role_menu` VALUES (2028, 1080);
INSERT INTO `system_role_menu` VALUES (2028, 1081);
INSERT INTO `system_role_menu` VALUES (2028, 1082);
INSERT INTO `system_role_menu` VALUES (2028, 1083);
INSERT INTO `system_role_menu` VALUES (2028, 1084);
INSERT INTO `system_role_menu` VALUES (2028, 1148);
INSERT INTO `system_role_menu` VALUES (2028, 1149);
INSERT INTO `system_role_menu` VALUES (2028, 1085);
INSERT INTO `system_role_menu` VALUES (2028, 1086);
INSERT INTO `system_role_menu` VALUES (2028, 1122);
INSERT INTO `system_role_menu` VALUES (2028, 1123);
INSERT INTO `system_role_menu` VALUES (2028, 1124);
INSERT INTO `system_role_menu` VALUES (2028, 1153);
INSERT INTO `system_role_menu` VALUES (2028, 1154);
INSERT INTO `system_role_menu` VALUES (2028, 1155);
INSERT INTO `system_role_menu` VALUES (2028, 1156);
INSERT INTO `system_role_menu` VALUES (2028, 1162);
INSERT INTO `system_role_menu` VALUES (2028, 1157);
INSERT INTO `system_role_menu` VALUES (2028, 1158);
INSERT INTO `system_role_menu` VALUES (2028, 1159);
INSERT INTO `system_role_menu` VALUES (2028, 1160);
INSERT INTO `system_role_menu` VALUES (2028, 1161);
INSERT INTO `system_role_menu` VALUES (2028, 1167);

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态 关联字典',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_by` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1011 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES (1000, 'zhou', 'bebe9825b805043d0a9bd1c9435733e3', '111', '周某意', '15022242984', 0, NULL, '2021-02-11 18:05:27', 1000, '2021-03-07 02:12:28', 0);
INSERT INTO `system_user` VALUES (1006, 'awake', '7fd862ffa4a5a39e16bf7c5ef4038fea', 'bZeY0H', 'awake', '15022242988', 0, NULL, '2021-02-11 18:05:30', 1000, '2021-03-07 02:13:28', 0);
INSERT INTO `system_user` VALUES (1007, 'liu', '124b0801ee29e84b0e33e94e95349294', 'kJPRus', '刘凯华', '15022242982', 0, 1000, '2021-02-13 13:53:27', NULL, NULL, 1);
INSERT INTO `system_user` VALUES (1008, 'liu1', '14689b4a97a473a7ab584f055014166b', 'RqBVz9', 'liu', '15022248888', 0, 1000, '2021-02-13 13:59:16', NULL, NULL, 1);
INSERT INTO `system_user` VALUES (1009, 'wang', '3d71f848135d79c8b61fc5f5c9b60414', '4T22Pq', 'wang', '15022242999', 0, 1000, '2021-02-13 14:00:10', NULL, NULL, 1);
INSERT INTO `system_user` VALUES (1010, '1111', '4cd52c604307f4c0eb65ef1c11bba756', 'GCpI89', '222', '15022242986', 0, 1000, '2021-02-25 20:53:47', 1000, '2021-03-07 02:13:29', 0);

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `is_deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除位'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户关联角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES (1006, 2026, 0);
INSERT INTO `system_user_role` VALUES (1007, 2026, 1);
INSERT INTO `system_user_role` VALUES (1008, 2026, 1);
INSERT INTO `system_user_role` VALUES (1009, 2026, 1);
INSERT INTO `system_user_role` VALUES (1000, 1001, 0);
INSERT INTO `system_user_role` VALUES (1010, 1001, 0);

SET FOREIGN_KEY_CHECKS = 1;
