package source;

import app.Config;
import app.error;
import io.swagger.annotations.*;
import source.bean.Source;
import subject.bean.Subject;
import utils.mysqlUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据源API
 *
 * @author yuanhao
 * @date 2017/12/21 14:20
 */
@Path("/SourceAPI")
@Api(value = "SourceAPI")
public class SourceAPI {

    @GET
    @Path("/getSource")
    @ApiOperation(value = "获得所有数据源信息", notes = "获得所有数据源信息")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败"),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getSource() {
        Response response = null;
        List<Source> sources = new ArrayList<>();
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.SOURCE_TABLE;
        List<Object> params = new ArrayList<Object>();
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            for (int i = 0; i < results.size(); i++) {
                Source source = new Source();
                source.setSourceID(Integer.parseInt(results.get(i).get("SourceID").toString()));
                source.setSourceName(results.get(i).get("SourceName").toString());
                source.setNote(results.get(i).get("Note").toString());
                sources.add(source);
            }
            response = Response.status(200).entity(sources).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(401).entity(new error(e.toString())).build();
        } finally {
            mysql.closeconnection();
        }
        return response;
    }

    @GET
    @Path("/getSourceByNote")
    @ApiOperation(value = "获得CQA数据源信息", notes = "根据Note信息，获得CQA数据源信息")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "MySql数据库  查询失败"),
            @ApiResponse(code = 200, message = "MySql数据库  查询成功", response = String.class)})
    @Consumes("application/x-www-form-urlencoded" + ";charset=" + "UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=" + "UTF-8")
    public static Response getSourceByNote(
            @DefaultValue("cqa") @ApiParam(value = "备注信息", required = true) @QueryParam("note") String note
    ) {
        Response response = null;
        List<Source> sources = new ArrayList<>();
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.SOURCE_TABLE + " where Note=?";
        List<Object> params = new ArrayList<Object>();
        params.add(note);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            for (int i = 0; i < results.size(); i++) {
                Source source = new Source();
                source.setSourceID(Integer.parseInt(results.get(i).get("SourceID").toString()));
                source.setSourceName(results.get(i).get("SourceName").toString());
                source.setNote(results.get(i).get("Note").toString());
                sources.add(source);
            }
            response = Response.status(200).entity(sources).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(401).entity(new error(e.toString())).build();
        } finally {
            mysql.closeconnection();
        }
        return response;
    }

}
