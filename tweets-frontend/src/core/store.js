import storage from "redux-storage";
import { applyMiddleware, createStore } from "redux";
import thunk from "redux-thunk";
import createEngine from "redux-storage-engine-sessionstorage";
import { composeWithDevTools } from "redux-devtools-extension/logOnlyInProduction";

import reducers from "../data/reducers/index";

const reducer = storage.reducer(reducers);
const engine = createEngine("tweets.fszuberski.com");

const middleware = storage.createMiddleware(engine);

const composeEnhancers = composeWithDevTools({
    // options like actionSanitizer, stateSanitizer
});

export const store = createStore(reducer, composeEnhancers(
    applyMiddleware(thunk, middleware)
));

const load = storage.createLoader(engine);
load(store);
