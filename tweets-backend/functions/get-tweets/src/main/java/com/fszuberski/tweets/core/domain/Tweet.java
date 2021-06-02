package com.fszuberski.tweets.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class Tweet {
    private final String id;
    private final String text;
    private final String createdBy;
    private final String createdAt;

    public static Tweet fromMap(Map<String, Object> tweetAsMap) {
        if (tweetAsMap == null || tweetAsMap.isEmpty()) {
            throw new IllegalArgumentException(String.format("Cannot create %s from null or empty map", Tweet.class.getSimpleName()));
        }

        return new Tweet(
                (String) tweetAsMap.get("TweetId"),
                (String) tweetAsMap.get("Text"),
                ((String) tweetAsMap.get("CreatedBy")).replace("USER#", ""),
                (String) tweetAsMap.get("CreatedAt"));
    }
}
