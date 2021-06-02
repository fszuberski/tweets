export const isNullOrUndefined = (input) => {
    return !!(input === undefined || input === null);
};

export const stringsEqualTrimIgnoreCase = (string1, string2) => {
    if (isNullOrUndefined(string1) || isNullOrUndefined(string2)) {
        return false;
    }

    return string1.trim()
        .toLowerCase() === string2.trim()
        .toLowerCase();
};

export const isLoading = (input) => {
    return isNullOrUndefined(input) || isNullOrUndefined(input.meta) || isNullOrUndefined(input.meta.loading) || input.meta.loading;
};




