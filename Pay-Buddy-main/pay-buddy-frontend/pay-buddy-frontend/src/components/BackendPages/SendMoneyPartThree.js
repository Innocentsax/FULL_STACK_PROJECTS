import "../Pages/welcome.css";
import successScreen from "../../assets/images/successScreen.svg";
import React from 'react'
import { useLocation, useNavigate } from "react-router-dom";
import { currency } from '../../includes/Config';

export default function SendMoneyPartThree() {
    const navigate = useNavigate();
    const{state} = useLocation();

  const dashboard = (e) => {
    e.preventDefault();
    navigate("/pay-buddy/dashboard");
  }
  return (
    <div className="welcome__parent">
      <div className="welcome__content">
          <img src={successScreen} className="img-fluid" />
          <div className="successMessage">
            {<h1>Your money is on it's way </h1>}
            <p>Your {currency.format(state.amountSent)} transfer to {state.beneficiary} is succesful</p>
            <button onClick={dashboard}>Continue</button>
         </div>
      </div>
    </div>
  )
}
