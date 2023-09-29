import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import { IoMdClose } from 'react-icons/io';
import { useAuth } from '../../context/authcontext';
import "./shoppingcart.css";

const ShoppingCart = () => {
  const { RemoveItemFromCartConfig, cartItems, IncreaseItemQuantityConfig, ReduceFromItemQuantityConfig, GetAllCartItems, ClearCartConfig,} = useAuth();

  const handleIncreaseItemQuantity = (productId) => {
    IncreaseItemQuantityConfig(productId);
  }
  const handleReduceCartItemQuantity = (productId) => {
    ReduceFromItemQuantityConfig(productId)
  }
  
  const handleRemoveItemFromCart = (itemId) => {
    RemoveItemFromCartConfig(itemId)
  }
  
  const handleClearCart = () => {
    ClearCartConfig()
  }
  
  const [isDisabled, setIsDisabled] = useState(false);


  useEffect(() => {
    GetAllCartItems();
  }, []);

  const tax = 0.00;

  return (
    <div className='cart'>
       {(cartItems === null || cartItems?.items.length === 0) && 
       <div className='text-center mt-30'>
          <h1 className='text-3xl font-bold-900 my-5 pb-30'>Your cart is empty</h1>
          <div className='flex justify-center align-middle py-3 text-xl'>
            <Link to="/shop" className='bg-[#917307] p-3 text-white focus:text-[#917307] active:text-[#917307] focus:bg-white active:bg-white rounded-md drop-shadow-md hover:text-2xl hover:drop-shadow-lg
             hover:text-[#917307] !important ' >
              CONTINUE SHOPPING
            </Link>
          </div>
      </div>}
       {cartItems !== null && cartItems?.items.length !== 0  && 
      <>
        <div>
          <h1 className='text-4xl font-bold-1000 mb-5'>Shopping Cart</h1>

          <div>
              <table className="table">
              <thead className="thead-dark bg-dark text-light">
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Image</th>
                  <th scope="col">Product</th>
                  <th scope="col">Unit Price</th>
                  <th scope="col">Quantity</th>
                  <th scope="col">Sub Total</th>
                  <th>Remove Item</th>
                </tr>
              </thead>

              <tbody>
                  {cartItems?.items.sort((a, b) => a.id > b.id).map((item, index) => {
                  return(
                    <>
                    <tr key={index}>
                    <td>{index + 1}</td>
                      <td>
                          <img src={item.imageUrl} style={{height: '6rem'}}/>
                      </td>
                      <td>{item.productName}</td>
                      <td>₦{item.unitPrice}</td>
                      <td><button disabled={item.orderQty === 1} className='btn text-white bg-[#917307]' onClick={()=>handleReduceCartItemQuantity(item.product.id)}>-</button>  {item.orderQty} <button className='btn text-white bg-[#917307] xl:mt-0 lg:mt-2' onClick={() => handleIncreaseItemQuantity(item.product.id)}>+</button></td>
                      <td>₦{item.unitPrice * item.orderQty}</td>
                      <td className='text-center'>
                          <button className='flex justify-center' type='submit' onClick={()=>handleRemoveItemFromCart(item.id)}><IoMdClose className="bg-gray-200 text-lg rounded"/></button>
                      </td>
                  </tr> 
                  </>
                  )}
                  )}
              </tbody>
            </table>
          </div>

          <div className='row'>
            <div className='offset-md-6 col-md-6 flex justify-end mb-3'>
               <button className='btn bg-red-600 text-white m-2' onClick={handleClearCart}>Clear Cart</button>
            </div>
          </div>
      </div>

        <div className='row '>
          <div className="offset-md-6 col-md-6 border p-4">
            <h1 className='text-4xl font-bold-900 text-black mb-5'>Cart Total</h1>
              <div className="flex justify-between my-2 border-b pb-3">
                    <p className='font-bold-900 text-black'>Subtotal</p>
                    <p>₦{cartItems.total}</p>
              </div>
              <div className="flex justify-between my-3 border-b pb-3">
                    <p className='font-bold-900 text-black'>Tax</p>
                    <p>₦{tax}</p>
              </div>
              <div className="flex justify-between my-3 mb-4">
                    <h1 className='text-2xl text-gray-800 font-bold-900'>Total</h1>
                    <p>₦{cartItems.total + tax}</p>
              </div>
                    
              <p className="w-100">
                <Link to="/checkout" className='w-100 text-white bg-[#917307] px-3 py-3 mb-2 text-center text-md rounded-sm font-extrabold  w-100 text-2xl block'>Proceed to Checkout</Link>
              </p>
          </div>
        </div>
         </>} 
    </div>
  )
}

export default ShoppingCart;