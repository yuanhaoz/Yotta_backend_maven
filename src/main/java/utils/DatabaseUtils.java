package utils;

import app.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库基本操作：
 * 1. 新建表格（基本表格的建表是靠程序自动生成的，而不是手动设置）
 * 2. 删除assemble_fragment中无用的碎片
 *
 * @author yuanhao
 * @date 2017/12/20 9:12
 */
public class DatabaseUtils {

    public static void main(String[] args) {
//        deleteBadFragment();
        createTable();
    }

    public static void createTable() {
        mysqlUtils mysql = new mysqlUtils();
        List<Object> params = new ArrayList<Object>();
        String sqlAssembleFragment = "CREATE TABLE IF NOT EXISTS `assemble_fragment` (\n" +
                "  `FragmentID` int(20) NOT NULL AUTO_INCREMENT COMMENT '碎片ID',\n" +
                "  `FragmentContent` longtext CHARACTER SET utf8 NOT NULL COMMENT '碎片内容（包含文本和图片，html形式）',\n" +
                "  `Text` longtext CHARACTER SET utf8 NOT NULL COMMENT '碎片内容（只有文本，纯文本形式）',\n" +
                "  `FragmentScratchTime` datetime DEFAULT NULL COMMENT '碎片爬取时间',\n" +
                "  `TermID` int(20) DEFAULT NULL COMMENT '主题ID',\n" +
                "  `TermName` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '主题名',\n" +
                "  `FacetName` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '分面名',\n" +
                "  `FacetLayer` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '分面层数',\n" +
                "  `ClassName` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '课程名',\n" +
                "  `SourceName` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '数据源名',\n" +
                "  PRIMARY KEY (`FragmentID`),\n" +
                "  KEY `assembleFragment_ClassName_TermName_FacetName` (`ClassName`,`TermName`,`FacetName`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=2750 DEFAULT CHARSET=utf8;";
        String sqlDependncey = "CREATE TABLE IF NOT EXISTS `dependency` (\n" +
                "  `ClassName` varchar(255) DEFAULT NULL COMMENT '课程名',\n" +
                "  `Start` varchar(255) DEFAULT NULL COMMENT '父主题名',\n" +
                "  `StartID` varchar(255) DEFAULT NULL COMMENT '父主题ID',\n" +
                "  `End` varchar(255) DEFAULT NULL COMMENT '子主题名',\n" +
                "  `EndID` varchar(255) DEFAULT NULL COMMENT '子主题ID',\n" +
                "  `Confidence` varchar(255) DEFAULT NULL COMMENT '认知关系置信度',\n" +
                "  KEY `dependency_ClassName` (`ClassName`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        String sqlDomain = "CREATE TABLE IF NOT EXISTS `domain` (\n" +
                "  `ClassID` int(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',\n" +
                "  `ClassName` varchar(255) DEFAULT NULL COMMENT '课程名',\n" +
                "  `SubjectName` varchar(255) DEFAULT NULL COMMENT '学科名',\n" +
                "  `Note` varchar(255) DEFAULT NULL COMMENT '备注',\n" +
                "  PRIMARY KEY (`ClassID`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;";
        String sqlDomainLayer = "CREATE TABLE IF NOT EXISTS `domain_layer` (\n" +
                "  `TermID` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '术语ID，存储各级未处理的主题（没有含有子主题的主题）',\n" +
                "  `TermName` varchar(255) DEFAULT NULL COMMENT '术语名',\n" +
                "  `TermUrl` varchar(255) DEFAULT NULL COMMENT '术语链接',\n" +
                "  `TermLayer` int(20) DEFAULT NULL COMMENT '术语层级',\n" +
                "  `ClassName` varchar(255) DEFAULT NULL COMMENT '课程名',\n" +
                "  PRIMARY KEY (`TermID`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COMMENT='note 是标记';";
        String sqlDomainLayerFuzhu = "CREATE TABLE IF NOT EXISTS `domain_layer_fuzhu` (\n" +
                "  `TermID` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '术语ID，存储各级未处理的主题（存储含有子主题的主题）',\n" +
                "  `TermName` varchar(255) DEFAULT NULL COMMENT '术语名',\n" +
                "  `TermUrl` varchar(255) DEFAULT NULL COMMENT '术语链接',\n" +
                "  `TermLayer` int(20) DEFAULT NULL COMMENT '术语层级',\n" +
                "  `isCatalog` int(20) DEFAULT NULL COMMENT '该主题是不是Catalog页面的主题：1表示是，0表示不是',\n" +
                "  `ClassName` varchar(255) DEFAULT NULL COMMENT '课程名',\n" +
                "  PRIMARY KEY (`TermID`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COMMENT='note 是标记';";
        String sqlDomainLayerFuzhu2 = "CREATE TABLE IF NOT EXISTS `domain_layer_fuzhu2` (\n" +
                "  `TermID` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '术语ID，删除重复的主题（含有子主题的主题）',\n" +
                "  `TermName` varchar(255) DEFAULT NULL COMMENT '术语名',\n" +
                "  `TermUrl` varchar(255) DEFAULT NULL COMMENT '术语链接',\n" +
                "  `TermLayer` int(20) DEFAULT NULL COMMENT '术语层级',\n" +
                "  `isCatalog` int(20) DEFAULT NULL COMMENT '该主题是不是Catalog页面的主题：1表示是，0表示不是',\n" +
                "  `ClassName` varchar(255) DEFAULT NULL COMMENT '课程名',\n" +
                "  PRIMARY KEY (`TermID`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='note 是标记';";
        String sqlDomainLayerRelation = "CREATE TABLE IF NOT EXISTS `domain_layer_relation` (\n" +
                "  `TopicRelationId` int(20) NOT NULL AUTO_INCREMENT COMMENT '术语关系ID，存储未处理的主题上下位关系（含有子主题的主题）',\n" +
                "  `Parent` varchar(255) DEFAULT NULL COMMENT '上位术语名',\n" +
                "  `ParentLayer` int(20) DEFAULT NULL COMMENT '上位术语层级',\n" +
                "  `Child` varchar(255) DEFAULT NULL COMMENT '下位术语名',\n" +
                "  `ChildLayer` int(20) DEFAULT NULL COMMENT '下位术语层级',\n" +
                "  `ClassName` varchar(255) DEFAULT NULL COMMENT '课程名',\n" +
                "  PRIMARY KEY (`TopicRelationId`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;";
        String sqlDomainTopic = "CREATE TABLE IF NOT EXISTS `domain_topic` (\n" +
                "  `TermID` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主题ID',\n" +
                "  `TermName` varchar(100) DEFAULT NULL COMMENT '主题名',\n" +
                "  `TermUrl` varchar(255) DEFAULT NULL COMMENT '主题链接',\n" +
                "  `TermLayer` int(20) DEFAULT NULL COMMENT '主题层级',\n" +
                "  `ClassName` varchar(100) DEFAULT NULL COMMENT '课程名',\n" +
                "  `ClassID` int(20) DEFAULT NULL COMMENT '课程ID',\n" +
                "  PRIMARY KEY (`TermID`),\n" +
                "  KEY `domainTopic_ClassName_TermName` (`ClassName`,`TermName`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;";
        String sqlDomainTopicRelation = "CREATE TABLE IF NOT EXISTS `domain_topic_relation` (\n" +
                "  `Parent` varchar(255) DEFAULT NULL COMMENT '无重复的主题关系表格，算法跑的，需要人工参与，不适合自动构建',\n" +
                "  `Child` varchar(255) DEFAULT NULL COMMENT '子主题',\n" +
                "  `ClassName` varchar(255) DEFAULT NULL COMMENT '课程名',\n" +
                "  KEY `domainTopicRelation_ClassName_Parent` (`ClassName`,`Parent`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        String sqlFacet = "CREATE TABLE IF NOT EXISTS `facet` (\n" +
                "  `TermID` int(20) NOT NULL COMMENT '主题ID',\n" +
                "  `TermName` varchar(255) DEFAULT NULL COMMENT '主题名',\n" +
                "  `FacetName` varchar(255) DEFAULT NULL COMMENT '分面名',\n" +
                "  `FacetLayer` int(20) DEFAULT NULL COMMENT '分面层级',\n" +
                "  `ClassName` varchar(255) DEFAULT NULL COMMENT '课程名',\n" +
                "  KEY `facet_ClassName_FacetName_FacetLayer` (`ClassName`,`FacetName`,`FacetLayer`),\n" +
                "  KEY `facet_ClassName_FacetLayer` (`ClassName`,`FacetLayer`),\n" +
                "  KEY `facet_TermName_ClassName_FacetLayer` (`TermName`,`ClassName`,`FacetLayer`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        String sqlFacetRelation = "CREATE TABLE IF NOT EXISTS `facet_relation` (\n" +
                "  `ChildFacet` varchar(255) DEFAULT NULL COMMENT '子分面名',\n" +
                "  `ChildLayer` int(20) DEFAULT NULL COMMENT '子分面层级',\n" +
                "  `ParentFacet` varchar(255) DEFAULT NULL COMMENT '父分面名',\n" +
                "  `ParentLayer` int(20) DEFAULT NULL COMMENT '父分面层级',\n" +
                "  `TermID` int(20) NOT NULL COMMENT '主题ID',\n" +
                "  `TermName` varchar(255) DEFAULT NULL COMMENT '主题名',\n" +
                "  `ClassName` varchar(255) DEFAULT NULL COMMENT '课程名',\n" +
                "  KEY `facetRelation_ClassName_TermName_ChildFacet_ChildLayer` (`ClassName`,`TermName`,`ChildFacet`,`ChildLayer`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        String sqlFragment = "CREATE TABLE IF NOT EXISTS `fragment` (\n" +
                "  `FragmentID` int(20) NOT NULL AUTO_INCREMENT COMMENT '碎片ID',\n" +
                "  `FragmentContent` longtext NOT NULL COMMENT '碎片内容',\n" +
                "  `FragmentScratchTime` datetime DEFAULT NULL COMMENT '碎片爬取时间',\n" +
                "  PRIMARY KEY (`FragmentID`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        String sqlSource = "CREATE TABLE IF NOT EXISTS `source` (\n" +
                "  `SourceID` int(20) NOT NULL AUTO_INCREMENT COMMENT '数据源ID',\n" +
                "  `SourceName` varchar(255) DEFAULT NULL COMMENT '数据源名',\n" +
                "  `Note` varchar(255) DEFAULT NULL COMMENT '说明',\n" +
                "  PRIMARY KEY (`SourceID`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;";
        String sqlSubject = "CREATE TABLE IF NOT EXISTS `subject` (\n" +
                "  `SubjectID` int(20) NOT NULL AUTO_INCREMENT COMMENT '学科ID',\n" +
                "  `SubjectName` varchar(255) DEFAULT NULL COMMENT '学科',\n" +
                "  `Note` varchar(255) DEFAULT NULL COMMENT '说明',\n" +
                "  PRIMARY KEY (`SubjectID`)\n" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
        String sqlUnaddImage = "CREATE TABLE IF NOT EXISTS `unadd_image` (\n" +
                "  `ImageID` int(20) NOT NULL AUTO_INCREMENT COMMENT '新增图片ID',\n" +
                "  `ImageContent` longblob COMMENT '新增图片内容',\n" +
                "  `ImageUrl` varchar(255) DEFAULT NULL COMMENT '新增图片链接',\n" +
                "  `ImageScratchTime` datetime DEFAULT NULL COMMENT '新增图片上传时间',\n" +
                "  `ImageAPI` varchar(255) DEFAULT NULL COMMENT '新增图片API',\n" +
                "  PRIMARY KEY (`ImageID`)\n" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
        String sqlUserInfo = "CREATE TABLE IF NOT EXISTS `user_info` (\n" +
                "  `name` varchar(255) NOT NULL COMMENT '用户名',\n" +
                "  `passwd` varchar(255) NOT NULL COMMENT '密码',\n" +
                "  `note` varchar(255) NOT NULL COMMENT '说明'\n" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
        String sqlUserLog = "CREATE TABLE IF NOT EXISTS `user_log` (\n" +
                "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '日志id',\n" +
                "  `name` varchar(255) DEFAULT NULL COMMENT '用户名',\n" +
                "  `passwd` varchar(255) DEFAULT NULL COMMENT '密码',\n" +
                "  `ip` varchar(255) DEFAULT NULL COMMENT '登陆ip地址',\n" +
                "  `ipPlace` varchar(255) DEFAULT NULL COMMENT '登陆地址',\n" +
                "  `logDate` varchar(255) DEFAULT NULL COMMENT '访问时间',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;";
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

}
