import CartItem from '../CartItem/CartItem'
import React from 'react'
//styles
import { Wrapper } from './CartToggle.styles'






const CartToggle = ({ cartItems, addToCart, removeFromCart }) => {
  const calculateTotal = (items) => items.reduce((ack, item) => ack + item.amount * item.price, 0)
 return (
  <Wrapper>
   <h2>Your Shopping Cart</h2>
   {cartItems.length === 0 ? <p>No items in cart</p> : null}
   {cartItems.map(item => (<CartItem key={item.id} item={item} addToCart={addToCart} removeFromCart={removeFromCart} />))}
   <h2>Total: ${calculateTotal(cartItems).toFixed(2)}</h2>
  </Wrapper>
  
 )
}

export default CartToggle