package app;

import com.spreada.utils.chinese.ZHConverter;

/**
 * 全局配置文件
 * @author 郑元浩
 * @description 这是一个全局的配置文件
 */
public class Config {

    public static String projectName = "Yotta";

    /**
     * Selenium Webdriver 配置
     */
    public static String PHANTOMJS_PATH = "D:\\phantomjs.exe";  // 无界面浏览器
	public static String IE_PATH = "D:\\IEDriverServer.exe";  // IE模拟
    public static String CHROME_PATH = "D:\\chromedriver.exe";  // Chrome模拟

    /**
     * Mysql 配置
     */
    public static String DBNAME = "yotta_create";
//    public static String DBNAME = "yotta";
    public static String HOST = "localhost";
    public static String USERNAME = "root";
    public static String PASSWD = "root";
    public static int PORT = 3306;
//    public static int PORT = 9220;
    public static String MYSQL_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DBNAME + "?user=" + USERNAME + "&password=" + PASSWD + "&characterEncoding=UTF8"; // 阿里云服务器：域名+http端口

//    public static String MYSQL_URL = "jdbc:mysql://localhost:3306/yotta_create_test?user=root&password=root&characterEncoding=UTF8"; // 阿里云服务器：域名+http端口
    public static String IP1 = "http://202.117.54.125"; // 跨域访问控制：域名+apache端口
    public static String IP2 = "http://202.117.54.125:8080/Yotta"; // 阿里云服务器：域名+http端口
    public static String SWAGGERHOST = "202.117.54.125:8080"; // swagger主机
    public static String SWAGGERBASEPATH = "/Yotta"; //swagger根路径

//	public static String MYSQL_URL = "jdbc:mysql://localhost:9220/yotta?user=root&password=root&characterEncoding=UTF8"; // 阿里云服务器：域名+http端口
//	public static String IP1="http://yotta.xjtushilei.com:888"; // 跨域访问控制：域名+apache端口
//	public static String IP2="http://yotta.xjtushilei.com:9218/Yotta"; // 阿里云服务器：域名+http端口
//    public static String SWAGGERHOST = "yotta.xjtushilei.com:9218"; // swagger主机
//    public static String SWAGGERBASEPATH = "/Yotta"; //swagger根路径

    /**
     * 认知关系算法参数：gephi-tookit
     */
    public static int FILTERDEGREE = 2;
    public static String GEXFPATH = "F:\\gexfpath";
//	public static String GEXFPATH = "C:\\gexfpath";

    /**
     * 数据库  配置
     *
     * @author 郑元浩
     */
    public static String USER_INFO = "user_info";
    public static String USER_LOG = "user_log";
    public static String SOURCE_TABLE = "source";
    public static String SUBJECT_TABLE = "subject";
    public static String DOMAIN_TABLE = "domain";
    public static String FACET_TABLE = "facet";
    public static String FACET_RELATION_TABLE = "facet_relation";
    public static String DOMAIN_LAYER_TABLE = "domain_layer";
    public static String DOMAIN_LAYER_FUZHU_TABLE = "domain_layer_fuzhu";
    public static String DOMAIN_LAYER_FUZHU2_TABLE = "domain_layer_fuzhu2";
    public static String DOMAIN_TOPIC_TABLE = "domain_topic";
    public static String DOMAIN_TOPIC_RELATION_TABLE = "domain_topic_relation";
    public static String DOMAIN_LAYER_RELATION_TABLE = "domain_layer_relation";
    public static String DEPENDENCY = "dependency";
    public static String UNADD_IMAGE = "unadd_image";
    public static String FRAGMENT = "fragment";
    public static String ASSEMBLE_FRAGMENT_TABLE = "assemble_fragment";
    public static String RDF_TABLE = "rdf";

    /**
     * 爬虫
     */
    public static ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);// 转化为简体中文
    public static int TEXTLENGTH = 50; // 保存文本最短长度
    public static int THREAD = 3; // webmagic框架爬虫的线程数，不要太多，否则会被禁止

}
