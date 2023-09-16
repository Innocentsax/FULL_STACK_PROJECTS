import React, { useEffect, useState } from 'react'
import CartService from '../../services/CartService';
import CurrencyFormat from "react-currency-format";
import "./Cartpage.css"
import { Link } from 'react-router-dom';
import Payment from '../../component/Payment';


const CartPage = () => {
const [data, setData] = useState([]);
const [cartTotal, setCartTotal] = useState([]);
const [cartQuantity, setCartTotalQuantity] = useState([]);

  useEffect(() => {
    CartService.getAllCartItem().then((resp) => {
      setCartTotalQuantity(resp.data.quantity);
    });
  }, []);



useEffect(() => {
  CartService.getAllCartItem().then((resp) => {
    setData(resp.data.cartItemList);
  })
},[])



 useEffect(() => {
   CartService.getAllCartItem().then((resp) => {
     setCartTotal(resp.data);
console.log(resp.data);

   });
 }, []);



  return (
    <div className="cart container">
      <h2>Your Cart</h2>
      <div className="cart-header_bar">
        <Link to="/">
          <button className="btn btn-success">Contnue Shopping</button>
        </Link>
        <div className="cart-header_middle">
          <a href="#">Shopping Bag</a>
          <a href="#">Your Wishlist</a>
        </div>
        <Payment />
      </div>
      <div className="cart__content-container">
        <div className="cart-content__left">
          {data.map((item) => (
            <>
              <div className="cart-content__cartItem-left">
                <img src={item.productImage} alt="" />
                <div className="product_spect">
                  <h6>
                    <span>Products:</span> {item.productName}
                  </h6>
                  <h6>
                    <span>ID:</span> 93656565
                  </h6>
                  <h6>
                    <span>Size:</span> {item.productSize}
                  </h6>
                </div>
                <div className="product-spect-quantity">
                  {item.quantity}
                  <CurrencyFormat
                    renderText={(value) => <strong>{value}</strong>}
                    decimalScale={2}
                    value={item.unitPrice}
                    displayType={"text"}
                    thousandSeparator={true}
                    prefix={"₦"}
                  />
                </div>
              </div>
              <hr />
            </>
          ))}
        </div>
        <div className="cart-content__right">
          <h3>ORDER SUMMARY</h3>
          <div className="content__summary">
            <div className="content__summary-content">
              <p>Subtotal</p>
              <p>{cartQuantity} item(s)</p>
            </div>
            <div className="content__summary-total">
              <h4>Total</h4>
              <CurrencyFormat
                renderText={(value) => <h4>{value}</h4>}
                decimalScale={2}
                value={cartTotal.cartTotal}
                displayType={"text"}
                thousandSeparator={true}
                prefix={"₦"}
              />
            </div>
          </div>
        </div>
      </div>

      <div className="checkout__textWarning">
        <h4>*Please use the following text credit card for payment*</h4>
        <h3>4242 4242 4242 4242 - Exp: 01/22 - CVW:123</h3>
      </div>
    </div>
  );
}

export default CartPage