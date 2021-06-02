package com.fszuberski.tweets.core;

import com.fszuberski.tweets.core.domain.GetTweetsQueryType;
import com.fszuberski.tweets.core.domain.Tweet;
import com.fszuberski.tweets.persistence.adapter.TweetsPersistenceAdapter;
import com.fszuberski.tweets.persistence.port.out.GetTweetsPort;
import com.fszuberski.tweets.port.in.GetTweets;
import com.fszuberski.tweets.port.in.GetTweets.GetTweetsException.InvalidUserId;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TweetService implements GetTweets {

    private final GetTweetsPort getTweetsPort;

    public TweetService() {
        this(new TweetsPersistenceAdapter());
    }

    public TweetService(GetTweetsPort getTweetsPort) {
        if (getTweetsPort == null) {
            throw new IllegalArgumentException(
                    String.format("Exception while initializing %s - %s cannot be null",
                            TweetService.class.getSimpleName(), GetTweetsPort.class.getSimpleName()));
        }
        this.getTweetsPort = getTweetsPort;
    }

    public List<Tweet> getTweets(String userId, GetTweetsQueryType queryType) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new InvalidUserId();
        }

        if (queryType == null) {
            queryType = GetTweetsQueryType.CREATED_BY_OTHER_USERS;
        }

        List<Map<String, Object>> tweetsAsMap =
                getTweetsPort.getTweetsAsMap(userId, queryType.isCreatedByUser(), queryType.isCreatedByOtherUsers());

        return createTweetsFromPersistentStoreResult(tweetsAsMap);
    }

    private List<Tweet> createTweetsFromPersistentStoreResult(List<Map<String, Object>> tweetsAsMap) {
        return tweetsAsMap.stream()
                .map(Tweet::fromMap)
                .collect(Collectors.toList());
    }
}
