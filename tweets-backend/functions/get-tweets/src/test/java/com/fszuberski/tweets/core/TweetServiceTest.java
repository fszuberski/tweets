package com.fszuberski.tweets.core;

import com.fszuberski.tweets.core.domain.GetTweetsQueryType;
import com.fszuberski.tweets.core.domain.Tweet;
import com.fszuberski.tweets.persistence.port.out.GetTweetsPort;
import com.fszuberski.tweets.port.in.GetTweets.GetTweetsException.InvalidUserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TweetServiceTest {

    private TweetService tweetService;
    private GetTweetsPort getTweetsPort;

    @BeforeEach
    void setup() {
        getTweetsPort = mock(GetTweetsPort.class);
        tweetService = new TweetService(getTweetsPort);
    }

    @Test
    void return_list_of_tweets() {
        List<Map<String, Object>> tweetsAsMap = generateTweetsAsMap();
        when(getTweetsPort.getTweetsAsMap(anyString(), anyBoolean(), anyBoolean())).thenReturn(tweetsAsMap);

        List<Tweet> tweetList = tweetService.getTweets("test-userId", GetTweetsQueryType.CREATED_BY_OTHER_USERS);

        assertNotNull(tweetList);
        assertEquals(tweetsAsMap.size(), tweetList.size());
    }

    @Test
    void return_list_of_tweets_given_null_queryType() {
        List<Map<String, Object>> tweetsAsMap = generateTweetsAsMap();
        when(getTweetsPort.getTweetsAsMap(anyString(), anyBoolean(), anyBoolean())).thenReturn(tweetsAsMap);

        List<Tweet> tweetList = tweetService.getTweets("test-userId", null);

        assertNotNull(tweetList);
        assertEquals(tweetsAsMap.size(), tweetList.size());
    }

    @Test
    void throw_IllegalArgumentException_given_null_getTweetsPort() {
        assertThrows(IllegalArgumentException.class, () -> new TweetService(null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throw_InvalidUserId_given_null_or_empty_userId(String userId) {
        assertThrows(InvalidUserId.class, () -> tweetService.getTweets(userId));
        assertThrows(InvalidUserId.class, () -> tweetService.getTweets(userId, GetTweetsQueryType.CREATED_BY_OTHER_USERS));
    }

    private List<Map<String, Object>> generateTweetsAsMap() {
        Map<String, Object> tweet1 = Map.of(
                "TweetId", "ca64426a-e574-4c80-931a-d4a45dca1f6d",
                "Text", "test-tweet-1",
                "CreatedAt", "2021-05-22T14:53:36Z",
                "CreatedBy", "USER#test-user-1"
        );

        Map<String, Object> tweet2 = Map.of(
                "TweetId", "a08b8477-b55d-44dd-a1b8-db3c60a7170a",
                "Text", "test-tweet-2",
                "CreatedAt", "2021-05-22T14:46:25Z",
                "CreatedBy", "USER#test-user-2"
        );

        return List.of(tweet1, tweet2);
    }

}