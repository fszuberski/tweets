export const DEFAULT_STATE = {
    meta: {
        loading: false
    },
    data: null,
    error: false
};

export const DEFAULT_STATE_ARRAY = {
    meta: {
        order: [],
        loading: false
    },
    data: null,
    error: false
};

export const DEFAULT_STATE__API__STARTED = (state, defaultState) => {
    return {
        ...state,
        meta: {
            ...defaultState.meta,
            loading: true
        }
    };
};

export const DEFAULT_STATE__API__SUCCESS = (state, defaultState, action) => {
    return {
        ...state,
        meta: {
            ...defaultState.meta,
            ...action.meta
        },
        data: action.payload,
        error: action.error
    };
};

export const DEFAULT_STATE__API__FAIL = (state, defaultState, action) => {
    return {
        ...state,
        meta: {
            ...defaultState.meta,
            ...action.meta
        },
        data: action.payload,
        error: action.error
    };
};