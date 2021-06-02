package com.fszuberski.tweets.port.in;

import com.fszuberski.tweets.core.domain.GetTweetsQueryType;
import com.fszuberski.tweets.core.domain.Tweet;

import java.util.List;

public interface GetTweets {

    default List<Tweet> getTweets(String userId) {
        return getTweets(userId, GetTweetsQueryType.CREATED_BY_OTHER_USERS);
    }

    List<Tweet> getTweets(String userId, GetTweetsQueryType queryType);

    class GetTweetsException extends RuntimeException {
        public GetTweetsException(String message) {
            super(message);
        }

        public static class InvalidUserId extends GetTweetsException {
            public InvalidUserId() {
                super("Invalid userId");
            }
        }
    }
}
