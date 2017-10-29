package domainTopic.bean;

/**
 * 主题
 * @author 郑元浩
 * @date 2016年11月28日
 */
public class DomainTopic {

    public String termName;
    public String termUrl;
    public int termLayer;

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermUrl() {
        return termUrl;
    }

    public void setTermUrl(String termUrl) {
        this.termUrl = termUrl;
    }

    public int getTermLayer() {
        return termLayer;
    }

    public void setTermLayer(int termLayer) {
        this.termLayer = termLayer;
    }

    public DomainTopic(String termName, String termUrl, int termLayer) {
        super();
        this.termName = termName;
        this.termUrl = termUrl;
        this.termLayer = termLayer;
    }

    @Override
    public String toString() {
        return "DomainTopic{" +
                "termName='" + termName + '\'' +
                ", termUrl='" + termUrl + '\'' +
                ", termLayer=" + termLayer +
                '}';
    }

    public DomainTopic() {
    }
}
