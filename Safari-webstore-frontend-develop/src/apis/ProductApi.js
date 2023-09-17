import BaseUrl from "./BaseUrl";
import axios from "axios";
import setHeader from "../utilities/Header";

const token = localStorage.getItem("token");


const productApis = {
  getAllProduct: async () => {
    const response = await axios.get(`${BaseUrl}/products`, {
      // headers: { Authorization: `Bearer ${token}` },
    });
    const { data: products } = response;
    return products;
  },

  getProductByCategory: async (category) => {
    try{
      const allProducts = await axios.get(`${BaseUrl}/products`);
    const products = await allProducts.content;
    return products.filter(product=>product.category[0].name === category);
    }catch(error){
        // console.log(error);
    }
  },

  getProductById: async (id) => {
    const { data: product } = await axios.get(`${BaseUrl}/products/${id}`);
    return product;
  },

  getFavorites: async ()=>{
    // let favorites = null;
    // console.log("in get favourites", setHeader())
    try{
    const favourites = await axios.get(`${BaseUrl}/api/customer/favourite/products`, setHeader());
    return favourites;
    }catch(error){
      console.log(error);
    }
  },

  getAllProducts: async () => {
    const { data: products } = await axios.get(`${BaseUrl}/products`);

    return products;
  },

  getAllProductsAdmin: async () => {
    const { data: products } = await axios.get(`${BaseUrl}/products`, setHeader());

    return products;
  },
  

  searchProductByParams: async (params) => {
    const { data: searchedProducts } = await axios.get(
      `${BaseUrl}/products/search?keyword=${params}`
    );

    return searchedProducts;
  },

  getAllProductsInCart: async () => {
    const response = await axios.get(`${BaseUrl}/products/allCartItems`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const { data: itemsInCartDB } = response;

    // console.log("inside the GETALLPRODUCTSINCART function", response);

    return itemsInCartDB;
  },

  addProductToCart: async (data) => {
    const { data: response } = await axios.post(
      `${BaseUrl}/products/add-to-cart`,
      data,
      setHeader()
    );

    return response;
  },

  getCartItem: async () => {
    let { data: cartItems } = await axios.get(
      `${BaseUrl}/products/cart-items`,
      setHeader()
    );

    let total = 0;

    cartItems = cartItems.map((element) => {
      total += element.price * Number(element.quantity);
      return { ...element, total };
    });

    return cartItems;
  },

  deleteCartItemById: async (id) => {
    let { data: response } = await axios.delete(
      `${BaseUrl}/products/delete/${id}`,
      setHeader()
    );

    return response;
  },

  deleteProductFromCart: async function deleteFromCart(id) {
    try {
      const response = await fetch(
        `${BaseUrl}/products/delete/${id}`,
        {
          method: "DELETE",
          body: JSON.stringify({
            productId: id,
          }),
          headers: {
            "Content-type": "application/json; charset=UTF-8",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      let data = await response.json();
      //   alert("Item Added To Cart");
      // console.log(data);
    } catch (err) {
      //   alert("Something Went Wrong");
      console.log(err);
    }
  },

  addProductToFavorite: async function addToFavorite(id) {
    try {
      const response = await fetch(
        `${BaseUrl}/api/customer/favorite/${id}`,
        {
          method: "POST",
          body: JSON.stringify({
            productId: id,
          }),
          headers: {
            "Content-type": "application/json; charset=UTF-8",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      let data = await response.json();
      //   alert("Item Added To Cart");
      // console.log(data);
    } catch (err) {
      // alert("Something Went Wrong");
      console.log(err);
    }
  },
};

export default productApis;
