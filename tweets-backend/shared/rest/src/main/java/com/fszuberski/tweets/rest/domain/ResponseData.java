package com.fszuberski.tweets.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseData {
    OK(200, "OK"),
    Created(201, "Created"),
    BadRequest(400, "Bad Request"),
    Unauthorized(401, "Unauthorized"),
    InternalServerError(500, "InternalServerError");

    private final int code;
    private final String description;

    public String format() {
        return String.format("[%s] %s", getCode(), getDescription());
    }
}
