import React, { useReducer } from "react";
import { UserReducer } from "./UserReducer";

const _USER = {
  dateOfBirth: "",
  email: "",
  firstName: "",
  gender: "",
  lastName: "",
  isLoggedIn:false,
  rememberMe:true,
  isSubscribedToNewsletter:false,
};
export const UserContext = React.createContext(_USER);
UserContext.displayName = "UserContext";

export const UserContextProvider = (props) => {
  const { children } = props;
  const [user, dispatch] = useReducer(UserReducer, _USER);

  const autoLogin = () => {
    dispatch({ type: "", payload: localStorage.getItem("token") });
  };
  const login = (userDetails)=>{
    dispatch({type:'SET_USER_DETAILS_FROM_LOGIN', payload:userDetails});
  }

  const doLogout = ()=>{
    dispatch({type:'LOG_OUT'});
  }

  return (
    <UserContext.Provider value={{ user, autoLogin, login, doLogout }}>
      {children}
    </UserContext.Provider>
  );
};