package com.wy.interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * 违禁词检验
 * 文件里存一些违禁词，每行一个
 * 建一个违禁词校验器，对进来的 String 数据进行校验（全字符匹配）, 返回是否名字违禁词
 * 要注意文件读取的错误处理
 * @author matthew_wu
 * @since 2020/12/9 4:38 下午
 */
public class KfcChecker {

    private static final Set<String> dict = new HashSet();

    static {
        try (InputStream in = KfcChecker.class.getClass().getResourceAsStream("/dict.txt");
             InputStreamReader reader = new InputStreamReader(in);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                dict.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(check("a"));
        System.out.println(check("a1"));
    }

    public static boolean check(String word) {
        return dict.contains(word);
    }

}
