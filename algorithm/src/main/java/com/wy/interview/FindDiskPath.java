package com.wy.interview;

import java.io.File;
import java.util.*;

/**
 * 磁盘文件查找
 * 查找某个文件在磁盘中是否存在，如果存在，列出全路径，比如目录结构是这样的：
 * index/user/student
 * 当我查询 student 的时候，能给出是在第三层，parent=user, parent.parent = index，请注意写出用什么样的数据结构去容纳
 *
 * @author matthew_wu
 * @since 2020/12/9 4:44 下午
 */
public class FindDiskPath {

    public static void main(String[] args) {
        File parent = new File("/Users/matthew_wu/Documents");
        List<String> path = findPath("odps_config.ini", parent, new ArrayList<>());
        System.out.println(path);
        for (int i = 0; i < path.size(); i++) {
            String[] arr = new String[path.size() - i];
            Arrays.fill(arr, "parent");
            System.out.println(String.join(".", arr) + "=" + path.get(i));
        }
    }

    public static List<String> findPath(String target, File file, List<String> path) {
        File[] files = file.listFiles();
        /*
         * 防止对于某些文件夹，因为用户没有访问权限而导致 java.lang.NullPointerException错误
         * ,所以首先需判断文件夹是否为空,然后再进行递归遍历
         *
         */
        if (files != null) {
            for (File subFile : files) {
                if (subFile.isFile()) {
                    if (subFile.getName().equals(target)) {
                        return path;
                    }
                }
                if (subFile.isDirectory()) {
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(subFile.getName());
                    List<String> get = findPath(target, subFile, newPath);
                    if (get != null) {
                        return get;
                    }
                }
            }
        }
        return null;
    }

}
