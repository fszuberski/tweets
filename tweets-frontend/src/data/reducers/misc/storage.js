import { LOAD, SAVE } from "redux-storage";

const defaultState = {
    loaded: false
};

export default (state = defaultState, action) => {
    switch (action.type) {
        case LOAD:
            return { ...state, loaded: true };

        case SAVE:
            return state;

        default:
            return state;
    }
}