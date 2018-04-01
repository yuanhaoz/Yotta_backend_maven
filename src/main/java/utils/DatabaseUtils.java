package utils;

import app.Config;
import assemble.bean.AssembleFragment2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库基本操作：
 * 1. 新建表格（基本表格的建表是靠程序自动生成的，而不是手动设置）
 * 2. 删除assemble_fragment中无用的碎片
 * 3. 将原来已有碎片的数据源SourceName列填上：“中文维基”。将fragmentContent带html标签的碎片内容解析成纯文本内容存储到Text字段中。
 * 4. 创建Yotta数据库所有表格的索引
 *
 * @author yuanhao
 * @date 2017/12/20 9:12
 */
public class DatabaseUtils {

    public static void main(String[] args) {
//        createTable();
//        deleteBadFragment();
//        updateSourceName();
//        createIndex();

//        String domainName = "Abstract_data_types";
//        String domainName = "String_data_structures";
        String domainName = "Source_code_generation";
        deleteDbByClassName(domainName);
    }

    /**
     * 创建Yotta数据库中的所有表格
     */
    public static void createTable() {
        mysqlUtils mysql = new mysqlUtils();
        List<Object> params = new ArrayList<Object>();
        String sqlAssembleFragment = "CREATE TABLE IF NOT EXISTS `assemble_fragment` (\n" +
                "  `FragmentID` int(20) NOT NULL AUTO_INCREMENT COMMENT '碎片ID',\n" +
                "  `FragmentContent` longtext NOT NULL COMMENT '碎片内容（包含文本和图片，html形式）',\n" +
                "  `Text` longtext NOT NULL COMMENT '碎片内容（只有文本，纯文本形式）',\n" +
                "  `FragmentScratchTime` datetime DEFAULT NULL COMMENT '碎片爬取时间',\n" +
                "  `TermID` int(20) DEFAULT NULL COMMENT '主题ID',\n" +
                "  `TermName` varchar(100) DEFAULT NULL COMMENT '主题名',\n" +
                "  `FacetName` varchar(100) DEFAULT NULL COMMENT '分面名',\n" +
                "  `FacetLayer` int(20) DEFAULT NULL COMMENT '分面层数',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  `SourceName` varchar(100) DEFAULT NULL COMMENT '数据源名',\n" +
                "  PRIMARY KEY (`FragmentID`),\n" +
                "  KEY `assembleFragment_ClassName_TermName_FacetName` (`ClassName`,`TermName`,`FacetName`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
        String sqlDependncey = "CREATE TABLE IF NOT EXISTS `dependency` (\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  `Start` varchar(100) DEFAULT NULL COMMENT '父主题名',\n" +
                "  `StartID` varchar(100) DEFAULT NULL COMMENT '父主题ID',\n" +
                "  `End` varchar(100) DEFAULT NULL COMMENT '子主题名',\n" +
                "  `EndID` varchar(100) DEFAULT NULL COMMENT '子主题ID',\n" +
                "  `Confidence` varchar(100) DEFAULT NULL COMMENT '认知关系置信度',\n" +
                "  KEY `dependency_ClassName` (`ClassName`)\n" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
        String sqlDomain = "CREATE TABLE IF NOT EXISTS `domain` (\n" +
                "  `ClassID` int(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  `SubjectName` varchar(100) DEFAULT NULL COMMENT '学科名',\n" +
                "  `Note` varchar(100) DEFAULT NULL COMMENT '备注',\n" +
                "  PRIMARY KEY (`ClassID`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
        String sqlDomainLayer = "CREATE TABLE IF NOT EXISTS `domain_layer` (\n" +
                "  `TermID` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '术语ID，存储各级未处理的主题（没有含有子主题的主题）',\n" +
                "  `TermName` varchar(100) DEFAULT NULL COMMENT '术语名',\n" +
                "  `TermUrl` varchar(255) DEFAULT NULL COMMENT '术语链接',\n" +
                "  `TermLayer` int(20) DEFAULT NULL COMMENT '术语层级',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  PRIMARY KEY (`TermID`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='note 是标记';";
        String sqlDomainLayerFuzhu = "CREATE TABLE IF NOT EXISTS `domain_layer_fuzhu` (\n" +
                "  `TermID` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '术语ID，存储各级未处理的主题（存储含有子主题的主题）',\n" +
                "  `TermName` varchar(100) DEFAULT NULL COMMENT '术语名',\n" +
                "  `TermUrl` varchar(255) DEFAULT NULL COMMENT '术语链接',\n" +
                "  `TermLayer` int(20) DEFAULT NULL COMMENT '术语层级',\n" +
                "  `isCatalog` int(20) DEFAULT NULL COMMENT '该主题是不是Catalog页面的主题：1表示是，0表示不是',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  PRIMARY KEY (`TermID`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='note 是标记';";
        String sqlDomainLayerFuzhu2 = "CREATE TABLE IF NOT EXISTS `domain_layer_fuzhu2` (\n" +
                "  `TermID` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '术语ID，删除重复的主题（含有子主题的主题）',\n" +
                "  `TermName` varchar(100) DEFAULT NULL COMMENT '术语名',\n" +
                "  `TermUrl` varchar(255) DEFAULT NULL COMMENT '术语链接',\n" +
                "  `TermLayer` int(20) DEFAULT NULL COMMENT '术语层级',\n" +
                "  `isCatalog` int(20) DEFAULT NULL COMMENT '该主题是不是Catalog页面的主题：1表示是，0表示不是',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  PRIMARY KEY (`TermID`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='note 是标记';";
        String sqlDomainLayerRelation = "CREATE TABLE IF NOT EXISTS `domain_layer_relation` (\n" +
                "  `TopicRelationId` int(20) NOT NULL AUTO_INCREMENT COMMENT '术语关系ID，存储未处理的主题上下位关系（含有子主题的主题）',\n" +
                "  `Parent` varchar(100) DEFAULT NULL COMMENT '上位术语名',\n" +
                "  `ParentLayer` int(20) DEFAULT NULL COMMENT '上位术语层级',\n" +
                "  `Child` varchar(100) DEFAULT NULL COMMENT '下位术语名',\n" +
                "  `ChildLayer` int(20) DEFAULT NULL COMMENT '下位术语层级',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  PRIMARY KEY (`TopicRelationId`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
        String sqlDomainTopic = "CREATE TABLE IF NOT EXISTS `domain_topic` (\n" +
                "  `TermID` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主题ID',\n" +
                "  `TermName` varchar(100) DEFAULT NULL COMMENT '主题名',\n" +
                "  `TermUrl` varchar(255) DEFAULT NULL COMMENT '主题链接',\n" +
                "  `TermLayer` int(20) DEFAULT NULL COMMENT '主题层级',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  `ClassID` int(20) DEFAULT NULL COMMENT '课程ID',\n" +
                "  PRIMARY KEY (`TermID`),\n" +
                "  KEY `domainTopic_ClassName_TermName` (`ClassName`,`TermName`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
        String sqlDomainTopicRelation = "CREATE TABLE IF NOT EXISTS `domain_topic_relation` (\n" +
                "  `Parent` varchar(100) DEFAULT NULL COMMENT '无重复的主题关系表格，算法跑的，需要人工参与，不适合自动构建',\n" +
                "  `Child` varchar(100) DEFAULT NULL COMMENT '子主题',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  KEY `domainTopicRelation_ClassName_Parent` (`ClassName`,`Parent`)\n" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
        String sqlFacet = "CREATE TABLE IF NOT EXISTS `facet` (\n" +
                "  `TermID` int(20) NOT NULL COMMENT '主题ID',\n" +
                "  `TermName` varchar(100) DEFAULT NULL COMMENT '主题名',\n" +
                "  `FacetName` varchar(100) DEFAULT NULL COMMENT '分面名',\n" +
                "  `FacetLayer` int(20) DEFAULT NULL COMMENT '分面层级',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  KEY `facet_ClassName_FacetName_FacetLayer` (`ClassName`,`FacetName`,`FacetLayer`),\n" +
                "  KEY `facet_ClassName_FacetLayer` (`ClassName`,`FacetLayer`),\n" +
                "  KEY `facet_TermName_ClassName_FacetLayer` (`TermName`,`ClassName`,`FacetLayer`)\n" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
        String sqlFacetRelation = "CREATE TABLE IF NOT EXISTS `facet_relation` (\n" +
                "  `ChildFacet` varchar(100) DEFAULT NULL COMMENT '子分面名',\n" +
                "  `ChildLayer` int(20) DEFAULT NULL COMMENT '子分面层级',\n" +
                "  `ParentFacet` varchar(100) DEFAULT NULL COMMENT '父分面名',\n" +
                "  `ParentLayer` int(20) DEFAULT NULL COMMENT '父分面层级',\n" +
                "  `TermID` int(20) NOT NULL COMMENT '主题ID',\n" +
                "  `TermName` varchar(100) DEFAULT NULL COMMENT '主题名',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  KEY `facetRelation_ClassName_TermName_ChildFacet_ChildLayer` (`ClassName`,`TermName`,`ChildFacet`,`ChildLayer`)\n" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
        String sqlFragment = "CREATE TABLE IF NOT EXISTS `fragment` (\n" +
                "  `FragmentID` int(20) NOT NULL AUTO_INCREMENT COMMENT '碎片ID',\n" +
                "  `FragmentContent` longtext NOT NULL COMMENT '碎片内容',\n" +
                "  `FragmentScratchTime` datetime DEFAULT NULL COMMENT '碎片爬取时间',\n" +
                "  PRIMARY KEY (`FragmentID`)\n" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
        String sqlSource = "CREATE TABLE IF NOT EXISTS `source` (\n" +
                "  `SourceID` int(20) NOT NULL AUTO_INCREMENT COMMENT '数据源ID',\n" +
                "  `SourceName` varchar(100) DEFAULT NULL COMMENT '数据源名',\n" +
                "  `Note` varchar(100) DEFAULT NULL COMMENT '说明',\n" +
                "  PRIMARY KEY (`SourceID`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
        String sqlSubject = "CREATE TABLE IF NOT EXISTS `subject` (\n" +
                "  `SubjectID` int(20) NOT NULL AUTO_INCREMENT COMMENT '学科ID',\n" +
                "  `SubjectName` varchar(100) DEFAULT NULL COMMENT '学科',\n" +
                "  `Note` varchar(100) DEFAULT NULL COMMENT '说明',\n" +
                "  PRIMARY KEY (`SubjectID`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
        String sqlUnaddImage = "CREATE TABLE IF NOT EXISTS `unadd_image` (\n" +
                "  `ImageID` int(20) NOT NULL AUTO_INCREMENT COMMENT '新增图片ID',\n" +
                "  `ImageContent` longblob COMMENT '新增图片内容',\n" +
                "  `ImageUrl` varchar(255) DEFAULT NULL COMMENT '新增图片链接',\n" +
                "  `ImageScratchTime` datetime DEFAULT NULL COMMENT '新增图片上传时间',\n" +
                "  `ImageAPI` varchar(100) DEFAULT NULL COMMENT '新增图片API',\n" +
                "  PRIMARY KEY (`ImageID`)\n" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
        String sqlUserInfo = "CREATE TABLE IF NOT EXISTS `user_info` (\n" +
                "  `name` varchar(100) NOT NULL COMMENT '用户名',\n" +
                "  `passwd` varchar(100) NOT NULL COMMENT '密码',\n" +
                "  `note` varchar(100) NOT NULL COMMENT '说明'\n" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
        String sqlUserLog = "CREATE TABLE IF NOT EXISTS `user_log` (\n" +
                "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '日志id',\n" +
                "  `name` varchar(100) DEFAULT NULL COMMENT '用户名',\n" +
                "  `passwd` varchar(100) DEFAULT NULL COMMENT '密码',\n" +
                "  `ip` varchar(100) DEFAULT NULL COMMENT '登陆ip地址',\n" +
                "  `ipPlace` varchar(100) DEFAULT NULL COMMENT '登陆地址',\n" +
                "  `logDate` varchar(100) DEFAULT NULL COMMENT '访问时间',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
        try {
            mysql.addDeleteModify(sqlAssembleFragment, params);
            mysql.addDeleteModify(sqlDependncey, params);
            mysql.addDeleteModify(sqlDomain, params);
            mysql.addDeleteModify(sqlDomainLayer, params);
            mysql.addDeleteModify(sqlDomainLayerFuzhu, params);
            mysql.addDeleteModify(sqlDomainLayerFuzhu2, params);
            mysql.addDeleteModify(sqlDomainLayerRelation, params);
            mysql.addDeleteModify(sqlDomainTopic, params);
            mysql.addDeleteModify(sqlDomainTopicRelation, params);
            mysql.addDeleteModify(sqlFacet, params);
            mysql.addDeleteModify(sqlFacetRelation, params);
            mysql.addDeleteModify(sqlFragment, params);
            mysql.addDeleteModify(sqlSource, params);
            mysql.addDeleteModify(sqlSubject, params);
            mysql.addDeleteModify(sqlUnaddImage, params);
            mysql.addDeleteModify(sqlUserInfo, params);
            mysql.addDeleteModify(sqlUserLog, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mysql.closeconnection();
    }

    /**
     * 删除已经爬取的碎片中质量差的碎片
     */
    public static void deleteBadFragment(){
        String[] badTexts = {"本条目", "主条目", "目标页面不存在", "此章节未", "外部链接", "[隐藏]", "参考文献", "延伸阅读", "参见", "[显示]", "[编辑]"};
        String[] badTexts1 = {"隐藏", "显示", "编辑"};
        mysqlUtils mysql = new mysqlUtils();
        List<Object> params = new ArrayList<Object>();
        String sql = "";
        for (int i = 0; i < badTexts.length; i++) {
            sql = "delete from " + Config.ASSEMBLE_FRAGMENT_TABLE + " where FragmentContent like '%" + badTexts[i] + "%'";
            try {
                mysql.addDeleteModify(sql, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < badTexts1.length; i++) {
            sql = "delete from " + Config.ASSEMBLE_FRAGMENT_TABLE + " where FragmentContent like '%[%" + badTexts1[i] + "%]%'";
            try {
                mysql.addDeleteModify(sql, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mysql.closeconnection();
    }

    /**
     * 1. 将原来已有碎片的数据源SourceName列填上：“中文维基”
     * 2. 将fragmentContent带html标签的碎片内容解析成纯文本内容存储到Text字段中
     */
    public static void updateSourceName(){
        mysqlUtils mysql = new mysqlUtils();

        // 1. 将原来已有碎片的数据源SourceName列填上：“中文维基”
        String sql = "update " + Config.ASSEMBLE_FRAGMENT_TABLE + " set SourceName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add("中文维基");
        try {
            mysql.addDeleteModify(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. 将fragmentContent带html标签的碎片内容解析成纯文本内容存储到Text字段中
        // 2.1 解析原有数据
        sql = "select FragmentID, FragmentContent from " + Config.ASSEMBLE_FRAGMENT_TABLE;
        List<AssembleFragment2> assembleFragment2List = new ArrayList<>();
        List<Object> paramsSelect = new ArrayList<Object>();
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, paramsSelect);
            for (int i = 0; i < results.size(); i++) {
                AssembleFragment2 assembleFragment2 = new AssembleFragment2();
                int fragmentID = Integer.parseInt(results.get(i).get("FragmentID").toString());
                String fragmentContent = results.get(i).get("FragmentContent").toString();
                String pureText = JsoupDao.parseHtmlText(fragmentContent).text(); // jsoup解析获取纯文本内容
                assembleFragment2.setFragmentID(fragmentID);
                assembleFragment2.setText(pureText);
                assembleFragment2List.add(assembleFragment2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 2.2 更新Text字段
        sql = "update " + Config.ASSEMBLE_FRAGMENT_TABLE + " set Text = ? where FragmentID = ?";
        for (int i = 0; i < assembleFragment2List.size(); i++) {
            List<Object> paramsUpdate = new ArrayList<Object>();
            paramsUpdate.add(assembleFragment2List.get(i).getText());
            paramsUpdate.add(assembleFragment2List.get(i).getFragmentID());
            try {
                mysql.addDeleteModify(sql, paramsUpdate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 关闭数据库连接
        mysql.closeconnection();
    }

    /**
     * 创建Yotta数据库所有表格的索引
     */
    public static void createIndex() {
        mysqlUtils mysql = new mysqlUtils();
        List<Object> params = new ArrayList<Object>();

        String sql1 = "CREATE INDEX domainTopic_ClassName_TermName ON domain_topic (ClassName, TermName);";
        String sql2 = "CREATE INDEX domainTopicRelation_ClassName_Parent ON domain_topic_relation (ClassName, Parent);";
        String sql3 = "CREATE INDEX facet_ClassName_FacetName_FacetLayer ON facet (ClassName, FacetName, FacetLayer);";
        String sql4 = "CREATE INDEX facet_ClassName_FacetLayer ON facet (ClassName, FacetLayer);";
        String sql5 = "CREATE INDEX facetRelation_ClassName_TermName_ChildFacet_ChildLayer ON facet_relation (ClassName, TermName, ChildFacet, ChildLayer);";
        String sql6 = "CREATE INDEX assembleFragment_ClassName_TermName_FacetName ON assemble_fragment (ClassName, TermName, FacetName);";
        String sql7 = "CREATE INDEX dependency_ClassName ON dependency (ClassName);";
        String sql8 = "CREATE INDEX facet_TermName_ClassName_FacetLayer ON facet (TermName, ClassName, FacetLayer);";

        try {
            mysql.addDeleteModify(sql1, params);
            mysql.addDeleteModify(sql2, params);
            mysql.addDeleteModify(sql3, params);
            mysql.addDeleteModify(sql4, params);
            mysql.addDeleteModify(sql5, params);
            mysql.addDeleteModify(sql6, params);
            mysql.addDeleteModify(sql7, params);
            mysql.addDeleteModify(sql8, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mysql.closeconnection();
    }

    public static void deleteDbByClassName(String domainName) {
//        String domainName = "理论物理学家";
        // 删除数据库中这门课程的数据
        List<String> tableList = new ArrayList<>();
        tableList.add(Config.DOMAIN_TABLE);
        tableList.add(Config.DOMAIN_LAYER_TABLE);
        tableList.add(Config.DOMAIN_LAYER_FUZHU_TABLE);
        tableList.add(Config.DOMAIN_LAYER_FUZHU2_TABLE);
        tableList.add(Config.DOMAIN_LAYER_RELATION_TABLE);
        tableList.add(Config.DOMAIN_TOPIC_TABLE);
        tableList.add(Config.DOMAIN_TOPIC_RELATION_TABLE);
        tableList.add(Config.FACET_TABLE);
        tableList.add(Config.FACET_RELATION_TABLE);
        tableList.add(Config.ASSEMBLE_FRAGMENT_TABLE);
        tableList.add(Config.DEPENDENCY);
        for (int i = 0; i < tableList.size(); i++) {
            deleteByTableAndDomain(tableList.get(i), domainName);
        }
        // 更新数据库表格自动增长ID的值
        HashMap<String, Integer> map = getMaxId();
        resetTableIncrement(map);
    }

    /**
     * 根据领域名删除数据
     * @param table 数据库表格名
     * @param domainName 领域名
     */
    public static void deleteByTableAndDomain(String table, String domainName) {
        mysqlUtils mysql = new mysqlUtils();
        String sql = "delete from " + table + " where ClassName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(domainName);
        try {
            mysql.addDeleteModify(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mysql.closeconnection();
    }

    /**
     * 获取所有表格当前最大的编号值+1，设置每个表格自动增长的值为该值
     */
    public static HashMap<String, Integer> getMaxId() {
        HashMap<String, Integer> map = new HashMap<>();
        mysqlUtils mysql = new mysqlUtils();
        List<Object> params = new ArrayList<>();
        String sqlDomain = "select max(ClassID) as maxClassID from " + Config.DOMAIN_TABLE;
        String sqlDomainLayer = "select max(TermID) as maxTermID from " + Config.DOMAIN_LAYER_TABLE;
        String sqlDomainLayerFuzhu = "select max(TermID) as maxTermID from " + Config.DOMAIN_LAYER_FUZHU_TABLE;
        String sqlDomainLayerFuzhu2 = "select max(TermID) as maxTermID from " + Config.DOMAIN_LAYER_FUZHU2_TABLE;
        String sqlDomainLayerRelation = "select max(TopicRelationId) as maxTopicRelationId from " + Config.DOMAIN_LAYER_RELATION_TABLE;
        String sqlDomainTopic = "select max(TermID) as maxTermID from " + Config.DOMAIN_TOPIC_TABLE;
        String sqlAssembleFragment = "select max(FragmentID) as maxFragmentID from " + Config.ASSEMBLE_FRAGMENT_TABLE;
        try {
            map.put(Config.DOMAIN_TABLE, Integer.parseInt(mysql.returnMultipleResult(sqlDomain, params).get(0).get("maxClassID").toString()) + 1);
            map.put(Config.DOMAIN_LAYER_TABLE, Integer.parseInt(mysql.returnMultipleResult(sqlDomainLayer, params).get(0).get("maxTermID").toString()) + 1);
            map.put(Config.DOMAIN_LAYER_FUZHU_TABLE, Integer.parseInt(mysql.returnMultipleResult(sqlDomainLayerFuzhu, params).get(0).get("maxTermID").toString()) + 1);
            map.put(Config.DOMAIN_LAYER_FUZHU2_TABLE, Integer.parseInt(mysql.returnMultipleResult(sqlDomainLayerFuzhu2, params).get(0).get("maxTermID").toString()) + 1);
            map.put(Config.DOMAIN_LAYER_RELATION_TABLE, Integer.parseInt(mysql.returnMultipleResult(sqlDomainLayerRelation, params).get(0).get("maxTopicRelationId").toString()) + 1);
            map.put(Config.DOMAIN_TOPIC_TABLE, Integer.parseInt(mysql.returnMultipleResult(sqlDomainTopic, params).get(0).get("maxTermID").toString()) + 1);
            map.put(Config.ASSEMBLE_FRAGMENT_TABLE, Integer.parseInt(mysql.returnMultipleResult(sqlAssembleFragment, params).get(0).get("maxFragmentID").toString()) + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mysql.closeconnection();

        for (String table : map.keySet()) {
            System.out.println(table + " \t --> 最大编号为：" + map.get(table));
        }

        return map;
    }

    /**
     * 更新每个表格的auto_increment值
     * @param map
     */
    public static void resetTableIncrement(HashMap<String, Integer> map) {
        mysqlUtils mysql = new mysqlUtils();
        for (String table : map.keySet()) {
            String sql = "alter table " + table + " auto_increment = ?";
            List<Object> params = new ArrayList<>();
            params.add(map.get(table));
            try {
                mysql.addDeleteModify(sql, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mysql.closeconnection();
    }

}
