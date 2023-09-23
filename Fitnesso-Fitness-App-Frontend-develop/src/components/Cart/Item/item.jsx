import Button from '@material-ui/core/Button'
import React from 'react'

//Styles
import { Wrapper } from './Item.styles'

const Item = ({ item, handleAddToCart }) => (
 <Wrapper>
   <img src={item.image} alt={item.productName} />
   <div>
     <h3>{item.productName}</h3>
     <p>{item.description}</p>
     <h3>${item.price}</h3>
   </div>
   <Button onClick={()=> handleAddToCart(item)}>Add to cart</Button>
 </Wrapper>
)

export default Item
