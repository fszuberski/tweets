package com.fszuberski.tweets.persistence.port.out;

import java.util.Map;
import java.util.Optional;

public interface GetUserProfilePort {
    Optional<Map<String, Object>> getUserProfileAsMap(String username);
}
