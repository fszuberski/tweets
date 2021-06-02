package com.fszuberski.tweets.authorizer.port.in;

import com.fszuberski.tweets.authorizer.core.domain.iam.Policy;

public interface GetPolicy {
    Policy getPolicy(String tokenAuthorizerContext, String methodArn);
}
