import "./Checkout.css";
import { useEffect, useState } from "react";
import CheckoutLeft from "../../components/Checkout/CheckoutLeft";
import CheckoutRight from "../../components/Checkout/CheckoutRight";
import Modal from "../../components/Modal/Modal";
import { apiGetAuthorization } from "../../utils/api/axios";

const Checkout = () => {
  const [cartItems, setCartItems] = useState([]);

  const getCartItems = async () => {
    try {
      const response = await apiGetAuthorization("customer/cart/item/view-all");

      console.log("checkout", response.data);
      setCartItems(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getCartItems();
  }, []);

  return (
    <div>
      <h2 className="checkout-text">Checkout</h2>
      <div className="checkout">
        <div className="checkout-left">
          <CheckoutLeft itemsInCart={cartItems} />
        </div>
        <div className="checkout-right">
          <CheckoutRight />
        </div>
      </div>

      <div className="App h-screen flex flex-col items-center justify-center bg-purple-200">
        <Modal />
    </div>
    </div>
  );
};

export default Checkout;
