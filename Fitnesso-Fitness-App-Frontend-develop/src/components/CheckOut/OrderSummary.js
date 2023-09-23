import React from 'react';
import "./OrderSummary.css";
import CartProvider from "../../context/CartProvider"
import { useNavigate } from "react-router-dom";
import {useContext} from "react";
import CartContext from "../../context/cart-context";

const OrderSummary = ({summaryOrder}) => {
  const cartList = localStorage.getItem("cartList");
  return (
    <div>
        <div className="OrderSummary-container">
        <div className="OrderSummary-row OrderSummary-split OrderSummary-title-content">
          <div className="OrderSummary-design OrderSummary-heading">Order Summary</div>
        </div>
        <div className="OrderSummary-row OrderSummary-cal-split">
          <div className="OrderSummary-method-title-info">Subtotal</div>
          <div className="OrderSummary-method-amount">$ {summaryOrder.subTotal} USD</div>
        </div>
        <div className="OrderSummary-row OrderSummary-cal-split">
          <div className="OrderSummary-method-title-info">Flat Rate</div>
          <div className="OrderSummary-method-amount-flat">$ {summaryOrder.flatRate} USD</div>
        </div>
        <div className="OrderSummary-row OrderSummary-cal-split">
          <div className="OrderSummary-method-title-info">Total</div>
          <div className="OrderSummary-method-amount">$ {summaryOrder.total} USD</div>
        </div>
      </div>
    </div>
  )
}

export default OrderSummary;