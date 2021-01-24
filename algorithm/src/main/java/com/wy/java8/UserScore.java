package com.wy.java8;

/**
 * @author matthew_wu
 * @since 2020-06-08 16:18
 */
public class UserScore {

    private String id;

    private Double score;

    private int innerGroup;

    private int innerScore;

    public UserScore(String id, Double score, int innerGroup, int innerScore) {
        this.id = id;
        this.score = score;
        this.innerGroup = innerGroup;
        this.innerScore = innerScore;
    }

    public String getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    public int getInnerGroup() {
        return innerGroup;
    }

    public int getInnerScore() {
        return innerScore;
    }
}
