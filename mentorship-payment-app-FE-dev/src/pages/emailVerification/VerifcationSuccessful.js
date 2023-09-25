import React from "react";
import { useNavigate } from "react-router-dom";
import { MdOutlineMarkEmailRead } from "react-icons/md";
import "./VerifcationSuccessful.css";

const VerifcationSuccessful = () => {

  const navigate = useNavigate();
  const navigateToLogin = () => {
    navigate("/login");
  };

  return (
    <div className="verify">
      <div>
        <MdOutlineMarkEmailRead className="verify-icon" />
        <h1 className="verify-header">Email successfully verified</h1>
        <br />
        <p className="verify-para">
          Congratulations! You have successfully verified your email.
          <br /> click the button below
          <br /> to login and start enjoying Fintech
        </p>
        <button id="verify-button" onClick={navigateToLogin}>
          Login
        </button>
      </div>
    </div>
  );
};


export default VerifcationSuccessful;
