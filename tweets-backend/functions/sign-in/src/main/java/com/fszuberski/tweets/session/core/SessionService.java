package com.fszuberski.tweets.session.core;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fszuberski.tweets.jwt.JWTService;
import com.fszuberski.tweets.persistence.adapter.UserProfilePersistenceAdapter;
import com.fszuberski.tweets.persistence.port.out.GetUserProfilePort;
import com.fszuberski.tweets.session.port.in.CreateSession;
import com.fszuberski.tweets.session.port.in.CreateSession.CreateSessionException.InvalidUsernameAndPassword;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

public class SessionService implements CreateSession {

    private final GetUserProfilePort getUserProfilePort;
    private final JWTService jwtService;

    public SessionService() {
        this(new UserProfilePersistenceAdapter(), new JWTService());
    }

    public SessionService(GetUserProfilePort getUserProfilePort, JWTService jwtService) {
        if (getUserProfilePort == null) {
            throw new IllegalArgumentException(
                    String.format("Exception while initializing %s - %s cannot be null",
                            SessionService.class.getSimpleName(), GetUserProfilePort.class.getSimpleName()));
        }

        if (jwtService == null) {
            throw new IllegalArgumentException(
                    String.format("Exception while initializing %s - %s cannot be null",
                            SessionService.class.getSimpleName(), JWTService.class.getSimpleName()));
        }

        this.getUserProfilePort = getUserProfilePort;
        this.jwtService = jwtService;
    }

    public String createSession(String username, String password) {
        validateInput(username, password);

        Optional<Map<String, Object>> userProfileMapOptional = getUserProfilePort.getUserProfileAsMap(username);
        if (userProfileMapOptional.isEmpty()) {
//            logger.log(String.format("No profile present [username='%s']", authenticationModel.getUsername()));
            throw new InvalidUsernameAndPassword();
        }

        Map<String, Object> userProfileMap = userProfileMapOptional.get();
        String passwordHash = (String) userProfileMap.get("PasswordHash");

        if (passwordHash == null || passwordHash.isEmpty()) {
            throw new RuntimeException();
        }

        BCrypt.Result authenticationResult = BCrypt
                .verifyer()
                .verify(password.getBytes(StandardCharsets.UTF_8),
                        passwordHash.getBytes(StandardCharsets.UTF_8));

        if (!authenticationResult.verified) {
//            logger.log(String.format("Invalid username and password combination [username:'%s']", authenticationModel.getUsername()));
            throw new InvalidUsernameAndPassword();
        }

        return jwtService.createAndSignJWT(username);
    }

    private void validateInput(String username, String password) {
        boolean isInvalidUsername = username == null || username.trim().isEmpty();
        boolean isInvalidPassword = password == null || password.trim().isEmpty();

        if (isInvalidUsername || isInvalidPassword) {
            throw new InvalidUsernameAndPassword();
        }
    }
}
