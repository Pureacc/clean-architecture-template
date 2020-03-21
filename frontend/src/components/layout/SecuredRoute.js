import {Redirect, Route} from "react-router-dom";
import React from "react";

export function SecuredRoute(props) {
    const {isAuthenticated} = props;
    return (
        isAuthenticated
            ? <Route {...props} />
            : <Redirect to="/signin"/>
    );
}