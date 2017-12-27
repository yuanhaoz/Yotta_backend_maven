package subject.bean;

import domain.bean.Domain;

import java.util.List;

/**
 * 学科对象：每个学科包含其下的所有课程
 *
 * @author yuanhao
 * @date 2017/12/26 20:12
 */
public class Subject3 extends Subject{

    private List<Domain> domains;

    @Override
    public String toString() {
        return "Subject3{" +
                "domains=" + domains +
                '}';
    }

    public List<Domain> getDomains() {
        return domains;
    }

    public void setDomains(List<Domain> domains) {
        this.domains = domains;
    }

    public Subject3() {

    }

    public Subject3(int subjectID, String subjectName, String note, List<Domain> domains) {

        super(subjectID, subjectName, note);
        this.domains = domains;
    }
}
