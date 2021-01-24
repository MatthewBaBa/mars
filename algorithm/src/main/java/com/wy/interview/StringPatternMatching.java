package com.wy.interview;

import java.util.Arrays;

/**
 * 字符串模式匹配
 * 有一个字符串，它的构成是词 + 空格的组合，如“北京 杭州 杭州 北京 上海”， 要求输入一个匹配模式（简单的以字符来写），比如 aabb，来判断该字符串是否符合该模式， 举个例子：
 * pattern = "abbac", str="北京 杭州 杭州 北京 上海", 返回 ture
 * pattern = "aacbb", str="北京 北京 上海 杭州 北京", 返回 false
 * pattern = "baabcc", str="北京 杭州 杭州 北京 上海 上海", 返回 ture
 * @author matthew_wu
 * @since 2020/12/10 11:34 上午
 */
public class StringPatternMatching {

    public static void main(String[] args) {
        System.out.println(solution("abbac", "北京 杭州 杭州 北京 上海"));
        System.out.println(solution("aacbb", "北京 北京 上海 杭州 北京"));
        System.out.println(solution("baabcc", "北京 杭州 杭州 北京 上海 上海"));
    }

    private static boolean solution(String pattern, String str) {
        char[] patternArr = pattern.toCharArray();
        String[] destArr = str.split(" ");
        if (patternArr.length != destArr.length) {
            return false;
        }

        int[] pos = new int[patternArr.length];
        for (int i = 1; i < patternArr.length; i++) {
            int find = i;
            for (int j = 0; j < i; j++) {
                if (patternArr[j] == patternArr[i]) {
                   find = j;
                   break;
                }
            }
            pos[i] = find;
        }
        System.out.println(Arrays.toString(pos));
        for (int i = 0; i < destArr.length; i++) {
            if (!destArr[pos[i]].equals(destArr[i])) {
                return false;
            }
        }
        return true;
    }

}
