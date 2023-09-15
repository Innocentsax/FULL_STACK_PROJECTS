import axios from "axios";
import React, { useLayoutEffect, useEffect, useState } from "react";
import { Button } from "react-bootstrap";
import { authHeader } from "../../services/BaseService";

const RateCard = (props) => {
  const { HomeCss, showBadge } = props;
  const { productName, images, price, id } = props.frequent;

  // const [jwtToken, setJwtToken] = useState();

  const configureJWT = () =>  localStorage.getItem("currentUser");
    
  

  // useEffect(configureJWT, [jwtToken]);

  const ADD_TO_CART_API = "http://localhost:8080/api/v1/customer/carts";

  function handleAddToCart() {
    axios
      .post(`${ADD_TO_CART_API}/${id}`, {}, {headers:{Authorization: configureJWT()}})
      .catch((error) => {
        console.log(error);
      });
      console.log("Added to cart")
  }

  return (
    <article className={`${HomeCss.varticalCard}`}>
      <div className={HomeCss.CardHeader}>
        <img src={images[0].img} alt={productName} />
      </div>
      <div className={HomeCss.CardBody}>
        <h4 className={HomeCss.name}>{productName}</h4>
        <p className={HomeCss.price}>&#x20A6; {price}</p>
        <Button
          className={HomeCss.showMore}
          variant="light"
          onClick={handleAddToCart}
        >
          add to cart
        </Button>
      </div>
      {showBadge && <div className={HomeCss.badge}>Sale!</div>}
    </article>
  );
};

export default RateCard;
