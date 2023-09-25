import React from "react";
import "./section1.css";
import { useNavigate } from "react-router-dom";

const Section1 = () => {
  const navigate = useNavigate();
  const navigateToSignup = () => {
    navigate("/signup");
  };

  return (
    <div className="section1-container">
      <div className="section1">
        <section>
          <div className="section1-a">
            <h1>
              Quick and easy payment
              <br /> platform for all your
              <br /> transactions
            </h1>
            <p>
              Save and manage all you transactions in one place, easy payment,
              anytime & anyday.
            </p>
            <button onClick={navigateToSignup}>Create an account</button>
          </div>
          <div className="section1-b">
            <ul>
              <li>Easy Transfer</li>
              <li>Verified Payment</li>
              <li>Pay on the go</li>
            </ul>
            <img
              className="transfericon"
              src="../images/transfericon.svg"
              alt=""
            />
            <img
              className="verifiedtick"
              src="../images/verifiedthick.svg"
              alt=""
            />
            <img className="cardicon" src="../images/cardicon.svg" alt="" />
            <img className="polygon" src="../images/polygon.svg" alt="" />
          </div>
          <div className="section1-c">
            <img src="../images/img.svg" alt="" />
          </div>
          <div className="section1-d">
            <p>Payment Successful</p>
            <img
              className="paymentTick"
              src="../images/paymentTick.svg"
              alt=""
            />
          </div>
        </section>
      </div>
    </div>
  );
};

export default Section1;
