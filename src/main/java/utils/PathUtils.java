package utils;

/**
 * @author yuanhao
 * @date 2017/12/19 16:08
 */
public class PathUtils {

    public static void main(String[] args) {
        System.out.println(PathUtils.class);
		System.out.println(PathUtils.class.getClassLoader());
		System.out.println();
		System.out.println(PathUtils.class.getResource(""));
		System.out.println(PathUtils.class.getClassLoader().getResource("").getPath());
    }
}
