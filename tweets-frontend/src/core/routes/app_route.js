import React from "react";
import { Redirect, Route } from "react-router-dom";
import { isSignedIn } from "../utils/session_utils";

const AppRoute = ({ component: Component, layout: Layout, authentication, ...rest }) => {

    if (isSignedIn(authentication)) {
        return (
            <Route { ...rest } render={ props => (
                <Redirect to={ { pathname: "/feed", state: { from: props.location } } }/>
            ) }/>
        );
    }

    return (
        <Route { ...rest } render={ props => (
            <Component { ...props } { ...rest } />
        ) }/>
    );
};

export default AppRoute;