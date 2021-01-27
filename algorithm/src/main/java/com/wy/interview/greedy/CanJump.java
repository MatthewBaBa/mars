package com.wy.interview.greedy;

/**
 * @author matthew_wu
 * @since 2021/2/4 3:21 下午
 */
public class CanJump {

    public static void main(String[] args) {

    }

    private static boolean solution(int[] nums)  {
        int farthest = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            // 不断计算能跳到最远的距离
            farthest = Math.max(farthest, i + nums[i]);
            // 可能碰到了0，卡住跳不动了
            if (farthest <= i) {
                return false;
            }
        }
        return true;
    }
}
