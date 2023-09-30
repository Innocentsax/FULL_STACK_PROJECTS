import "../Pages/welcome.css";
import successScreen from "../../assets/images/successScreen.svg";
import React from 'react'
import { useLocation, useNavigate } from "react-router-dom";
import { currency } from '../../includes/Config';

export default function BuyElectricitySuccessScreen() {
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
                    {<h1>Your Electricity purchase was successful  🥳 </h1>}
                    <p>Electricity purchase of {currency.format(state.amount)} from {state.serviceType} has been made.
                        {state.purchaseCode}.</p>
                    <button onClick={dashboard}>Continue</button>
                </div>
            </div>
        </div>
    )
}