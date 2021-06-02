package com.fszuberski.tweets.core.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GetTweetsQueryTypeTest {

    @ParameterizedTest
    @MethodSource("fromString_should_return_query_type__parameters")
    void fromString_should_return_query_type(String queryTypeStr, GetTweetsQueryType expectedQueryType) {
        GetTweetsQueryType queryType = GetTweetsQueryType.fromString(queryTypeStr);

        assertNotNull(queryType);
        assertEquals(expectedQueryType, queryType);
    }

    private static Stream<Arguments> fromString_should_return_query_type__parameters() {
        return Stream.of(
                Arguments.of(GetTweetsQueryType.CREATED_BY_USER.name(), GetTweetsQueryType.CREATED_BY_USER),
                Arguments.of(GetTweetsQueryType.CREATED_BY_USER.name().toLowerCase(), GetTweetsQueryType.CREATED_BY_USER),
                Arguments.of(GetTweetsQueryType.CREATED_BY_USER.name().toUpperCase(), GetTweetsQueryType.CREATED_BY_USER),
                Arguments.of(GetTweetsQueryType.CREATED_BY_OTHER_USERS.name(), GetTweetsQueryType.CREATED_BY_OTHER_USERS),
                Arguments.of(GetTweetsQueryType.CREATED_BY_OTHER_USERS.name().toLowerCase(), GetTweetsQueryType.CREATED_BY_OTHER_USERS),
                Arguments.of(GetTweetsQueryType.CREATED_BY_OTHER_USERS.name().toUpperCase(), GetTweetsQueryType.CREATED_BY_OTHER_USERS),
                Arguments.of(GetTweetsQueryType.ALL.name(), GetTweetsQueryType.ALL),
                Arguments.of(GetTweetsQueryType.ALL.name().toLowerCase(), GetTweetsQueryType.ALL),
                Arguments.of(GetTweetsQueryType.ALL.name().toUpperCase(), GetTweetsQueryType.ALL)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalid"})
    void fromString_should_return_default_query_type_given_invalid_queryTypeStr(String queryTypeStr) {
        GetTweetsQueryType queryType = GetTweetsQueryType.fromString(queryTypeStr);

        assertNotNull(queryType);
        assertEquals(GetTweetsQueryType.CREATED_BY_OTHER_USERS, queryType);
    }
}