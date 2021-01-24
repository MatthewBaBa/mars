package com.wy.java8;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author matthew_wu
 * @since 2020-06-08 16:17
 */
public class CustomCollectDemo {

    public static void main(String[] args) {


        Map<String, ImmutablePair<Double, String>> collect = UserScoreData.getData().stream().collect(Collectors.groupingBy(UserScore::getId,
                Collectors.collectingAndThen(Collectors.toList(), list -> {
                    double total = list.stream().collect(Collectors.summingDouble(UserScore::getScore));
                    String s = getTotal(list);
                    return ImmutablePair.of(total, s);
                })
        ));

        collect.entrySet().stream().forEach(v -> System.out.println(v.getValue()));

        collect.entrySet().stream().sorted((k1, k2) -> {
            int leftComp = k2.getValue().getLeft().compareTo(k1.getValue().getLeft());
            if (leftComp != 0) {
                return leftComp;
            }
            return k1.getValue().getRight().compareTo(k2.getValue().getRight());
        }).forEachOrdered(v -> System.out.println(v));

    }

    public static String getTotal(List<UserScore> list) {
        StringBuilder builder = new StringBuilder();
        Map<Integer, String> map = list.stream().collect(Collectors.toMap(UserScore::getInnerGroup,
                e -> String.format("%03d", e.getInnerScore())));
        for (int i = 1; i < 5 ; i++) {
            builder.append(map.getOrDefault(i, "000"));
        }
        return builder.toString();
    }

}
