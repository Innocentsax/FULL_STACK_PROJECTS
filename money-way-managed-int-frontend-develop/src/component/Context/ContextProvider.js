
import React, { useContext, useState } from "react";

const StateContext = React.createContext();

export const ContextProvider = ({ children }) => {
    const [token, setToken] = useState("");
    const [tokenAuthenticated, setTokenAuthenticated] = useState(false);
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setUserEmail] = useState("");
    const [accountBalance, setAccountBalance] = useState("");
    const [accountNumber, setAccountNumber] = useState("");
    const [bank, setBank] = useState("");

    return (
        <StateContext.Provider value={{token, setToken, firstName,  setFirstName, lastName, bank, setBank,
            setLastName, email, setUserEmail, accountBalance, setAccountBalance, accountNumber, setAccountNumber, tokenAuthenticated, setTokenAuthenticated}}>
            {children}
        </StateContext.Provider>
    );
};

export const useStateContext = () => useContext(StateContext);
