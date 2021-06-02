package com.fszuberski.tweets.persistence.port.out;

import java.util.List;
import java.util.Map;

public interface GetTweetsPort {

    default List<Map<String, Object>> getTweetsAsMap(String userId) {
        return getTweetsAsMap(userId, false, true);
    }

    List<Map<String, Object>> getTweetsAsMap(String userId, boolean createdByUser, boolean createdByOtherUsers);
}
