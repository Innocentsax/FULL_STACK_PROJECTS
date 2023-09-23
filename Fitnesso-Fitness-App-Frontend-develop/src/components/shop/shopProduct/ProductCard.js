import React, { useEffect, useState } from "react";
import axios from "axios"
import ProductModel from "./ProductModel";
import { GrInstagram } from "react-icons/gr";
import { FiTwitter } from "react-icons/fi";
import { GrLinkedin } from "react-icons/gr";
import { FaYoutubeSquare } from "react-icons/fa";
// import React, { Component } from 'react'

import "./Low.css"


const ProductCard = () => {
  const [products, setProducts] = useState([])
  useEffect(() => {
    const getProducts = async () => {
      try {
        const response = await axios.get('https://fitnesso-app-new.herokuapp.com/product/viewproducts')
        const data = response.data
        setProducts(data)
      } catch (error) {
        if (error.response) {
          console.log(error.response.data)
        } else {
          console.log(error.message)
        }
      }
    }
    getProducts()
  }, [])
  console.log(products)
  return (
     <section className="prod">
    
      <div className="productSection">
        {products.slice(0,3).map((item) => (
          <div className="productContainer" key={item.id}>
            <ProductModel
              backgroundImage={item.image}
              time={item.duration_in_days}
              cost={item.price}
              product={item.productName}
            />
          </div>
        ))}
      </div>
      <div className="bg3">
        <div className="parentForm">
          <div className="writeUp">
            <h1 className="title2">Get Our Free Meal Plan</h1>
            <p className="paragraph">
              Signup to our newsletter to get a free 30 day meal plan. You will
              receive all guidance on what to eat, how to cook and how much to
              eat.
            </p>
          </div>
          <div className="form">
            <input type="text" placeholder="Full Name" />
            <input type="text" placeholder="Email" />
            <button className="button">SignUp</button>
          </div>
        </div>
      </div>
      <div className="bg4">
        <div className="pinky">
          <div className="iconContainer">
            
                  <div className="icon">
                  <FiTwitter className="icn"style={{ color: "#FFF" }} />
                  <p style={{ color: "#FFF" }}>@fitnesso</p>
                </div>
                  <div className="icon1">
                  <GrInstagram className="icn1"style={{ color: "#FFF" }} />
                  <p style={{ color: "#FFF" }}>@fitnesso</p>
                </div>
                   <div className="icon2">
                   <GrLinkedin className="icn2"style={{ color: "#FFF" }} />
                   <p style={{ color: "#FFF" }}>Fitnesso</p>
                 </div>
                   <div className="icon3">
                   <FaYoutubeSquare className="icn3"style={{ color: "#FFF" }} />
                   <p style={{ color: "#FFF" }}>/Fitnesso</p>
                 </div>
            
          </div>
        </div>
      </div>
   </section>
  );
};

export default ProductCard;
