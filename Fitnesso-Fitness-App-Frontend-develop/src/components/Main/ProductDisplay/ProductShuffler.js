import React, { useState, useEffect } from "react";
import axios from "axios";
import "./shuffle.css";
import { Card } from "@mui/material";
import fisherShuffle from "./Shuffler";

function ProductShuffler() {
  const [loading, setLoading] = useState(false);
  const [products, setProducts] = useState([]);
  useEffect(() => {
    const loadPosts = async () => {
      setLoading(true);
      const response = await axios.get(
        "https://fitnesso-app-new.herokuapp.com/product/view_all_products_np"
      );
      //const response = await axios.get("http://localhost:9067/product/viewproducts");
      const newData = fisherShuffle(response.data).slice(0, 5);
      setProducts(newData);
      console.log(response.data);
      setLoading(false);
    };
    loadPosts();
  }, []);

  return (
    <>
    <div className="trainers_text">
        <span className="colour-free">Product & Services</span>
      </div>
    <div className="hp_product_card_wrapper">
      {products?.map((item) => (
        <div key={item.id} className="product__box">
              <div className="product_wrapper_container">
                <a className="search__container" href={`/cart`}>
                  <span className="search__image">
                     <img src={item.image}/>
                  </span>
                  <span className="product__title">{item.productName}</span>
                  <span className="product__category">{item.category}</span>
                  <span className="product__price">₦ {item.price}</span>
                </a>
              </div>
              </div>
      ))}
    </div>
    </>
  );
}

export default ProductShuffler;
