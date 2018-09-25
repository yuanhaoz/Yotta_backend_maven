package questionQuality;

import app.Config;
import app.error;
import app.success;
import facet.bean.FacetSimple;
import io.swagger.annotations.*;
import questionQuality.bean.AssembleFragmentQuestionAndAsker;
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

        /**
         * 删除 assemble_fragment 和 assemble_fragment_question 中的数据
         */
        mysqlUtils mysql = new mysqlUtils();
//        String sql = "delete from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and SourceName=? and question_quality_label=?";
        String sql = "DELETE af, afq\n" +
                "FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "af.TermName = ? AND\n" +
                "af.SourceName = ? AND\n" +
                "afq.question_quality_label = ?";
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

        /**
         * 删除 assemble_fragment 和 assemble_fragment_question 中的数据
         */
        mysqlUtils mysql = new mysqlUtils();
//        String sql = "delete from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and question_quality_label=?";
        String sql = "DELETE af, afq\n" +
                "FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "af.TermName = ? AND\n" +
                "afq.question_quality_label = ?";
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

        /**
         * 删除 assemble_fragment 和 assemble_fragment_question 中的数据
         */
        mysqlUtils mysql = new mysqlUtils();
//        String sql = "delete from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and question_quality_label=?";
        String sql = "DELETE af, afq\n" +
                "FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "afq.question_quality_label = ?";
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
    @Path("/deleteQuestionById")
    @ApiOperation(value = "根据问题碎片Id，删除该问题", notes = "根据问题碎片Id，删除该问题")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response deleteQuestionById(
            @DefaultValue("1") @ApiParam(value = "问题碎片ID", required = true) @QueryParam("question_id") int question_id
    ) {
        Response response = null;

        /**
         * 删除 assemble_fragment 和 assemble_fragment_question 中的数据
         */
        mysqlUtils mysql = new mysqlUtils();
//        String sql = "delete from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where question_id=?";
        String sql = "DELETE af, afq\n" +
                "FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "afq.question_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(question_id);
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
        String sql = "update " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " set question_quality_label=? where question_id=?";
        try {
            for (int i = 0; i < assembleFragmentList.size(); i++) {
                AssembleFragmentQuestionAndAsker assembleFragment = assembleFragmentList.get(i);
                // 更新选定主题的质量标签
                List<Object> params = new ArrayList<Object>();
                params.add(assembleFragment.getQuestion_quality_label());
                params.add(assembleFragment.getQuestion_id());
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
        String sql = "update " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " set question_quality_label=? where question_id=?";
        try {
            for (int i = 0; i < assembleFragmentList.size(); i++) {
                AssembleFragmentQuestionAndAsker assembleFragment = assembleFragmentList.get(i);
                // 更新选定主题的质量标签
                List<Object> params = new ArrayList<Object>();
                params.add(assembleFragment.getQuestion_quality_label());
                params.add(assembleFragment.getQuestion_id());
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
        String sql = "update " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " set question_quality_label=? where question_id=?";
        try {
            for (int i = 0; i < assembleFragmentList.size(); i++) {
                AssembleFragmentQuestionAndAsker assembleFragment = assembleFragmentList.get(i);
                List<Object> params = new ArrayList<Object>();
                params.add(assembleFragment.getQuestion_quality_label());
                params.add(assembleFragment.getQuestion_id());
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
    @Path("/getQuestionById")
    @ApiOperation(value = "根据问题Id，获取问题数据", notes = "根据问题Id，获取主问题数据")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败", response = String.class),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getQuestionById(
            @DefaultValue("1") @ApiParam(value = "问题Id", required = true) @QueryParam("questionId") int questionId
    ) {
        Response response = null;
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();

        /**
         * 读取assemble_fragment_question，获得主题下的所有问题碎片
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where question_id=?";
        List<Object> params = new ArrayList<Object>();
        params.add(questionId);
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
        String sql = "SELECT afq.* FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "af.TermName = ? AND\n" +
                "af.SourceName = ?";
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
        String sqlSO = "SELECT afq.* FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "af.TermName = ? AND\n" +
                "af.SourceName = \"Stackoverflow\"";
        String sqlYahoo = "SELECT afq.* FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "af.TermName = ? AND\n" +
                "af.SourceName = \"Yahoo\"";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        try {
            List<Map<String, Object>> resultsSO = mysql.returnMultipleResult(sqlSO, params);
            assembleFragmentList = QuestionQualityDAO.getQuestionsFromSql(resultsSO);
            List<Map<String, Object>> resultsYahoo = mysql.returnMultipleResult(sqlYahoo, params);
            assembleFragmentList.addAll(QuestionQualityDAO.getQuestionsFromSql(resultsYahoo));
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
