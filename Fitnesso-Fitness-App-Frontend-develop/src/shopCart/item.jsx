import React from 'react'
import Button from '@material-ui/core/Button'

//Styles
import { Wrapper } from './Item.style'

const Item = ({ item, handleAddToCart }) => (
 <Wrapper>
   <img src={item.image} alt={item.title} />
   <div>
     <h3 className="prod_title">{item.productName}</h3>
     <h3 className="prod_category">Category: {item.category}</h3>
     <h4>Description: </h4>
     <p className="prod_desc">{item.description}</p>
     <h3 className="prod_price">${item.price}</h3>
   <Button onClick={()=> handleAddToCart(item)} className="prod_btn">Add to cart</Button>
   </div>
 </Wrapper>
)

export default Item
