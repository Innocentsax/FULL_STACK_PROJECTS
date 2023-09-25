import React, { useContext, useReducer } from "react";
import jwt_decode from "jwt-decode";
import AuthContext from "./authContext";
import AuthReducer from "./authReducer";
import {
  LOADING,
  LOGIN_SUCCESS,
  LOGOUT,
  LOGIN_FAIL,
  LOGOUT_FAIL,
  RESET_PASSWORD,
  RESET_PASSWORD_FAIL,
  RESET_PASSWORD_SUCCESS,
} from "./types";
import { apiPost } from "../utils/apiHelper";

const AuthState = ({ children }) => {
  const initialState = {
    token: localStorage.getItem("accessToken"),
    isAuthenticated: localStorage?.getItem("accessToken") ? true : false,
    loading: false,
    user: null,
    error: null,
  };
  const [state, dispatch] = useReducer(AuthReducer, initialState);

  const config = {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
  };

  const login = async (formData) => {
    dispatch({
      type: LOADING,
      payload: true,
    });

    try {
      //If there is need to do any conditional stuff.
      const response = await apiPost(`/api/v1/login`, formData, config, false);
      const accessToken = response.data.result.accessToken;
      if (accessToken === null) {
        alert("invalid login");
      } else {
        localStorage.setItem("accessToken", response.data.result.accessToken);
        const { name } = jwt_decode(accessToken);
        localStorage.setItem("name", name);
      }
      const { exp } = jwt_decode(response.data.result.accessToken);
      dispatch({
        type: LOGIN_SUCCESS,
        payload: {
          ...response.data,
          isAuthenticated: true,
          expiresIn: exp,
        },
      });
    } catch (error) {
      dispatch({
        type: LOGIN_FAIL,
        payload: error.message,
      });
    }
  };

  const logout = async (data) => {
    // const refreshToken = localStorage.getItem('refreshToken');
    try {
      await apiPost("/auth/logout", {}, {});
      dispatch({
        type: LOGOUT,
        payload: data,
      });
    } catch (err) {
      dispatch({
        type: LOGOUT_FAIL,
        payload: err.message,
      });
    }
  };

  const reset_Password = async (formData) => {
    dispatch({
      type: RESET_PASSWORD,
      payload: true,
    });

    try {
      const response = await apiPost(
        `/api/v1/reset-password`,
        formData,
        config
      );

      dispatch({
        type: RESET_PASSWORD_SUCCESS,
        payload: response.message,
      });
    } catch (err) {
      dispatch({
        type: RESET_PASSWORD_FAIL,
        payload: err.message,
      });
    }
  };

  return (
    <AuthContext.Provider
      value={{
        loading: state.loading,
        isAuthenticated: state.isAuthenticated,
        user: state.user,
        error: state.error,
        login,
        logout,
        reset_Password,
      }}>
      {children}
    </AuthContext.Provider>
  );
};
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("UseAuth must be used in an AuthProvider");
  }
  return context;
};
export default AuthState;
