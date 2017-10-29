package domainTopic.bean;

import java.util.List;

/**
 * 用于生成主题上下位那个图的Json所构建的基本数据结构
 * @author 郑元浩
 * @date 2016年12月20日
 */
public class Topic {

    public int id;
    public String name;
    public Object data;
    public List<Topic> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Topic> getChildren() {
        return children;
    }

    public void setChildren(List<Topic> children) {
        this.children = children;
    }

    public Topic(int id, String name, Object data, List<Topic> children) {
        super();
        this.id = id;
        this.name = name;
        this.data = data;
        this.children = children;
    }

    public Topic() {
        super();
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", data=" + data +
                ", children=" + children +
                '}';
    }
}
