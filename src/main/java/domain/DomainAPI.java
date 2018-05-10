package domain;

import app.Config;
import app.error;
import app.success;
import domain.bean.Domain;
import domain.bean.Domain2;
import domainTopic.bean.DomainTopic;
import io.swagger.annotations.*;
import subject.bean.Subject2;
import subject.bean.Subject3;
import utils.Log;
import utils.mysqlUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**  
 * 领域
 * @author 郑元浩 
 */
@Path("/DomainAPI")
@Api(value = "DomainAPI")
public class DomainAPI {

    @GET
    @Path("/getDomainByCourseId")
    @ApiOperation(value = "根据网院课程Id，返回对应的课程信息", notes = "根据网院课程Id，返回对应的课程信息")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败"),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getDomainByCourseId(
            @ApiParam(value = "网院课程Id", required = true) @QueryParam("courseId") int courseId
    ) {
        Response response = null;
        /**
         * 读取course_wangyuan，得到网院课程Id对应的课程信息
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.COURSE_WANGYUAN_TABLE + " where courseId=?";
        List<Object> params = new ArrayList<Object>();
        params.add(courseId);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            response = Response.status(200).entity(results).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(401).entity(new error(e.toString())).build();
        } finally {
            mysql.closeconnection();
        }
        return response;
    }

    @GET
    @Path("/getDomainsBySubject")
    @ApiOperation(value = "获得学科和课程信息，不包含主题信息", notes = "获得学科和课程信息，不包含主题信息")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败"),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getDomainsBySubject() {
        Response response = null;
        List<Subject3> subjects = new ArrayList<>();
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.SUBJECT_TABLE;
        List<Object> params = new ArrayList<>();
        String sqlDomain = "select * from " + Config.DOMAIN_TABLE + " where SubjectName = ?";
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            for (int i = 0; i < results.size(); i++) {
                Subject3 subject = new Subject3();
                subject.setSubjectID(Integer.parseInt(results.get(i).get("SubjectID").toString()));
                subject.setSubjectName(results.get(i).get("SubjectName").toString());
                subject.setNote(results.get(i).get("Note").toString());
                // 添加课程
                List<Domain> domains = new ArrayList<>();
                List<Object> paramsDomain = new ArrayList<>();
                paramsDomain.add(results.get(i).get("SubjectName").toString());
                List<Map<String, Object>> resultsDomain = mysql.returnMultipleResult(sqlDomain, paramsDomain);
                for (int j = 0; j < resultsDomain.size(); j++) {
                    Domain domain = new Domain();
                    domain.setClassID(Integer.parseInt(resultsDomain.get(j).get("ClassID").toString()));
                    domain.setClassName(resultsDomain.get(j).get("ClassName").toString());
                    domain.setSubjectName(resultsDomain.get(j).get("SubjectName").toString());
                    domain.setNote(resultsDomain.get(j).get("Note").toString());
                    domains.add(domain);
                }
                subject.setDomains(domains);
                subjects.add(subject);
            }
            response = Response.status(200).entity(subjects).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(401).entity(new error(e.toString())).build();
        } finally {
            mysql.closeconnection();
        }
        return response;
    }

    @GET
    @Path("/getDomain")
    @ApiOperation(value = "获得所有领域信息", notes = "获得所有领域信息")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败"),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getDomain() {
        Response response = null;
        /**
         * 读取domain，得到所有领域名
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.DOMAIN_TABLE;
        List<Object> params = new ArrayList<Object>();
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            response = Response.status(200)
                    .entity(results)
                    .cookie(NewCookie.valueOf("domain=数据结构"))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(401).entity(new error(e.toString())).build();
        } finally {
            mysql.closeconnection();
        }
        return response;
    }


    @GET
    @Path("/getDomainManage")
    @ApiOperation(value = "获得所有领域信息，管理系统", notes = "获得所有领域信息，管理系统")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败"),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getDomainManage() {
        Response response = null;
//      mysqlUtils mysql = new mysqlUtils();
//      String sql = "select * from " + Config.DOMAIN_TABLE;
//      String sql_TopicNum = "SELECT " +
//              "Count(dt.TermID) AS TopicNum, d.ClassName AS ClassName " +
//              "FROM " + Config.DOMAIN_TABLE + " AS d , " + Config.DOMAIN_TOPIC_TABLE + " AS dt " +
//              "WHERE " +
//              "d.ClassName = dt.ClassName " +
//              "GROUP BY " +
//              "dt.ClassName";
//      String sql_FacetNum = "SELECT " +
//              "Count(f.FacetName) AS FacetNum, d.ClassName AS ClassName " +
//              "FROM " + Config.DOMAIN_TABLE + " AS d , " + Config.FACET_TABLE + " AS f " +
//              "WHERE " +
//              "d.ClassName = f.ClassName " +
//              "GROUP BY " +
//              "d.ClassName";
//      String sql_FragmentNum = "SELECT " +
//              "Count(af.FragmentID) AS FragmentNum, d.ClassName AS ClassName " +
//              "FROM " + Config.DOMAIN_TABLE + " AS d ," + Config.ASSEMBLE_FRAGMENT_TABLE + " AS af " +
//              "WHERE " +
//              "d.ClassName = af.ClassName " +
//              "GROUP BY " +
//              "d.ClassName";
//      String sql_DependenceNum = "SELECT " +
//              "Count(de.`Start`) AS DependenceNum, d.ClassName AS ClassName " +
//              "FROM " + Config.DOMAIN_TABLE + " AS d ," + Config.DEPENDENCY + " AS de " +
//              "WHERE " +
//              "d.ClassName = de.ClassName " +
//              "GROUP BY " +
//              "d.ClassName";
//      String sql_FirstFacetNum = "SELECT " +
//              "Count(f.TermName) AS FirstFacetNum, d.ClassName AS ClassName " +
//              "FROM " + Config.DOMAIN_TABLE + " AS d , " + Config.FACET_TABLE + " AS f " +
//              "WHERE " +
//              "d.ClassName = f.ClassName AND " +
//              "f.FacetLayer = '1' " +
//              "GROUP BY " +
//              "d.ClassName";
//      String sql_SecondFacetNum = "SELECT " +
//              "Count(f.TermName) AS SecondFacetNum, d.ClassName AS ClassName " +
//              "FROM " + Config.DOMAIN_TABLE + " AS d , " + Config.FACET_TABLE + " AS f " +
//              "WHERE " +
//              "d.ClassName = f.ClassName AND " +
//              "f.FacetLayer = '2' " +
//              "GROUP BY " +
//              "d.ClassName";
//      String sql_ThirdFacetNum = "SELECT " +
//              "Count(f.TermName) AS ThirdFacetNum, d.ClassName AS ClassName " +
//              "FROM " + Config.DOMAIN_TABLE + " AS d , " + Config.FACET_TABLE + " AS f " +
//              "WHERE " +
//              "d.ClassName = f.ClassName AND " +
//              "f.FacetLayer = '3' " +
//              "GROUP BY " +
//              "d.ClassName";
//      try {
//          List<Map<String, Object>> results = new ArrayList<>();
//          List<Object> params_Num = new ArrayList<Object>();
//          try {
//              List<Map<String, Object>> resultsDomain = mysql.returnMultipleResult(sql, params_Num);
//              for (int i = 0; i < resultsDomain.size(); i++) {
//                  Map<String, Object> map = new LinkedHashMap<>();
//                  String classID = resultsDomain.get(i).get("ClassID").toString();
//                  String className = resultsDomain.get(i).get("ClassName").toString();
//                  map.put("ClassID", classID);
//                  map.put("ClassName", className);
//                  // 主题
//                  List<Map<String, Object>> results_Topic = mysql.returnMultipleResult(sql_TopicNum, params_Num);
//                  boolean flag = false;
//                  for (int j = 0; j < results_Topic.size(); j++) {
//                      if (className.equals(results_Topic.get(j).get("ClassName"))) {
//                          flag = true;
//                          map.put("TopicNum", results_Topic.get(j).get("TopicNum"));
//                      }
//                  }
//                  if (!flag) {
//                      map.put("TopicNum", 0);
//                  }
//                  // 分面
//                  List<Map<String, Object>> results_Facet = mysql.returnMultipleResult(sql_FacetNum, params_Num);
//                  flag = false;
//                  for (int j = 0; j < results_Facet.size(); j++) {
//                      if (className.equals(results_Facet.get(j).get("ClassName"))) {
//                          flag = true;
//                          map.put("FacetNum", results_Facet.get(j).get("FacetNum"));
//                      }
//                  }
//                  if (!flag) {
//                      map.put("FacetNum", 0);
//                  }
//                  // 一级分面
//                  List<Map<String, Object>> results_FirstFacet = mysql.returnMultipleResult(sql_FirstFacetNum, params_Num);
//                  flag = false;
//                  for (int j = 0; j < results_FirstFacet.size(); j++) {
//                      if (className.equals(results_FirstFacet.get(j).get("ClassName"))) {
//                          flag = true;
//                          map.put("FirstFacetNum", results_FirstFacet.get(j).get("FirstFacetNum"));
//                      }
//                  }
//                  if (!flag) {
//                      map.put("FirstFacetNum", 0);
//                  }
//                  // 二级分面
//                  List<Map<String, Object>> results_SecondFacet = mysql.returnMultipleResult(sql_SecondFacetNum, params_Num);
//                  flag = false;
//                  for (int j = 0; j < results_SecondFacet.size(); j++) {
//                      if (className.equals(results_SecondFacet.get(j).get("ClassName"))) {
//                          flag = true;
//                          map.put("SecondFacetNum", results_SecondFacet.get(j).get("SecondFacetNum"));
//                      }
//                  }
//                  if (!flag) {
//                      map.put("SecondFacetNum", 0);
//                  }
//                  // 三级分面
//                  List<Map<String, Object>> results_ThirdFacet = mysql.returnMultipleResult(sql_ThirdFacetNum, params_Num);
//                  flag = false;
//                  for (int j = 0; j < results_ThirdFacet.size(); j++) {
//                      if (className.equals(results_ThirdFacet.get(j).get("ClassName"))) {
//                          flag = true;
//                          map.put("ThirdFacetNum", results_ThirdFacet.get(j).get("ThirdFacetNum"));
//                      }
//                  }
//                  if (!flag) {
//                      map.put("ThirdFacetNum", 0);
//                  }
//                  // 碎片数量
//                  List<Map<String, Object>> results_Fragment = mysql.returnMultipleResult(sql_FragmentNum, params_Num);
//                  flag = false;
//                  for (int j = 0; j < results_Fragment.size(); j++) {
//                      if (className.equals(results_Fragment.get(j).get("ClassName"))) {
//                          flag = true;
//                          map.put("FragmentNum", results_Fragment.get(j).get("FragmentNum"));
//                      }
//                  }
//                  if (!flag) {
//                      map.put("FragmentNum", 0);
//                  }
//                  // 依赖关系
//                  List<Map<String, Object>> results_dependence = mysql.returnMultipleResult(sql_DependenceNum, params_Num);
//                  flag = false;
//                  for (int j = 0; j < results_dependence.size(); j++) {
//                      if (className.equals(results_dependence.get(j).get("ClassName"))) {
//                          flag = true;
//                          map.put("DependenceNum", results_dependence.get(j).get("DependenceNum"));
//                      }
//                  }
//                  if (!flag) {
//                      map.put("DependenceNum", 0);
//                  }
//              }
//          } catch (Exception e) {
//              e.printStackTrace();
//          }
//          response = Response.status(200).entity(results).build();
//      } catch (Exception e) {
//          e.printStackTrace();
//          response = Response.status(401).entity(new error(e.toString())).build();
//      } finally {
//          mysql.closeconnection();
//      }
        /**
         * 读取domain，得到所有领域名和各领域下主题、分面、碎片、关系的数量
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.DOMAIN_TABLE;
        String sql_TopicNum = "select count(TermID) as TopicNum from " + Config.DOMAIN_TOPIC_TABLE + " where ClassName=?";
        String sql_FacetNum = "select count(FacetName) as FacetNum from " + Config.FACET_TABLE + " where ClassName=?";
        String sql_FragmentNum = "select count(FragmentID) as FragmentNum from " + Config.ASSEMBLE_FRAGMENT_TABLE + " where ClassName=?";
        String sql_DependenceNum = "select count(Start) as DependenceNum from " + Config.DEPENDENCY + " where ClassName=?";
        String sql_FirstFacetNum = "select count(FacetName) as FirstFacetNum from " + Config.FACET_TABLE + " where ClassName=? and FacetLayer='1'";
        String sql_SecondFacetNum = "select count(FacetName) as SecondFacetNum from " + Config.FACET_TABLE + " where ClassName=? and FacetLayer='2'";
        String sql_ThirdFacetNum = "select count(FacetName) as ThirdFacetNum from " + Config.FACET_TABLE + " where ClassName=? and FacetLayer='3'";
        List<Object> params = new ArrayList<Object>();
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);

            List<Map<String, Object>> results_Num = results;
            for (int i = 0; i < results.size(); i++) {
                List<Object> params_Num = new ArrayList<Object>();
                params_Num.add(results.get(i).get("ClassName").toString());
                try {
                    List<Map<String, Object>> results_Topic = mysql.returnMultipleResult(sql_TopicNum, params_Num);
                    results_Num.get(i).put("TopicNum", Integer.valueOf(results_Topic.get(0).get("TopicNum").toString()));

                    List<Map<String, Object>> results_Facet = mysql.returnMultipleResult(sql_FacetNum, params_Num);
                    results_Num.get(i).put("FacetNum", Integer.valueOf(results_Facet.get(0).get("FacetNum").toString()));

                    List<Map<String, Object>> results_FirstFacet = mysql.returnMultipleResult(sql_FirstFacetNum, params_Num);
                    results_Num.get(i).put("FirstFacetNum", Integer.valueOf(results_FirstFacet.get(0).get("FirstFacetNum").toString()));

                    List<Map<String, Object>> results_SecondFacet = mysql.returnMultipleResult(sql_SecondFacetNum, params_Num);
                    results_Num.get(i).put("SecondFacetNum", Integer.valueOf(results_SecondFacet.get(0).get("SecondFacetNum").toString()));

                    List<Map<String, Object>> results_ThirdFacet = mysql.returnMultipleResult(sql_ThirdFacetNum, params_Num);
                    results_Num.get(i).put("ThirdFacetNum", Integer.valueOf(results_ThirdFacet.get(0).get("ThirdFacetNum").toString()));

                    List<Map<String, Object>> results_Fragment = mysql.returnMultipleResult(sql_FragmentNum, params_Num);
                    results_Num.get(i).put("FragmentNum", Integer.valueOf(results_Fragment.get(0).get("FragmentNum").toString()));

                    List<Map<String, Object>> results_Dependence = mysql.returnMultipleResult(sql_DependenceNum, params_Num);
                    results_Num.get(i).put("DependenceNum", Integer.valueOf(results_Dependence.get(0).get("DependenceNum").toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            results = results_Num;

            response = Response.status(200).entity(results).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(401).entity(new error(e.toString())).build();
        } finally {
            mysql.closeconnection();
        }
        return response;
    }


    @GET
    @Path("/createClass")
    @ApiOperation(value = "创建一门课程", notes = "在数据库中加入一门新课程")
    @ApiResponses(value = {
            @ApiResponse(code = 402, message = "数据库错误", response = error.class),
            @ApiResponse(code = 200, message = "正常返回结果", response = success.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response createClass(@ApiParam(value = "课程名字", required = true) @QueryParam("ClassName") String ClassName) {
//		Response response = null;
        /**
         * 在数据库中加入一门新课程
         */
        try {
            boolean result = false;
            mysqlUtils mysql = new mysqlUtils();
            String sql = "insert into " + Config.DOMAIN_TABLE + "(ClassName) values(?);";
            List<Object> params = new ArrayList<Object>();
            params.add(ClassName);
            try {
                String exist = "select * from " + Config.DOMAIN_TABLE + " where ClassName=?";
                try {
                    List<Map<String, Object>> results = mysql.returnMultipleResult(exist, params);
                    if (results.size() == 0) {
                        result = mysql.addDeleteModify(sql, params);
                    } else {
                        return Response.status(200).entity(new success(ClassName + " 已经存在！")).build();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
/*			catch(MySQLIntegrityConstraintViolationException e){
                return Response.status(200).entity(new success(ClassName+" 已经存在！")).build();
			}*/ catch (Exception e) {
                e.printStackTrace();
            } finally {
                mysql.closeconnection();
            }
            if (result) {
                return Response.status(200).entity(new success(ClassName + " 创建成功~")).build();
            } else {
                return Response.status(401).entity(new error(ClassName + " 创建失败~")).build();
            }
        } catch (Exception e) {
            return Response.status(402).entity(new error(e.toString())).build();
        }

    }

    @GET
    @Path("/queryKeyword")
    @ApiOperation(value = "关键词查询", notes = "通过关键词在数据库查询相关信息")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败"),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response queryKeyword(@ApiParam(value = "关键词", required = true) @QueryParam("Keyword") String Keyword) {
        Response response = null;
        /**
         * 根据输入的关键词在数据库查找相关的信息并返回
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql_Class = "select * from " + Config.DOMAIN_TABLE + " where ClassName like ?";
        String sql_Topic = "select * from " + Config.DOMAIN_TOPIC_TABLE + " where TermName like ?";
        String sql_Facet = "select * from " + Config.FACET_TABLE + " where FacetName like ?";
/*		String sql_TextFragmentNum = "select * from " + Config.ASSEMBLE_TEXT_TABLE+" where ClassName=?";
        String sql_ImageFragmentNum = "select * from " + Config.ASSEMBLE_IMAGE_TABLE+" where ClassName=?";
*/
        List<Object> params = new ArrayList<Object>();
        params.add("%" + Keyword + "%");
        try {
            List<Map<String, Object>> results_query = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> results_Class = mysql.returnMultipleResult(sql_Class, params);
            for (int i = 0; i < results_Class.size(); i++) {
                Map<String, Object> subject = new HashMap<String, Object>();
                subject.put("Type", "Class");
                subject.put("Name", results_Class.get(i).get("ClassName").toString());
                results_query.add(subject);
            }

            List<Map<String, Object>> results_Topic = mysql.returnMultipleResult(sql_Topic, params);
            for (int i = 0; i < results_Topic.size(); i++) {
                Map<String, Object> topic = new HashMap<String, Object>();
                topic.put("Type", "Term");
                topic.put("Class", results_Topic.get(i).get("ClassName").toString());
                topic.put("Name", results_Topic.get(i).get("TermName").toString());
                results_query.add(topic);
            }

            List<Map<String, Object>> results_Facet = mysql.returnMultipleResult(sql_Facet, params);
            for (int i = 0; i < results_Facet.size(); i++) {
                Map<String, Object> facet = new HashMap<String, Object>();
                facet.put("Type", "Facet");
                facet.put("Class", results_Facet.get(i).get("ClassName").toString());
                facet.put("Term", results_Facet.get(i).get("TermName").toString());
                facet.put("Name", results_Facet.get(i).get("FacetName").toString());
                facet.put("Layer", results_Facet.get(i).get("FacetLayer").toString());
                results_query.add(facet);
            }
            response = Response.status(200).entity(results_query).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(401).entity(new error(e.toString())).build();
        } finally {
            mysql.closeconnection();
        }
        return response;
    }

    @GET
    @Path("/countClassNum")
    @ApiOperation(value = "统计课程数", notes = "统计课程数")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败"),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response countClassNum() {
        Response response = null;
        /**
         * 根据输入的关键词在数据库查找相关的信息并返回
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.DOMAIN_TABLE;
        List<Object> params = new ArrayList<Object>();
        try {
            Map<String, Object> results_query = new HashMap<String, Object>();
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            results_query.put("ClassNum", results.size());
            response = Response.status(200).entity(results_query).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(401).entity(new error(e.toString())).build();
        } finally {
            mysql.closeconnection();
        }
        return response;
    }

    @GET
    @Path("/updateClassName")
    @ApiOperation(value = "修改课程名", notes = "修改课程名")
    @ApiResponses(value = {
            @ApiResponse(code = 402, message = "数据库错误", response = error.class),
            @ApiResponse(code = 200, message = "正常返回结果", response = success.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response updateClassName(@ApiParam(value = "课程名字", required = true) @QueryParam("ClassName") String ClassName, @ApiParam(value = "新课程名字", required = true) @QueryParam("NewClassName") String NewClassName) {
        /**
         * 修改数据库中一门课程的名字
         */
        try {
            boolean result = false;
            mysqlUtils mysql = new mysqlUtils();
            String sql_Class = "update " + Config.DOMAIN_TABLE + " set ClassName=? where ClassName=?;";
            String sql_Topic = "update " + Config.DOMAIN_TOPIC_TABLE + " set ClassName=? where ClassName=?;";
            String sql_TopicRelation = "update " + Config.DOMAIN_TOPIC_RELATION_TABLE + " set ClassName=? where ClassName=?;";
            String sql_Facet = "update " + Config.FACET_TABLE + " set ClassName=? where ClassName=?;";
            String sql_FacetRelation = "update " + Config.FACET_RELATION_TABLE + " set ClassName=? where ClassName=?;";
            String sql_Dependence = "update " + Config.DEPENDENCY + " set ClassName=? where ClassName=?;";
//			String sql_SpiderText="update "+Config.SPIDER_TEXT_TABLE+" set ClassName=? where ClassName=?;";
//			String sql_SpiderImage="update "+Config.SPIDER_IMAGE_TABLE+" set ClassName=? where ClassName=?;";
            String sql_AssmbleFragment = "update " + Config.ASSEMBLE_FRAGMENT_TABLE + " set ClassName=? where ClassName=?;";
//			String sql_AssmbleText="update "+Config.ASSEMBLE_TEXT_TABLE+" set ClassName=? where ClassName=?;";
//			String sql_AssembleImage="update "+Config.ASSEMBLE_IMAGE_TABLE+" set ClassName=? where ClassName=?;";
            List<Object> params = new ArrayList<Object>();
            params.add(NewClassName);
            params.add(ClassName);
            try {
                String exist = "select * from " + Config.DOMAIN_TABLE + " where ClassName=?";
                List<Object> params_Query = new ArrayList<Object>();
                params_Query.add(NewClassName);
                try {
                    List<Map<String, Object>> results = mysql.returnMultipleResult(exist, params_Query);
                    if (results.size() == 0) {
                        result = mysql.addDeleteModify(sql_Class, params);
                        try {
                            mysql.addDeleteModify(sql_Topic, params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            mysql.addDeleteModify(sql_TopicRelation, params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            mysql.addDeleteModify(sql_Facet, params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            mysql.addDeleteModify(sql_FacetRelation, params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            mysql.addDeleteModify(sql_Dependence, params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            mysql.addDeleteModify(sql_AssmbleFragment, params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

/*						try{
							mysql.addDeleteModify(sql_AssmbleText, params);
						}catch(Exception e){
							e.printStackTrace();
						}
						try{
							mysql.addDeleteModify(sql_AssembleImage, params);
						}catch(Exception e){
							e.printStackTrace();
						}*/
                    } else {
                        return Response.status(200).entity(new success(NewClassName + " 已经存在！")).build();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mysql.closeconnection();
            }
            if (result) {
                return Response.status(200).entity(new success(ClassName + "改为" + NewClassName + " 修改成功~")).build();
            } else {
                return Response.status(401).entity(new error(ClassName + "改为" + NewClassName + " 修改失败~")).build();
            }
        } catch (Exception e) {
            return Response.status(402).entity(new error(e.toString())).build();
        }

    }

    @GET
    @Path("/convertRdf")
    @ApiOperation(value = "按课程转换RDF数据", notes = "按课程转换RDF数据")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败"),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response convertRdf(@DefaultValue("数据结构") @ApiParam(value = "课程名字", required = true) @QueryParam("ClassName") String ClassName) {
        Response response = null;
        /**
         * 按课程转换RDF数据
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql_convert = "insert into " + Config.RDF_TABLE + "(data) values(?);";
        String sql_domainTopic = "select * from " + Config.DOMAIN_TOPIC_TABLE + " where ClassName=?";
        String sql_topicTopic = "select * from " + Config.DEPENDENCY + " where ClassName=?";
        String sql_facet1Domain = "select * from " + Config.FACET_TABLE + " where ClassName=? and FacetLayer='1'";
        String sql_facet2Domain = "select * from " + Config.FACET_TABLE + " where ClassName=? and FacetLayer='2'";
//		String sql_facet3Domain = "select * from " + Config.FACET_TABLE+" where ClassName=? and FacetLayer='3'";
        String sql_facetFacetP = "select * from " + Config.FACET_RELATION_TABLE + " where ClassName=? and TermName=? and ChildFacet=? and ChildLayer=?";
        String sql_facetFacetC = "select * from " + Config.FACET_RELATION_TABLE + " where ClassName=? and TermName=? and ParentFacet=? and ParentLayer=?";
        String sql_fragment = "select * from " + Config.ASSEMBLE_FRAGMENT_TABLE + " where ClassName=? and TermName=? and FacetName=? and FacetLayer=?";
        List<Object> params = new ArrayList<Object>();
        params.add(ClassName);
        try {
            List<Map<String, Object>> results_domainTopic = mysql.returnMultipleResult(sql_domainTopic, params);
            List<Map<String, Object>> results_topicTopic = mysql.returnMultipleResult(sql_topicTopic, params);
            List<Map<String, Object>> results_facet1Domain = mysql.returnMultipleResult(sql_facet1Domain, params);
            List<Map<String, Object>> results_facet2Domain = mysql.returnMultipleResult(sql_facet2Domain, params);
//			List<Map<String, Object>> results_facet3Domain = mysql.returnMultipleResult(sql_facet3Domain, params);
            for (int i = 0; i < results_domainTopic.size(); i++) {
                String str = "<http://kf.skyclass.net/term/" + results_domainTopic.get(i).get("TermName") + ">" + " " + "<http://purl.org/dc/terms/subject>" + " " + "<http://dbpedia.org/domain/" + results_domainTopic.get(i).get("ClassName") + ">";
                List<Object> params_convert = new ArrayList<Object>();
                params_convert.add(str);
                try {
                    mysql.addDeleteModify(sql_convert, params_convert);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < results_topicTopic.size(); i++) {
                String str = "<http://kf.skyclass.net/term/" + results_topicTopic.get(i).get("Start") + ">" + " " + "<http://kf.skyclass.net/relation/dependency>" + " " + "<http://kf.skyclass.net/term/" + results_topicTopic.get(i).get("End") + ">";
                List<Object> params_convert = new ArrayList<Object>();
                params_convert.add(str);
                try {
                    mysql.addDeleteModify(sql_convert, params_convert);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < results_facet1Domain.size(); i++) {
                List<Object> params_frag = new ArrayList<Object>();
                params_frag.add(ClassName);
                params_frag.add(results_facet1Domain.get(i).get("TermName"));
                params_frag.add(results_facet1Domain.get(i).get("FacetName"));
                params_frag.add(results_facet1Domain.get(i).get("FacetLayer"));
                try {
                    List<Map<String, Object>> results_facet1Frag = mysql.returnMultipleResult(sql_fragment, params_frag);
                    if (results_facet1Frag.size() == 0) {
                        String str = "<http://kf.skyclass.net/term/" + results_facet1Domain.get(i).get("TermName") + ">" + " " + "<http://kf.skyclass.net/facet/" + results_facet1Domain.get(i).get("FacetName") + ">" + " " + "\" \"";
                        List<Object> params_convert = new ArrayList<Object>();
                        params_convert.add(str);
                        try {
                            mysql.addDeleteModify(sql_convert, params_convert);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (results_facet1Frag.size() != 0) {
                        for (int j = 0; j < results_facet1Frag.size(); j++) {
                            String str = "<http://kf.skyclass.net/term/" + results_facet1Domain.get(i).get("TermName") + ">" + " " + "<http://kf.skyclass.net/facet/" + results_facet1Domain.get(i).get("FacetName") + ">" + " " + "\"" + results_facet1Frag.get(j).get("FragmentContent") + "\"";
                            List<Object> params_convert = new ArrayList<Object>();
                            params_convert.add(str);
                            try {
                                mysql.addDeleteModify(sql_convert, params_convert);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            for (int i = 0; i < results_facet2Domain.size(); i++) {
                List<Object> params_facetFacetP = new ArrayList<Object>();
                params_facetFacetP.add(ClassName);
                params_facetFacetP.add(results_facet2Domain.get(i).get("TermName"));
                params_facetFacetP.add(results_facet2Domain.get(i).get("FacetName"));
                params_facetFacetP.add(2);

                List<Object> params_facetFacetC = new ArrayList<Object>();
                params_facetFacetC.add(ClassName);
                params_facetFacetC.add(results_facet2Domain.get(i).get("TermName"));
                params_facetFacetC.add(results_facet2Domain.get(i).get("FacetName"));
                params_facetFacetC.add(2);

                List<Object> params_frag = new ArrayList<Object>();
                params_frag.add(ClassName);
                params_frag.add(results_facet2Domain.get(i).get("TermName"));
                params_frag.add(results_facet2Domain.get(i).get("FacetName"));
                params_frag.add(results_facet2Domain.get(i).get("FacetLayer"));

                try {
                    List<Map<String, Object>> results_facet2Frag = mysql.returnMultipleResult(sql_fragment, params_frag);
                    if (results_facet2Frag.size() == 0) {
                        List<Map<String, Object>> results_facet2Facet1 = mysql.returnMultipleResult(sql_facetFacetP, params_facetFacetP);
                        String randomID = mysql.getRandomString();
                        String str1 = "<http://kf.skyclass.net/term/" + results_facet2Domain.get(i).get("TermName") + ">" + " " + "<http://kf.skyclass.net/facet/" + results_facet2Facet1.get(0).get("ParentFacet") + ">" + " " + randomID;
                        String str2 = randomID + " " + "<http://kf.skyclass.net/facet/" + results_facet2Domain.get(i).get("FacetName") + ">" + " " + "\"" + " " + "\"";
                        List<Object> params_convert1 = new ArrayList<Object>();
                        params_convert1.add(str1);
                        List<Object> params_convert2 = new ArrayList<Object>();
                        params_convert2.add(str2);
                        try {
                            mysql.addDeleteModify(sql_convert, params_convert1);
                            mysql.addDeleteModify(sql_convert, params_convert2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        List<Map<String, Object>> results_facet2Facet3 = mysql.returnMultipleResult(sql_facetFacetC, params_facetFacetC);
                        if (results_facet2Facet3.size() != 0) {

                            String randomID1 = mysql.getRandomString();
                            String str3 = randomID + " " + "<http://kf.skyclass.net/facet/" + results_facet2Domain.get(i).get("FacetName") + ">" + " " + randomID1;
                            List<Object> params_convert3 = new ArrayList<Object>();
                            params_convert3.add(str3);
                            try {
                                mysql.addDeleteModify(sql_convert, params_convert3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            for (int j = 0; j < results_facet2Facet3.size(); j++) {

                                List<Object> params_fragFacet3 = new ArrayList<Object>();
                                params_fragFacet3.add(ClassName);
                                params_fragFacet3.add(results_facet2Domain.get(i).get("TermName"));
                                params_fragFacet3.add(results_facet2Facet3.get(j).get("ChildFacet"));
                                params_fragFacet3.add(3);
                                try {
                                    List<Map<String, Object>> results_facet3Frag = mysql.returnMultipleResult(sql_fragment, params_fragFacet3);
                                    if (results_facet3Frag.size() == 0) {
                                        String str4 = randomID1 + " " + "<http://kf.skyclass.net/facet/" + results_facet2Facet3.get(j).get("ChildFacet") + ">" + " " + "\"" + " " + "\"";
                                        List<Object> params_convert4 = new ArrayList<Object>();
                                        params_convert4.add(str4);
                                        try {
                                            mysql.addDeleteModify(sql_convert, params_convert4);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (results_facet3Frag.size() != 0) {
                                        for (int k = 0; k < results_facet3Frag.size(); k++) {
                                            String str4 = randomID1 + " " + "<http://kf.skyclass.net/facet/" + results_facet2Facet3.get(j).get("ChildFacet") + ">" + " " + "\"" + results_facet3Frag.get(k).get("FragmentContent") + "\"";
                                            List<Object> params_convert4 = new ArrayList<Object>();
                                            params_convert4.add(str4);
                                            try {
                                                mysql.addDeleteModify(sql_convert, params_convert4);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }


                    }
                    if (results_facet2Frag.size() != 0) {
                        List<Map<String, Object>> results_facet2Facet1 = mysql.returnMultipleResult(sql_facetFacetP, params_facetFacetP);
                        String randomID = mysql.getRandomString();
                        String str1 = "<http://kf.skyclass.net/term/" + results_facet2Domain.get(i).get("TermName") + ">" + " " + "<http://kf.skyclass.net/facet/" + results_facet2Facet1.get(0).get("ParentFacet") + ">" + " " + randomID;
                        List<Object> params_convert1 = new ArrayList<Object>();
                        params_convert1.add(str1);
                        try {
                            mysql.addDeleteModify(sql_convert, params_convert1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        for (int j = 0; j < results_facet2Frag.size(); j++) {

                            String str2 = randomID + " " + "<http://kf.skyclass.net/facet/" + results_facet2Domain.get(i).get("FacetName") + ">" + " " + "\"" + results_facet2Frag.get(j).get("FragmentContent") + "\"";

                            List<Object> params_convert2 = new ArrayList<Object>();
                            params_convert2.add(str2);
                            try {
                                mysql.addDeleteModify(sql_convert, params_convert2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        List<Map<String, Object>> results_facet2Facet3 = mysql.returnMultipleResult(sql_facetFacetC, params_facetFacetC);
                        if (results_facet2Facet3.size() != 0) {

                            String randomID1 = mysql.getRandomString();
                            String str3 = randomID + " " + "<http://kf.skyclass.net/facet/" + results_facet2Domain.get(i).get("FacetName") + ">" + " " + randomID1;
                            List<Object> params_convert3 = new ArrayList<Object>();
                            params_convert3.add(str3);
                            try {
                                mysql.addDeleteModify(sql_convert, params_convert3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            for (int j = 0; j < results_facet2Facet3.size(); j++) {

                                List<Object> params_fragFacet3 = new ArrayList<Object>();
                                params_fragFacet3.add(ClassName);
                                params_fragFacet3.add(results_facet2Domain.get(i).get("TermName"));
                                params_fragFacet3.add(results_facet2Facet3.get(j).get("ChildFacet"));
                                params_fragFacet3.add(3);
                                try {
                                    List<Map<String, Object>> results_facet3Frag = mysql.returnMultipleResult(sql_fragment, params_fragFacet3);
                                    if (results_facet3Frag.size() == 0) {
                                        String str4 = randomID1 + " " + "<http://kf.skyclass.net/facet/" + results_facet2Facet3.get(j).get("ChildFacet") + ">" + " " + "\"" + " " + "\"";
                                        List<Object> params_convert4 = new ArrayList<Object>();
                                        params_convert4.add(str4);
                                        try {
                                            mysql.addDeleteModify(sql_convert, params_convert4);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (results_facet3Frag.size() != 0) {
                                        for (int k = 0; k < results_facet3Frag.size(); k++) {
                                            String str4 = randomID1 + " " + "<http://kf.skyclass.net/facet/" + results_facet2Facet3.get(j).get("ChildFacet") + ">" + " " + "\"" + results_facet3Frag.get(k).get("FragmentContent") + "\"";
                                            List<Object> params_convert4 = new ArrayList<Object>();
                                            params_convert4.add(str4);
                                            try {
                                                mysql.addDeleteModify(sql_convert, params_convert4);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            response = Response.status(200).entity(new success("转换成功~")).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(401).entity(new error("转换失败")).build();
        } finally {
            mysql.closeconnection();
        }
        return response;
    }


}
