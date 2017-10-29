package domainTopic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domainTopic.bean.Rela;
import domainTopic.bean.Topic;
import utils.Log;
import utils.mysqlUtils;
import app.Config;

/**
 * 领域主题操作类
 * @author 郑元浩
 * @date 2016年12月5日
 */
public class DomainTopicDAO {

    /**
     * 返回一个主题的termID
     * @param className 课程名
     * @param termName 主题名
     * @return 主题ID
     */
    public static int getDomainTopic(String className, String termName) {
        int termID = 0;
        /**
         * 读取domain_topic，获得每一层知识主题
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.DOMAIN_TOPIC_TABLE + " where ClassName = ? and TermName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(termName);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            /**
             * 一个关键词只有一个ID，返回一条记录
             */
            if (results.size() == 1) {
                termID = Integer.parseInt(results.get(0).get("TermID").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        return termID;
    }

    /**
     * 返回所有主题
     * @param className 课程名
     * @return 主题列表
     */
    public static List<String> getDomainTopicList(String className) {
        List<String> topicList = new ArrayList<String>();
        /**
         * 读取domain_topic，获得每一层知识主题
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.DOMAIN_TOPIC_TABLE + " where ClassName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            for (int i = 0; i < results.size(); i++) {
                Map<String, Object> map = results.get(i);
                String topic = map.get("TermName").toString();
                topicList.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        return topicList;
    }

    /**
     * 返回一门课程的所有主题之间的上下位关系，按照一门课的主题进行关系查询
     * @param className 课程名
     * @return 主题上下位关系
     */
    public static List<Rela> getRelationList(String className, String parentTopic) {
        List<Rela> relaList = new ArrayList<Rela>();
        /**
         * 读取domain_topic，获得每一层知识主题
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.DOMAIN_TOPIC_RELATION_TABLE + " where ClassName = ? and Parent = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(parentTopic);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            for (int i = 0; i < results.size(); i++) {
                Map<String, Object> map = results.get(i);
                String child = map.get("Child").toString();
                Rela topic = new Rela(parentTopic, child);
                relaList.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        return relaList;
    }

    private static int topicNum = 0;
    private static List<Rela> relaListRec = new ArrayList<Rela>();
    /**
     * 递归实现获取所有主题关系数据
     * 递归很关键，很强势
     * @param className 课程名
     * @param topicNeed 父主题
     * @return 该课程上下位主题关系
     */
    public static Topic getRelationAll(String className, String topicNeed) {
        /**
         * 用于存储该父亲节点的所有下位主题信息
         */
        Topic topicAll = new Topic();
        Object data = "";
//		int parentID = DomainTopicDAO.getDomainTopic(className, topicNeed); // 得到父主题ID
        int parentID = topicNum++; // 得到父主题ID
        List<Topic> childrenList = new ArrayList<Topic>();
        /**
         * 获取该知识主题相关的上下位关系的主题信息
         */
        List<Rela> relaList = DomainTopicDAO.getRelationList(className, topicNeed);
        if (relaList.size() != 0) {
            /**
             * 存在上下位关系的节点，递归实现
             */
            for (int i = 0; i < relaList.size(); i++) {
                Rela rela = relaList.get(i);
                String child = rela.getChild();
                Log.log(rela.getParent() + "-->" + rela.getChild());
                boolean flag = true;
                for (int j = 0; j < relaListRec.size(); j++) {
                    if (rela.getParent().equals(relaListRec.get(j).getChild())
                            && rela.getChild().equals(relaListRec.get(j).getParent())) {
                        flag = false;
                    }
                }
                if (!flag || rela.getParent().equals(rela.getChild())
                        || rela.getChild().equals(className)
                        || rela.getParent().toLowerCase().equals(rela.getChild().toLowerCase())) {
                    continue;
                }
                relaListRec.add(rela);
                Topic topicChild = getRelationAll(className, child);
                childrenList.add(topicChild);
            }
        } else {
			Log.log("没有下位主题");
        }
        /**
         * 不存在上下位关系节点，直接保存该节点到总的信息链表中
         * 将该上下位关系保存到结果集合中
         */
        topicAll.setId(parentID);
        topicAll.setName(topicNeed);
        topicAll.setData(data);
        topicAll.setChildren(childrenList);
        return topicAll;
    }

}
