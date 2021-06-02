package com.fszuberski.tweets.authorizer.core.domain.iam;

import lombok.Getter;

import java.util.*;

class PolicyDocument {

    private static final String VERSION = "Version";
    private static final String STATEMENT = "Statement";

    @Getter
    private final String version;

    @Getter
    private final List<Statement> statementList;

    public PolicyDocument(List<Statement> statementList) {
        this.version = "2012-10-17";
        this.statementList = statementList != null ? statementList : new ArrayList<>();
    }

    public Map<String, Object> asMap() {
        List<Map<String, Object>> statements = new ArrayList<>();
        for (Statement statement : statementList) {
            statements.add(statement.asMap());
        }

        return Map.of(
                VERSION, version,
                STATEMENT, statements
        );
    }
}
