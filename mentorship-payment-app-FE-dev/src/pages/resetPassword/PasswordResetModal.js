import React from "react";
import { useNavigate } from "react-router-dom";
import "./PasswordResetModal.css";
import { MdMarkEmailUnread } from "react-icons/md";

// function PasswordResetModal() {
const PasswordResetModal = () => {
    const navigate = useNavigate();
    const navigateToHome = () => {
      navigate("/");
    };

    return (
      <div>
        <div className="app_PasswordResetModal">
          <MdMarkEmailUnread className="verify-icon" />
          <h3>
            {" "}
            We sent a password reset link to your email <br />
            Please click the link to reset your password <br />
            <span>
              Didn’t receive the email?{" "}
              <a href="/forgotPassword" className="app__resend-link">
                {" "}
                Click to Resend link
              </a>
            </span>
          </h3>
          <button
            className="app_PasswordResetModal-btn"
            onClick={navigateToHome}
          >
            Back To Home
          </button>
        </div>
      </div>
    );
  };
// }

export default PasswordResetModal;
