package questionQuality;

import app.Config;
import app.error;
import app.success;
import facet.bean.FacetSimple;
import io.swagger.annotations.*;
import spider.bean.AssembleFragmentQuestionAndAsker;
import utils.mysqlUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**  
 * CQA问题质量预测
 * @author 郑元浩 
 */
@Path("/QuestionQualityAPI")
@Api(value = "QuestionQualityAPI")
public class QuestionQualityAPI {

    @POST
    @Path("/deleteQuestionsByTopicAndFacetAndSource")
    @ApiOperation(value = "输入课程、主题、分面、数据源，删除课程的所有碎片", notes = "输入课程、主题、分面、数据源，删除课程的所有碎片")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response deleteQuestionsByTopicAndFacetAndSource(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName,
            @FormParam("facetName") String facetName,
            @FormParam("sourceName") String sourceName
    ) {
        Response response = null;
        mysqlUtils mysql = new mysqlUtils();
        String sql = "delete from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and FacetName=? and SourceName=? and question_quality_label=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        params.add(facetName);
        params.add(sourceName);
        params.add("low");
        try {
            mysql.addDeleteModify(sql, params);
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(new success("删除成功")).build();
        return response;
    }

    @POST
    @Path("/deleteQuestionsByTopicAndFacet")
    @ApiOperation(value = "输入课程、主题、分面，删除课程的所有碎片", notes = "输入课程、主题、分面，删除课程的所有碎片")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response deleteQuestionsByTopicAndFacet(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName,
            @FormParam("facetName") String facetName
    ) {
        Response response = null;
        mysqlUtils mysql = new mysqlUtils();
        String sql = "delete from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and FacetName=? and question_quality_label=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        params.add(facetName);
        params.add("low");
        try {
            mysql.addDeleteModify(sql, params);
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(new success("删除成功")).build();
        return response;
    }

    @POST
    @Path("/deleteQuestionsByTopicAndSource")
    @ApiOperation(value = "输入课程、主题、数据源，删除课程的所有碎片", notes = "输入课程、主题、数据源，删除课程的所有碎片")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response deleteQuestionsByTopicAndSource(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName,
            @FormParam("sourceName") String sourceName
    ) {
        Response response = null;
        mysqlUtils mysql = new mysqlUtils();
        String sql = "delete from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and SourceName=? and question_quality_label=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        params.add(sourceName);
        params.add("low");
        try {
            mysql.addDeleteModify(sql, params);
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(new success("删除成功")).build();
        return response;
    }

    @POST
    @Path("/deleteQuestionsByTopic")
    @ApiOperation(value = "输入课程、主题，删除课程的所有碎片", notes = "输入课程、主题，删除课程的所有碎片")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response deleteQuestionsByTopic(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName
    ) {
        Response response = null;
        mysqlUtils mysql = new mysqlUtils();
        String sql = "delete from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and question_quality_label=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        params.add("low");
        try {
            mysql.addDeleteModify(sql, params);
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(new success("删除成功")).build();
        return response;
    }

    @POST
    @Path("/deleteQuestionsByClass")
    @ApiOperation(value = "输入课程，删除课程的所有碎片", notes = "输入课程，删除课程的所有碎片")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response deleteQuestionsByClass(
            @FormParam("className") String className
    ) {
        Response response = null;
        mysqlUtils mysql = new mysqlUtils();
        String sql = "delete from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and question_quality_label=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add("low");
        try {
            mysql.addDeleteModify(sql, params);
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(new success("删除成功")).build();
        return response;
    }

    @GET
    @Path("/deleteQuestionByFragmentID")
    @ApiOperation(value = "根据问题碎片Id，删除该问题", notes = "根据问题碎片Id，删除该问题")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response deleteQuestionByFragmentID(
            @DefaultValue("1") @ApiParam(value = "问题碎片ID", required = true) @QueryParam("fragmentID") int fragmentID
    ) {
        Response response = null;
        mysqlUtils mysql = new mysqlUtils();
        String sql = "delete from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where FragmentID=?";
        List<Object> params = new ArrayList<Object>();
        params.add(fragmentID);
        try {
            mysql.addDeleteModify(sql, params);
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(new success("删除成功！")).build();
        return response;
    }

    @POST
    @Path("/getQuestionLabelByTopicAndFacetAndSource")
    @ApiOperation(value = "输入课程、主题、分面、数据源，计算问题质量", notes = "输入课程、主题、分面、数据源，计算问题质量")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getQuestionLabelByTopicAndFacetAndSource(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName,
            @FormParam("facetName") String facetName,
            @FormParam("sourceName") String sourceName
    ) {
        Response response = null;
        /**
         * 计算问题标签
         */
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = QuestionQualityDAO.getQuestionQualityByTopicAndFacetAndSource(className, topicName, facetName, sourceName);
        /**
         * 更新assemble_fragment_question，更新问题标签
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "update " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " set question_quality_label=? where FragmentID=?";
        try {
            for (int i = 0; i < assembleFragmentList.size(); i++) {
                AssembleFragmentQuestionAndAsker assembleFragment = assembleFragmentList.get(i);
                // 更新选定主题的质量标签
                List<Object> params = new ArrayList<Object>();
                params.add(assembleFragment.getQuestion_quality_label());
                params.add(assembleFragment.getFragmentID());
                mysql.addDeleteModify(sql, params);
            }
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(assembleFragmentList).build();
        return response;
    }

    @POST
    @Path("/getQuestionLabelByTopicAndFacet")
    @ApiOperation(value = "输入课程、主题、分面，计算问题质量", notes = "输入课程、主题、分面，计算问题质量")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getQuestionLabelByTopicAndFacet(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName,
            @FormParam("facetName") String facetName
    ) {
        Response response = null;
        /**
         * 计算问题标签
         */
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = QuestionQualityDAO.getQuestionQualityByTopicAndFacet(className, topicName, facetName);
        /**
         * 更新assemble_fragment_question，更新问题标签
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "update " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " set question_quality_label=? where FragmentID=?";
        try {
            for (int i = 0; i < assembleFragmentList.size(); i++) {
                AssembleFragmentQuestionAndAsker assembleFragment = assembleFragmentList.get(i);
                // 更新选定主题的质量标签
                List<Object> params = new ArrayList<Object>();
                params.add(assembleFragment.getQuestion_quality_label());
                params.add(assembleFragment.getFragmentID());
                mysql.addDeleteModify(sql, params);
            }
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(assembleFragmentList).build();
        return response;
    }

    @POST
    @Path("/getQuestionLabelByTopicAndSource")
    @ApiOperation(value = "输入课程、主题、数据源，计算问题质量", notes = "输入课程、主题、数据源，计算问题质量")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getQuestionLabelByTopicAndSource(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName,
            @FormParam("sourceName") String sourceName
    ) {
        Response response = null;
        /**
         * 计算问题标签
         */
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = QuestionQualityDAO.getQuestionQualityByTopicAndSource(className, topicName, sourceName);
        /**
         * 更新assemble_fragment_question，更新问题标签
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "update " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " set question_quality_label=? where FragmentID=?";
        try {
            for (int i = 0; i < assembleFragmentList.size(); i++) {
                AssembleFragmentQuestionAndAsker assembleFragment = assembleFragmentList.get(i);
                // 更新选定主题的质量标签
                List<Object> params = new ArrayList<Object>();
                params.add(assembleFragment.getQuestion_quality_label());
                params.add(assembleFragment.getFragmentID());
                mysql.addDeleteModify(sql, params);
            }
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(assembleFragmentList).build();
        return response;
    }

    @POST
    @Path("/getQuestionLabelByTopic")
    @ApiOperation(value = "输入课程、主题，计算问题质量", notes = "输入课程、主题，计算问题质量")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getQuestionLabelByTopic(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName
    ) {
        Response response = null;
        /**
         * 计算问题标签
         */
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = QuestionQualityDAO.getQuestionQualityByTopic(className, topicName);
        /**
         * 更新assemble_fragment_question，更新问题标签
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "update " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " set question_quality_label=? where FragmentID=?";
        try {
            for (int i = 0; i < assembleFragmentList.size(); i++) {
                AssembleFragmentQuestionAndAsker assembleFragment = assembleFragmentList.get(i);
                // 更新选定主题的质量标签
                List<Object> params = new ArrayList<Object>();
                params.add(assembleFragment.getQuestion_quality_label());
                params.add(assembleFragment.getFragmentID());
                mysql.addDeleteModify(sql, params);
            }
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(assembleFragmentList).build();
        return response;
    }

    @POST
    @Path("/getQuestionLabelByClass")
    @ApiOperation(value = "输入课程，计算问题质量", notes = "输入课程，计算问题质量")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getQuestionLabelByClass(
            @FormParam("className") String className
    ) {
        Response response = null;
        /**
         * 计算问题标签
         */
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = QuestionQualityDAO.getQuestionQualityByClass(className);
        /**
         * 更新assemble_fragment_question，更新问题标签
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "update " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " set question_quality_label=? where FragmentID=?";
        try {
            for (int i = 0; i < assembleFragmentList.size(); i++) {
                AssembleFragmentQuestionAndAsker assembleFragment = assembleFragmentList.get(i);
                List<Object> params = new ArrayList<Object>();
                params.add(assembleFragment.getQuestion_quality_label());
                params.add(assembleFragment.getFragmentID());
                mysql.addDeleteModify(sql, params);
            }
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(assembleFragmentList).build();
        return response;
    }


    @GET
    @Path("/getFragmentByFragmentID")
    @ApiOperation(value = "根据碎片Id，获取主题下的碎片数据", notes = "根据碎片Id，获取主题下的碎片数据")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getFragmentByFragmentID(
            @DefaultValue("1") @ApiParam(value = "碎片ID", required = true) @QueryParam("fragmentID") int fragmentID
    ) {
        Response response = null;
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();

        /**
         * 读取assemble_fragment_question，获得主题下的所有问题碎片
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where FragmentID=?";
        List<Object> params = new ArrayList<Object>();
        params.add(fragmentID);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            assembleFragmentList = QuestionQualityDAO.getQuestionsFromSql(results);
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(assembleFragmentList).build();

        return response;
    }

    @POST
    @Path("/getFragmentByTopicAndFacetAndSource")
    @ApiOperation(value = "根据课程名/主题/分面/数据源，获取主题下的碎片数据", notes = "根据课程名/主题/分面/数据源，获取主题下的碎片数据")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getFragmentByTopicAndFacetAndSource(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName,
            @FormParam("facetName") String facetName,
            @FormParam("sourceName") String sourceName
    ) {
        Response response = null;
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();

        /**
         * 判断是否包含子分面，如果包含子分面，将子分面的碎片展示出来
         */
        List<FacetSimple> facetSimpleList = new ArrayList<>();
        mysqlUtils mysqlFacet = new mysqlUtils();
        String sqlFacet = "select * from " + Config.FACET_RELATION_TABLE + " where ClassName=? and TermName=? and ParentFacet=? and ParentLayer=?";
        List<Object> paramsFacet = new ArrayList<Object>();
        paramsFacet.add(className);
        paramsFacet.add(topicName);
        paramsFacet.add(facetName);
        paramsFacet.add(1);
        try {
            List<Map<String, Object>> results = mysqlFacet.returnMultipleResult(sqlFacet, paramsFacet);
            if (results.size() > 0) {
                for (int i = 0; i < results.size(); i++) {
                    FacetSimple facetSimple = new FacetSimple();
                    facetSimple.setFacetName(results.get(i).get("ChildFacet").toString());
                    facetSimple.setFacetLayer(Integer.parseInt(results.get(i).get("ChildLayer").toString()));
                    facetSimpleList.add(facetSimple);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysqlFacet.closeconnection();
        }

        /**
         * 读取assemble_fragment_question，获得主题下的所有问题碎片
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and FacetName=? and SourceName=?";
        try {
            if (facetSimpleList.size() == 0) {
                // 没有子分面，直接是该分面下的碎片
                List<Object> params = new ArrayList<Object>();
                params.add(className);
                params.add(topicName);
                params.add(facetName);
                params.add(sourceName);
                List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
                assembleFragmentList = QuestionQualityDAO.getQuestionsFromSql(results);
            } else {
                // 有子分面，则是该分面的子分面下的碎片
                for (int i = 0; i < facetSimpleList.size(); i++) {
                    List<Object> params = new ArrayList<Object>();
                    params.add(className);
                    params.add(topicName);
                    params.add(facetSimpleList.get(i).getFacetName());
                    params.add(sourceName);
                    List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
                    assembleFragmentList.addAll(QuestionQualityDAO.getQuestionsFromSql(results));
                }
            }
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(assembleFragmentList).build();
        return response;
    }

    @POST
    @Path("/getFragmentByTopicAndFacet")
    @ApiOperation(value = "根据课程名/主题/分面，获取主题下的碎片数据", notes = "根据课程名/主题/分面，获取主题下的碎片数据")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getFragmentByTopicAndFacet(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName,
            @FormParam("facetName") String facetName
    ) {
        Response response = null;
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();

        /**
         * 判断是否包含子分面，如果包含子分面，将子分面的碎片展示出来
         */
        List<FacetSimple> facetSimpleList = new ArrayList<>();
        mysqlUtils mysqlFacet = new mysqlUtils();
        String sqlFacet = "select * from " + Config.FACET_RELATION_TABLE + " where ClassName=? and TermName=? and ParentFacet=? and ParentLayer=?";
        List<Object> paramsFacet = new ArrayList<Object>();
        paramsFacet.add(className);
        paramsFacet.add(topicName);
        paramsFacet.add(facetName);
        paramsFacet.add(1);
        try {
            List<Map<String, Object>> results = mysqlFacet.returnMultipleResult(sqlFacet, paramsFacet);
            if (results.size() > 0) {
                for (int i = 0; i < results.size(); i++) {
                    FacetSimple facetSimple = new FacetSimple();
                    facetSimple.setFacetName(results.get(i).get("ChildFacet").toString());
                    facetSimple.setFacetLayer(Integer.parseInt(results.get(i).get("ChildLayer").toString()));
                    facetSimpleList.add(facetSimple);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysqlFacet.closeconnection();
        }

        /**
         * 读取assemble_fragment_question，获得主题下的所有问题碎片
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and FacetName=?";
        try {
            if (facetSimpleList.size() == 0) {
                // 没有子分面，直接是该分面下的碎片
                List<Object> params = new ArrayList<Object>();
                params.add(className);
                params.add(topicName);
                params.add(facetName);
                List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
                assembleFragmentList = QuestionQualityDAO.getQuestionsFromSql(results);
            } else {
                // 有子分面，则是该分面的子分面下的碎片
                for (int i = 0; i < facetSimpleList.size(); i++) {
                    List<Object> params = new ArrayList<Object>();
                    params.add(className);
                    params.add(topicName);
                    params.add(facetSimpleList.get(i).getFacetName());
                    List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
                    assembleFragmentList.addAll(QuestionQualityDAO.getQuestionsFromSql(results));
                }
            }
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }

        response = Response.status(200).entity(assembleFragmentList).build();
        return response;
    }

    @POST
    @Path("/getFragmentByTopicAndSource")
    @ApiOperation(value = "根据课程名/主题/数据源，获取主题下的碎片数据", notes = "根据课程名/主题/数据源，获取主题下的碎片数据")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getFragmentByTopicAndSource(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName,
            @FormParam("sourceName") String sourceName
    ) {
        Response response = null;
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();

        /**
         * 读取assemble_fragment_question，获得主题下的所有问题碎片
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and SourceName=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        params.add(sourceName);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            assembleFragmentList = QuestionQualityDAO.getQuestionsFromSql(results);
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(assembleFragmentList).build();
        return response;
    }

    @POST
    @Path("/getFragmentByTopic")
    @ApiOperation(value = "根据课程名和主题，获取主题下的碎片数据", notes = "根据课程名和主题，获取主题下的碎片数据")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getFragmentByTopic(
            @FormParam("className") String className,
            @FormParam("topicName") String topicName
    ) {
        Response response = null;
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();

        /**
         * 读取assemble_fragment_question，获得主题下的所有问题碎片
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            assembleFragmentList = QuestionQualityDAO.getQuestionsFromSql(results);
        } catch (Exception e) {
            response = Response.status(401).entity(new error(e.toString())).build();
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }
        response = Response.status(200).entity(assembleFragmentList).build();
        return response;
    }

}