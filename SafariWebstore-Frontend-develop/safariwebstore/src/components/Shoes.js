import axios from 'axios'
import React, { useState, useEffect } from 'react'
import './Clothes.css'
import Pagination from './Pagination'
import Product from '../components/Product'
// import { useCookies } from 'react-cookie';

export default function Shoes() {
    const [shoes, setShoes] = useState([])
    const [cartItems, setCartItems] = useState([])
    const [favouriteItems, setFavouriteItems] = useState([])
    const shoeList = shoes.filter(item => item.category.productCategoryName === 'shoe')

    useEffect(() => {
        axios.get("http://localhost:8080/api/products/?pageNo=0&pageSize=30").then(res => {
        setShoes(res.data)
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
        axios.get(`http://localhost:8080/api/products/add_to_cart/` + item.id, {headers: {"Authorization": `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsdWNreWphbmU2MEBnbWFpbC5jb20iLCJleHAiOjE2NTc1MzE4NjEsImlhdCI6MTYzNTkzMTg2MX0.nTVz30i8dUrROz7dzUchaKfBxWJtxcrECIMPyJzaQsYKlmqDqKocv_mW_F0c_79C-5Pcs9zQexLM-QnzjfS8Yw`}}).then(res => {
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
        axios.post(`http://localhost:8080/api/products/add_favorite/` + item.id, {headers: {"Authorization": `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsdWNreWphbmU2MEBnbWFpbC5jb20iLCJleHAiOjE2NTc1MzQ5NTgsImlhdCI6MTYzNTkzNDk1OH0.d2jjztPoeB5Vt97pwUKmwHTlXz7EHa7abOQ8fXbYMOtWVB_6Neym6zNJSjFmF1hkVodHNJNfS5AR5P_8Zi2uVQ`}}).then(res => {
            console.log(res.data)
        })
    }

    function handleSort (e) {
        setShoes(shoeList.filter(item => item.subCategory.subCategoryName === e.target.value.toLowerCase()))
    }

    // function handleSizeSort (e) {
    //     setClothes(clothList.filter(item => item.size === e.target.value))
    // }

    function defaultProducts () {
        setShoes(shoeList);
    }

    const subCategories = ["Booties", "Flats", "Boots", "Sandals", "Sneakers", "Slides and Slippers",
    "Heels", "Wedges", "Mules", "Party Shoes", "Vegan Shoes", "Oxford"]
    const listCategories = subCategories.map((item, index) => 
    
    <button className="hoverStyle" key={index} value={item} onClick={handleSort}>{item}</button>)

    const clotheSizes= ["35.5/5", "36/5.5", "37.5/6.5", "38/7", "39/7.5", "39.5/8", "40/7.5", "41/9.5", "41.5/10", "42/10.5", "42/11", "43/12"]
    const listSizes = clotheSizes.map((item, index)=> <button className="hoverSize" key={index} value={item} >{item}</button>)

    const colors = ["Beige", "Blue", "Black", "Orange", "Green", "Brown", "Purple", "Gold", "Taupe", "White","Pink", "Red"]
    const colorList = colors.map(color => <div style={{display:'flex'}}><div className="colorStyle" style={{backgroundColor: color, marginRight:'10px'}}/> {color}</div>)

    const price = ["0 - 10,000", "10,000 - 20,000", "20,000 - 50,000", "50,000 - 100,000", "100,000 - 200,000"]
    const priceList = price.map(item => <div className='prices'><input type="checkbox" value={item} className='checkBox'/>
    <label style={{marginLeft:"10px"}}>&#x20A6;{item}</label></div>)
    const options = ["", "Most popular", "Most sexy", "Latest"]
    let option = ""
    const optionList = options.map(option => <option value={option}>{option}</option>)
    

    return (
        <>
          <div className="header">
              <h1 className="h1Title">SHOES</h1>
              <div className='optionDiv'>
                <div className="category">CATEGORY</div>
                <div className='sortDiv'>
                    <label style={{marginLeft: '20px'}}>Sort by: {option}</label>
                    <select style={{width: 'auto'}}>
                        {optionList}
                    </select>
                </div>
              </div>
          </div>
          <br/>
          <div className="clothContainer">
              <div className="sideMenu">
                <hr />
                <div className='categoryStyle'>
                    <button className="hoverStyle" onClick={defaultProducts}>All</button>
                    {listCategories}
                </div>
                <br />
                <div className='optionDiv'>
                    <div>SIZE</div>
                    <div className='clear'>Clear </div><span className='cancel'>&#x2715;</span>
                </div>
                <hr />
                <div className="clotheSize" style={sizeStyle}>
                    {listSizes}
                </div>
                <br /><br />
                <div className='optionDiv'>
                <div>COLOR</div>
                    <div className='clear'>Clear</div><span className='cancel'>&#x2715;</span>
                </div>
                <hr />
                <div className="colorDivStyle" style={colorStyle}>
                    {colorList}
                </div>
                <br /><br />
                <div className='optionDiv'>
                    <div>PRICE</div>
                    <div className='clear'>Clear </div><span className='cancel'>&#x2715;</span>
                </div>
                <hr />
                <div className='priceStyle'>
                    {priceList}
                    <div>
                        <input type="text" placeholder="&#x20A6;" className="price"/>
                        <label style={{margin: '10px'}}>to</label>
                        <input type="text" placeholder="&#x20A6;" className="price"/>
                        <button className='apply'>Apply</button>
                    </div>
                </div>
                

            </div>
                
            <div style={{marginLeft: "49px", width:'990px'}} className="productContainer">
            <hr />
                
                    <Pagination
                        data={shoeList}
                        RenderComponent={Product}
                        pageLimit={5}
                        dataLimit={16}
                        cart={addToCart}
                        favourite={addToFavourite}
                    />
                
                
            </div>

            </div>
        </>
    )
}


const sizeStyle = {
    fontFamily: "Mulish",
    fontStyle: "normal",
    fontWeight: "600",
    fontSize: "14px",
    lineHeight: "18px",
    
}
const colorStyle = {
    fontFamily: "Mulish",
    fontStyle: "normal",
    fontWeight: "normal",
    fontSize: "14px",
    lineHeight: "18px"
}

