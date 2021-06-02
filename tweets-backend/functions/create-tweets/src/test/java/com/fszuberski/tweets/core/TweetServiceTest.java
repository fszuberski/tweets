package com.fszuberski.tweets.core;

import com.fszuberski.tweets.persistence.port.out.SaveTweetPort;
import com.fszuberski.tweets.port.in.CreateTweet;
import com.fszuberski.tweets.port.in.CreateTweet.CreateTweetException.InvalidText;
import com.fszuberski.tweets.port.in.CreateTweet.CreateTweetException.InvalidUserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TweetServiceTest {

    private TweetService tweetService;
    private SaveTweetPort saveTweetPort;

    @BeforeEach
    void setup() {
        saveTweetPort = mock(SaveTweetPort.class);
        tweetService = new TweetService(saveTweetPort);
    }

    @Test
    void throw_IllegalArgumentException_given_null_saveTweetPort() {
        assertThrows(IllegalArgumentException.class, () -> new TweetService(null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throw_InvalidUserId_exception_given_null_or_empty_userId(String userId) {
        assertThrows(InvalidUserId.class, () -> tweetService.createTweet(userId, "test-text"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throw_InvalidText_exception_given_null_or_empty_text(String text) {
        assertThrows(InvalidText.class, () -> tweetService.createTweet("test-userid", text));
    }

    @Test
    void save_new_tweet() {
        tweetService.createTweet("test-userid", "test-text");
        verify(saveTweetPort, times(1)).saveTweet("test-userid", "test-text");
    }
}