import webClient from "../../core/web_client";
import { extractErrorData, extractResponseData } from "../../core/utils/api_utils";
import { isNullOrUndefined } from "../../core/utils/misc_utils";
import { GetTweetsQueryTypesEnum } from "../../core/enums/get_tweets_query_types";

export const getTweets = (queryType) => dispatch => {
    dispatch({
        type: "actions.feed.API__TWEETS__GET__STARTED"
    });

    return webClient
        .get(`tweets?type=${ !isNullOrUndefined(queryType) ? queryType : GetTweetsQueryTypesEnum.CREATED_BY_OTHER_USERS}`)
        .then(response => {
                const responseData = extractResponseData(response);

                dispatch({
                    type: "actions.feed.API__TWEETS__GET__SUCCESS",
                    meta: {},
                    payload: responseData,
                    error: false
                });
        })
        .catch(error => {
            dispatch({
                type: "actions.feed.API__TWEETS__GET__FAIL",
                meta: extractErrorData(error),
                payload: {},
                error: true
            })
        });
}

export const createTweet = (data) => dispatch => {
    dispatch({
        type: "actions.feed.API__TWEETS__CREATE__STARTED"
    });

    return webClient
        .post("tweets", data)
        .then(response => {
            const responseData = extractResponseData(response);

            dispatch({
                type: "actions.feed.API__TWEETS__CREATE__SUCCESS",
                meta: {},
                payload: responseData,
                error: false
            });
        })
        .catch(error => {
            dispatch({
                type: "actions.feed.API__TWEETS__CREATE__FAIL",
                meta: extractErrorData(error),
                payload: {},
                error: true
            })
        });
}