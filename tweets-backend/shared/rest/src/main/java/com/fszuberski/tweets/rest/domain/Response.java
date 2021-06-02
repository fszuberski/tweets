package com.fszuberski.tweets.rest.domain;

import lombok.Getter;

@Getter
public class Response {

    private final String message;

    public Response(String message) {
        this.message = message;
    }

    public static Response created() {
        return Response.created("Created");
    }

    public static Response created(String message) {
        return new Response(message);
    }
}
