import React from "react";
import "./OneItemOrder.css";

const OneItemOrder = ({ itemData }) => {
  return (
    <div className="OneItemOrder-row OneItemOrder-item-container">
      <div className="OneItemOrder-cal-split">
        <img src={itemData.product.image} className="OneItemOrder-image600" alt="pic" />
      </div>
      <div>
        <div className="OneItemOrder-method-title OneItemOrder-cal-split">
          {itemData.product.productName}
        </div>
        <div className="OneItemOrder-method-title-info OneItemOrder-cal-split">
          Quantity: {itemData.quantity}
        </div>
        <div className="OneItemOrder-method-amount OneItemOrder-cal-split">
          $ {itemData.product.price} USD
        </div>
      </div>
    </div>
  );
};

export default OneItemOrder;
