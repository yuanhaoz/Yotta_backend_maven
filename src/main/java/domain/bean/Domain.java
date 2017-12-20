package domain.bean;

/**
 * 领域名--->课程名，用于数据库读写
 *
 * @author 郑元浩 
 * @date 2016年11月28日
 */
public class Domain {

    private int classID;
    private String className;
    private String SubjectName;
    private String Note;

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    /**
     * @param classID
     * @param className
     */
    public Domain(int classID, String className) {
        super();
        this.classID = classID;
        this.className = className;
    }

    public Domain(int classID, String className, String subjectName, String note) {
        this.classID = classID;
        this.className = className;
        SubjectName = subjectName;
        Note = note;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "classID=" + classID +
                ", className='" + className + '\'' +
                ", SubjectName='" + SubjectName + '\'' +
                ", Note='" + Note + '\'' +
                '}';
    }

    /**
     *
     */
    public Domain() {
        super();
        // TODO Auto-generated constructor stub
    }


}
