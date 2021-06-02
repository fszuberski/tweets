import webClient from "../../core/web_client";
import { extractErrorData, extractResponseData } from "../../core/utils/api_utils";


export const signIn = (data) => dispatch => {
    dispatch({
        type: "actions.common.API__AUTHENTICATION__SIGN_IN__STARTED"
    });

    return webClient
        .post("session", data)
        .then(response => {
            const responseData = extractResponseData(response);

            dispatch({
                type: "actions.common.API__AUTHENTICATION__SIGN_IN__SUCCESS",
                meta: {},
                payload: responseData,
                error: false
            });
        })
        .catch(error => {
            dispatch({
                type: "actions.common.API__AUTHENTICATION__SIGN_IN__FAIL",
                meta: extractErrorData(error),
                payload: {},
                error: true
            })
        });
}

export const signOut = () => {
    return {
        type: "actions.common.API__AUTHENTICATION__SIGN_OUT"
    };
};