import React from 'react';
import "./DiscountCoupon.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGift } from '@fortawesome/free-solid-svg-icons'

const DiscountCoupon = ({setDiscount, discount}) => {
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setDiscount({ [name]: value });
      }
  return (
    <div>
        <div className="DiscountCoupon-container">
        <div className="DiscountCoupon-info-input DiscountCoupon-title-content DiscountCoupon-input-container">
          <label>Discount Code </label>
          <div className="DiscountCoupon-row">
          <input type="text" name="couponCode" value={discount.couponCode || ''}  onChange={handleInputChange} /><button><FontAwesomeIcon icon={faGift} /> APPLY</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default DiscountCoupon;