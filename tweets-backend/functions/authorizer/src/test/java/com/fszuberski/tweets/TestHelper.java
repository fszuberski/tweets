package com.fszuberski.tweets;

import com.fszuberski.tweets.authorizer.core.domain.HttpMethod;
import com.fszuberski.tweets.authorizer.core.domain.TokenAuthorizerContext;
import com.fszuberski.tweets.authorizer.core.domain.iam.Action;
import com.fszuberski.tweets.authorizer.core.domain.iam.Effect;
import com.fszuberski.tweets.authorizer.core.domain.iam.Policy;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestHelper {

    public static final String EXAMPLE_RESOURCE = "arn:aws:execute-api:region:accountid:restapiid/stage/*/*";
    public static final Map<String, List<HttpMethod>> EXAMPLE_ALLOWED_RESOURCES = Map.of(
            "example", List.of(HttpMethod.GET, HttpMethod.POST)
    );
    public static final List<String> EXAMPLE_RESOURCE_LIST = List.of(
            "arn:aws:execute-api:region:accountid:restapiid/stage/GET/resource",
            "arn:aws:execute-api:region:accountid:restapiid/stage/POST/resource",
            "arn:aws:execute-api:region:accountid:restapiid/stage/OPTIONS/resource"
    );

    public static void assertValidDenyAllPolicy(Policy policy) {
        Map<String, Object> documentPolicyMap = policy.getPolicyDocument();
        assertNotNull(documentPolicyMap);

        List<Map<String, Object>> statementMapList = ((List<Map<String, Object>>) documentPolicyMap.get("Statement"));
        assertNotNull(statementMapList);
        assertEquals(1, statementMapList.size());

        Map<String, Object> statementMap = statementMapList.get(0);
        assertEquals(Action.ALL.getAwsName(), statementMap.get("Action"));
        assertEquals(Effect.DENY.getAwsName(), statementMap.get("Effect"));
        assertEquals("*", statementMap.get("Resource"));
    }

    public static void assertValidAllowPolicy(Policy policy, int expectedAllowedResourceCount) {
        Map<String, Object> documentPolicyMap = policy.getPolicyDocument();
        assertNotNull(documentPolicyMap);

        List<Map<String, Object>> statementMapList = ((List<Map<String, Object>>) documentPolicyMap.get("Statement"));
        assertNotNull(statementMapList);
        assertEquals(1, statementMapList.size());

        Map<String, Object> statementMap = statementMapList.get(0);
        assertEquals(Action.EXECUTE_API_INVOKE.getAwsName(), statementMap.get("Action"));
        assertEquals(Effect.ALLOW.getAwsName(), statementMap.get("Effect"));
        assertEquals(expectedAllowedResourceCount, ((List<String>) statementMap.get("Resource")).size());
    }

    public static TokenAuthorizerContext generateTokenAuthorizerContext() {
        return new TokenAuthorizerContext(
                "TOKEN",
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIxNzk5OTIzLCJpc3MiOiJmc3p1YmVyc2tpLmNvbSJ9.YpnolrA6S-NToS_yUqm1JwVgsRbtAIC-EGPLCOagwow",
                EXAMPLE_RESOURCE);
    }
}
