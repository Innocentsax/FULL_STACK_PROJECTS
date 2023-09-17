import React from "react";
import paymentmethod from "../styles/paymentmethod.module.css";

export default function PaymentMethod() {
  return (
    <>
      <div className={paymentmethod.container}>
        <div className={paymentmethod.topRow}>
          <input
            type="checkbox"
            id="setDefault"
            className={paymentmethod.input}
          />
          <h6>Payment Methods</h6>
        </div>

        <div className={paymentmethod.midRow}>
          <div className={paymentmethod.midRowHeader}>
            <input
              type="radio"
              id="setDefault"
              className={paymentmethod.input}
            />

            <h6>Pay with card</h6>
          </div>
          <h6>
            (Get 5% off total price and money back guarantee)
          </h6>
        </div>

        <small>You will be redirected to Paystack payment gateway</small>

        <div className={paymentmethod.bottomRow}>

          <input
            type="radio"
            id="setDefault"
            className={paymentmethod.input}
          />
          <h6>Pay on delivery</h6>


        </div>
        <ul className={paymentmethod.list}>
          <li>
            Kindly note that we will only accept POS payment on delivery
          </li>
          <li>You have to make payment before opening package </li>
          <li>
            Once the seal is broken, item can only be returned if damaged or
            defective
          </li>
        </ul>
      </div>
    </>
  );
}
