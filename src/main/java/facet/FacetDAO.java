package facet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utils.Log;
import utils.mysqlUtils;
import app.Config;
import facet.bean.FacetRelation;
import facet.bean.FacetSimple;

/**
 * 分面树构建需要的类
 * @author 郑元浩
 * @date 2016年12月5日
 */
public class FacetDAO {

    /**
     * 得到某个领域某个主题的某一级所有分面信息
     * @param className 课程名
     * @param topicName 主题名
     * @param facetLayer 分面层级
     * @return 简单分面列表信息
     */
    public static List<FacetSimple> getFacet(String className, String topicName, int facetLayer) {
        List<FacetSimple> facetSimpleList = new ArrayList<FacetSimple>();
        /**
         * 读取facet和facet_relation，获得知识点的多级分面
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select FacetName, FacetLayer from " + Config.FACET_TABLE + " where ClassName=? and TermName=? and FacetLayer=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        params.add(facetLayer);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            for (int i = 0; i < results.size(); i++) {
                Map<String, Object> map = results.get(i);
                String facetName = map.get("FacetName").toString();
                FacetSimple facetSimple = new FacetSimple(facetName, facetLayer);
                facetSimpleList.add(facetSimple);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        return facetSimpleList;
    }

    /**
     * 得到某个领域某个主题的两级分面关系
     * @param className 课程名
     * @param topicName 主题名
     * @param parentLayer 父分面层级
     * @param childLayer 子分面层级
     * @return 分面关系列表
     */
    public static List<FacetRelation> getFacetRelation(String className, String topicName, int parentLayer, int childLayer) {
        List<FacetRelation> facetRelationList = new ArrayList<FacetRelation>();
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select ChildFacet, ChildLayer, ParentFacet, ParentLayer from "
                + Config.FACET_RELATION_TABLE + " where ClassName=? and TermName=? "
                + "and ParentLayer = ? and ChildLayer = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        params.add(parentLayer);
        params.add(childLayer);
        try {
            List<Map<String, Object>> resultsRelation = mysql.returnMultipleResult(sql, params);
            for (int i = 0; i < resultsRelation.size(); i++) {
                Map<String, Object> mapRelation = resultsRelation.get(i);
                String parentFacet = mapRelation.get("ParentFacet").toString();
                String childFacet = mapRelation.get("ChildFacet").toString();
                FacetRelation facetRelation = new FacetRelation(childFacet, childLayer, parentFacet, parentLayer);
                Log.log(facetRelation);
                facetRelationList.add(facetRelation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        return facetRelationList;
    }

    /**
     * 根据输入分面信息，得到其子分面的信息
     * @param facetSimple 简单分面
     * @param facetRelationList 分面关系列表
     * @return 子分面列表
     */
    public static List<FacetSimple> getChildFacet(FacetSimple facetSimple, List<FacetRelation> facetRelationList) {
        List<FacetSimple> facetSimpleList = new ArrayList<FacetSimple>();
        String facetName = facetSimple.getFacetName();
        for (int i = 0; i < facetRelationList.size(); i++) {
            /**
             * 遍历关系表格中的每条关系
             */
            FacetRelation facetRelation = facetRelationList.get(i);
            String parentFacet = facetRelation.getParentFacet();
            String childFacetName = facetRelation.getChildFacet();
            int childLayer = facetRelation.getChildLayer();
            if (facetName.equals(parentFacet)) {
                /**
                 * 存在子分面，保存子分面信息
                 */
                FacetSimple childFacet = new FacetSimple(childFacetName, childLayer);
                facetSimpleList.add(childFacet);
            }
        }
        return facetSimpleList;
    }

}
