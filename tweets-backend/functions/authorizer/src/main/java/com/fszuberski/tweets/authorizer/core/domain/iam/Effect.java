package com.fszuberski.tweets.authorizer.core.domain.iam;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Effect {
    ALLOW("Allow"),
    DENY("Deny");

    private final String awsName;
}
