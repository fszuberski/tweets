package com.fszuberski.tweets.session.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class Session {
    private final String username;
    private final String profilePictureUrl;
    private final String token;

    public static Session fromMap(Map<String, Object> profileAsMap, String generatedToken) {
        if (profileAsMap == null || profileAsMap.isEmpty()) {
            throw new IllegalArgumentException(String.format("Cannot create %s from null or empty map", Session.class.getSimpleName()));
        }

        return new Session(
                (String) profileAsMap.get("Username"),
                (String) profileAsMap.get("ProfilePictureURL"),
                generatedToken
        );
    }
}
