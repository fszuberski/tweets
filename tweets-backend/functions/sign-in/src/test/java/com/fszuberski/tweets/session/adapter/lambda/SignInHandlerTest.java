package com.fszuberski.tweets.session.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fszuberski.LambdaTestLogger;
import com.fszuberski.tweets.rest.domain.ApiException;
import com.fszuberski.tweets.session.adapter.lambda.model.AuthenticationRequestModel;
import com.fszuberski.tweets.session.adapter.lambda.model.AuthenticationResponseModel;
import com.fszuberski.tweets.session.port.in.CreateSession;
import org.junit.jupiter.api.*;

import static com.fszuberski.tweets.session.port.in.CreateSession.CreateSessionException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SignInHandlerTest {

    private SignInHandler signInHandler;
    private Context context;
    private CreateSession createSession;

    @BeforeEach
    void setup() {
        createSession = mock(CreateSession.class);

        context = mock(Context.class);
        LambdaLogger loggerStub = new LambdaTestLogger();
        when(context.getLogger()).thenReturn(loggerStub);

        signInHandler = new SignInHandler(createSession);
    }

    @Test
    void throw_IllegalArgument_given_null_createSession() {
        assertThrows(IllegalArgumentException.class, () -> new SignInHandler(null));
    }

    @Test
    void return_response_containing_jwt() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIxNzk0NjU1LCJpc3MiOiJmc3p1YmVyc2tpLmNvbSJ9.X8V6QfkyZs4ArqDX1_DRWmFNDHeBrqrD16rArGGwpVs";
        AuthenticationRequestModel authenticationRequestModel = generateAuthenticationModel();
        when(createSession.createSession(authenticationRequestModel.getUsername(), authenticationRequestModel.getPassword()))
                .thenReturn(jwt);

        AuthenticationResponseModel authenticationResponseModel = signInHandler.handleRequest(authenticationRequestModel, context);

        assertNotNull(authenticationResponseModel);
        assertEquals(jwt, authenticationResponseModel.getToken());
    }

    @Test
    void throw_BadRequest_exception_given_null_authenticationModel() {
        assertThrows(ApiException.BadRequest.class, () -> signInHandler.handleRequest(null, context));
    }

    @Test
    void throw_BadRequest_exception_given_invalid_username_and_password_combination() {
        AuthenticationRequestModel authenticationRequestModel = generateAuthenticationModel();
        when(createSession.createSession(authenticationRequestModel.getUsername(), authenticationRequestModel.getPassword()))
                .thenThrow(new InvalidUsernameAndPassword());

        assertThrows(ApiException.BadRequest.class, () -> signInHandler.handleRequest(authenticationRequestModel, context));
    }

    @Test
    void throw_InternalServerError_exception_given_generic_exception_occurs() {
        AuthenticationRequestModel authenticationRequestModel = generateAuthenticationModel();
        when(createSession.createSession(authenticationRequestModel.getUsername(), authenticationRequestModel.getPassword()))
                .thenThrow(new RuntimeException());

        assertThrows(ApiException.InternalServerError.class, () -> signInHandler.handleRequest(authenticationRequestModel, context));
    }

    private AuthenticationRequestModel generateAuthenticationModel() {
        return new AuthenticationRequestModel("username", "password");
    }
}