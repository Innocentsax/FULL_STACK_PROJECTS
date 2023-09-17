import React from 'react'
import '../styles/product.css'
import { useState } from 'react';
import {Card} from 'react-bootstrap';
// import products from '../products';

const Product = ({data, cart, favourite}) => {
  const [isAddedToCart, setIsAddedToCart] = useState(false)
  const [isAddedToFavourite, setIsAddedToFavourite] = useState(false)

  // const [cart, setCart] = useState([])

  function handleCartItems() {
    setIsAddedToCart(!isAddedToCart)
    cart()
  }

  function handleFavouriteItems() {
      setIsAddedToFavourite(!isAddedToFavourite)
      favourite()
  }

  // function addToCart(item) {
  //   let newCart = cart;
  //   newCart.push(item);
  //   setCart(newCart)
  //   console.log(cart)
  // }
    return (
        <Card className='my-3 p-3 rounded' >
          <a href={`/product/${data.id}`}>
              <Card.Img src={data.imagesList[0].imageURl} variant ='top' style={{height: '450px'}}/>  
          </a>
          <Card.Body >
          </Card.Body>
          <Card.Title className='prodCenter mx-3'>
          <a href={`/product/${data.id}`}>
              <strong>{data.productName}</strong>
          </a>
              <strong className=' my-3 p-3'><br/>&#8358;{data.price}</strong>
              
              <div className ='p-3 fav-cart'>
                <span className="btn-favorite m-3" onClick={handleFavouriteItems}><i className="far fa-heart fa-lg"></i></span>
                <button className="btn btn-outline-danger btn-sm" onClick={handleCartItems}>ADD TO CART<i className="fas fa-shopping-cart"></i></button>
              </div>              
          </Card.Title>    
      </Card>
    )
}

export default Product
