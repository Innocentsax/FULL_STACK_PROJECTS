import React, { createContext, useContext, useState } from "react";

const StateContext = createContext();

const initialState = {
  userProfile: false,
  notification: false,
};

export const ContextProvider = ({ children }) => {
  const [activeMenu, setActiveMenu] = useState(true);
  const [isClicked, setIsClicked] = useState(initialState);
  const [screenSize, setScreenSize] = useState(undefined);
  const [token, setToken] = useState("");
  const [employee, setEmployee] = useState({});

  const handleClick = (clicked) => {
    setIsClicked({ ...initialState, [clicked]: true });
  };
  const closeClick = (clicked) => {
    setIsClicked({ ...initialState, [clicked]: false })
  }

  return (
    <StateContext.Provider
      value={{
        activeMenu,
        setActiveMenu,
        isClicked,
        setIsClicked,
        handleClick,
        screenSize,
        setScreenSize,
        closeClick,
        employee,
        setEmployee,
        token,
        setToken
      }}
    >
      {children}
    </StateContext.Provider>
  );
};

export const useStateContext = () => useContext(StateContext);
