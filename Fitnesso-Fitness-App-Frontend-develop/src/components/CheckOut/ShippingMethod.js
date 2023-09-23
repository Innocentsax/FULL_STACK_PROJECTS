import React from "react";
import "./ShippingMethod.css";

const ShippingMethod = ({setShipMethod}) => {

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setShipMethod({ [name]: value });
      }
  return (
    <div>
      <div className="ShippingMethod-container">
        <div className="ShippingMethod-row ShippingMethod-split ShippingMethod-title-content">
          <div className="ShippingMethod-design ShippingMethod-heading">Shipping Method</div>
          <div className="ShippingMethod-design">*Required</div>
        </div>
        <div className="ShippingMethod-method-container ShippingMethod-title-content ShippingMethod-row">
          <div>
            <input type="radio" name="shipWay" value={"FLAT_RATE"} onChange={handleInputChange} />
          </div>
          <div className="ShippingMethod-method-dep">
            <div className="ShippingMethod-method-title">Flat rate</div>
            <div className="ShippingMethod-method-title-info">Standard flat rate for all shipments</div>
          </div>
          <div className="ShippingMethod-method-amount">$ 50 USD</div>
        </div>
        <div className="ShippingMethod-method-container ShippingMethod-title-content ShippingMethod-row">
          <div>
            <input type="radio" name="shipWay" value={"EXPEDITED_SHIPPING"} onChange={handleInputChange} />
          </div>
          <div className="ShippingMethod-method-dep">
            <div className="ShippingMethod-method-title">Expedited Shipping </div>
            <div className="ShippingMethod-method-title-info">Expedited shipping to get the shipment in a day or two</div>
          </div>
          <div className="ShippingMethod-method-amount">$ 90 USD</div>
        </div>
        <div className="ShippingMethod-method-container ShippingMethod-title-content ShippingMethod-row">
          <div>
            <input type="radio" name="shipWay" value={"OVERNIGHT_SHIPPING"} onChange={handleInputChange}/>
          </div>
          <div className="ShippingMethod-method-dep">
            <div className="ShippingMethod-method-title">Overnight Shipping</div>
            <div className="ShippingMethod-method-title-info">An expensive option to get the shipment on the next business day</div>
          </div>
          <div className="ShippingMethod-method-amount">$ 120 USD</div>
        </div>
      </div>
    </div>
  );
};

export default ShippingMethod;
