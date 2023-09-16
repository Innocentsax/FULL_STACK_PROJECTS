import React, { useEffect, useState } from 'react';
import './SingleProductPage.css';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { BASE_API_URL } from '../../common/Constants';
import CurrencyFormat from 'react-currency-format';
import {  useSelector } from 'react-redux';
import Cart from '../../models/Cart';
import CartService from '../../services/CartService';
import { Role } from "../../models/Role";


const SingleProductPage =()=>{
    const [singleProduct, setSingleProduct] = useState([])
   const [errorMessage, setErrorMessage] = useState("");
   const [infoMessage, setInfoMessage] = useState(false);


    const {productId} = useParams();
     const currentUser = useSelector((state) => state.user);


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
           setErrorMessage("Unexpected error occured");
           console.log(error);
         });
     };

     useEffect(() => {
       setTimeout(() => {
         if (infoMessage) {
           setInfoMessage(false);
         }
       }, 1500);
     }, [infoMessage]);

    const fetchSingleProduct = () => {
        axios
          .get(
            `${BASE_API_URL}/api/v1/auth/users/fetch-single-product/` +
              productId
          )
          .then((response) => {
            setSingleProduct(response.data);
            console.log(response.data);
          })
          .catch((error) => {
            console.log(error);
          });
        console.log(singleProduct);
    }

    useEffect(() => {
        fetchSingleProduct();
    }, []);

    return (
      <div className="parentContainer container">
        {errorMessage && (
          <div className="alert alert-danger">{errorMessage}</div>
        )}

        {infoMessage && (
          <div
            className="alert alert-success alert-dismissible fade show d-flex justify-content-between"
            role="alert"
          >
            Added successfully
          </div>
        )}
        <div className="contentSection">
          <div className="productPicture">
            <img src={singleProduct.productImage} alt="" srcset="" />
          </div>
          <div className="productDetails">
            <h3 className="productArticleHeading">
              {singleProduct.productName}
            </h3>
            <p className="priceText">
              <CurrencyFormat
                renderText={(value) => <h4>{value}</h4>}
                decimalScale={2}
                value={singleProduct.productPrice}
                displayType={"text"}
                thousandSeparator={true}
                prefix={"₦"}
              />
            </p>
            <hr className="productDetailsLine" />
            <article className="productFullDetails">
              {singleProduct.productDescription}
            </article>
            <div>
              {currentUser?.roles[0].name === Role.ROLE_PREMIUM && (
              <button
                className="btn btn-success"
                onClick={() => cart(cart, productId)}
              >
                Add To Cart
              </button>
              )}
            </div>
            <hr className="productDetailsLine" />
          </div>
        </div>
      </div>
    );
}
export default SingleProductPage;