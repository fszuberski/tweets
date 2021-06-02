package com.fszuberski.tweets.session.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fszuberski.tweets.rest.domain.ApiException;
import com.fszuberski.tweets.session.adapter.lambda.model.AuthenticationRequestModel;
import com.fszuberski.tweets.session.adapter.lambda.model.AuthenticationResponseModel;
import com.fszuberski.tweets.session.core.SessionService;
import com.fszuberski.tweets.session.port.in.CreateSession;

import static com.fszuberski.tweets.session.port.in.CreateSession.CreateSessionException.*;

// TODO: proper HTTP codes in aws console when Unauthorized
// Handler value: com.fszuberski.tweets.session.adapter.lambda.SignInHandler
public class SignInHandler implements RequestHandler<AuthenticationRequestModel, AuthenticationResponseModel> {

    private final CreateSession createSession;

    public SignInHandler() {
        this(new SessionService());
    }

    public SignInHandler(CreateSession createSession) {
        if (createSession == null) {
            throw new IllegalArgumentException(
                    String.format("Exception while initializing %s - %s cannot be null",
                            SignInHandler.class.getSimpleName(), CreateSession.class.getSimpleName()));
        }
        this.createSession = createSession;
    }

    @Override
    public AuthenticationResponseModel handleRequest(AuthenticationRequestModel authenticationRequestModel, Context context) {
        if (authenticationRequestModel == null) {
            throw new ApiException.BadRequest("Invalid request body");
        }

        String jws;
        try {
            jws = createSession.createSession(authenticationRequestModel.getUsername(), authenticationRequestModel.getPassword());
        } catch (InvalidUsernameAndPassword e) {
            throw new ApiException.BadRequest(e.getMessage());
        } catch (Exception e) {
            throw new ApiException.InternalServerError();
        }

        return new AuthenticationResponseModel(jws);
    }
}