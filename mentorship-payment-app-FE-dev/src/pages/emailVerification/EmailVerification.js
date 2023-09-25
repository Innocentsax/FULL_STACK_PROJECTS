import React from "react";
import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import axios from "axios";
import VerifcationSuccessful from "./VerifcationSuccessful";
import VerifcationFailed from "./VerifcationFailed";

const EmailVerification = () => {
  const [message, setMessage] = useState("");
  const { search } = useLocation();
  const token = search.split("=")[1];

  // const localhostUrl = `http://localhost:9005/api/v1/user/verifyRegistration?token=${token}`;
  const herokuUrl = `https://mentorship-payment-app.herokuapp.com/api/v1/user/verifyRegistration?token=${token}`;
  const currentUrl = herokuUrl;

  useEffect(() => {
    const loadVerification = async () => {
      const response = await axios.get(
        currentUrl
      );
     
      setMessage(response.data.message);
    };

    loadVerification();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  },[]);


  console.log(message);
  return (
    <>
      {message === "User verified successfully" ? <VerifcationSuccessful /> : <VerifcationFailed />}
    </>
  );
};

export default EmailVerification;
