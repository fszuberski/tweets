package com.fszuberski.tweets.core.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TweetTest {

    @Test
    void throw_IllegalArgumentException_given_null_map() {
        assertThrows(IllegalArgumentException.class, () -> Tweet.fromMap(null));
    }

    @Test
    void throw_IllegalArgumentException_given_empty_map() {
        assertThrows(IllegalArgumentException.class, () -> Tweet.fromMap(Collections.emptyMap()));
    }

    @Test
    void return_tweet() {
        Map<String, Object> tweetAsMap = generateTweetAsMap();
        Tweet tweet = Tweet.fromMap(tweetAsMap);

        assertNotNull(tweet);
        assertEquals(tweetAsMap.get("TweetId"), tweet.getId());
        assertEquals(tweetAsMap.get("Text"), tweet.getText());
        assertEquals(((String)tweetAsMap.get("CreatedBy")).replace("USER#", ""), tweet.getCreatedBy());
        assertEquals(tweetAsMap.get("CreatedAt"), tweet.getCreatedAt());
        assertEquals(tweetAsMap.get("ProfilePictureURL"), "https://i.pinimg.com/originals/18/ed/33/18ed330d561a580f77dc39c941455f0e.jpg");
    }

    private Map<String, Object> generateTweetAsMap() {
        return Map.of(
                "TweetId", "ca64426a-e574-4c80-931a-d4a45dca1f6d",
                "Text", "test-tweet-1",
                "CreatedAt", "2021-05-22T14:53:36Z",
                "CreatedBy", "USER#test-user-1",
                "ProfilePictureURL", "https://i.pinimg.com/originals/18/ed/33/18ed330d561a580f77dc39c941455f0e.jpg"
        );
    }
}