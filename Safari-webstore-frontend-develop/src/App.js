import Navbar from "./components/Navbar";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import "./styles/style.scss";
import { CartProvider } from "./utilities/CartContext";
import Routes from "./components/routes";
import React from 'react';
import {UserContext} from './context/UserContext';



function App() {
  const userCtx = React.useContext(UserContext);
  
  React.useEffect(()=>{
    userCtx.autoLogin();
  }, [])
  return (
    <>
      <Router> 
        <CartProvider>
          <Navbar />
          <Routes />

          </CartProvider>
      </Router>
    </>
  );
}

export default App;
