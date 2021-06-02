package com.fszuberski.tweets.session.adapter.lambda.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AuthenticationResponseModel {
    private final String token;
}
