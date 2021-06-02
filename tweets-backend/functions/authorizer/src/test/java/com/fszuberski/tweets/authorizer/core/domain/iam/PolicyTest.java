package com.fszuberski.tweets.authorizer.core.domain.iam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Collections;
import java.util.Map;

import static com.fszuberski.tweets.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

class PolicyTest {

    @Test
    void instantiate_allow_policy() {
        Policy policy = Policy.create(EXAMPLE_ALLOWED_RESOURCES, EXAMPLE_RESOURCE, Map.of("testKey", "testValue"));

        assertNotNull(policy);
        assertEquals("accountid", policy.getPrincipalId());

        assertValidAllowPolicy(policy, EXAMPLE_ALLOWED_RESOURCES.get("example").size());

        Map<String, String> context = policy.getContext();
        assertNotNull(context);

        String contextValue = context.get("testKey");
        assertEquals("testValue", contextValue);
    }

    @Test
    void instantiate_deny_all_policy() {
        Policy policy = Policy.denyAll(EXAMPLE_RESOURCE);

        assertNotNull(policy);
        assertEquals("accountid", policy.getPrincipalId());
        assertValidDenyAllPolicy(policy);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void create_should_throw_IllegalArgumentException_given_null_or_empty_method_arn(String methodArn) {
        assertThrows(IllegalArgumentException.class, () -> Policy.create(EXAMPLE_ALLOWED_RESOURCES, methodArn, Collections.emptyMap()));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void denyAll_should_throw_IllegalArgumentException_given_null_or_empty_method_arn(String methodArn) {
        assertThrows(IllegalArgumentException.class, () -> Policy.denyAll(methodArn));
    }
}