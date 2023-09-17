import React, { useState, useEffect } from "react";
import BackToTop from "../components/BackToTop";
import Footer from "../components/Footer";
import CartLayout from "../components/CartLayout";
import productApis from "../apis/ProductApi";

function Cart() {
  const [userCartItem, setUserCartItem] = useState([]);
  const [totalCartPrice, setTotalCartPrice] = useState(0);

  useEffect(async () => {
    const cartItem = await productApis.getCartItem();

    setUserCartItem(cartItem);

    const totalPrice = cartItem.length
      ? cartItem[cartItem.length - 1].total
      : 0;
    setTotalCartPrice(totalPrice);
  }, []);

  const deleteCartItem = async (id) => {
    const response = await productApis.deleteCartItemById(id);

    if (response.status === 200) {
      const cartItem = await productApis.getCartItem();
      setUserCartItem(cartItem);


      const totalPrice = cartItem.length
        ? cartItem[cartItem.length - 1].total
        : 0;
      setTotalCartPrice(totalPrice);
    }
  };
console.log("Page Cart iTEM", userCartItem);
  return (
    <>
      <CartLayout
        userCartItem={userCartItem}
        totalPrice={totalCartPrice}
        delete={deleteCartItem}
      />
      <BackToTop />
      <Footer />
    </>
  );
}

export default Cart;
