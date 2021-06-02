package com.fszuberski.tweets.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fszuberski.tweets.adapter.lambda.model.TweetModel;
import com.fszuberski.tweets.core.TweetService;
import com.fszuberski.tweets.port.in.CreateTweet;
import com.fszuberski.tweets.port.in.CreateTweet.CreateTweetException;
import com.fszuberski.tweets.rest.domain.ApiException;
import com.fszuberski.tweets.rest.domain.Response;

// Handler value: com.fszuberski.tweets.adapter.lambda.CreateTweetHandler
public class CreateTweetHandler implements RequestHandler<TweetModel, Response> {

    private final CreateTweet createTweet;

    public CreateTweetHandler() {
        this(new TweetService());
    }

    public CreateTweetHandler(CreateTweet createTweet) {
        if (createTweet == null) {
            throw new IllegalArgumentException(
                    String.format("Exception while initializing %s - %s cannot be null",
                            CreateTweetHandler.class.getSimpleName(), CreateTweet.class.getSimpleName()));
        }
        this.createTweet = createTweet;
    }

    public Response handleRequest(TweetModel tweetModel, Context context) {
        if (tweetModel == null) {
            throw new ApiException.BadRequest("Invalid request body");
        }

        try {
            createTweet.createTweet(tweetModel.getUserId(), tweetModel.getText());
            return Response.created("Tweet created");
        } catch (CreateTweetException.InvalidUserId e) {
            throw new ApiException.Unauthorized();
        } catch (CreateTweetException.InvalidText e) {
            throw new ApiException.BadRequest(e.getMessage());
        } catch (Exception e) {
            throw new ApiException.InternalServerError();
        }
    }
}
