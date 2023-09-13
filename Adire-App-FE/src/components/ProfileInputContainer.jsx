import React, { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import FormRow from './FormRow'
import { toast } from 'react-toastify';

const ProfileInputContainer = () => {
    const { isLoading, user } = useSelector((store) => store.user);
    const dispatch = useDispatch();

    const [userData, setUserData] = useState({
        firstName: user?.firstName ||'',
        lastName: user?.lastName ||'',
        email: user?.email ||'',
        phoneNumber: user?.phoneNumber ||'',
        url: user?.url ||'',
    })


    const handleSubmit = (e) => {
        e.preventDefault();
        const { firstName, lastName, email, phoneNumber, address, url } = userData;
    
        if (!firstName || !lastName || !email || !phoneNumber || !address) {
          toast.error('Please Fill Out All Fields');
          return;
        }
    };

    const handleChange = (e) =>{
      const name = e.target.name
      const value = e.target.value
      setUserData({...userData,[name]:value})
    }
  
    // useEffect(() => {
    //   console.log("use me");
    
    //   return () => {
    //     console.log("return me");
    //   }
    // }, [])
    

  return (
    <>
        {/* first name field */}
        <FormRow
          type='text'
          name='firstName'
          // value={values.firstName}
          labelText="first name"
          placeholder="Type your full name"
          // handleChange={handleChange}
        />

        {/* last name field */}
        <FormRow
          type='text'
          name='lastName'
          // value={values.lastName}
          labelText="last name"
          placeholder="Type your full name"
          // handleChange={handleChange}
        />
        
        {/* email field */}
        <FormRow
          type='email'
          name='email'
          // value={values.email}
          placeholder="Type your email"
          // handleChange={handleChange}
        />

        {/* Phone Number field */}
        <FormRow
          type='tel'
          name='phoneNumber'
          // value={values.phoneNumber}
          labelText="phone number"
          placeholder="Enter your phone number"
          // handleChange={handleChange}
        />

        {/* password field */}
        <FormRow
          type='text'
          name='address'
          // value={values.address}
          placeholder="Enter your address"
          // handleChange={handleChange}
        />
    </>
  )
}

export default ProfileInputContainer