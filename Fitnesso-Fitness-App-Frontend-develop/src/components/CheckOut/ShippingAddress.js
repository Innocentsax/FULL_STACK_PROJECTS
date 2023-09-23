import React, {useState} from "react";
import "./ShippingAddress.css";

const ShippingAddress = ({addressName, setShipping, shipping, onChange }) => {
    
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setShipping({
            ...shipping,
            [name]: value
          });
      }
  return (
    <div>
      <div className="ShippingAddress-container">
        <div className="ShippingAddress-row ShippingAddress-split ShippingAddress-title-content">
          <div className="ShippingAddress-design ShippingAddress-heading">{addressName}</div>
          <div className="ShippingAddress-design">*Required</div>
        </div>
        <div className="ShippingAddress-info-input ShippingAddress-title-content ShippingAddress-input-container">
          <label>Full Name* </label>
          <input className="ShippingAddress-big-input" value={shipping.userName || ''}  onChange={handleInputChange} type="text" name="userName"/>
        </div>
        <div className="ShippingAddress-info-input ShippingAddress-title-content ShippingAddress-input-container">
          <label>Street Address* </label>
          <input className="ShippingAddress-big-input" value={shipping.streetDetail || ''} onChange={handleInputChange} type="text" name="streetDetail" />
        </div>
        <div className="ShippingAddress-row ShippingAddress-all-small">
          <div className="ShippingAddress-info-input ShippingAddress-title-content ShippingAddress-input-container">
            <label>City* </label>
            <input className="ShippingAddress-small-input" value={shipping.city || ''}  onChange={handleInputChange} type="text" name="city" />
          </div>
          <div className="ShippingAddress-info-input ShippingAddress-title-content ShippingAddress-input-container">
            <label>State/province* </label>
            <input className="ShippingAddress-small-input" value={shipping.state || ''} onChange={handleInputChange} type="text" name="state" />
          </div>
          <div className="ShippingAddress-info-input ShippingAddress-title-content ShippingAddress-input-container">
            <label>Zip/Postal Code* </label>
            <input className="ShippingAddress-small-input" value={shipping.zipCode || ''} onChange={handleInputChange} type="text" name="zipCode" />
          </div>
        </div>
        <div className="ShippingAddress-info-input ShippingAddress-title-content ShippingAddress-input-container">
          <label>Country* </label>
          <input className="ShippingAddress-big-input" value={shipping.country || ''} onChange={handleInputChange} type="text" name="country" />
        </div>
      </div>
    </div>
  );
};

export default ShippingAddress;
