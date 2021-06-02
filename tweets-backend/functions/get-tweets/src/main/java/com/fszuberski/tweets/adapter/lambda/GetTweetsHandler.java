package com.fszuberski.tweets.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fszuberski.tweets.adapter.lambda.model.QueryData;
import com.fszuberski.tweets.core.domain.GetTweetsQueryType;
import com.fszuberski.tweets.core.domain.Tweet;
import com.fszuberski.tweets.core.TweetService;
import com.fszuberski.tweets.port.in.GetTweets;
import com.fszuberski.tweets.port.in.GetTweets.GetTweetsException.InvalidUserId;
import com.fszuberski.tweets.rest.domain.ApiException;

import java.util.List;

// Handler value: com.fszuberski.tweets.adapter.lambda.GetTweetsHandler
public class GetTweetsHandler implements RequestHandler<QueryData, List<Tweet>> {

    private final GetTweets getTweets;

    public GetTweetsHandler() {
        this(new TweetService());
    }

    public GetTweetsHandler(GetTweets getTweets) {
        if (getTweets == null) {
            throw new IllegalArgumentException(
                    String.format("Exception while initializing %s - %s cannot be null",
                            GetTweetsHandler.class.getSimpleName(), GetTweets.class.getSimpleName()));
        }
        this.getTweets = getTweets;
    }

    @Override
    public List<Tweet> handleRequest(QueryData queryData, Context context) {
        if (queryData == null) {
            throw new ApiException.BadRequest("Invalid request body");
        }

        String userId = queryData.getUserId();
        if (userId == null || userId.trim().isEmpty()) {
            throw new ApiException.Unauthorized();
        }

        GetTweetsQueryType queryType = GetTweetsQueryType.fromString(queryData.getType());

        try {
            return getTweets.getTweets(userId, queryType);
        } catch (InvalidUserId e) {
            throw new ApiException.Unauthorized();
        } catch (Exception e) {
            throw new ApiException.InternalServerError();
        }
    }
}