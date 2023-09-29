import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import BigNumber from 'bignumber.js';
import CheckoutCard from '../../components/CheckoutCard/CheckoutCard';
import { useAuth } from '../../context/authcontext';
import Modal from '../../components/Modal/Modal';
import Loader from '../../components/Loader/Loader';
import "./checkout.css";

const Checkout2 = () => {
  const [pickupState, setPickUpState] = useState("");
  const { cartItems, GetAllCartItems, GetPickUpCentersByStateConfig, pickupCentersInState,  GetAllStatesConfig,
    allStates, GetPickupCenterByEmailConfig,pickupCenterByEmail, OrderConfig } = useAuth();

  const [isModalOpen, setIsModalOpen] = useState(false);
  const toggleModal = () => setIsModalOpen(!isModalOpen);

  //============ Payment Modal =============//
  const [isPaymentModalOpen, setIsPaymentModalOpen] = useState(false);
  const togglePaymentModal = () => setIsPaymentModalOpen(!isPaymentModalOpen);

  const handleSubmitPickupState = (e) => {
    e.preventDefault();
    toggleModal();
    GetPickUpCentersByStateConfig(pickupState);
    setPickUpState("");
  }

  const [pickupCenter, setPickUpCenter] = useState("");
  const [isLoading, setIsLoading] = useState(false);


  const handleChange = (e) => {
    setPickUpCenter(e.target.value);
  }

  const handleSubmitPickupCenter = (e) => {
    e.preventDefault();
    GetPickupCenterByEmailConfig(pickupCenter);
    toggleModal();
  }

  const handlePaymentModal = (e) => {
    e.preventDefault();
    togglePaymentModal();
  }


  useEffect(() => {
    GetAllCartItems();
    GetPickUpCentersByStateConfig();
    GetAllStatesConfig();
    GetPickupCenterByEmailConfig();
    OrderConfig();
  }, []);

  const orderData = {
    grandTotal: cartItems.total,
    pickupCenterEmail: pickupCenter,
  }

  const handleProcessPayment = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    await OrderConfig(orderData);
    setIsLoading(false);
    togglePaymentModal();
  }

  // useEffect(() => {
  //   if(pickupCenter !== ""){
      
  //   }
  // }, [pickupCenter]);

  const number = 3;

  return (
    <div className='lg:mx-20 lg:pr-8 lg:pl-25 lg:pb-5 lg:pt-10 sm:mx-5'>
     {(cartItems === null || cartItems.items.length === 0) ? <div className='text-center mt-20'>
          <h1 className='text-3xl font-bold-900 my-5 pb-30'>You have 0 item(s) in your cart</h1>
          <div className='flex justify-center align-middle py-3 text-xl'>
            <Link to="/shop" className='bg-[#917307] p-3 text-white focus:text-[#917307] active:text-[#917307] focus:bg-white active:bg-white rounded-md drop-shadow-md hover:text-2xl hover:drop-shadow-lg
             hover:text-[#917307] !important ' >
              CONTINUE SHOPPING
            </Link>
          </div>
      </div> 
        :
       <div>
      <div className='row'>
          <h1 className='text-3xl font-extrabold pb-2'>Checkout</h1>
      </div>

      <div className='row'>
        <div className='col-md-8'>
          {pickupCenter === "" ?
            <CheckoutCard title="1. PICKUP STATION">
            <form onSubmit={handleSubmitPickupState} className="pt-3">
                <label htmlFor="pickup-stations" className='text-xl mb-3 form-label'>Select a pickup station near you: </label>
               
                <select onChange={(e) => setPickUpState(e.target.value)} name="pickupstate" id="pickup-stations" className='form-control text-lg'>
                  <option value="">Select state</option>
                  {allStates.length > 0 && allStates.map((state, index) => 
                    <option value={state.name} key={index}>{state.name}</option>
                  )}
                 </select>

                <div>
                    <button className="submit mt-4 mb-2 px-5 py-3 text-lg font-extrabold hover:text-white">Submit</button>
                </div>
              </form>
            </CheckoutCard>
            :
            <CheckoutCard title="1. PICKUP CENTER DETAILS">
              {pickupCenterByEmail !== null && 
                <ul className="text-lg mt-0 px-3">
                   <li className="text-lg font-medium">- Name: {pickupCenterByEmail.name}</li>
                  <li className="text-lg font-medium">- Address: {pickupCenterByEmail.location}</li>
                  <li className="text-lg font-medium">- State: {pickupCenterByEmail.state}</li>
                  <li className="text-lg font-medium">- Phone-number: {pickupCenterByEmail.phone}</li>
                  <li className="text-lg font-medium">- Email: {pickupCenterByEmail.email}</li>
                </ul>
              }
               <div className='text-center py-3'>
                  <button className="mb-2 px-5 py-3 text-lg font-extrabold text-white bg-[#9d7c07] hover:text-xl" onClick={handlePaymentModal}>Pay from Wallet</button>
                </div>
            </CheckoutCard>
          }
        </div>

        <div className='col-md-4'>
            <CheckoutCard title={`2. Your Order(${number} items)`}>
                <div className='divide-y'>
                  {cartItems.items.map((item, index)  => {
                    return(
                      <div key={index} className="py-2 flex justify-between">
                        <div>
                          <p>{item.productName}</p>
                          <p className='text-gray-400'>Qty : {item.orderQty}</p>
                        </div>  

                        <p className='text-[#917307]'>₦{item.unitPrice * item.orderQty}</p>
                      </div>
                    )
                  })}
                  <div className="flex justify-between my-3 font-extrabold pt-3">
                    <h1 className='text-2xl text-gray-800'>Total</h1>
                    <p>₦{cartItems.total}</p>
                  </div>

                  {pickupCenterByEmail === null && 
                    <div className='text-center py-3'>
                    <Link to="/shopping-cart" className="text-[#917307] hover:p-3 hover:font-bold-900 hover:rounded-md hover:drop-shadow-md hover:bg-[#e6d69d] focus:text-white active:text-white hover:pt-5">Modify Cart</Link>
                  </div>}
                </div>
            </CheckoutCard>
        </div>
      </div>

      {isModalOpen && (
        <Modal onClose={toggleModal}>
          <h2 className="text-sm font-bold mb-4 uppercase">Please select a pickup center in your state :</h2>
          {pickupCentersInState.length > 0 && pickupCentersInState.map((item, index)=>
            <div key={index} className="divide-y">
              <form onSubmit={handleSubmitPickupCenter}>
                <div className='form-group'>
                  <input type="radio" name="pickupcenter" value={`${item.email}`} onChange={handleChange} checked={pickupCenter === `${item.email}`} className="form-radio h-4 w-4 text-indigo-600 transition duration-150 ease-in-out"/>
                  <label className="ml-3 mt-3 form-label text-xl font-semibold">{item.name}</label>
                </div>
                <ul className="text-lg mt-0 px-3">
                  <li className="text-lg font-medium">- Address: {item.location}</li>
                  <li className="text-lg font-medium">- State: {item.state}</li>
                  <li className="text-lg font-medium">- Phone-number: {item.phone}</li>
                  <li className="text-lg font-medium">- Email: {item.email}</li>
                </ul>

                <div className=''>
                    <button className="submit mt-4 mb-2 px-4 py-2 text-lg font-extrabold hover:text-gray" type="submit">Choose</button>
                </div>
              </form>
            </div>       
            )}
        </Modal>
      )}

      {
        isPaymentModalOpen && (
          <Modal onClose={togglePaymentModal}>
            <h2 className=''>This will be deducted from your wallet, Are you sure you want to make this payment?</h2>
            <p className='flex justify-evenly mt-3'>
              <button className='bg-[#917307] text-white px-3 py-2 text-md hover:bg-teal-600 hover:drop-shadow-md rounded-sm' onClick={handleProcessPayment}>Yes</button>
              <button className='bg-gray-700 text-white px-3 py-2 text-md hover:bg-red-500 hover:drop-shadow-md rounded-sm' onClick={togglePaymentModal}>No</button>
            </p>
            {isLoading && <Loader />}
          </Modal>
        )
      }
      </div>
    }
    </div>
  )
}

export default Checkout2;