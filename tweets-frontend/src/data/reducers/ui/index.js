import feedType from "./feed_type"

export default(state = {}, action) => {
    return {
        ...state,
        feedType: feedType(state.feedType, action)
    }
}