# Yotta_backend

Yotta后端

采用maven重新整理，并推荐使用idea进行开发

## 环境配置

1. 配置修改
    1. app目录下Config.java文件为配置文件，需要修改。
    2. IDEA运行时配置tomcat服务器，在Deployment中添加"--:war exploded"并设置Application context为工程目录："/Yotta"；同时配置"--:war"，路径为"/"即可。启动后在target下生成对应的Yotta.war文件。
    3. server运行的端口和最终需要放到的tomcat服务端口保持一致，这样生成的war包可以直接放到tomcat服务器的webapps下即可正常工作。
    4. 启动tomcat时，如果报错：Caused by: java.lang.NoClassDefFoundError: javax/ws/rs/ProcessingException。由于jersey包没有导入完全，“project setting”->“Artifacts”->“* exploded”->“WEB-INF”->“lib”中没有把“javax.ws.rs-api:2.1”这个包加到tomcat的运行环境中，导入启动tomcat的过程出错，因此需要手动点击添加，随后即可正常启动tomcat服务器。[参考链接](http://blog.csdn.net/u012428012/article/details/78831290)
    
2. 手动添加ZHConverter.jar到本地maven仓库，命令为：mvn install:install-file -Dfile=ZHConverter-2.6.12.jar -DgroupId=com.spreada.utils -DartifactId=ZHConverter -Dversion=2.6.12 -Dpackaging=jar

3. 数据库设置
    1. 配置文件my.ini设置数据传输大小限制：max_allowed_packet=512M，重启mysql服务。
    2. 设置数据库表格为MyISAM引擎。
    3. 给表格建立索引，加快访问速度；varchar索引字段长度为100。
    
4. 爬虫设置
    1. 设置IEDriverServer.exe到Config.IE_PATH路径下，默认是放在D盘下。
    2. 设置IE浏览器的缩放为100%。
    3. “Internet选项”->“安全”：四个安全设置都启用保护模式。
    4. 单个爬虫：输入学科和课程即可。如：“计算机科学”，“数据结构”。
    5. 批量爬虫上传文件要求：
        1. xls格式的excel文件。
        2. 表格包含两列，第一行分别为：“学科”、“课程名”。
        3. 第二行开始为具体的学科和课程信息，如：“计算机科学”，“数据结构”。