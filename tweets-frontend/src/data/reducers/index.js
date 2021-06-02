import { combineReducers } from "redux";

import authentication from "./authentication";
import misc from "./misc";

const rootReducer = combineReducers({
    authentication,
    misc
});


export default rootReducer;