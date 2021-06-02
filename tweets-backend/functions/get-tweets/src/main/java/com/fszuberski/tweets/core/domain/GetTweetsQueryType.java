package com.fszuberski.tweets.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum GetTweetsQueryType {
    CREATED_BY_USER(true, false),
    CREATED_BY_OTHER_USERS(false, true),
    ALL(true, true);

    private final boolean createdByUser;
    private final boolean createdByOtherUsers;

    public static GetTweetsQueryType fromString(String queryTypeStr) {
        return Arrays
                .stream(values())
                .filter(value -> value.name().equalsIgnoreCase(queryTypeStr))
                .findFirst()
                .orElse(CREATED_BY_OTHER_USERS);
    }
}