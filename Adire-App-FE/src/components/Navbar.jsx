import Wrapper from '../assets/wrappers/Navbar';
import ProfileImg from '../assets/images/profile.jpeg' 
import { useState } from 'react';
import Logo from './Logo';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// import { toggleSidebar, clearStore } from '../features/user/userSlice';

const Navbar = () => {
  const [showLogout, setShowLogout] = useState(false);
  const { user } = useSelector((store) => store.user);
  const dispatch = useDispatch();

//   const toggle = () => {
//     dispatch(toggleSidebar());
//   };

  return (
    <Wrapper>
      <div className='nav-center'>
        
        <div className='btn-container'>
          <Link
            to={"account-settings"}
            type='button'
            className='profile-btn'
            // onClick={() => setShowLogout(!showLogout)}
          >
            <img src={ProfileImg} alt="profile image" className='person-img' />
            {/* Hi, {`${user?.firstName} .${user?.lastName.chartAt(0)}`} */}
            Hi, {`${user.firstName} ${user.lastName.charAt(0)}.`}
          </Link>
        </div>
      </div>
    </Wrapper>
  );
};
export default Navbar;