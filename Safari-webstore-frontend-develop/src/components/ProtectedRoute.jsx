import React from "react";
import {Route, Redirect} from 'react-router-dom';
import { authKeysExist } from "../context/UserReducer";

const ProtectedRoute = ({ component: Component, ...rest }) => {
    return (
  <Route {...rest} render={(props) => authKeysExist()? (<Component {...props} />) 
    : (<Redirect to={{ pathname: "/", }}  />)
    }
  />
)};


export default ProtectedRoute;