import React, { useState, useEffect } from 'react'
import ProfileImg from '../assets/images/profile.jpeg'
import Wrapper from '../assets/wrappers/Profile'
import FormRow from './FormRow'
// import UpdatePassword from './UpdatePassword'
import { updateUser, changePassword } from '../features/user/userSlice';
import { toast } from 'react-toastify';
import { useDispatch, useSelector } from 'react-redux';
import Loading from './Loading'
// import axios from 'axios'
import customFetch from '../utils/axios'
import { getUserFromLocalStorage } from '../utils/localStorage'
import { PenIcon } from '../utils/SVGIcons'
import LoaderLayout from '../assets/wrappers/LoaderLayout';
// import Loader from './Loader'


const Profile = ({ isProfilePage }) => {
  
  // const { user, userData, isLoading } = useSelector((store) => store.user);
  const { isLoading } = useSelector((store) => store.user);
    const dispatch = useDispatch();
    // console.log(userData);

    const [userDetails, setUserDetails] = useState({
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',
      url: '',
      oldPassword: '',
      newPassword: '',
      confirmNewPassword: '',
    })

    const handleSubmit = (e) => {
      e.preventDefault();
      const { firstName, lastName, email, phoneNumber, url, oldPassword, newPassword, confirmNewPassword} = userDetails;
  
      if (isProfilePage && (!firstName || !lastName || !email || !phoneNumber)) {
        toast.error('Please Fills Out All Fields');
        return;
      }
      if(isProfilePage) {
        dispatch(updateUser({ firstName, lastName, email, phoneNumber, url }));
        console.log(userDetails);
        console.log(getUserFromLocalStorage());
        return;
      }

      if (!isProfilePage && (oldPassword === newPassword)) {
        toast.error("New password can't be the old one");
        return;
      }
      if (!isProfilePage && (!oldPassword || !newPassword || !confirmNewPassword)) {
        toast.error('Please Fill Out All Fieldsss');
        return;
      }
      if (!isProfilePage && (newPassword !== confirmNewPassword)) {
        toast.error('Passwords do not match');
        return;
      }
      
      dispatch(changePassword({ oldPassword, newPassword, confirmNewPassword }));
      setUserDetails({ ...userDetails, oldPassword: '', newPassword: '', confirmNewPassword: '' })
      
      console.log("change password");
    };

    console.log(isProfilePage);

    const handleChange = (e) =>{
      const name = e.target.name
      const value = e.target.value
      console.log(name, value);
      setUserDetails({...userDetails, [name]:value})
    }
  

    useEffect(() => {
      const getUserDetails = async () => {
        try {
          const response = await customFetch.get('/api/designer/getDesignerInfo');
          setUserDetails(response.data);
        } catch (error) {
          console.error(error);
        }
      };
      getUserDetails();
    }, []);

    if (isLoading) {
      return (
      <LoaderLayout>
          <Loading />
      </LoaderLayout>
      );
    }
  
  return (
    
    <Wrapper>
      <h5 className='settings-title'>{isProfilePage ? 'edit profile' : 'change password' }</h5>
      {
        isProfilePage && (
          <>
          <div className='img-container'>
            <img src={ProfileImg} alt="profile" className='person-img' />
            <span onClick={() => console.log('profile')} className='image-icon'>
              <PenIcon />
            </span>
          </div>
            </>
        )
      }
      
      <form className='form' 
        onSubmit={handleSubmit}
      >
      
        { isProfilePage && (
          <>
            {/* first name field */}
            <FormRow
              type='text'
              name='firstName'
              value={userDetails?.firstName}
              labelText="first name"
              placeholder="Type your full name"
              handleChange={handleChange}
            />
    
            {/* last name field */}
            <FormRow
              type='text'
              name='lastName'
              value={userDetails?.lastName}
              labelText="last name"
              placeholder="Type your full name"
              handleChange={handleChange}
            />
            
            {/* email field */}
            <FormRow
              type='email'
              name='email'
              value={userDetails?.email}
              placeholder="Type your email"
              isReadOnly={true}
              handleChange={handleChange}
            />
    
            {/* Phone Number field */}
            <FormRow
              type='tel'
              name='phoneNumber'
              value={userDetails?.phoneNumber}
              labelText="phone number"
              placeholder="Enter your phone number"
              handleChange={handleChange}
            />

          </>
        )}

        {/* { !isProfilePage && <UpdatePassword /> } */}
        { !isProfilePage && (
          <>
            <FormRow
              type='password'
              name='oldPassword'
              value={userDetails.oldPassword}
              placeholder="Enter your password"
              labelText="old password"
              handleChange={handleChange}
            />
      
            <FormRow
              type='password'
              name='newPassword'
              value={userDetails.newPassword}
              placeholder="Enter your password"
              labelText="new password"
              handleChange={handleChange}
            />
      
            <FormRow
              type='password'
              name='confirmNewPassword'
              value={userDetails.confirmNewPassword}
              placeholder="Enter your password"
              labelText="confirm new password"
              handleChange={handleChange}
            />
          </>
        )}
        

        {/* <button type='submit' className='btn btn-block' disabled={isLoading}>
          {isLoading ? 'loading...' : 'submit'}
        </button>
         */}
        <p>
          <button 
          type='submit' 
          disabled={isLoading} 
          // onClick={toggleMember} 
          className='btn btn-block'>
            {isLoading ? 'Please Wait...' : isProfilePage ? 'Save' : 'Save Changes'}
            {/* {currentItem === "profile" ? 'Save' : 'Save Changes'} */}
          </button>
        </p>

        {/* <button className='btn btn-block' type='submit' disabled={isLoading}>
          {isLoading ? 'Please Wait...' : 'save changes'}
        </button> */}
      </form>
    </Wrapper>
  )
}

export default Profile

