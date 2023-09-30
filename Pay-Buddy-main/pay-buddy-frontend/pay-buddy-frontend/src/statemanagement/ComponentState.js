import React, { createContext, useState } from 'react';

const MyContext = createContext();

const MyContextProvider = ({ children }) => {
    const [pagename, setPageName] = useState('');

    return (
        <MyContext.Provider value={{pagename, setPageName }}>
            {children}
        </MyContext.Provider>
    );
};

export { MyContext, MyContextProvider };