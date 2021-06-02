package com.fszuberski.tweets.authorizer.core;

import com.fszuberski.tweets.jwt.JWTService;
import com.fszuberski.tweets.authorizer.adapter.lambda.TweetsAuthorizerHandler;
import com.fszuberski.tweets.authorizer.core.domain.HttpMethod;
import com.fszuberski.tweets.authorizer.core.domain.iam.Policy;
import com.fszuberski.tweets.authorizer.port.in.GetPolicy;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AuthorizerPolicyService implements GetPolicy {

    private static final Map<String, List<HttpMethod>> ALLOWED_RESOURCES = Map.of(
            "tweets", List.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
    );

    private final JWTService jwtService;

    public AuthorizerPolicyService() {
        this(new JWTService());
    }

    public AuthorizerPolicyService(JWTService jwtService) {
        if (jwtService == null) {
            throw new IllegalArgumentException(
                    String.format("Exception while initializing %s - %s cannot be null",
                            TweetsAuthorizerHandler.class.getSimpleName(), JWTService.class.getSimpleName()));
        }
        this.jwtService = jwtService;
    }

    public Policy getPolicy(String authorizationToken, String methodArn) {
        if (authorizationToken == null || authorizationToken.trim().isEmpty()) {
            return Policy.denyAll(methodArn);
        }

        boolean isValidJWT = jwtService.isValidSignedJWT(authorizationToken);
        if (!isValidJWT) {
            return Policy.denyAll(methodArn);
        }

        return Policy.create(ALLOWED_RESOURCES, methodArn, createContext(authorizationToken));
    }

    private Map<String, String> createContext(String jwt) {
        return Collections.singletonMap("userid", jwtService.getSubject(jwt));
    }
}
