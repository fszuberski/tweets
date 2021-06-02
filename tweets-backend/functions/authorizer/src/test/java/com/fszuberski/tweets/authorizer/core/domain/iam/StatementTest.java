package com.fszuberski.tweets.authorizer.core.domain.iam;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.fszuberski.tweets.TestHelper.EXAMPLE_RESOURCE;
import static com.fszuberski.tweets.TestHelper.EXAMPLE_RESOURCE_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SuppressWarnings("unchecked")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StatementTest {

    @Test
    void instantiate_given_valid_constructor_parameters() {
        Statement statement = new Statement(Action.EXECUTE_API_INVOKE, Effect.ALLOW, EXAMPLE_RESOURCE_LIST);

        assertNotNull(statement);
        assertEquals(Action.EXECUTE_API_INVOKE.getAwsName(), statement.getAction());
        assertEquals(Effect.ALLOW.getAwsName(), statement.getEffect());
        assertTrue(statement.getResourceList().containsAll(EXAMPLE_RESOURCE_LIST));
    }

    @Test
    void instantiate_with_EXECUTE_API_INVOKE_action_given_null_action() {
        Statement statement = new Statement(null, Effect.ALLOW);

        assertNotNull(statement);
        assertEquals(Action.EXECUTE_API_INVOKE.getAwsName(), statement.getAction());
    }

    @Test
    void instantiate_with_deny_effect_field_given_null_effect() {
        Statement statement = new Statement(Action.ALL, null);

        assertNotNull(statement);
        assertEquals(Effect.DENY.getAwsName(), statement.getEffect());
    }

    @Test
    void instantiate_with_wildcard_resourceList_field_given_null_resourceList() {
        Statement statement = new Statement(Action.ALL, Effect.ALLOW, null);

        assertNotNull(statement);
        assertNotNull(statement.getResourceList());
        assertEquals(1, statement.getResourceList().size());
        assertEquals("*", statement.getResourceList().get(0));
    }

    @ParameterizedTest
    @MethodSource("return_valid_map__parameters")
    void return_valid_map(Action action, Effect effect, List<String> resourceList) {
        Statement statement = new Statement(action, effect, resourceList);
        Map<String, Object> statementMap = statement.asMap();

        assertNotNull(statementMap);

        // checking 'action' value
        if (action != null) {
            assertEquals(action.getAwsName(), statementMap.get("Action"));
        } else {
            assertEquals(Action.EXECUTE_API_INVOKE.getAwsName(), statementMap.get("Action"));
        }

        // checking 'effect' value
        if (effect != null) {
            assertEquals(effect.getAwsName(), statementMap.get("Effect"));
        } else {
            assertEquals(Effect.DENY.getAwsName(), statementMap.get("Effect"));
        }

        // checking 'resource' value
        if (resourceList != null && resourceList.size() == 1) {
            assertEquals(resourceList.get(0), statementMap.get("Resource"));
        } else if (resourceList != null && resourceList.size() > 1) {
            assertTrue(((List<String>) statementMap.get("Resource")).containsAll(resourceList));
        } else {
            assertEquals("*", statementMap.get("Resource"));
        }
    }

    private static Stream<Arguments> return_valid_map__parameters() {
        List<Arguments> arguments = new ArrayList<>();
        for (Action action : Action.values()) {
            for (Effect effect : Effect.values()) {
                arguments.add(Arguments.of(action, effect, List.of(EXAMPLE_RESOURCE)));
                arguments.add(Arguments.of(action, effect, EXAMPLE_RESOURCE_LIST));
            }
        }

        arguments.add(Arguments.of(null, Effect.ALLOW, List.of(EXAMPLE_RESOURCE)));
        arguments.add(Arguments.of(Action.EXECUTE_API_INVOKE, null, List.of(EXAMPLE_RESOURCE)));
        arguments.add(Arguments.of(Action.EXECUTE_API_INVOKE, Effect.ALLOW, null));
        arguments.add(Arguments.of(Action.EXECUTE_API_INVOKE, null, null));

        return arguments.stream();
    }
}