import React from "react";
import { useNavigate } from "react-router-dom";
import "./VerifyRegistration.css";
import { MdMarkEmailUnread } from "react-icons/md";


const VerifyEmailRegistration = () => {
  const navigate = useNavigate();
  const navigateToLogin = () => {
    navigate("/login");
  };
  return (
    <div>
      <div className="ver-reg">
      <MdMarkEmailUnread className="verify-icon" />
        <h3>
          {" "}
          Your account have been succefully registered,
          <br /> Please login to your email to verify your account
        </h3>
        <button className="ver-reg-btn" onClick={navigateToLogin}>
          Close
        </button>
      </div>
    </div>
  );
};

export default VerifyEmailRegistration;
