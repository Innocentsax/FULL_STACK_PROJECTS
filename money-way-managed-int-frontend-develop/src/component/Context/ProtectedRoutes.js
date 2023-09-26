import React from "react";
import { Route, Navigate } from "react-router-dom";
import {useStateContext} from "./ContextProvider";

const ProtectedRoute = ({ element: Element, ...rest }) => {
    const { token } = useStateContext(); // Get the JWT token from your context.
    // Check if the user is authenticated based on the JWT token.
    const isAuthenticated = !!token; // This assumes that token is a truthy value when the user is logged in.

    return (
        <Route
            {...rest}
            element={
                isAuthenticated ? <Element /> : <Navigate to="/login" replace />
            }
        />
    );
};

export default ProtectedRoute;
