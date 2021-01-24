package com.wy.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * @author matthew_wu
 * @since 2020/12/2 4:13 下午
 */
public class UserScoreData {

    private static List<UserScore> userScores = new ArrayList<>();

    static {
       userScores.add(new UserScore("id3", 0.1, 2, 11));
       userScores.add(new UserScore("id1", 0.2, 1, 1));
       userScores.add(new UserScore("id2", 0.1, 2, 1));
       userScores.add(new UserScore("id1", 0.3, 2, 11));
   }

    public static List<UserScore> getData() {
        return userScores;
    }

}
