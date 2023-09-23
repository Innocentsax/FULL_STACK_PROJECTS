import { useState, useEffect } from "react";
import { useQuery } from "react-query";
//Components
import Drawer from "@material-ui/core/Drawer";
import CartToggle from "../CartToggle/CartToggle";
import LinearProgress from "@material-ui/core/LinearProgress";
import Grid from "@material-ui/core/Grid";
import AddShoppingCartIcon from '@material-ui/icons/AddShoppingCart';
import Badge from "@material-ui/core/Badge";
import Item from "../Item/item";
//Styles
import { Wrapper, StyledButton } from "./CartPage.styles";
import Navbar from '../../Main/Navbar/Navbar'
import Footer from '../../Main/Footer/Footer'




const CartPage = () => {

  
  const [cartOpen, setCartOpen] = useState(false);
  const [apiPage, setApiPage] = useState(1)
  const [pages, setPages] = useState([])
  const [cartItems, setCartItems] = useState([]);
  const { data, isLoading, error } = useQuery("products", getProducts);

  async function getProducts() {
  let result = await fetch(`https://fitnesso-app-new.herokuapp.com/product/viewproducts/${apiPage}`);
  return result.json();
}
setCartItems(data.content)
console.log(data)
let pageNo = data.pageNumber
  
const List = (pageNo)=> {
  if(pageNo > 0){
    let arr = new Array(pageNo)
   let newArr = arr.map((value, index) => index+1)
   return newArr
  }
}
  const getTotalItems = (items) => items.reduce((ack, item) => ack + item.price, 0);

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
    <>
    <Navbar />
    <Wrapper>
      <Drawer anchor="right" open={cartOpen} onClose={() => setCartOpen(false)}>
        <CartToggle cartItems={cartItems} addToCart={handleAddToCart} removeFromCart={handleRemoveFromCart}/>
      </Drawer>
      <StyledButton onClick={() => setCartOpen(true)}>
        <Badge badgeContent={getTotalItems(cartItems)} color="error">
          <AddShoppingCartIcon />
        </Badge>
      </StyledButton>
      <Grid container spacing={3}>
        {data.content.map((item) => (
          <Grid item key={item.id} xs={12} sm={4}>
            <Item item={item} handleAddToCart={handleAddToCart} />
          </Grid>
        ))}
      </Grid>
      
    </Wrapper>
    <ul>
    {List.map((v, i)=><li key ={i} onClick = {()=> setApiPage(i + 1)}>{v}</li>)}
    </ul>
    <Footer />
    </>
  );
};

export default CartPage;
