import React, { useState } from "react";
import { useQuery } from "react-query";
//Components
import Drawer from "@material-ui/core/Drawer";
import Cart from "./Cart/Cart";
import LinearProgress from "@material-ui/core/LinearProgress";
import AddShoppingCartIcon from "@material-ui/icons/AddShoppingCart";
import Badge from "@material-ui/core/Badge";
import Item from "./item";
import "./ShopApp.css";
//Styles

import { Wrapper, StyledButton } from "./App.styles";

async function getProducts() {
  let result = await fetch(
    "https://fitnesso-app-new.herokuapp.com/product/viewproducts/"
  );
  return result.json();
}

const ShopApp = () => {
  const [cartOpen, setCartOpen] = useState(false);
  const [cartItems, setCartItems] = useState([]);
  const { data, isLoading, error } = useQuery("products", getProducts);

  console.log(data);

  const getTotalItems = (items) =>
    items.reduce((ack, item) => ack + item.amount, 0);

  const handleAddToCart = (clickedItem) => {
    setCartItems((prev) => {
      const isItemInCart = prev.find((item) => item.id === clickedItem.id);
      if (isItemInCart) {
        return prev.map((item) =>
          item.id === clickedItem.id
            ? { ...item, amount: item.amount + 1 }
            : item
        );
      }
      return [...prev, { ...clickedItem, amount: 1 }];
    });
  };

  const handleRemoveFromCart = (id) => {
    setCartItems((prev) =>
      prev.reduce((ack, item) => {
        if (item.id === id) {
          if (item.amount === 1) return ack;
          return [...ack, { ...item, amount: item.amount - 1 }];
        } else {
          return [...ack, item];
        }
      }, [])
    );
  };

  if (isLoading) return <LinearProgress />;
  if (error) return <div>Something went wrong...</div>;
  return (
    <Wrapper style={{ marginTop: "90px" }}>
      <Drawer anchor="right" open={cartOpen} onClose={() => setCartOpen(false)}>
        <Cart
          cartItems={cartItems}
          addToCart={handleAddToCart}
          removeFromCart={handleRemoveFromCart}
        />
      </Drawer>
      <StyledButton onClick={() => setCartOpen(true)}>
        <Badge badgeContent={getTotalItems(cartItems)} color="error">
          <AddShoppingCartIcon />
        </Badge>
      </StyledButton>
      <div className="prodts_wrapper">
        {data.map((item) => (
          <div className="prodt-cont">
            <Item item={item} handleAddToCart={handleAddToCart} />
          </div>
        ))}
      </div>
    </Wrapper>
  );
};

export default ShopApp;
