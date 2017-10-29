package dependency.ranktext;

/**
 * 主题认知关系类
 * @author 郑元浩
 * @date 2017年10月18日 下午7:44:08
 */
public class TwoTuple<A, B> {
    public final A first;
    public final B second;

    public TwoTuple(A a, B b) {
        first = a;
        second = b;
    }

    public String toString() {
        return first.toString() + second.toString();
    }
}
