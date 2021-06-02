package com.fszuberski.tweets.session.core;

import com.fszuberski.tweets.jwt.JWTService;
import com.fszuberski.tweets.persistence.port.out.GetUserProfilePort;
import com.fszuberski.tweets.session.port.in.CreateSession.CreateSessionException.InvalidUsernameAndPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionServiceTest {

    private SessionService sessionService;
    private GetUserProfilePort getUserProfilePort;
    private JWTService jwtService;

    @BeforeEach
    void setup() {
        getUserProfilePort = mock(GetUserProfilePort.class);
        jwtService = mock(JWTService.class);

        sessionService = new SessionService(getUserProfilePort, jwtService);
    }

    @Test
    void return_jwt() {
        when(getUserProfilePort.getUserProfileAsMap(any())).thenReturn(Optional.of(generateUserProfileMap()));
        when(jwtService.createAndSignJWT(any()))
                .thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIxNzk0NjU1LCJpc3MiOiJmc3p1YmVyc2tpLmNvbSJ9.X8V6QfkyZs4ArqDX1_DRWmFNDHeBrqrD16rArGGwpVs");
        String jwt = sessionService.createSession("username", "password");

        assertNotNull(jwt);
        assertTrue(jwt.length() > 0);
    }

    @Test
    void throw_IllegalArgumentException_given_null_getUserProfilePort() {
        assertThrows(IllegalArgumentException.class, () -> new SessionService(null, jwtService));
    }

    @Test
    void throw_IllegalArgumentException_given_null_jwtService() {
        assertThrows(IllegalArgumentException.class, () -> new SessionService(getUserProfilePort, null));
    }

    @ParameterizedTest
    @MethodSource("throw_InvalidUsernameAndPassword_given_invalid_input__parameters")
    void throw_InvalidUsernameAndPassword_given_invalid_input(String username, String password) {
        assertThrows(InvalidUsernameAndPassword.class, () -> sessionService.createSession(username, password));
    }

    private static Stream<Arguments> throw_InvalidUsernameAndPassword_given_invalid_input__parameters() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", null),
                Arguments.of(null, ""),
                Arguments.of("", "")
        );
    }

    @Test
    void throw_InvalidUsernameAndPassword_given_no_profile_exists_with_username() {
        when(getUserProfilePort.getUserProfileAsMap(any())).thenReturn(Optional.empty());
        assertThrows(InvalidUsernameAndPassword.class, () -> sessionService.createSession("username", "password"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throw_RuntimeException_given_profile_passwordHash_is_null_or_empty(String passwordHash) {
        Map<String, Object> userProfileMap = generateUserProfileMap();
        userProfileMap.put("PasswordHash", passwordHash);
        when(getUserProfilePort.getUserProfileAsMap(any())).thenReturn(Optional.of(userProfileMap));

        assertThrows(RuntimeException.class, () -> sessionService.createSession("username", "password"));
    }

    @Test
    void throw_InvalidUsernameAndPassword_given_invalid_password() {
        when(getUserProfilePort.getUserProfileAsMap(any())).thenReturn(Optional.of(generateUserProfileMap()));
        assertThrows(InvalidUsernameAndPassword.class, () -> sessionService.createSession("username", "invalid-password"));
    }

    private Map<String, Object> generateUserProfileMap() {
        Map<String, Object> userProfileMap = new HashMap<>();
        userProfileMap.put("PK", "PROFILE#test");
        userProfileMap.put("SK", "PROFILE#test");
        userProfileMap.put("Username", "test");
        userProfileMap.put("PasswordHash", "$2y$12$kdM0oFlaWGAlQP2uExb3.OGVwyoCGjyCWDsVQG.1mpHlOguQ4J9rq");
        userProfileMap.put("CreatedAt", "2021-05-21T23:27:05Z");
        userProfileMap.put("ProfilePictureURL", "https://i.pinimg.com/originals/18/ed/33/18ed330d561a580f77dc39c941455f0e.jpg");

        return userProfileMap;
    }
}