import React, { useState, useRef, useEffect } from "react";
import cartItemStyle from "../stylesheets/cartItem.module.css";
import productImage from "../icons/image1.png";
import "../styles/numberInput.css";
import currencyFormatter from "../../src/currencyFormerter";

export default function CartItem({productData}) {
  const [currentPrice, setCurrentPrice] = useState(productData.price);
  const currentQuantity = useRef(productData.quantity);
  useEffect(() => {
    setCurrentPrice(
      (currentPrice) => currentPrice * currentQuantity.current.value
    );
  }, []);
  const updateSubtotal = (e) => {
    setCurrentPrice(
      (currentPrice) => currentPrice * currentQuantity.current.value
    );
  };
  return (
    <>
      <div className={cartItemStyle.container}>
        <div className={cartItemStyle.content}>
          <img
            src={productData.product.imagesList[0].imageURl}
            alt=""
            className={cartItemStyle.productImage}
            style={{width:'100px', height:'100px'}}
          />
          <div className={cartItemStyle.mid}>
            <div className={cartItemStyle.productDetails}>
              <h6>{productData.product.productName}</h6>
              <small>{productData.product.size}</small>
            </div>

            <div className={cartItemStyle.moveToFavorites}>
              <i class="far fa-heart"></i>
              MOVE TO FAVOURITES
            </div>
          </div>
          <div className={cartItemStyle.remove}>
            <i class="fas fa-times-circle"></i>
            REMOVE
          </div>
        </div>
        <div className={cartItemStyle.quantity}>
          <input
            type="number"
            defaultValue={productData.quantity}
            min="1"
            max="1000"
            ref={currentQuantity}
            className={cartItemStyle.quantityInput}
            onKeyUp={updateSubtotal}
          />
        </div>
        <div className={cartItemStyle.price}>
          {currencyFormatter.format(productData.price)}
        </div>
        <div className={cartItemStyle.subTotal}>
          {currencyFormatter.format(currentPrice)}
        </div>
      </div>
    </>
  );
}
