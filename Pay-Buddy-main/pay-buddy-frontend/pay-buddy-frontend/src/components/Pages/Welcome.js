import React from "react";
import "./welcome.css";
import successScreen from "../../assets/images/successScreen.svg";
import { useLocation, useNavigate } from "react-router-dom";

const Welcome = () => {
  const { state } = useLocation();
  const navigate = useNavigate();

  const login = (e) => {
    e.preventDefault();
    window.location.href=`${state.emailDomain}`;
  }

  return (
    <div className="welcome__parent">
      <div className="welcome__content">
        <img src={successScreen} className="img-fluid" />
          <div className="successMessage">
            <h1>Congratulations {state.firstName}</h1>
            <p>An email verification link has been sent to you</p>
            <button onClick={(e) => login(e)}>Continue</button>
        </div>
      </div>
    </div>
  );
};

export default Welcome;
