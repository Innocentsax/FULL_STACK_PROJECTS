import { createContext, useState } from "react";

const CartContext = createContext({
  cart: [],
  totalItemsInCart: 0,
  addToCart: (cartProduct) => {},
  removeCartItem: (productId) => {},
  itemIsInCart: (productId) => {},
});
export function CartContextProvider(props) {
  const [userCart, setUserCart] = useState([]);

  function addToCartHandler(cartProduct) {
    // addToCart(8);

    setUserCart((prevuserCart) => {
      return prevuserCart.concat(cartProduct);
    });
  }

  function removeFromCartHandler(productId) {
    setUserCart((prevuserCart) => {
      return prevuserCart.filter((product) => product.id !== productId);
    });
  }
  function itemIsInCartHandler(productId) {
    return userCart.some((product) => product.id === productId);
  }
  const context = {
    cart: userCart,
    totalItemsInCart: userCart.length,
    addToCart: addToCartHandler,
    removeCartItem: removeFromCartHandler,
    itemIsInCart: itemIsInCartHandler,
  };
  return (
    <CartContext.Provider value={context}>
      {props.children}
    </CartContext.Provider>
  );
}
export default CartContext;
