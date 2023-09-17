import axios from 'axios'
import React, { useState, useEffect } from 'react'
import './Clothes.css'
import Pagination from './Pagination'
import Product from '../components/Product'
import { useCookies } from 'react-cookie';

export default function Accessories() {
  const [cookies, setCookies] = useCookies()
  const [accessories, setAccessories] = useState([])
    const [cartItems, setCartItems] = useState([])
    const [favouriteItems, setFavouriteItems] = useState([])
    const accessoriesList = accessories.filter(item => item.category.productCategoryName === 'accessories')

    useEffect(() => {
        axios.get("http://localhost:8080/api/products/?pageNo=0&pageSize=30").then(res => {
        setAccessories(res.data)
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
        axios.post(`http://localhost:8080/api/products/add_favorite/` + item.id, {headers: {"Authorization": `Bearer ` + cookies.token}}).then(res => {
            console.log(res.data)
        })
    }

    function handleSort (e) {
        setAccessories(accessoriesList.filter(item => item.subCategory.subCategoryName === e.target.value.toLowerCase()))
    }

    // function handleSizeSort (e) {
    //     setClothes(clothList.filter(item => item.size === e.target.value))
    // }

    function defaultProducts () {
        setAccessories(accessoriesList);
    }

    const subCategories = ["Facemasks", "Jewelry", "Watches", "Hair accessories", "Belts", "Backpacks",
    "Handbags", "Frangrances", "Sunglasses & eyewears", "Socks", "Hats and beanies", "Gloves"]
    const listCategories = subCategories.map((item, index) => 
    
    <button className="hoverStyle" key={index} value={item} onClick={handleSort}>{item}</button>)

    const clotheSizes= ["XS", "S", "S/M", "M", "M/L", "L", "XL", "6", "7", "8"]
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
              <h1 className="h1Title">ACCESSORIES</h1>
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
                        data={accessoriesList}
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
