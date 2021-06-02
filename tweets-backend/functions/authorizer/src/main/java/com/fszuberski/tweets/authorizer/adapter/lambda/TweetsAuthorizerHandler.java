package com.fszuberski.tweets.authorizer.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fszuberski.tweets.authorizer.core.AuthorizerPolicyService;
import com.fszuberski.tweets.authorizer.core.domain.TokenAuthorizerContext;
import com.fszuberski.tweets.authorizer.core.domain.iam.Policy;
import com.fszuberski.tweets.authorizer.port.in.GetPolicy;
import com.fszuberski.tweets.rest.domain.ApiException;

/**
 * eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIxNzk5OTIzLCJpc3MiOiJmc3p1YmVyc2tpLmNvbSJ9.YpnolrA6S-NToS_yUqm1JwVgsRbtAIC-EGPLCOagwow
 * <p>
 * Based on:
 * https://github.com/awslabs/aws-apigateway-lambda-authorizer-blueprints
 * https://www.alexdebrie.com/posts/lambda-custom-authorizers/
 */
// Handler value: com.fszuberski.tweets.authorizer.adapter.lambda.TweetsAuthorizerHandler
public class TweetsAuthorizerHandler implements RequestHandler<TokenAuthorizerContext, Policy> {

    private final GetPolicy getPolicy;

    public TweetsAuthorizerHandler() {
        this(new AuthorizerPolicyService());
    }

    public TweetsAuthorizerHandler(GetPolicy getPolicy) {
        if (getPolicy == null) {
            throw new IllegalArgumentException(
                    String.format("Exception while initializing %s - %s cannot be null",
                            TweetsAuthorizerHandler.class.getSimpleName(), GetPolicy.class.getSimpleName()));
        }
        this.getPolicy = getPolicy;
    }

    public Policy handleRequest(TokenAuthorizerContext tokenAuthorizerContext, Context context) {
        if (tokenAuthorizerContext == null) {
            throw new ApiException.InternalServerError();
        }

        String authorizationToken = tokenAuthorizerContext.getAuthorizationToken();
        String methodArn = tokenAuthorizerContext.getMethodArn();

        return getPolicy.getPolicy(authorizationToken, methodArn);
    }
}
