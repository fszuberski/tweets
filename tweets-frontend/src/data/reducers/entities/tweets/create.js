import { DEFAULT_STATE, DEFAULT_STATE__API__FAIL, DEFAULT_STATE__API__STARTED, DEFAULT_STATE__API__SUCCESS } from "../../../../core/utils/store_utils";


export default (state = DEFAULT_STATE, action) => {
    switch (action.type) {
        case "actions.feed.API__TWEETS__CREATE__STARTED":
            return DEFAULT_STATE__API__STARTED(state, DEFAULT_STATE);

        case "actions.feed.API__TWEETS__CREATE__SUCCESS":
            return DEFAULT_STATE__API__SUCCESS(state, DEFAULT_STATE, action);

        case "actions.feed.API__TWEETS__CREATE__FAIL":
            return DEFAULT_STATE__API__FAIL(state, DEFAULT_STATE, action);

        case "actions.common.API__AUTHENTICATION__SIGN_OUT":
            return DEFAULT_STATE;

        default:
            return state;
    }
}