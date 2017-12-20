package domainTopic.bean;

import java.util.List;

/**
 * 用于生成主题上下位那个图的Json所构建的基本数据结构   
 *
 * @author 郑元浩 
 * @date 2016年12月20日
 */
public class TopicShangXiaWei {

    public int id;
    public String name;
    public Object data;
    public List<TopicShangXiaWei> children;

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

    public List<TopicShangXiaWei> getChildren() {
        return children;
    }

    public void setChildren(List<TopicShangXiaWei> children) {
        this.children = children;
    }

    /**
     * @param id
     * @param name
     * @param data
     * @param children
     */
    public TopicShangXiaWei(int id, String name, Object data, List<TopicShangXiaWei> children) {
        super();
        this.id = id;
        this.name = name;
        this.data = data;
        this.children = children;
    }

    /**
     *
     */
    public TopicShangXiaWei() {
        super();
        // TODO Auto-generated constructor stub
    }


}
