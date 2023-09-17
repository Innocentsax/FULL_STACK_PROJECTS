import React, { Component,useRef, useState, useEffect } from 'react';
import ReactDOM from 'react-dom';
import { Container,Grid } from '@mui/material';
import Product from '../components/Product'
import Slider from './Slider';
import products from '../products';
import home from '../stylesheets/home.module.css';
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import axios from "axios"
import Pagination from './Pagination'
import { useCookies } from 'react-cookie';

const Item = styled(Paper)(({ theme }) => ({
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));
export default function Home() {
  const [cookies, setCookies] = useCookies()
  const [productList, setProductList] = useState([])
  const [cartItems, setCartItems] = useState([])
  const [favouriteItems, setFavouriteItems] = useState([])

  useEffect(() => {
    axios.get("http://localhost:8080/api/products/?pageNo=0&pageSize=30").then(res => {
    setProductList(res.data)
  })
      
  }, [])

  function addToCart(item) {
    let newCart = cartItems;
    if (newCart.includes(item)) {
        for (let i = 0; i < newCart.length; i++) {
            if(newCart[i] === item) {
                newCart.splice(i, 1)
            }
        }
        setCartItems(newCart)
    } else {
        newCart.push(item)
        setCartItems(newCart)
    }
    console.log(cartItems)
    axios.get(`http://localhost:8080/api/products/add_to_cart/` + item.id, {headers: {"Authorization": `Bearer ` + cookies.token}}).then(res => {
        console.log(res.data)
    })
}

function addToFavourite(item) {
    let newFavourite = favouriteItems;
    if(newFavourite.includes(item)) {
        for(let i =0; i < newFavourite.length; i++) {
            if(newFavourite[i] === item) {
                newFavourite.splice(i, 1)
            }
        }
        setFavouriteItems(newFavourite)
    } 
    else {
        newFavourite.push(item)
        setFavouriteItems(newFavourite)
    }
    console.log(favouriteItems)
    axios.get(`http://localhost:8080/api/products/add_favorite/` + item.id, {headers: {"Authorization": `Bearer ` + cookies.token}}).then(res => {
        console.log(res.data)
    })
}

  return (
  
<div className={home.container}>
  <Slider />

<div className = 'my-3 p-3 shop-yourstyle' id='shopYourStyle'>Shop your style</div>
<p className='about-products container'> do not mass-produce any of our range, but operate a batch 
    process system. This means that all our items are made with that bit more care and 
    love - and you can see that in the finished item!</p>
    <Grid container spacing={2}>
  <Grid item xs={6} md={8}>
    {/* <Item>xs=6 md=8</Item> */}
  </Grid>
  </Grid>
{/* <Container fluid > */}
<Grid container spacing={2} >

  <Grid item container spacing={3} alignItems="center"
  justifyContent="center">

    
    <Pagination
        data={productList}
        RenderComponent={Product}
        pageLimit={5}
        dataLimit={15}
        cart={addToCart}
        favourite={addToFavourite}
    />
</Grid>
  </Grid>
</div>
  
  );

}
