import { combineReducers } from "redux";

import authentication from "./authentication";
import entities from "./entities";
import misc from "./misc";
import ui from "./ui";

const rootReducer = combineReducers({
    authentication,
    entities,
    misc,
    ui
});


export default rootReducer;