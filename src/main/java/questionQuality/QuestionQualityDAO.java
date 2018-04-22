package questionQuality;

import app.Config;
import spider.bean.AssembleFragmentQuestionAndAsker;
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
        getQuestionQualityByTopicAndFacetAndSource("Abstract_data_types", "2–3 heap", "Abstract", "Yahoo");
//        getQuestionQualityByTopicAndFacet("Abstract_data_types", "2–3 heap", "Stackoverflow");
//        getQuestionQualityByTopicAndSource("Abstract_data_types", "2–3 heap", "Stackoverflow");
//        getQuestionQualityByTopic("Abstract_data_types", "2–3 heap");
//        getQuestionQualityByClass("Abstract_data_types");
    }

    /**
     * 根据课程、主题、分面、数据源，计算问题的标签信息
     * @param className
     * @param topicName
     * @param facetName
     * @param sourceName
     * @return
     */
    public static List<AssembleFragmentQuestionAndAsker> getQuestionQualityByTopicAndFacetAndSource(
            String className, String topicName, String facetName, String sourceName
    ) {
        // 得到这门课的平均质量值
        List<Double> avgQuality = getAvgQuality(className);
        // 返回的数据
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();
        // 读取assemble_fragment_question
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and FacetName=? and sourceName=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        params.add(facetName);
        params.add(sourceName);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            assembleFragmentList = getQuestionQuality(avgQuality, results);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }

        return assembleFragmentList;
    }

    /**
     * 根据课程、主题、分面，计算问题的标签信息
     * @param className
     * @param topicName
     * @param facetName
     * @return
     */
    public static List<AssembleFragmentQuestionAndAsker> getQuestionQualityByTopicAndFacet(
            String className, String topicName, String facetName
    ) {
        // 得到这门课的平均质量值
        List<Double> avgQuality = getAvgQuality(className);
        // 返回的数据
        List<AssembleFragmentQuestionAndAsker> assembleFragmentList = new ArrayList<>();
        // 读取assemble_fragment_question
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and FacetName=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        params.add(facetName);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            assembleFragmentList = getQuestionQuality(avgQuality, results);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }

        return assembleFragmentList;
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
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=? and SourceName=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        params.add(sourceName);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            assembleFragmentList = getQuestionQuality(avgQuality, results);
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
        // 读取assemble_fragment_question
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=? and TermName=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        params.add(topicName);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            assembleFragmentList = getQuestionQuality(avgQuality, results);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
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
        // 读取assemble_fragment_question
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            assembleFragmentList = getQuestionQuality(avgQuality, results);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
        }

        return assembleFragmentList;
    }

    /**
     * 根据每个课程下的质量均值，计算查询到的问题的质量
     * @param avgQuality
     * @param results
     * @return
     */
    public static List<AssembleFragmentQuestionAndAsker> getQuestionQuality(List<Double> avgQuality, List<Map<String, Object>> results) {
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
            assembleFragment.setFragmentID(Integer.parseInt(map.get("FragmentID").toString()));
            assembleFragment.setFragmentContent(map.get("FragmentContent").toString());
            assembleFragment.setText(map.get("Text").toString());
            assembleFragment.setFragmentScratchTime(map.get("FragmentScratchTime").toString());
            assembleFragment.setTermID(Integer.parseInt(map.get("TermID").toString()));
            assembleFragment.setTermName(map.get("TermName").toString());
            assembleFragment.setFacetName(map.get("FacetName").toString());
            assembleFragment.setFacetLayer(Integer.parseInt(map.get("FacetLayer").toString()));
            assembleFragment.setClassName(map.get("ClassName").toString());
            assembleFragment.setSourceName(map.get("SourceName").toString());
            assembleFragment.setQuestion_url(map.get("question_url").toString());
            assembleFragment.setQuestion_title(map.get("question_title").toString());
            assembleFragment.setQuestion_title_pure(map.get("question_title_pure").toString());
            assembleFragment.setQuestion_body(map.get("question_body").toString());
            assembleFragment.setQuestion_body_pure(map.get("question_body_pure").toString());
            assembleFragment.setQuestion_best_answer(map.get("question_best_answer").toString());
            assembleFragment.setQuestion_best_answer_pure(map.get("question_best_answer_pure").toString());
            assembleFragment.setQuestion_score(map.get("question_score").toString());
            String answers = map.get("question_answerCount").toString();
            if (answers.contains(" answers")) {
                answers = answers.substring(0, answers.indexOf(" answers"));
            } else if (answers.contains(" answer")) {
                answers = answers.substring(0, answers.indexOf(" answer"));
            }
            assembleFragment.setQuestion_answerCount(answers);
            String viewCount = map.get("question_viewCount").toString();
            if (viewCount.contains(" times")) {
                viewCount = viewCount.substring(0, viewCount.indexOf(" times"));
            }
            assembleFragment.setQuestion_viewCount(viewCount);
            assembleFragment.setAsker_url(map.get("asker_url").toString());
            assembleFragment.setAsker_name(map.get("asker_name").toString());
            assembleFragment.setAsker_reputation(map.get("asker_reputation").toString());
            assembleFragment.setAsker_answerCount(map.get("asker_answerCount").toString());
            assembleFragment.setAsker_questionCount(map.get("asker_questionCount").toString());
            String viewsAsker = map.get("asker_viewCount").toString();
            if (viewsAsker.contains(" profile views")) {
                viewsAsker = viewsAsker.substring(0, viewsAsker.indexOf(" profile views"));
            }
            assembleFragment.setAsker_viewCount(viewsAsker);
            assembleFragment.setAsker_best_answer_rate(map.get("asker_best_answer_rate").toString());

            /**
             * 根据数据源的不同设置问题的质量标签
             */
            // 得到问题分数和回答数
            int question_score = processQuestionScore(map.get("question_score").toString());
            int question_answerCount = QuestionQualityDAO.processQuestionAnswerCount(map.get("question_answerCount").toString());
            // 问题的数据源
            String sourceName = map.get("SourceName").toString();
            if (sourceName.equalsIgnoreCase("stackoverflow")) {
                // StackOverflow数据源
                String views = map.get("question_viewCount").toString(); // 问题浏览[10, 20000]
                int question_viewCount = processQuestionViewCount(views);
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
         * 读取assemble_fragment_question
         */
        mysqlUtils mysql = new mysqlUtils();
        String sql = "select * from " + Config.ASSEMBLE_FRAGMENT_QUESTION_TABLE + " where ClassName=?";
        List<Object> params = new ArrayList<Object>();
        params.add(className);
        try {
            List<Map<String, Object>> results = mysql.returnMultipleResult(sql, params);
            int countBig = 0;
            int countSmall = 0;
            double avgQualitySoBigView = 0.0; // stackoverflow问题浏览数大于1000的问题的质量分数均值
            double avgQualitySoSmallView = 0.0; // stackoverflow问题浏览数小于1000的问题的质量分数均值
            double avgQualityYahoo = 0.0; // Yahoo问题的质量分数均值
            for (int j = 0; j < results.size(); j++) {
                Map<String, Object> map = results.get(j);
                int id = Integer.parseInt(map.get("FragmentID").toString());
                // 问题分数[-20, 50]
                String question_score_str = map.get("question_score").toString();
                int question_score = QuestionQualityDAO.processQuestionScore(question_score_str);
                // 问题回答[0, 10]
                String answers = map.get("question_answerCount").toString();
                int question_answerCount = processQuestionAnswerCount(answers);
                // 问题的数据源
                String sourceName = map.get("SourceName").toString();
                /**
                 * 根据数据源的不同设置问题的质量标签
                 */
                if (sourceName.equalsIgnoreCase("stackoverflow")) {
                    // StackOverflow数据源
                    String views = map.get("question_viewCount").toString(); // 问题浏览[10, 20000]
                    int question_viewCount = processQuestionViewCount(views);
                    double quality_score = (double) question_score / (double) question_viewCount;
                    if (question_viewCount > 1000) {
                        countBig++;
                        avgQualitySoBigView += quality_score;
                    } else {
                        countSmall++;
                        avgQualitySoSmallView += quality_score;
                    }
                } else if (sourceName.equalsIgnoreCase("yahoo")) {
                    // Yahoo数据源
                    avgQualityYahoo += question_answerCount;
                }
            }
            avgQualitySoBigView /= countBig;
            avgQualitySoSmallView /= countSmall;
            avgQualityYahoo /= results.size();
            avgQuality.add(avgQualitySoBigView);
            avgQuality.add(avgQualitySoSmallView);
            avgQuality.add(avgQualityYahoo);

            System.out.println("数据源：stackoverflow，课程名：" + className + "，浏览数大于1000的问题，质量分数平均数：" + avgQualitySoBigView);
            System.out.println("数据源：stackoverflow，课程名：" + className + "，浏览数小于1000的问题，质量分数平均数：" + avgQualitySoSmallView);
            System.out.println("数据源：yahoo，课程名：" + className + "，质量分数平均数：" + avgQualityYahoo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeconnection();
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
            assembleFragment.setFragmentID(Integer.parseInt(map.get("FragmentID").toString()));
            assembleFragment.setFragmentContent(map.get("FragmentContent").toString());
            assembleFragment.setText(map.get("Text").toString());
            assembleFragment.setFragmentScratchTime(map.get("FragmentScratchTime").toString());
            assembleFragment.setTermID(Integer.parseInt(map.get("TermID").toString()));
            assembleFragment.setTermName(map.get("TermName").toString());
            assembleFragment.setFacetName(map.get("FacetName").toString());
            assembleFragment.setFacetLayer(Integer.parseInt(map.get("FacetLayer").toString()));
            assembleFragment.setClassName(map.get("ClassName").toString());
            assembleFragment.setSourceName(map.get("SourceName").toString());

            assembleFragment.setQuestion_url(map.get("question_url").toString());
            assembleFragment.setQuestion_title(map.get("question_title").toString());
            assembleFragment.setQuestion_title_pure(map.get("question_title_pure").toString());
            assembleFragment.setQuestion_body(map.get("question_body").toString());
            assembleFragment.setQuestion_body_pure(map.get("question_body_pure").toString());
            assembleFragment.setQuestion_best_answer(map.get("question_best_answer").toString());
            assembleFragment.setQuestion_best_answer_pure(map.get("question_best_answer_pure").toString());

            assembleFragment.setQuestion_score(map.get("question_score").toString());
            String answers = map.get("question_answerCount").toString();
            if (answers.contains(" answers")) {
                answers = answers.substring(0, answers.indexOf(" answers"));
            } else if (answers.contains(" answer")) {
                answers = answers.substring(0, answers.indexOf(" answer"));
            }
            assembleFragment.setQuestion_answerCount(answers);
            String views = map.get("question_viewCount").toString();
            if (views.contains(" times")) {
                views = views.substring(0, views.indexOf(" times"));
            }
            assembleFragment.setQuestion_viewCount(views);

            assembleFragment.setAsker_url(map.get("asker_url").toString());
            assembleFragment.setAsker_name(map.get("asker_name").toString());
            assembleFragment.setAsker_reputation(map.get("asker_reputation").toString());
            assembleFragment.setAsker_answerCount(map.get("asker_answerCount").toString());
            assembleFragment.setAsker_questionCount(map.get("asker_questionCount").toString());
            String viewsAsker = map.get("asker_viewCount").toString();
            if (viewsAsker.contains(" profile views")) {
                viewsAsker = viewsAsker.substring(0, viewsAsker.indexOf(" profile views"));
            }
            assembleFragment.setAsker_viewCount(viewsAsker);
            assembleFragment.setAsker_best_answer_rate(map.get("asker_best_answer_rate").toString());
            assembleFragment.setQuestion_quality_label(map.get("question_quality_label").toString());

            assembleFragmentList.add(assembleFragment);
        }
        return assembleFragmentList;
    }

    /**
     * 处理问题分数
     * @param question_score_str
     * @return
     */
    public static int processQuestionScore(String question_score_str) {
        if (question_score_str.contains(",")) {
            question_score_str = question_score_str.replaceAll(",", "");
        }
        return Integer.parseInt(question_score_str);
    }

    /**
     * 处理回答数
     * @param answers
     * @return
     */
    public static int processQuestionAnswerCount(String answers) {
        if (answers.contains(" answers")) {
            answers = answers.substring(0, answers.indexOf(" answers"));
        } else if (answers.contains(" answer")) {
            answers = answers.substring(0, answers.indexOf(" answer"));
        }
        if (answers.contains(",")) {
            answers = answers.replaceAll(",", "");
        }
        return Integer.parseInt(answers);
    }

    /**
     * 处理浏览数
     * @param views
     * @return
     */
    public static int processQuestionViewCount(String views) {
        if (views.contains(" times")) {
            views = views.substring(0, views.indexOf(" times"));
        }
        if (views.contains(",")) {
            views = views.replaceAll(",", "");
        }
        return Integer.parseInt(views);
    }

}
