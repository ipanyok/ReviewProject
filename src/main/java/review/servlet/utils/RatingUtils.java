package review.servlet.utils;

import review.servlet.beans.TitlesBean;

import java.util.*;

public class RatingUtils {

    public static List<Map<TitlesBean, Double>> createRating(Map<TitlesBean, Double> ratingMap, int countRatings, int ratingMiddleMark) {
        List<Map<TitlesBean, Double>> resultList = new ArrayList<>();
        List<Double> badRatings = new ArrayList<>();
        List<Double> goodRatings = new ArrayList<>();
        List<Double> values = new ArrayList<>(ratingMap.values());
        if (!ratingMap.isEmpty()) {
            Collections.sort(values);
            for (Double value : values) {
                if (badRatings.size() == countRatings) {
                    break;
                }
                if (value < ratingMiddleMark) {
                    badRatings.add(value);
                } else {
                    break;
                }
            }

            Collections.reverse(values);
            for (Double value : values) {
                if (goodRatings.size() == countRatings) {
                    break;
                }
                if (value > ratingMiddleMark) {
                    goodRatings.add(value);
                } else {
                    break;
                }
            }
        }

        Map<TitlesBean, Double> buffer = new HashMap<>();
        for (Double e : badRatings) {
            for (Map.Entry<TitlesBean, Double> entry : ratingMap.entrySet()) {
                if (entry.getValue().equals(e)) {
                    buffer.put(entry.getKey(), e);
                }
            }
        }
        resultList.add(buffer);

        buffer = new HashMap<>();
        for (Double e : goodRatings) {
            for (Map.Entry<TitlesBean, Double> entry : ratingMap.entrySet()) {
                if (entry.getValue().equals(e)) {
                    buffer.put(entry.getKey(), e);
                }
            }
        }
        resultList.add(buffer);
        return resultList;
    }
}
