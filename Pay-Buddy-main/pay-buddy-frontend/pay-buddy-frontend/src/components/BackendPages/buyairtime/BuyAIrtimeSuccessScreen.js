import "../../Pages/welcome.css";
import successScreen from "../../../assets/images/successScreen.svg";
import React from 'react'
import { useLocation, useNavigate } from "react-router-dom";
import { currency } from '../../../includes/Config';

export default function BuyAirtimeSuccessScreen() {
    const navigate = useNavigate();
   const {state} = useLocation()

  const dashboard = (e) => {
    e.preventDefault();
    navigate("/pay-buddy/dashboard");
  }
  return (
    <div className="welcome__parent">
      <div className="welcome__content">
          <img src={successScreen} className="img-fluid" />
          <div className="successMessage">
            {<h1>Your airtime purchase was succesful  🥳 </h1>}
            <p>Your airtime purchase of {currency.format(state.amount)} has been sent to {state.phoneNumber}</p>
            <button onClick={dashboard}>Continue</button>
         </div>
      </div>
    </div>
  )
}
