package com.fszuberski.tweets.session.core.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static com.fszuberski.TestHelper.generateUserProfileMap;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionTest {

    @Test
    void throw_IllegalArgumentException_given_null_map() {
        assertThrows(IllegalArgumentException.class, () -> Session.fromMap(null, "token"));
    }

    @Test
    void throw_IllegalArgumentException_given_empty_map() {
        assertThrows(IllegalArgumentException.class, () -> Session.fromMap(Collections.emptyMap(), "token"));
    }
    
    @Test
    void return_session() {
        Map<String, Object> userProfileAsMap = generateUserProfileMap();
        Session session = Session.fromMap(userProfileAsMap, "token");

        assertNotNull(session);
        assertEquals("token", session.getToken());
        assertEquals("test", session.getUsername());
        assertEquals("https://i.pinimg.com/originals/18/ed/33/18ed330d561a580f77dc39c941455f0e.jpg", session.getProfilePictureUrl());
    }
}