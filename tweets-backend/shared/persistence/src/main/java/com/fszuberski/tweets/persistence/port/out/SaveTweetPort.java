package com.fszuberski.tweets.persistence.port.out;

public interface SaveTweetPort {
    void saveTweet(String createdByUserId, String text);
}
