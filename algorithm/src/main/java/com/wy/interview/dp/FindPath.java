package com.wy.interview.dp;

/**
 * @author matthew_wu
 * @since 2020/7/1 21:28
 */
public class FindPath {

    /**
     在上面的数字三角形中寻找一条从顶部到底边的路径，使得路径上所经过的数字之和最大。路径上的每一步都只能往左下或 右下走。只需要求出这个最大和即可，不必给出具体路径。 三角形的行数大于1小于等于100，数字为 0 - 99
     输入格式：
     5      //表示三角形的行数    接下来输入三角形
     7
     3   8
     8   1   0
     2   7   4   4
     4   5   2   6   5
     要求输出最大和
     接下来，我们来分析一下解题思路：
     首先，肯定得用二维数组来存放数字三角形
     然后我们用D( r, j) 来表示第r行第 j 个数字(r,j从1开始算)
     我们用MaxSum(r, j)表示从D(r,j)到底边的各条路径中，最佳路径的数字之和。
     因此，此题的最终问题就变成了求 MaxSum(1,1)
     当我们看到这个题目的时候，首先想到的就是可以用简单的递归来解题：
     D(r, j)出发，下一步只能走D(r+1,j)或者D(r+1, j+1)。故对于N行的三角形，我们可以写出如下的递归式：

     if ( r == N)
     MaxSum(r,j) = D(r,j)
     else
     MaxSum( r, j) = Max{ MaxSum(r＋1,j), MaxSum(r+1,j+1) } + D(r,j)

     根据上面这个简单的递归式，我们就可以很轻松地写出完整的递归代码：
     **/

    public static void main(String[] args) {
        int[][] a = new int[6][6];
        a[0] = new int[]{0, 0, 0, 0, 0, 0};
        a[1] = new int[]{0, 7 ,0, 0, 0, 0};
        a[2] = new int[]{0, 3, 8, 0, 0, 0};
        a[3] = new int[]{0, 8 ,1, 0, 0, 0};
        a[4] = new int[]{0, 2, 7, 4, 4, 0};
        a[5] = new int[]{0 ,4, 5, 2, 6, 5};

        int[] dp = a[5];
        for (int i = 4; i > 0 ; i--) {
            for (int j = 1; j <= i; j++) {
                dp[j] = Math.max(dp[j], dp[j+1]) + a[i][j];
            }
        }
        System.out.println(dp[1]);
    }
}
