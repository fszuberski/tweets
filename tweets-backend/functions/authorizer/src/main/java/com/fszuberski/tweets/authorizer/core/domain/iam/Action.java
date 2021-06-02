package com.fszuberski.tweets.authorizer.core.domain.iam;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Action {
    ALL("*"),
    EXECUTE_API_INVOKE("execute-api:Invoke");

    private final String awsName;
}
