package com.fszuberski.tweets.authorizer.core.domain.iam;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

class Statement {

    private static final String EFFECT = "Effect";
    private static final String ACTION = "Action";
    private static final String RESOURCE = "Resource";

    @Getter
    private final String action;

    @Getter
    private final String effect;

    @Getter
    private final List<String> resourceList;

    public Statement(Action action, Effect effect) {
        this(action, effect, Collections.singletonList("*"));
    }

    public Statement(Action action, Effect effect, List<String> resourceList) {
        this.action = action != null ? action.getAwsName() : Action.EXECUTE_API_INVOKE.getAwsName();
        this.effect = effect != null ? effect.getAwsName() : Effect.DENY.getAwsName();
        this.resourceList = resourceList != null ? resourceList : Collections.singletonList("*");
    }

    public Map<String, Object> asMap() {
        return Map.of(
                EFFECT, effect,
                ACTION, action,
                RESOURCE, resourceList.size() == 1 ? resourceList.get(0) : resourceList
        );
    }
}
