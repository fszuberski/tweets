package com.fszuberski.tweets.rest.domain;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public static class BadRequest extends ApiException {
        public BadRequest() {
            super(ResponseData.BadRequest.format());
        }

        public BadRequest(String message) {
            super(message);
        }
    }

    public static class Unauthorized extends ApiException {
        public Unauthorized() {
            super(ResponseData.Unauthorized.format());
        }
    }

    public static class InternalServerError extends ApiException {
        public InternalServerError() {
            super(ResponseData.InternalServerError.format());
        }
    }
}
