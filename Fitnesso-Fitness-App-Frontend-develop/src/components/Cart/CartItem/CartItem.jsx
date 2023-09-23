import Button from '@material-ui/core/Button'
import React from 'react'


//styles
import {Wrapper} from './CartItem.styles'



const CartItem = ({ item, addToCart, removeFromCart }) => (
 <Wrapper>
  <div>
    <h3>{item.title}</h3>
    <div className ='information'>
       <p>Price: ${item.price}</p>
       <p>Total: ${(item.amount * item.price).toFixed(2)}</p>
    </div>
    <div className ='cart_buttons'>
      <Button size='small' disableElevation variant='contained' onClick={() => removeFromCart(item.id)}> - </Button>
      <p>{item.amount}</p>
      <Button size = 'small' variant ='contained' onClick={() => addToCart(item)}> + </Button>
   </div>
  </div>
  <img className='cart_img' src={item.image} alt={item.title} />
 </Wrapper>
)

export default CartItem