import React from "react";
import { Redirect, Route } from "react-router-dom";

import { isSignedIn } from "../utils/session_utils";
import { isNullOrUndefined } from "../utils/misc_utils";

const SignedInAppRoute = ({ component: Component, authentication, storageLoaded, ...rest }) => {
    if (!isNullOrUndefined(storageLoaded) && !storageLoaded) {
        return (
            <Route { ...rest } render={ props => (
                <Component { ...props } { ...rest } />
            ) }/>
        );
    }

    if (isSignedIn(authentication)) {
        return (
            <Route { ...rest } render={ props => (
                <Component { ...props } { ...rest } />
            ) }/>
        );
    }

    return (
        <Route { ...rest } render={ props => (
            <Redirect to={ { pathname: "/", state: { from: props.location } } }/>
        ) }/>
    );
};

export default SignedInAppRoute;