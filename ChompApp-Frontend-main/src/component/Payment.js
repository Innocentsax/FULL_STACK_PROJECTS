import React, { useEffect, useState } from 'react'
import CartService from '../services/CartService';
import StripeCheckout from "react-stripe-checkout"
import { useNavigate } from 'react-router-dom';

import "react-toastify/dist/ReactToastify.css";

const Payment = () => {
    const [cartTotal, setCartTotal] = useState([])
    const navigate = useNavigate();

     useEffect(() => {
       CartService.getAllCartItem().then((resp) => {
         setCartTotal(resp.data);

         console.log(cartTotal);
       });
     }, []);

     const publishablekey =
       "pk_test_51IbB5iJrhAChGpxwxROklguJqVX3OZiG6e2tnHjegK4jwuAFGWmUtGYtLWvDF0EGE4qsI5wfeNguLtPA4XNGJ24I00mo8wN45I";
       const onToken = () => {

          CartService.clearCart().then(() => {
              navigate("/");
             
          }).catch((error) => {
              console.log(error);
          })
        };
  return (
    <div>
      <StripeCheckout
        label="CHECKOUT NOW"
        name="Chomp App"
        description={`Your total is ₦${cartTotal.cartTotal}`}
        panelLabel="Pay Now "
        token={onToken}
        stripeKey={publishablekey}
      />
    </div>
  );
}

export default Payment