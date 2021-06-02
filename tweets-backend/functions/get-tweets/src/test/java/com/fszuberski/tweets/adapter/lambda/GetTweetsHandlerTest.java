package com.fszuberski.tweets.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.fszuberski.tweets.adapter.lambda.model.QueryData;
import com.fszuberski.tweets.core.domain.GetTweetsQueryType;
import com.fszuberski.tweets.port.in.GetTweets;
import com.fszuberski.tweets.port.in.GetTweets.GetTweetsException.InvalidUserId;
import com.fszuberski.tweets.rest.domain.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GetTweetsHandlerTest {

    private GetTweetsHandler getTweetsHandler;
    private GetTweets getTweets;
    private Context context;

    @BeforeEach
    void setup() {
        getTweets = mock(GetTweets.class);
        context = mock(Context.class);
        getTweetsHandler = new GetTweetsHandler(getTweets);
    }

    @Test
    void throw_IllegalArgumentException_given_null_getTweets() {
        assertThrows(IllegalArgumentException.class, () -> new GetTweetsHandler(null));
    }

    @Test
    void throw_BadRequest_exception_given_null_queryData() {
        assertThrows(ApiException.BadRequest.class, () -> getTweetsHandler.handleRequest(null, context));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throw_Unauthorized_exception_given_null_or_empty_userId(String userId) {
        QueryData queryData = generateQueryData();
        queryData.setUserId(userId);

        assertThrows(ApiException.Unauthorized.class, () -> getTweetsHandler.handleRequest(queryData, context));
    }

    @Test
    void throw_Unauthorized_exception_given_invalid_userId() {
        QueryData queryData = generateQueryData();
        when(getTweets.getTweets(queryData.getUserId(), GetTweetsQueryType.fromString(queryData.getType())))
                .thenThrow(new InvalidUserId());

        assertThrows(ApiException.Unauthorized.class, () -> getTweetsHandler.handleRequest(queryData, context));
    }

    @Test
    void throw_InternalServerError_exception_given_generic_exception_occurs() {
        QueryData queryData = generateQueryData();
        when(getTweets.getTweets(queryData.getUserId(), GetTweetsQueryType.fromString(queryData.getType())))
                .thenThrow(new RuntimeException());

        assertThrows(ApiException.InternalServerError.class, () -> getTweetsHandler.handleRequest(queryData, context));
    }

    private QueryData generateQueryData() {
        return new QueryData("test", "created_by_user");
    }

}