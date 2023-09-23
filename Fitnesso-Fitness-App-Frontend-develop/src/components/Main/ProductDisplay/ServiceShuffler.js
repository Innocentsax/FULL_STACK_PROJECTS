import React, { useState, useEffect } from "react";
import axios from "axios";
import "./shuffle.css";
import { Card } from "@mui/material";
import fisherShuffle from "./Shuffler";

function ServiceShuffler() {
  const [loading, setLoading] = useState(false);
  const [services, setServices] = useState([]);
  useEffect(() => {
    const loadPosts = async () => {
      setLoading(true);
      const response = await axios.get(
        "https://fitnesso-app-new.herokuapp.com/product/view_services_np"
      );
      //const response = await axios.get("http://localhost:9067/product/viewproducts");
      const newData = fisherShuffle(response.data).slice(0, 8);
      setServices(newData);
      console.log(response.data);
      setLoading(false);
    };
    loadPosts();
  }, []);

  return (
    <div className="hp_product_card_wrapper">
      {services?.map((item) => (
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
  );
}

export default ServiceShuffler;
