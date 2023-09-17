import React, { useContext, useState } from "react";
import productApis from "../apis/ProductApi";

const NavContext = React.createContext();
const UpdateNavContext = React.createContext();

export function useCart() {
  return useContext(NavContext);
}

export function useCartUpdate() {
  return useContext(UpdateNavContext);
}

export function CartProvider({ children }) {
  const getCartCount = localStorage.getItem("cart-count");

  const [count, setCount] = useState(0);

  productApis
    .getCartItem()
    .then((data) => {
      let counter = 0;

      data.forEach((cart) => {
        counter += Number(cart.quantity);
      });

      localStorage.setItem("cart-count", Number(counter));

      setCount(Number(counter));
    })
    .catch((error) => {
      console.log(error);
    });

  function addToCart() {
    setCount((prevCount) => prevCount + 1);
    localStorage.setItem("cart-count", count + 1);
  }

  return (
    <NavContext.Provider value={count}>
      <UpdateNavContext.Provider value={addToCart}>
        {children}
      </UpdateNavContext.Provider>
    </NavContext.Provider>
  );
}
