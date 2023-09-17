import React, { useState, useEffect } from "react";
import CartItem from "./CartItem";
import cartStyle from "../stylesheets/cart.module.css";
import axios from "axios";
import { useCookies } from "react-cookie";
import navbar from "../stylesheets/navbar.module.css";
import { Link } from "react-router-dom";

export default function Cart() {
  const [cookies, setCookies] = useCookies()
  const [cartItems, setCartItems] = useState([])

  useEffect(() => {
    axios.get(`http://localhost:8080/api/products/viewCartItems`, {headers: {"Authorization": `Bearer ` + cookies.token}})
    .then(res => {
      setCartItems(res.data.cartItemList)
    })
    
  }, [])
  
  
  return (
    <>
      <div className={cartStyle.container}>
        <div className={cartStyle.row}>
          <h6>Shopping Cart ({cartItems.length} Items)</h6>
        </div>
        <div className={cartStyle.row}>
          <div className={cartStyle.content}>ITEM DESCRIPTION</div>
          <div className={cartStyle.quantity}>QUANTITY</div>
          <div className={cartStyle.price}>UNIT PRICE </div>
          <div className={cartStyle.subTotal}>SUB TOTAL</div>
        </div>
        {cartItems.map((product, key) => (
          <CartItem key={key} productData={product} />
        ))}

        <div className={cartStyle.row}>
          <h6>
            TOTAL PRICE: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#x20A6;120,000
          </h6>
          <small>Delivery fee not included yet</small>
        </div>
        <div className={cartStyle.row}>
          <Link className={navbar.link} to="/">
          <button className={cartStyle.shopping}>CONTINUE SHOPPING</button>
          </Link>
          <Link className={navbar.link} to="/cart/checkout">
            <button className={cartStyle.checkout}>CONTINUE TO CHECKOUT</button>
          </Link>
        </div>
      </div>
    </>
  );
}
