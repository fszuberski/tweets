package com.fszuberski;

import java.util.HashMap;
import java.util.Map;

public class TestHelper {

    public static Map<String, Object> generateUserProfileMap() {
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
