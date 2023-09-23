import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from "axios";
import React, { useState, useEffect } from "react";
import { FavoriteButton } from "../../services/FavoriteButton";
import "./ViewProduct.css";
import { useNavigate } from "react-router-dom";

const ViewProduct = () => {
  const [product, setProduct] = useState({});
  const [productId, setProductId] = useState(0);
  const navigate = useNavigate();
  const [index, setIndex] = useState();
  const token = localStorage.getItem("token");
  const url = "https://fitnesso-app-new.herokuapp.com/product/";

  useEffect(() => {
    const id = localStorage.getItem("productId");
    setIndex();
    setProductId(id);
    getProductInfo();
  });

  const getProductInfo = async (e) => {
    const response = await axios.get(`${url}${productId}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const data = response.data;
    setProduct(data);
  };

  const sendCart = () => {
    navigate(`/product`);
  };

  return (
    <div>
      {product.quantity == null ? (
        <div className="item-view-container">
          <div className="details" key={product._id}>
            {/* <FavoriteButton itemId={product.id} /> */}
            <div className="big-img">
              <img src={product.image} alt="" />
            </div>

            <div className="box">
              <div className="row">
                <h2>{product.productName}</h2>
                <span>₦{product.price}</span>
              </div>

              <p>{product.description}</p>
              {/* <p>{item.content}</p> */}

              <button className="cart">Add to cart</button>
            </div>
          </div>
        </div>
      ) : (
        <div className="item-view-container">
          <div className="details" key={product._id}>
            <div className="big-img">
              <img src={product.image} alt="" />
            </div>

            <div className="box">
              <div className="row">
                <h2>{product.productName}</h2>
                <span>₦{product.price}</span>
              </div>

              <p>{product.description}</p>
              {/* <p>{item.content}</p> */}

              <a href="/product">
                <button className="cart">Add to cart</button>
              </a>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ViewProduct;
