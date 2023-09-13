import React from 'react'
import { Link } from 'react-router-dom'
import brandImg from '../assets/images/brandImg.jpg'
import { FormRow } from '.'
import { useSelector, useDispatch } from 'react-redux';
import { toast } from 'react-toastify';
import { handleChange, createCustomer, editCustomer } from '../features/customer/customerSlice'
import Wrapper from '../assets/wrappers/AddCustomer';

const AddCustomer = () => {

  const {
    isLoading,
    address,
    email,
    firstName,
    lastName,
    phoneNumber,
    isEditing,
    editCustomerId,
  } = useSelector((store) => store.customer);
  const dispatch = useDispatch();

  console.log(isEditing);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!firstName || !lastName || !email || !address || !phoneNumber) {
      toast.error('Please Fill Out All Fields');
      return;
    }

    if(isEditing) {
      console.log(lastName);
      dispatch(
        editCustomer({ 
          customerId: editCustomerId, customer: { address, email, firstName, lastName, phoneNumber }
        })
      );
      return;
    }
    console.log(firstName, lastName, email, address, phoneNumber);
    dispatch(createCustomer({ address, email, firstName, lastName, phoneNumber }));
  };

  const handleCustomerInput = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    console.log(name, value);
    dispatch(handleChange({ name, value }));
  };
  
  return (
    
    <Wrapper>
      <div className='page-layout'>
        {/* info */}
        <div className='customer-container'>
          <div className='header'>
            <div className='info'>
              <h3>Customers</h3>
              <p className='sub-heading'>empower your business with seamless order management</p>
            </div>
            <Link to={"/customers"} className='view-all-btn'>view all</Link>
          </div>

          <form className='form' >

            {/* first name of customer field */}
            <FormRow
              type='text'
              name='firstName'
              value={firstName}
              placeholder="Enter your name"
              labelText="first name"
              handleChange={handleCustomerInput}
            />

            {/* last name of customer field */}
            <FormRow
              type='text'
              name='lastName'
              value={lastName}
              placeholder="Enter your name"
              labelText="last name"
              handleChange={handleCustomerInput}
            />

            {/* email field */}
            <FormRow
              type='email'
              name='email'
              value={email}
              placeholder="Enter your email"
              handleChange={handleCustomerInput}
            />

            {/* address field */}
            <FormRow
              type='text'
              name='address'
              value={address}
              placeholder="Enter your address"
              handleChange={handleCustomerInput}
            />

            {/* phone number field */}
            <FormRow
              type='tel'
              name='phoneNumber'
              value={phoneNumber}
              placeholder="Enter your phone number"
              labelText="phone number"
              handleChange={handleCustomerInput}
            />

            <button 
              className='btn btn-block' 
              type='submit' 
              disabled={isLoading}
              onClick={handleSubmit}
            >
              {isLoading ? 'Please Wait...' : isEditing ? 'save changes' : 'create customer'}
            </button>

          </form>
          {/* <div className='img-container'> */}
        </div>
        <div className='image-container'>
          <img className='brand-img' src={brandImg} alt='brand'  />
          <h4 className='slogan'>Take control of your future and start working for yourself</h4>
        </div>
      </div>
    </Wrapper>
  )
}

export default AddCustomer