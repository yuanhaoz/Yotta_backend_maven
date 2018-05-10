package questionQuality;

import app.Config;
import questionQuality.bean.AssembleFragmentQuestionAndAsker;
import utils.mysqlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yuanhao
 * @date 2018/4/17 16:19
 */
public class QuestionQualityDAO {

    public static void main(String[] args) {
//        getQuestionQualityByTopicAndSource("Abstract_data_types", "2–3 heap", "Stackoverflow");
//        getQuestionQualityByTopic("Abstract_data_types", "2–3 heap");
//        getQuestionQualityByClass("Abstract_data_types");
    }

    /**
     * 根据课程、主题、数据源，计算问题的标签信息
     * @param className
     * @param topicName
     * @param sourceName
     * @return
     */
    public static List<AssembleFragmentQuestionAndAsker> getQuestionQualityByTopicAndSource(
            String className, String topicName, String sourceName
    ) {
        // 得到这门课的平均质量值
        List<Double> avgQuality = getAvgQuality(className);
        // 返回的数据
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();
        // 读取assemble_fragment_question
        mysqlUtils mysql = new mysqlUtils();
//        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and SourceName=?";
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
            assembleFragmentList = getQuestionQuality(avgQuality, results, sourceName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }

        return assembleFragmentList;
    }

    /**
     * 根据课程、主题，计算问题的标签信息
     * @param className
     * @param topicName
     * @return
     */
    public static List<AssembleFragmentQuestionAndAsker> getQuestionQualityByTopic(
            String className, String topicName
    ) {
        // 得到这门课的平均质量值
        List<Double> avgQuality = getAvgQuality(className);
        // 返回的数据
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();

        // 读取assemble_fragment_question：SO数据源
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
        params.add("Stackoverflow");
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            assembleFragmentList = getQuestionQuality(avgQuality, results, "Stackoverflow");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }

        // 读取assemble_fragment_question：Yahoo数据源
        mysqlUtils mysqlYahoo = new mysqlUtils();
        String sqlYahoo = "SELECT afq.* FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "af.TermName = ? AND\n" +
                "af.SourceName = ?";
        List<Object> paramsYahoo = new ArrayList<Object>();
        paramsYahoo.add(className);
        paramsYahoo.add(topicName);
        paramsYahoo.add("Yahoo");
        try {
            List<Map<String, Object>> results = mysqlYahoo.returnMultipleResult(sqlYahoo, paramsYahoo);
            assembleFragmentList.addAll(getQuestionQuality(avgQuality, results, "Yahoo"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysqlYahoo.closeconnection();
        }

        return assembleFragmentList;
    }

    /**
     * 根据课程名，计算问题的标签信息
     * @param className
     * @return
     */
    public static List<AssembleFragmentQuestionAndAsker> getQuestionQualityByClass(String className) {
        // 得到这门课的平均质量值
        List<Double> avgQuality = getAvgQuality(className);
        // 返回的数据
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();

        // 读取assemble_fragment_question：SO数据源
        mysqlUtils mysql = new mysqlUtils();
        String sql = "SELECT afq.* FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "af.SourceName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add("Stackoverflow");
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            assembleFragmentList = getQuestionQuality(avgQuality, results, "Stackoverflow");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }

        // 读取assemble_fragment_question：Yahoo数据源
        mysqlUtils mysqlYahoo = new mysqlUtils();
        String sqlYahoo = "SELECT afq.* FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "af.SourceName = ?";
        List<Object> paramsYahoo = new ArrayList<Object>();
        paramsYahoo.add(className);
        paramsYahoo.add("Yahoo");
        try {
            List<Map<String, Object>> results = mysqlYahoo.returnMultipleResult(sqlYahoo, paramsYahoo);
            assembleFragmentList.addAll(getQuestionQuality(avgQuality, results, "Yahoo"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysqlYahoo.closeconnection();
        }

        return assembleFragmentList;
    }

    /**
     * 根据每个课程下的质量均值，计算查询到的问题的质量
     * @param avgQuality 每个课程的平均质量阈值
     * @param results 需要计算质量的碎片集合
     * @param sourceName 数据源
     * @return
     */
    public static List<AssembleFragmentQuestionAndAsker> getQuestionQuality(List<Double> avgQuality, List<Map<String, Object>> results, String sourceName) {
        // 返回的数据
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();
        double avgSoBigView = avgQuality.get(0); // stackoverflow问题浏览数大于1000的问题的质量分数均值
        double avgSoSmallView = avgQuality.get(1); // stackoverflow问题浏览数小于1000的问题的质量分数均值
        double avgYahoo = avgQuality.get(2); // Yahoo问题的质量分数均值
        /**
         * 设置问题标签
         */
        for (int j = 0; j < results.size(); j++) {
            AssembleFragmentQuestionAndAsker assembleFragment = new AssembleFragmentQuestionAndAsker();
            Map<String, Object> map = results.get(j);
            // 设置问题信息：除了问题的质量标签
            assembleFragment.setQuestion_id(Integer.parseInt(map.get("question_id").toString()));
            assembleFragment.setPage_website_logo(map.get("page_website_logo").toString());
            assembleFragment.setPage_search_url(map.get("page_search_url").toString());
            assembleFragment.setPage_column_color(map.get("page_column_color").toString());
            assembleFragment.setQuestion_url(map.get("question_url").toString());
            assembleFragment.setQuestion_title(map.get("question_title").toString());
            assembleFragment.setQuestion_title_pure(map.get("question_title_pure").toString());
            assembleFragment.setQuestion_body(map.get("question_body").toString());
            assembleFragment.setQuestion_body_pure(map.get("question_body_pure").toString());
            assembleFragment.setQuestion_best_answer(map.get("question_best_answer").toString());
            assembleFragment.setQuestion_best_answer_pure(map.get("question_best_answer_pure").toString());
            assembleFragment.setQuestion_score(map.get("question_score").toString());
            assembleFragment.setQuestion_answerCount(map.get("question_answerCount").toString());
            assembleFragment.setQuestion_viewCount(map.get("question_viewCount").toString());
            assembleFragment.setAsker_url(map.get("asker_url").toString());
            assembleFragment.setAsker_name(map.get("asker_name").toString());
            assembleFragment.setAsker_reputation(map.get("asker_reputation").toString());
            assembleFragment.setAsker_answerCount(map.get("asker_answerCount").toString());
            assembleFragment.setAsker_questionCount(map.get("asker_questionCount").toString());
            assembleFragment.setAsker_viewCount(map.get("asker_viewCount").toString());
            assembleFragment.setAsker_best_answer_rate(map.get("asker_best_answer_rate").toString());
            assembleFragment.setFragment_id(Integer.parseInt(map.get("fragment_id").toString()));

            /**
             * 根据数据源的不同设置问题的质量标签
             */
            // 得到问题分数和回答数
            int question_score = Integer.parseInt(map.get("question_score").toString());
            int question_answerCount = Integer.parseInt(map.get("question_answerCount").toString());
            // 问题的数据源
            if (sourceName.equalsIgnoreCase("stackoverflow")) {
                // StackOverflow数据源
                int question_viewCount = Integer.parseInt(map.get("question_viewCount").toString()); // 问题浏览[10, 20000]
                double quality_score = (double) question_score / (double) question_viewCount;
                if (question_viewCount > 1000) {
                    // 对于浏览数大于1000的问题，当质量分数小于0.001为低质量，高于0.001为高质量（平均值为0.0024左右）
                    if (quality_score > avgSoBigView - 0.001) {
                        assembleFragment.setQuestion_quality_label("high");
                    } else {
                        assembleFragment.setQuestion_quality_label("low");
                    }
                } else {
                    // 对于浏览数小于1000的问题，当质量分数小于0.004为低质量，高于0.004为高质量（平均值为0.0058左右）
                    if (quality_score > avgSoSmallView - 0.001) {
                        assembleFragment.setQuestion_quality_label("high");
                    } else {
                        assembleFragment.setQuestion_quality_label("low");
                    }
                }
            } else if (sourceName.equalsIgnoreCase("yahoo")) {
                // Yahoo数据源
                if (question_score > 0) { // 问题分数大于0就是高质量
                    assembleFragment.setQuestion_quality_label("high");
                } else { // 问题分数小于0根据回答数是否大于均值来设置其质量高低
                    if (question_answerCount > avgYahoo) {
                        assembleFragment.setQuestion_quality_label("high");
                    } else {
                        assembleFragment.setQuestion_quality_label("low");
                    }
                }
            }
            assembleFragmentList.add(assembleFragment);
        }

        return assembleFragmentList;
    }

    /**
     * 根据课程名，计算得到该课程下所有问题在stackoverflow和Yahoo数据源的质量分数均值
     * @param className
     * @return
     */
    public static List<Double> getAvgQuality(String className) {
        List<Double> avgQuality = new ArrayList<>();

        /**
         * 读取assemble_fragment_question：SO数据源
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "SELECT afq.* FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "af.SourceName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add("Stackoverflow");
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            int countBig = 0;
            int countSmall = 0;
            double avgQualitySoBigView = 0.0; // stackoverflow问题浏览数大于1000的问题的质量分数均值
            double avgQualitySoSmallView = 0.0; // stackoverflow问题浏览数小于1000的问题的质量分数均值
            for (int j = 0; j < results.size(); j++) {
                Map<String, Object> map = results.get(j);
                int id = Integer.parseInt(map.get("question_id").toString());
                // 问题分数[-20, 50]
                String question_score_str = map.get("question_score").toString();
                int question_score = 0;
                if (question_score_str != "") {
                    question_score = Integer.parseInt(question_score_str);
                }
                // 问题回答[0, 10]
                String answers = map.get("question_answerCount").toString();
                int question_answerCount = 0;
                if (answers != "") {
                    question_answerCount = Integer.parseInt(answers);
                }
                // 问题浏览[10, 20000]
                String views = map.get("question_viewCount").toString();
                int question_viewCount = 1;
                if (views != "") {
                    question_viewCount = Integer.parseInt(views);
                }
                double quality_score = (double) question_score / (double) question_viewCount;
                if (question_viewCount > 1000) {
                    countBig++;
                    avgQualitySoBigView += quality_score;
                } else {
                    countSmall++;
                    avgQualitySoSmallView += quality_score;
                }
            }
            avgQualitySoBigView /= countBig;
            avgQualitySoSmallView /= countSmall;
            avgQuality.add(avgQualitySoBigView);
            avgQuality.add(avgQualitySoSmallView);

            System.out.println("数据源：stackoverflow，课程名：" + className + "，浏览数大于1000的问题，质量分数平均数：" + avgQualitySoBigView);
            System.out.println("数据源：stackoverflow，课程名：" + className + "，浏览数小于1000的问题，质量分数平均数：" + avgQualitySoSmallView);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }

        /**
         * 读取assemble_fragment_question：Yahoo数据源
         */
        mysqlUtils mysqlYahoo = new mysqlUtils();
        String sqlYahoo = "SELECT afq.* FROM\n" +
                Config.ASSEMBLE_FRAGMENT_TABLE + " AS af ,\n" +
                Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " AS afq\n" +
                "WHERE\n" +
                "af.FragmentID = afq.fragment_id AND\n" +
                "af.ClassName = ? AND\n" +
                "af.SourceName = ?";
        List<Object> paramsYahoo = new ArrayList<Object>();
        paramsYahoo.add(className);
        paramsYahoo.add("Yahoo");
        try {
            List<Map<String, Object>> results = mysqlYahoo.returnMultipleResult(sqlYahoo, paramsYahoo);
            double avgQualityYahoo = 0.0; // Yahoo问题的质量分数均值
            for (int j = 0; j < results.size(); j++) {
                Map<String, Object> map = results.get(j);
                int id = Integer.parseInt(map.get("question_id").toString());
                // 问题分数[-20, 50]
                String question_score_str = map.get("question_score").toString();
                int question_score = 0;
                if (question_score_str != "") {
                    question_score = Integer.parseInt(question_score_str);
                }
                // 问题回答[0, 10]
                String answers = map.get("question_answerCount").toString();
                int question_answerCount = 0;
                if (answers != "") {
                    question_answerCount = Integer.parseInt(answers);
                }
                // Yahoo数据源
                avgQualityYahoo += question_answerCount;
            }
            avgQualityYahoo /= results.size();
            avgQuality.add(avgQualityYahoo);

            System.out.println("数据源：yahoo，课程名：" + className + "，质量分数平均数：" + avgQualityYahoo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysqlYahoo.closeconnection();
        }

        return avgQuality;
    }

    /**
     * 处理数据库查询assemble_fragment_question表格返回的数据
     * @param results
     * @return
     */
    public static List<AssembleFragmentQuestionAndAsker> getQuestionsFromSql(
            List<Map<String, Object>> results
    ) {
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();
        for (int j = 0; j < results.size(); j++) {
            Map<String, Object> map = results.get(j);
            AssembleFragmentQuestionAndAsker assembleFragment = new AssembleFragmentQuestionAndAsker();
            assembleFragment.setQuestion_id(Integer.parseInt(map.get("question_id").toString()));
            assembleFragment.setPage_website_logo(map.get("page_website_logo").toString());
            assembleFragment.setPage_search_url(map.get("page_search_url").toString());
            assembleFragment.setPage_column_color(map.get("page_column_color").toString());

            assembleFragment.setQuestion_url(map.get("question_url").toString());
            assembleFragment.setQuestion_title(map.get("question_title").toString());
            assembleFragment.setQuestion_title_pure(map.get("question_title_pure").toString());
            assembleFragment.setQuestion_body(map.get("question_body").toString());
            assembleFragment.setQuestion_body_pure(map.get("question_body_pure").toString());
            assembleFragment.setQuestion_best_answer(map.get("question_best_answer").toString());
            assembleFragment.setQuestion_best_answer_pure(map.get("question_best_answer_pure").toString());

            assembleFragment.setQuestion_score(map.get("question_score").toString());
            assembleFragment.setQuestion_answerCount(map.get("question_answerCount").toString());
            assembleFragment.setQuestion_viewCount(map.get("question_viewCount").toString());

            assembleFragment.setAsker_url(map.get("asker_url").toString());
            assembleFragment.setAsker_name(map.get("asker_name").toString());
            assembleFragment.setAsker_reputation(map.get("asker_reputation").toString());
            assembleFragment.setAsker_answerCount(map.get("asker_answerCount").toString());
            assembleFragment.setAsker_questionCount(map.get("asker_questionCount").toString());
            assembleFragment.setAsker_viewCount(map.get("asker_viewCount").toString());
            assembleFragment.setAsker_best_answer_rate(map.get("asker_best_answer_rate").toString());
            assembleFragment.setQuestion_quality_label(map.get("question_quality_label").toString());
            assembleFragment.setFragment_id(Integer.parseInt(map.get("fragment_id").toString()));

            assembleFragmentList.add(assembleFragment);
        }
        return assembleFragmentList;
    }

}
