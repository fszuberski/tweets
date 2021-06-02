package com.fszuberski.tweets.authorizer.core.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TokenAuthorizerContext {
    private String type;
    private String authorizationToken;
    private String methodArn;
}
