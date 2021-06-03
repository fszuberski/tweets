import axios from "axios";
import { store } from "./store";
import { isNullOrUndefined, stringsEqualTrimIgnoreCase } from "./utils/misc_utils";
import { API_AUTHORIZATION_HEADER, API_BASE_URL, API_CONTENT_TYPE_HEADER, REST_CONTENT_TYPE_APPLICATION_JSON } from "./utils/common";

const configureWebClient = (webClientToConfigure) => {
    webClientToConfigure
        .interceptors
        .request
        .use(config => {

            const token =
                !isNullOrUndefined(store) &&
                !isNullOrUndefined(store.getState()) &&
                !isNullOrUndefined(store.getState().authentication) &&
                !isNullOrUndefined(store.getState().authentication.data)
                    ? store.getState().authentication.data.token
                    : null;

            let headers = {
                ...config.headers
            };

            if ((stringsEqualTrimIgnoreCase(config.method, "post") ||
                stringsEqualTrimIgnoreCase(config.method, "put")) &&
                isNullOrUndefined(headers[API_CONTENT_TYPE_HEADER])) {

                headers[API_CONTENT_TYPE_HEADER] = REST_CONTENT_TYPE_APPLICATION_JSON;
            }

            if (!isNullOrUndefined(token)) {
                headers[API_AUTHORIZATION_HEADER] = `${ token }`;
            }

            return {
                ...config,
                headers
            };
        });

    return webClientToConfigure;
};

const webClient = configureWebClient(axios.create({
    baseURL: API_BASE_URL,
    timeout: 20000
}));


export default webClient;