package com.wy.interview;

import java.security.MessageDigest;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 给定任意的URL，生成全局唯一的短链接，如 https://t.cn/aBcDeF
 * 1. 可能是大多数人都会想到的一种方法。有人会提出，将一个长 URL 进行 Hash 运算，然后将 Hash 值作为这个长链接的唯一标示。但是通常容易想到的不一定是最优的，我们知道 Hash 有可能产生碰撞，Hash 碰撞的解决，会增强短链接系统的复杂度。
 * 2. 发号算法：顾名思义这个系统的第一个请求过来了，短链接系统的第一个请求我们可以给编为 t.cn/0，第二个 t.cn/1 等等；
 *
 * 实现的方式也会很简单：
 * 1. 小型的系统用 MySQL 的自增索引就可以满足；
 * 2. 大型系统可以考虑分布式 key-value 系统。
 * 3. 弊端：可以被穷举，其次考虑分布式序列生成器，譬如机器 A 发号只发向 100 取余等于 0的数字 100n，同理机器 B 只发向 100 取余等于 1 数字 100n+1，以此类推，各个机器相互独立互不干扰。需要考虑存储/记录/故障转移/故障恢复等/机器扩容等考察。
 *
 * 考察点：
 * - 对常用的 hash 算法是否了解，md5 算法，64 进制（52 个字母 + 10 个数字 + 2 个可见字符）算法等；
 * - 数据唯一性的处理，一对一如何做到；
 * - 数据处理效率，如何快速生成，和快速查找；
 * - 301 是永久重定向，302 是临时重定向。
 * -- 如果选择 301：短地址生成以后就不会变化，所以用 301 是符合 http 语义的，同时对服务器压力也会有一定减少，但这样一来，我们就无法统计到短地址被点击的次数了。
 * -- 如果选择 302：选择 302 虽然会增加服务器压力，但是可以统计到短地址被点击的次数了，可以针对点击次数进行后期的大数据处理，机器学习，以及推荐算法。
 *
 * 参考实现：（Hash 碰撞问题）
 * @author matthew_wu
 * @since 2020/12/25 3:17 下午
 */
public class ShortUrlGeneration {

    private static final AtomicLong ID = new AtomicLong(1000000L);
    private static final String URL_PREFIX = "https://t.cn/";
    private static final String[] chars = new String[] {  "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "_", "." };

    public static void main(String[] args) throws Exception {
        for (long i = 0; i < 100; i++) {
            System.out.println(genShortUrl(UUID.randomUUID().toString()));
        }
    }

    public static String genShortUrl(String longUrl) throws Exception {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] bytes = md.digest(Long.toString(ID.incrementAndGet()).getBytes());
        String hashUrl = to64RadixString(bytes);
        return new StringBuilder(URL_PREFIX).append(hashUrl).toString();
    }

    public static String to64RadixString(byte[] bytes) {
        StringBuilder bin = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i];
            for (int j = 0; j < 8; j++) {
                bin.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bin.length(); i = i + 6) {
            int index = 0;
            if (i + 6 > bin.length()) {
                index = Integer.parseInt(bin.substring(i, bin.length()), 2);
            } else {
                index = Integer.parseInt(bin.substring(i, i + 6), 2);
            }
            result.append(chars[index]);
        }
        return result.toString();
    }

}
