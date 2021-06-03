import tweets from "./tweets";

export default (state = {}, action) => {
    return {
        ...state,
        tweets: tweets(state.tweets, action)
    }
}