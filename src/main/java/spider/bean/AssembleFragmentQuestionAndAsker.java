package spider.bean;

/**
 * 装配好的问题碎片：包括问题和提问者信息
 * @author yuanhao
 * @date 2018/4/12 14:31
 */
public class AssembleFragmentQuestionAndAsker extends AssembleFragment {

    // 问题信息
    private String question_url;
    private String question_title;
    private String question_title_pure;
    private String question_body;
    private String question_body_pure;
    private String question_best_answer;
    private String question_best_answer_pure;
    private String question_score;
    private String question_answerCount;
    private String question_viewCount;
    private String asker_url;

    // 用户信息
    private String asker_name;
    private String asker_reputation;
    private String asker_answerCount;
    private String asker_questionCount;
    private String asker_viewCount;
    private String asker_best_answer_rate;

    // 标签信息
    private String question_quality_label;

    @Override
    public String toString() {
        return "AssembleFragmentQuestionAndAsker{" +
                "question_url='" + question_url + '\'' +
                ", question_title='" + question_title + '\'' +
                ", question_title_pure='" + question_title_pure + '\'' +
                ", question_body='" + question_body + '\'' +
                ", question_body_pure='" + question_body_pure + '\'' +
                ", question_best_answer='" + question_best_answer + '\'' +
                ", question_best_answer_pure='" + question_best_answer_pure + '\'' +
                ", question_score='" + question_score + '\'' +
                ", question_answerCount='" + question_answerCount + '\'' +
                ", question_viewCount='" + question_viewCount + '\'' +
                ", asker_url='" + asker_url + '\'' +
                ", asker_name='" + asker_name + '\'' +
                ", asker_reputation='" + asker_reputation + '\'' +
                ", asker_answerCount='" + asker_answerCount + '\'' +
                ", asker_questionCount='" + asker_questionCount + '\'' +
                ", asker_viewCount='" + asker_viewCount + '\'' +
                ", asker_best_answer_rate='" + asker_best_answer_rate + '\'' +
                ", question_quality_label='" + question_quality_label + '\'' +
                '}';
    }

    public String getQuestion_url() {
        return question_url;
    }

    public void setQuestion_url(String question_url) {
        this.question_url = question_url;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_title_pure() {
        return question_title_pure;
    }

    public void setQuestion_title_pure(String question_title_pure) {
        this.question_title_pure = question_title_pure;
    }

    public String getQuestion_body() {
        return question_body;
    }

    public void setQuestion_body(String question_body) {
        this.question_body = question_body;
    }

    public String getQuestion_body_pure() {
        return question_body_pure;
    }

    public void setQuestion_body_pure(String question_body_pure) {
        this.question_body_pure = question_body_pure;
    }

    public String getQuestion_best_answer() {
        return question_best_answer;
    }

    public void setQuestion_best_answer(String question_best_answer) {
        this.question_best_answer = question_best_answer;
    }

    public String getQuestion_best_answer_pure() {
        return question_best_answer_pure;
    }

    public void setQuestion_best_answer_pure(String question_best_answer_pure) {
        this.question_best_answer_pure = question_best_answer_pure;
    }

    public String getQuestion_score() {
        return question_score;
    }

    public void setQuestion_score(String question_score) {
        this.question_score = question_score;
    }

    public String getQuestion_answerCount() {
        return question_answerCount;
    }

    public void setQuestion_answerCount(String question_answerCount) {
        this.question_answerCount = question_answerCount;
    }

    public String getQuestion_viewCount() {
        return question_viewCount;
    }

    public void setQuestion_viewCount(String question_viewCount) {
        this.question_viewCount = question_viewCount;
    }

    public String getAsker_url() {
        return asker_url;
    }

    public void setAsker_url(String asker_url) {
        this.asker_url = asker_url;
    }

    public String getAsker_name() {
        return asker_name;
    }

    public void setAsker_name(String asker_name) {
        this.asker_name = asker_name;
    }

    public String getAsker_reputation() {
        return asker_reputation;
    }

    public void setAsker_reputation(String asker_reputation) {
        this.asker_reputation = asker_reputation;
    }

    public String getAsker_answerCount() {
        return asker_answerCount;
    }

    public void setAsker_answerCount(String asker_answerCount) {
        this.asker_answerCount = asker_answerCount;
    }

    public String getAsker_questionCount() {
        return asker_questionCount;
    }

    public void setAsker_questionCount(String asker_questionCount) {
        this.asker_questionCount = asker_questionCount;
    }

    public String getAsker_viewCount() {
        return asker_viewCount;
    }

    public void setAsker_viewCount(String asker_viewCount) {
        this.asker_viewCount = asker_viewCount;
    }

    public String getAsker_best_answer_rate() {
        return asker_best_answer_rate;
    }

    public void setAsker_best_answer_rate(String asker_best_answer_rate) {
        this.asker_best_answer_rate = asker_best_answer_rate;
    }

    public String getQuestion_quality_label() {
        return question_quality_label;
    }

    public void setQuestion_quality_label(String question_quality_label) {
        this.question_quality_label = question_quality_label;
    }

    public AssembleFragmentQuestionAndAsker() {

    }

    public AssembleFragmentQuestionAndAsker(String question_url, String question_title, String question_title_pure, String question_body, String question_body_pure, String question_best_answer, String question_best_answer_pure, String question_score, String question_answerCount, String question_viewCount, String asker_url, String asker_name, String asker_reputation, String asker_answerCount, String asker_questionCount, String asker_viewCount, String asker_best_answer_rate, String question_quality_label) {

        this.question_url = question_url;
        this.question_title = question_title;
        this.question_title_pure = question_title_pure;
        this.question_body = question_body;
        this.question_body_pure = question_body_pure;
        this.question_best_answer = question_best_answer;
        this.question_best_answer_pure = question_best_answer_pure;
        this.question_score = question_score;
        this.question_answerCount = question_answerCount;
        this.question_viewCount = question_viewCount;
        this.asker_url = asker_url;
        this.asker_name = asker_name;
        this.asker_reputation = asker_reputation;
        this.asker_answerCount = asker_answerCount;
        this.asker_questionCount = asker_questionCount;
        this.asker_viewCount = asker_viewCount;
        this.asker_best_answer_rate = asker_best_answer_rate;
        this.question_quality_label = question_quality_label;
    }

    public AssembleFragmentQuestionAndAsker(int fragmentID, String fragmentContent, String text, String fragmentScratchTime, int termID, String termName, String facetName, int facetLayer, String className, String sourceName, String question_url, String question_title, String question_title_pure, String question_body, String question_body_pure, String question_best_answer, String question_best_answer_pure, String question_score, String question_answerCount, String question_viewCount, String asker_url, String asker_name, String asker_reputation, String asker_answerCount, String asker_questionCount, String asker_viewCount, String asker_best_answer_rate, String question_quality_label) {
        super(fragmentID, fragmentContent, text, fragmentScratchTime, termID, termName, facetName, facetLayer, className, sourceName);
        this.question_url = question_url;
        this.question_title = question_title;
        this.question_title_pure = question_title_pure;
        this.question_body = question_body;
        this.question_body_pure = question_body_pure;
        this.question_best_answer = question_best_answer;
        this.question_best_answer_pure = question_best_answer_pure;
        this.question_score = question_score;
        this.question_answerCount = question_answerCount;
        this.question_viewCount = question_viewCount;
        this.asker_url = asker_url;
        this.asker_name = asker_name;
        this.asker_reputation = asker_reputation;
        this.asker_answerCount = asker_answerCount;
        this.asker_questionCount = asker_questionCount;
        this.asker_viewCount = asker_viewCount;
        this.asker_best_answer_rate = asker_best_answer_rate;
        this.question_quality_label = question_quality_label;
    }
}
