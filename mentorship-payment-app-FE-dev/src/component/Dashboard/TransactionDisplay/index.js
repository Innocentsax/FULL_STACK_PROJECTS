import React, { useEffect, useState } from "react";
import "./index.css";
import axios from "axios";
import { BsEye, BsWallet2, BsEyeSlash } from "react-icons/bs";
import { MdSwitchAccount } from "react-icons/md";
import { useNavigate } from "react-router-dom";

export const TransactionDisplay = () => {
  const [showBalance, setShowBalance] = useState(false);
  const [values, setValues] = useState([]);

  const toggleBalance = () => {
    setShowBalance(showBalance ? false : true);
    console.log(showBalance);
  };

  const navigate = useNavigate();

  const token = localStorage.getItem("accessToken");
  if (!token) {
    navigate("/login");
  }
  console.log("this is the logged in token: " + token);

  useEffect(() => {
    // eslint-disable-next-line
    axios
      .get("https://mentorship-payment-app.herokuapp.com/api/v1/wallet", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        console.log("display this result", res.data.result);
        const result = res.data.result;
        setValues(result);
      })
      .catch((err) => {
        console.log(err);
      });
    // eslint-disable-next-line
  }, []);

  return (
    <>
      <div className='transaction--head'>
        <div className='wallet-and-body' style={{ display: "flex" }}>
          <div className='wallet'>
            <BsWallet2 className='wallet-group' />
          </div>
          <div className='transaction-body'>
            <p>Account Balance</p>

            <h1>
              {values.balance
                ? `${showBalance ? "*******" : values.balance}`
                : "Loading..."}
            </h1>
            <p>{values.bankName}</p>
            <div className='row-effect'>
              <div>
                <MdSwitchAccount className=' icon-account' />
              </div>
              <div>{values.accountNumber}</div>
            </div>
          </div>
        </div>
        {values.bankName && (
          <div className='eye-image' onClick={toggleBalance}>
            {showBalance ? (
              <BsEyeSlash className='eyeicons' />
            ) : (
              <BsEye className='eyeicons' />
            )}
          </div>
        )}
      </div>
    </>
  );
};
