package com.fszuberski.tweets.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.fszuberski.tweets.adapter.lambda.model.TweetModel;
import com.fszuberski.tweets.port.in.CreateTweet;
import com.fszuberski.tweets.port.in.CreateTweet.CreateTweetException.InvalidText;
import com.fszuberski.tweets.port.in.CreateTweet.CreateTweetException.InvalidUserId;
import com.fszuberski.tweets.rest.domain.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreateTweetHandlerTest {

    private CreateTweetHandler createTweetHandler;
    private Context context;
    private CreateTweet createTweet;

    @BeforeEach
    void setup() {
        context = mock(Context.class);
        createTweet = mock(CreateTweet.class);
        createTweetHandler = new CreateTweetHandler(createTweet);
    }

    @Test
    void throw_IllegalArgumentException_given_null_createTweet() {
        assertThrows(IllegalArgumentException.class, () -> new CreateTweetHandler(null));
    }

    @Test
    void throw_BadRequest_exception_given_null_tweetModel() {
        assertThrows(ApiException.BadRequest.class, () -> createTweetHandler.handleRequest(null, context));
    }

    @Test
    void throw_Unauthorized_exception_given_InvalidUserId_exception_occurs() {
        TweetModel tweetModel = generateTweetModel();
        doThrow(new InvalidUserId()).when(createTweet).createTweet(tweetModel.getUserId(), tweetModel.getText());
        assertThrows(ApiException.Unauthorized.class, () -> createTweetHandler.handleRequest(tweetModel, context));
    }

    @Test
    void throw_BadRequest_exception_given_InvalidText_exception_occurs() {
        TweetModel tweetModel = generateTweetModel();
        doThrow(new InvalidText()).when(createTweet).createTweet(tweetModel.getUserId(), tweetModel.getText());
        assertThrows(ApiException.BadRequest.class, () -> createTweetHandler.handleRequest(tweetModel, context));
    }

    @Test
    void throw_InternalServerError_exception_given_generic_exception_occurs() {
        TweetModel tweetModel = generateTweetModel();
        doThrow(new RuntimeException()).when(createTweet).createTweet(tweetModel.getUserId(), tweetModel.getText());
        assertThrows(ApiException.InternalServerError.class, () -> createTweetHandler.handleRequest(tweetModel, context));
    }

    private TweetModel generateTweetModel() {
        return new TweetModel("test-user-id", "test-message");
    }
}