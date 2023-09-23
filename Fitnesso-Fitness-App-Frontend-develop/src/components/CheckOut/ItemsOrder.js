import React,{useEffect} from 'react';
import "./ItemsOrder.css"
import OneItemOrder from './OneItemOrder';
import CartProvider from "../../context/CartProvider"
import { useNavigate } from "react-router-dom";
import {useContext} from "react";
import CartContext from "../../context/cart-context";

const ItemsOrder = () => {

  const cartCxt = useContext(CartContext);

  const cartList = localStorage.getItem("cartList");
  // console.log(cartList.cartList.id);
  const loginState = localStorage.getItem("token");
  const cartState = localStorage.getItem("cartList");
    const navigate = useNavigate();


    console.log("CartCxt: ", cartCxt.items)

  
  return (
    <CartProvider>
      <div className="ItemsOrder-container">
        <div className="ItemsOrder-row ItemsOrder-split ItemsOrder-title-content">
          <div className="ItemsOrder-design ItemsOrder-heading">Items in Order</div>
        </div>
        {cartCxt.items.map((item, i) => (
          <OneItemOrder key={item.product.id} itemData={item}/>
        ))}
      </div>
    </CartProvider>
  )
}

export default ItemsOrder;