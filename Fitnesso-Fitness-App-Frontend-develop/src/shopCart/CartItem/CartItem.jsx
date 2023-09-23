import React from 'react'
import Button from '@material-ui/core/Button'

//styles
import { Wrapper } from './CartItem.styles'

//cartItem functionality
const CartItem = ({ item, addToCart, removeFromCart }) => (
 <Wrapper>
  <div>
    <h3>{item.title}</h3>
    <div className ='information'>
       <p>Price: ${item.price}</p>
       <p>Total: ${(item.amount * item.price).toFixed(2)}</p>
    </div>
    <div className ='buttons'>
      <Button size='small' disableElevation variant='contained' onClick={() => removeFromCart(item.id)}> - </Button>
      <p>{item.amount}</p>
      <Button size = 'small' variant ='contained' onClick={() => addToCart(item)}> + </Button>
   </div>
  </div>
  <img src={item.image} alt={item.title} className="cartImg"/>
</Wrapper> 
)

export default CartItem