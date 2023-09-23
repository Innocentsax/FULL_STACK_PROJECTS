import React, {createContext} from 'react'
import { useNavigate } from 'react-router-dom'

import CartItem from '../CartItem/CartItem'
//styles
import { Wrapper } from './Cart.styles'
//Types
import { CartItemType } from '../ShopApp'

import {useContext} from "react";
import CartContext from "../../context/cart-context";

// const CartInfoContext = createContext()


const Cart = ({ cartItems, addToCart, removeFromCart }) => {

 const cartCxt = useContext(CartContext);

  const calculateTotal = (items) => items.reduce((ack, item) => ack + item.amount * item.price, 0)
  const navigate = useNavigate();

  var myMap = new Map();
  cartItems.map((item) => (
    myMap.set(item.id, item.amount)
  ));

  let obj = {};
  myMap.forEach(function(value, key){
    obj[key] = value;
  })

  const addToCartFunction = async () => {
    console.log(obj, "map object");
    console.log(localStorage.getItem("token"), "token here");
      const res = await fetch("https://fitnesso-app-new.herokuapp.com/carts/add", {
        method: "POST",
        headers: {"Content-type": "application/json",
      "Authorization" : "Bearer " + localStorage.getItem("token")},
      body: JSON.stringify(obj)
      });

      const data = await res.json();
      console.log("Response:: ",res);

      console.log(data);
      cartCxt.items = data.cartData;
      if(cartCxt.items.length === 0){
        alert("Cart list returned Empty");
      }
      else{
        navigate("/checkout");
      }
  }
  
 return (
  <Wrapper>
   <h2>Your Shopping Cart</h2>
   {cartItems.length === 0 ? <p>No items in cart</p> : null}
   {cartItems.map(item => (<CartItem key={item.id} item={item} addToCart={addToCart} removeFromCart={removeFromCart} />))}
   <h2>Total: ${calculateTotal(cartItems).toFixed(2)}</h2>
   {console.log(cartItems, 'cart item')}
   {cartItems.length>0 && <button onClick = {addToCartFunction} className="prod_cart_btn">CHECK OUT</button>}
  </Wrapper>
  
 )
}

export default Cart