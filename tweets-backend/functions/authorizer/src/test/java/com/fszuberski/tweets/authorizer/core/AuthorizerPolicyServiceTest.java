package com.fszuberski.tweets.authorizer.core;

import com.fszuberski.tweets.jwt.JWTService;
import com.fszuberski.tweets.authorizer.core.domain.TokenAuthorizerContext;
import com.fszuberski.tweets.authorizer.core.domain.iam.Policy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.fszuberski.tweets.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthorizerPolicyServiceTest {
    private AuthorizerPolicyService authorizerPolicyService;
    private JWTService jwtService;

    @BeforeEach
    void setup() {
        jwtService = mock(JWTService.class);
        authorizerPolicyService = new AuthorizerPolicyService(jwtService);
    }

    @Test
    void throw_IllegalArgumentException_given_null_jwtService() {
        assertThrows(IllegalArgumentException.class, () -> new AuthorizerPolicyService(null));
    }

    @Test
    void return_allow_policy_given_valid_authorization_token() {
        when(jwtService.isValidSignedJWT(any())).thenReturn(true);
        when(jwtService.getSubject(any())).thenReturn("test-user-id-value");
        TokenAuthorizerContext tokenAuthorizerContext = generateTokenAuthorizerContext();

        Policy policy = authorizerPolicyService
                .getPolicy(tokenAuthorizerContext.getAuthorizationToken(), tokenAuthorizerContext.getMethodArn());

        assertNotNull(policy);
        assertValidAllowPolicy(policy, 3);

        assertNotNull(policy.getContext());
        String contextValue = policy.getContext().get("userid");
        assertEquals("test-user-id-value", contextValue);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void return_deny_all_policy_given_null_or_empty_authorization_token(String authorizationToken) {
        TokenAuthorizerContext tokenAuthorizerContext = generateTokenAuthorizerContext();
        tokenAuthorizerContext.setAuthorizationToken(authorizationToken);

        Policy policy = authorizerPolicyService
                .getPolicy(tokenAuthorizerContext.getAuthorizationToken(), tokenAuthorizerContext.getMethodArn());

        assertNotNull(policy);
        assertEquals("accountid", policy.getPrincipalId());
        assertValidDenyAllPolicy(policy);
    }

    @Test
    void return_deny_all_policy_given_invalid_authorization_token() {
        when(jwtService.isValidSignedJWT(any())).thenReturn(false);
        TokenAuthorizerContext tokenAuthorizerContext = generateTokenAuthorizerContext();

        Policy policy = authorizerPolicyService
                .getPolicy(tokenAuthorizerContext.getAuthorizationToken(), tokenAuthorizerContext.getMethodArn());

        assertNotNull(policy);
        assertEquals("accountid", policy.getPrincipalId());
        assertValidDenyAllPolicy(policy);
    }
}