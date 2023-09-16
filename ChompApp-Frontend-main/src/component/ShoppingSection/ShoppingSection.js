import React from 'react'
import CurrencyFormat from 'react-currency-format';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { Role } from '../../models/Role';

import "./ShoppingSection.css";

const ShoppingSection = ({
  img,
  title,
  price,
  cart,
  productId,
  key,
  productDescription
}) => {

  const truncate = (string, n) => {
    return string?.length > n ? string.substr(0, n - 1) + "..." : string;
  }

  const currentUser = useSelector((state) => state.user);

  return (
    <div className="menuCard">
      <div className="menuCard__container" key={key}>
        <img src={img} alt="burger" />
        <div className="menuCard__left">
          <div className="menuCard__left-header">
            <h6>{title}</h6>
              <CurrencyFormat
                renderText={(value) => <p>{value}</p>}
                decimalScale={2}
                value={price}
                displayType={"text"}
                thousandSeparator={true}
                prefix={"₦"}
              />
          </div>
          <p>
            {truncate(productDescription, 80)}
          </p>
          <div className="menuItem__left-bottom">
            {currentUser?.roles[0].name === Role.ROLE_PREMIUM && (

            <button
              className="btn btn-success"
              onClick={() => cart(cart, productId)}
            >
              Add to cart
            </button>
            )}
            <Link to={`/singleProduct/${productId}`}>View</Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ShoppingSection;