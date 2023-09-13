import React, { useState, useEffect } from 'react'
import Wrapper from '../../assets/wrappers/AccountSettingsPage';
import { Profile, UpdatePassword } from '../../components';
import BtnContainer from '../../components/BtnContainer';
import { getUserDetails } from '../../features/user/userSlice';
import { useDispatch } from 'react-redux';

const AccountSettings = () => {
  const [isProfilePage, setIsProfilePage] = useState(true);
  const dispatch = useDispatch();


//   useEffect(() => {
//     console.log("sogo");
//     dispatch(getUserDetails());
// }, []);

  return (
    <Wrapper>
      <h4>account</h4>
      <section className='account-center'>

        {/* btn container */}
        <BtnContainer
          isProfilePage={isProfilePage}
          setIsProfilePage={setIsProfilePage}
        />

        {/* Account Setting */}
        <div className='item-container'> 
          <Profile isProfilePage={isProfilePage} />
        </div>
      </section>
    </Wrapper>
  )
}

export default AccountSettings