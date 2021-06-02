package com.fszuberski.tweets.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JWTServiceTest {

    private final static byte[] TEST_JWT_SECRET = "0987bc5f-7963-462f-a59d-c6f968be40d0".getBytes(StandardCharsets.UTF_8);

    private JWTService jwtService;


    @BeforeEach
    public void setup() {
        jwtService = new JWTService(TEST_JWT_SECRET);
    }

    @ParameterizedTest
    @MethodSource("null_or_empty_secret__parameters")
    public void constructor_should_throw_IllegalArgumentException_given_null_or_empty_secret(byte[] secret) {
        assertThrows(IllegalArgumentException.class, () -> new JWTService(secret));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjE3OTQ2NTUsImlzcyI6ImZzenViZXJza2kuY29tIn0.1Si-cGetu17K9AqkSvaGgCvwD9RqrgTfOBrxKLk93vw",
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIxNzk0NjU1LCJpc3MiOiJmc3p1YmVyc2tpLmNvbSJ9.X8V6QfkyZs4ArqDX1_DRWmFNDHeBrqrD16rArGGwpVs"
    })
    public void isValidSignedJWT_should_return_true_given_jwt_signed_with_valid_secret(String jwt) {
        boolean validSignedJWT = jwtService.isValidSignedJWT(jwt);
        assertTrue(validSignedJWT);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"test"})
    public void createAndSignJWT_should_return_jwt_given_null_or_empty_subject(String subject) {
        String jwt = jwtService.createAndSignJWT(subject);

        assertNotNull(jwt);
        assertNotEquals(0, jwt.length());

        System.out.println(jwt);

        String extractedSubject = getJwtParser()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

        if (subject == null || subject.length() == 0) {
            assertNull(extractedSubject);
        } else {
            assertEquals(subject, extractedSubject);
        }
    }


    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("invalid_jwt__parameters")
    public void isValidSignedJWT_should_return_false_given_invalid_jwt(String invalidJwt) {
        boolean validSignedJWT = jwtService.isValidSignedJWT(invalidJwt);
        assertFalse(validSignedJWT);
    }

    @Test
    public void getSubject_should_return_subject_given_valid_jwt() {
        String subject = jwtService.getSubject("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIxNzk0NjU1LCJpc3MiOiJmc3p1YmVyc2tpLmNvbSJ9.X8V6QfkyZs4ArqDX1_DRWmFNDHeBrqrD16rArGGwpVs");
        assertEquals("test", subject);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("invalid_jwt__parameters")
    public void getSubject_should_return_empty_string_given_invalid_jwt(String invalidJwt) {
        String subject = jwtService.getSubject(invalidJwt);
        assertEquals("", subject);
    }

    private static Stream<byte[]> null_or_empty_secret__parameters() {
        return Stream.of(null, new byte[] {});
    }

    private static Stream<String> invalid_jwt__parameters() {
        return Stream.of(
                "invalidjwt",
                // missing payload
                "eyJhbGciOiJIUzI1NiJ9..X8V6QfkyZs4ArqDX1_DRWmFNDHeBrqrD16rArGGwpVs",
                // signed with different key
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIxNzk1MzE4LCJpc3MiOiJmc3p1YmVyc2tpLmNvbSJ9.hTmdylskxLvBrFq_7DBaIcSv_3KbUg3WIHARhrSzfdw");
    }

    private JwtParser getJwtParser() {
        return Jwts
                .parserBuilder()
                .setSigningKey(TEST_JWT_SECRET)
                .build();
    }

}