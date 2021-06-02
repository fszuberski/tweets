package com.fszuberski.tweets.authorizer.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.fszuberski.tweets.authorizer.core.domain.TokenAuthorizerContext;
import com.fszuberski.tweets.authorizer.core.domain.iam.Policy;
import com.fszuberski.tweets.authorizer.port.in.GetPolicy;
import com.fszuberski.tweets.rest.domain.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.fszuberski.tweets.TestHelper.generateTokenAuthorizerContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TweetsAuthorizerHandlerTest {

    private TweetsAuthorizerHandler tweetsAuthorizerHandler;
    private GetPolicy getPolicy;

    @BeforeEach
    void setup() {
        getPolicy = mock(GetPolicy.class);
        tweetsAuthorizerHandler = new TweetsAuthorizerHandler(getPolicy);
    }

    @Test
    void throw_IllegalArgument_given_null_getPolicy() {
        assertThrows(IllegalArgumentException.class, () -> new TweetsAuthorizerHandler(null));
    }

    @Test
    void return_policy() {
        TokenAuthorizerContext tokenAuthorizerContext = generateTokenAuthorizerContext();
        when(getPolicy.getPolicy(tokenAuthorizerContext.getAuthorizationToken(), tokenAuthorizerContext.getMethodArn()))
                .thenReturn(mock(Policy.class));

        Policy policy = tweetsAuthorizerHandler.handleRequest(tokenAuthorizerContext, mock(Context.class));

        assertNotNull(policy);
    }

    @Test
    void throw_InternalServerError_given_null_tokenAuthorizerContext() {
        assertThrows(ApiException.InternalServerError.class,
                () -> tweetsAuthorizerHandler.handleRequest(null, mock(Context.class)));
    }
}