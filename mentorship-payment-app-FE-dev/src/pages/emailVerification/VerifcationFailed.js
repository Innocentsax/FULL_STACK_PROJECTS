import React from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";
import { TbFaceIdError } from "react-icons/tb";
import "./VerificationFailed.css";

const VerifcationFailed = () => {
  const { search } = useLocation();
  const token = search.split("=")[1];

  // const localhostUrl = `http://localhost:9005/api/v1/user/resendVerificationToken?token=${token}`;
  const herokuUrl = `https://mentorship-payment-app.herokuapp.com/api/v1/user/resendVerificationToken?token=${token}`;
  const currentUrl = herokuUrl;

  async function handleSubmit() {
    try {
      await axios.get(currentUrl);
    } catch (error) {
      console.log(error.response.data);
    }
  }

  return (
    <div className="ver-fail">
      <div>
        <TbFaceIdError className="ver-fail-icon" />
        <h1 className="ver-fail-header">Email verification failed</h1>
        <p className="ver-fail-para">
          Sorry! Email verification failed.
          <br /> Please click button below to receive a new verification mail.
        </p>
        <button id="ver-fail-button" onClick={handleSubmit}>
          Resend link
        </button>
      </div>
    </div>
  );
};

export default VerifcationFailed;
