package com.fszuberski.tweets.port.in;

public interface CreateTweet {
    void createTweet(String userId, String text);

    class CreateTweetException extends RuntimeException {

        public CreateTweetException(String message) {
            super(message);
        }

        public static class InvalidUserId extends CreateTweetException {
            public InvalidUserId() {
                super("Invalid userId");
            }
        }

        public static class InvalidText extends CreateTweetException {
            public InvalidText() {
                super("Invalid text");
            }
        }
    }
}
