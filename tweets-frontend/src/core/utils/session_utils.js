import { isNullOrUndefined } from "./misc_utils";

export const isSignedIn = (authentication) => {
    return !!(!isNullOrUndefined(authentication) && !isNullOrUndefined(authentication.data) && !isNullOrUndefined(authentication.data.token));
};
