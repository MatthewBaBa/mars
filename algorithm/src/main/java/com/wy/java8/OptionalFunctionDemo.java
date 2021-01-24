package com.wy.java8;

import java.util.Optional;

/**
 * @author matthew_wu
 * @since 2020/12/2 3:46 下午
 */
public class OptionalFunctionDemo {

    public static void main(String[] args) {
        Optional<UserScore> userScoreOpt;
        UserScore userScore;

        //IfPresent
        userScoreOpt = Optional.of(UserScoreData.getData().get(0));
        userScoreOpt.ifPresent(u -> System.out.println("User ID is: " + u.getId()));

        //orElse
        userScore = Optional.ofNullable((UserScore) null)
                .orElse(new UserScore("newId", 0.1, 1, 1));
        System.out.println("User ID is: " + userScore.getId());

        //orElseGet
        userScore = Optional.ofNullable((UserScore) null)
                .orElseGet(() -> new UserScore("newId", 0.1, 1, 1));
        System.out.println("User ID is: " + userScore.getId());

        //orElseGet
        userScore = Optional.ofNullable((UserScore) null)
                .orElseThrow(() -> new RuntimeException("user score not found"));

        //MAP FLATMAP FILTER


    }

}
