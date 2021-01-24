package com.wy.interview;

import java.io.*;

/**
 * 给定一个utf-8编码的文本文件，要求用JDK原生类读取其中的内容，并统计指定字符（如：hello）出现的次数。
 * - 对JDK原生的类是否熟悉
 * - 对文件编码处理是否熟悉
 * - 对文件的打开与关闭等边界条件是否有考虑
 * - 对字符串的常见操作是否熟悉，包括indexOf，正则等
 * 使用 try-with-resource
 * @author matthew_wu
 * @since 2020/12/9 3:08 下午
 */
public class ReadFile {

    public static void main(String[] args) {
        int count = 0;
        try (
                InputStream in = ReadFile.class.getClass().getResourceAsStream("/in.txt");
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splits = line.split(" ");
                for (String word : splits) {
                    if ("hello".equals(word)) {
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

}
