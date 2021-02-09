package com.wy.interview.bfs;

import java.util.*;

/**
 * 752. 打开转盘锁
 * @author matthew_wu
 * @since 2021/3/8 4:32 下午
 */
public class OpenLock {

    public static void main(String[] args) {
        System.out.println(solution(new String[]{"0201","0101","0102","1212","2002"}, "0202"));
    }

    private static int solution(String[] deadends, String target) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> deads = new HashSet<>();
        for (String deadend: deadends) {
            deads.add(deadend);
        }
        int step = 0;
        queue.add("0000");
        visited.add("0000");

        while (!queue.isEmpty()) {
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                System.out.println("size    " + sz);
                String cur = queue.poll();
                if (deads.contains(cur)) {
                    continue;
                }
                if (target.equals(cur)) {
                    return step;
                }
                for (int j = 0; j < 4; j++) {
                    String up = plusOne(cur, j);
                    if (!visited.contains(up)) {
                        queue.offer(up);
                        visited.add(up);
                    }
                    String down = minusOne(cur, j);
                    if (!visited.contains(down))  {
                        queue.offer(down);
                        visited.add(down);
                    }
                }
            }
            step++;
        }
        return -1;
    }

    private static String plusOne(String s, int idx) {
        char[] ch = s.toCharArray();
        if (ch[idx] == '9') {
            ch[idx] = '0';
        } else {
            ch[idx] += 1;
        }
        return new String(ch);
    }

    private static String minusOne(String s, int idx) {
        char[] ch = s.toCharArray();
        if (ch[idx] == '0') {
            ch[idx] = '9';
        } else {
            ch[idx] -= 1;
        }
        return new String(ch);
    }

}
