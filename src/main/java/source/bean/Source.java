package source.bean;

/**
 * 数据源
 *
 * @author yuanhao
 * @date 2017/12/21 14:20
 */
public class Source {

    private int sourceID;
    private String sourceName;
    private String note;

    @Override
    public String toString() {
        return "Source{" +
                "sourceID=" + sourceID +
                ", sourceName='" + sourceName + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Source() {

    }

    public Source(int sourceID, String sourceName, String note) {

        this.sourceID = sourceID;
        this.sourceName = sourceName;
        this.note = note;
    }
}
