import {
    LOGIN_SUCCESS,
    LOGOUT,
    LOGIN_FAIL,
    AUTH_ERROR,
    LOADING,
    
  } from "./types";
  export default function authReducer(state, action) {
    switch (action.type) {
      case LOGIN_SUCCESS:
            console.log(action.payload);
          localStorage.setItem("accessToken", action.payload.result.accessToken);
          
          localStorage.setItem("expiry", action.payload.expiresIn);
          window.location.href = `/dashboard`;
        return {
          ...state,
          ...action.payload,
            loading: false,
        };
      case LOADING:
        return {
          ...state,
          loading: true,
        };
      case AUTH_ERROR:
      case LOGIN_FAIL:
      case LOGOUT:
          localStorage.removeItem("accessToken");
          window.location.href = `/login`;
        return {
          ...state,
          isAuthenticated: false,
          loading: false,
        };
      default:
        return state;
    }
  }