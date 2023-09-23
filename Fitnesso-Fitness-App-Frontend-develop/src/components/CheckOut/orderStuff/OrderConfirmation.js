import React from "react";
import "./OrderConfirmation.css";

const OrderConfirmation = ({ allData, issuer }) => {
  console.log(issuer);
  return (
    <div>
      <div className="OrderConfirmation-container">
        <div className="OrderConfirmation-row OrderConfirmation-split OrderConfirmation-title-content">
          <div className="OrderConfirmation-design OrderConfirmation-heading">
            Customer Information
          </div>
        </div>
        <div className="OrderConfirmation-row OrderConfirmation-design OrderConfirmation-body">
          <div className="OrderConfirmation-infos-list">
            <div className="OrderConfirmation-header-title">Email</div>
            <div>{allData.email}</div>
          </div>
          <div className="OrderConfirmation-infos-list">
            <div className="OrderConfirmation-header-title">
              Shipping Address
            </div>
            <div className="OrderConfirmation-item-list">{allData.shippingAddress.userName}</div>
            <div className="OrderConfirmation-item-list">{allData.shippingAddress.streetDetail}</div>
            <div className="OrderConfirmation-item-list">
            {allData.shippingAddress.city}{" "}{allData.shippingAddress.state}{" "}{allData.shippingAddress.zipCode}
            </div>
            <div className="OrderConfirmation-item-list">{allData.shippingAddress.country}</div>
          </div>
        </div>
      </div>

      <div className="OrderConfirmation-container-below">
        <div className="OrderConfirmation-row OrderConfirmation-split OrderConfirmation-title-content">
          <div className="OrderConfirmation-design OrderConfirmation-heading">
            Shipping Method
          </div>
        </div>
        <div className="OrderConfirmation-row OrderConfirmation-design OrderConfirmation-body">
          <div>
            <div className="OrderConfirmation-item-list">{allData.shippingMethod}</div>
            <div className="OrderConfirmation-item-list">
              Standard flat rate for all shipment
            </div>
          </div>
        </div>
      </div>

      <div className="OrderConfirmation-container-below">
        <div className="OrderConfirmation-row OrderConfirmation-split OrderConfirmation-title-content">
          <div className="OrderConfirmation-design OrderConfirmation-heading">
            Payment Information
          </div>
        </div>
        <div className="OrderConfirmation-row OrderConfirmation-design OrderConfirmation-body">
          <div className="OrderConfirmation-infos-list">
            <div className="OrderConfirmation-header-title">PaymentInfo</div>
            <div className="OrderConfirmation-item-list">{issuer} {allData.paymentRequest.cardNumber.substring(0, 4)}</div>
            <div className="OrderConfirmation-item-list">{allData.paymentRequest.expiringDate.substring(0, 2)}/{allData.paymentRequest.expiringDate.substring(2, 4)} </div>
          </div>
          <div className="OrderConfirmation-infos-list">
            <div className="OrderConfirmation-header-title">
              Billing Address
            </div>
            <div className="OrderConfirmation-item-list">{allData.billingAddress.userName.toLowerCase()}</div>
            <div className="OrderConfirmation-item-list">{allData.billingAddress.streetDetail.toLowerCase()}</div>
            <div className="OrderConfirmation-item-list">
            {allData.billingAddress.city}{" "}{allData.billingAddress.state}{" "}{allData.billingAddress.zipCode}
            </div>
            <div className="OrderConfirmation-item-list">{allData.billingAddress.country}</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default OrderConfirmation;
