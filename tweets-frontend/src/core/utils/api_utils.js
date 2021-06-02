import { isNullOrUndefined } from "./misc_utils";

export const extractResponseData = (response) => {

    if (isNullOrUndefined(response) || isNullOrUndefined(response.data)) {
        return null;
    }

    if (isNullOrUndefined(response.data.data)) {
        return response.data;
    }

    return response.data.data;
};

export const extractErrorData = (error) => {
    if (isNullOrUndefined(error)) {
        return null;
    }

    if (isNullOrUndefined(error.response)) {
        return null;
    }

    if (isNullOrUndefined(error.response.data)) {
        return null;
    }

    return error.response.data;
};