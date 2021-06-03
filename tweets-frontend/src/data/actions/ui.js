export const selectFeedType = (feedType) => dispatch => {
    dispatch({
        type: "actions.ui.FEED_TYPE_SELECTED",
        payload: feedType
    })
}