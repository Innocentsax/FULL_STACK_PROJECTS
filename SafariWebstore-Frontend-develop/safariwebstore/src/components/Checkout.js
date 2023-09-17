import React, { useState , useEffect} from "react";
import axios from "axios";
import ShippingForm from "./ShippingForm";
import DeliveryMethod from "./DeliveryMethod";
import PaymentMethod from "./PaymentMethod";
import OrderSummary from "./OrderSummary";
import checkout from "../styles/checkout.module.css";

export default function Checkout() {
  const carts = [{
    id: 1,
    productName: "Topless shirt",
    quantity: 1,
    price: 12000,
    size: "XXL -EU"
  }, {
    id: 2,
    productName: "Leather Shoes",
    quantity: 2,
    price: 20000,
    size: "M -EU"
  }, {
    id: 3,
    productName: "Jeans Trousers",
    quantity: 4,
    price: 50000,
    size: "L -US"
  }, {
    id: 4,
    productName: "Kampala shirt",
    quantity: 3,
    price: 20000,
    size: "L -EU"
  }];
  const [cart, setCart] = useState(carts);

  const url = 'http://localhost:8080/api/customer/checkout'

  
  const fetchFromCart = () => {
    axios.get(url)
      .then((response) => {
      const productCart = response.data.cart.productCart;
      setCart(productCart);
    }).catch(error => console.error(`Error: ${error}`))
  }
  useEffect(() =>fetchFromCart(), [])

  return (
    <>
      <div className={checkout.container}>
        <ShippingForm />
        <div className={checkout.thirdRow}>
          <DeliveryMethod />
          <PaymentMethod />
        </div>
        <OrderSummary cart={cart}/>
      </div>
    </>
  );
}
