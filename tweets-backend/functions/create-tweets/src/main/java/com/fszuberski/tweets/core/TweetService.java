package com.fszuberski.tweets.core;

import com.fszuberski.tweets.persistence.adapter.TweetsPersistenceAdapter;
import com.fszuberski.tweets.persistence.port.out.SaveTweetPort;
import com.fszuberski.tweets.port.in.CreateTweet;
import com.fszuberski.tweets.port.in.CreateTweet.CreateTweetException.InvalidText;
import com.fszuberski.tweets.port.in.CreateTweet.CreateTweetException.InvalidUserId;

public class TweetService implements CreateTweet {

    private final SaveTweetPort saveTweetPort;

    public TweetService() {
        this(new TweetsPersistenceAdapter());
    }

    public TweetService(SaveTweetPort saveTweetPort) {
        if (saveTweetPort == null) {
            throw new IllegalArgumentException(
                    String.format("Exception while initializing %s - %s cannot be null",
                            TweetService.class.getSimpleName(), SaveTweetPort.class.getSimpleName()));
        }
        this.saveTweetPort = saveTweetPort;
    }

    public void createTweet(String userId, String text) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new InvalidUserId();
        }

        if (text == null || text.trim().isEmpty()) {
            throw new InvalidText();
        }

        saveTweetPort.saveTweet(userId, text);
    }
}
