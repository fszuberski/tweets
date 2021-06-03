import { GetTweetsQueryTypesEnum } from "../../../core/enums/get_tweets_query_types";

export default (state = { selected: GetTweetsQueryTypesEnum.CREATED_BY_OTHER_USERS }, action) => {
    switch (action.type) {
        case "actions.ui.FEED_TYPE_SELECTED":
            return { ...state, selected: action.payload };

        default:
            return state;
    }
}