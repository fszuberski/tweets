package com.fszuberski.tweets.session.port.in;

public interface CreateSession {

    String createSession(String username, String password);

    class CreateSessionException extends RuntimeException {

        public CreateSessionException(String message) {
            super(message);
        }

        public static class InvalidUsernameAndPassword extends CreateSessionException {
            public InvalidUsernameAndPassword() {
                super("Invalid username and/or password");
            }
        }
    }
}
