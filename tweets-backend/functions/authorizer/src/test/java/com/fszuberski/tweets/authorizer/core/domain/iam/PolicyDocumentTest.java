package com.fszuberski.tweets.authorizer.core.domain.iam;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SuppressWarnings("unchecked")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PolicyDocumentTest {

    @Test
    void instantiate_given_valid_constructor_parameters() {
        List<Statement> statementList =
                List.of(mock(Statement.class), mock(Statement.class), mock(Statement.class));
        PolicyDocument policyDocument = new PolicyDocument(statementList);

        assertNotNull(policyDocument);
        assertEquals("2012-10-17", policyDocument.getVersion());
        assertTrue(policyDocument.getStatementList().containsAll(statementList));
    }

    @Test
    void instantiate_with_empty_statementList_given_null_statementList() {
        PolicyDocument policyDocument = new PolicyDocument(null);

        assertNotNull(policyDocument);
        assertNotNull(policyDocument.getStatementList());
        assertEquals(0, policyDocument.getStatementList().size());
    }

    @ParameterizedTest
    @MethodSource("return_valid_map__parameters")
    void return_valid_map(List<Statement> statementList) {
        PolicyDocument policyDocument = new PolicyDocument(statementList);
        Map<String, Object> policyDocumentMap = policyDocument.asMap();

        assertNotNull(policyDocumentMap);
        assertEquals("2012-10-17", policyDocumentMap.get("Version"));
        assertEquals(statementList.size(), ((List<Statement>)policyDocumentMap.get("Statement")).size());
    }

    private static Stream<Arguments> return_valid_map__parameters() {
        return Stream.of(
                Arguments.of(Collections.emptyList()),
                Arguments.of(List.of(mock(Statement.class))),
                Arguments.of(List.of(mock(Statement.class), mock(Statement.class), mock(Statement.class)))
        );
    }
}