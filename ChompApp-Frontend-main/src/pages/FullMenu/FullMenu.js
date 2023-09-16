import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';
import Footer from '../../component/footer/Footer';
import ShoppingSection from '../../component/ShoppingSection/ShoppingSection';
import Cart from '../../models/Cart';
import CartService from '../../services/CartService';
import ProductService from '../../services/ProductService';
import "./FullMenu.css";
import { motion } from "framer-motion";


const FullMenu = () => {
     const [productList, setProductList] = useState([]);
     const [errorMessage, setErrorMessage] = useState(false);
     const [infoMessage, setInfoMessage] = useState(false);

  const currentUser = useSelector((state) => state.user);


   useEffect(() => {
     ProductService.getAllProducts()
       .then((response) => {
         setProductList(response.data.content);
       })
       .catch((error) => {
         console.log(error);
       });
   }, []);

   const cart = (product, productId) => {
     if (!currentUser?.userId) {
       setErrorMessage("You should login to buy a product");
       return;
     }

     const cart = new Cart(
       currentUser.userId,
       product.productId,
       product.productName,
       product.productImage,
       product.size,
       product.quantity,
       product.unitPrice,
       product.subTotal,
       product.cartId
     );

     CartService.addToCart(cart, productId)
       .then((response) => {
         console.log(cart);
         console.log(response);
         setInfoMessage(true);
       })
       .catch((error) => {
         setErrorMessage(true);
         console.log(error);
       });
   };

     useEffect(() => {
       setTimeout(() => {
         if (infoMessage || errorMessage) {
           setInfoMessage(false);
           setErrorMessage(false);
         }
       }, 2000);
     }, [infoMessage, errorMessage]);

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0, transition: { duration: 0.5 } }}
    >
      <div className="fullMenu">
        <div className="fullMenu__wrapper container">
          <h2>Our Full Menu</h2>
          <hr />
          {errorMessage && (
            <div
              className="alert alert-danger alert-dismissible fade show d-flex justify-content-between"
              role="alert"
            >
              Unexpected error occured
            </div>
          )}

          {infoMessage && (
            <div
              className="alert alert-success alert-dismissible fade show d-flex justify-content-between"
              role="alert"
            >
              Added successfully
            </div>
          )}
          <div className="content__wrapper">
            {productList.map(
              ({
                productImage,
                productName,
                productPrice,
                size,
                categoryName,
                productId,
                productDescription,
              }) => (
                <div className="section__four-menu">
                  <ShoppingSection
                    img={productImage}
                    title={productName}
                    price={productPrice}
                    size={size}
                    cart={cart}
                    productId={productId}
                    productDescription={productDescription}
                    key={productId}
                  />
                </div>
              )
            )}
          </div>
        </div>
        <Footer />
      </div>
    </motion.div>
  );
}

export default FullMenu