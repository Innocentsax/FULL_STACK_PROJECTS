import React, { useState, useEffect } from 'react'
import NavLinks from './NavLinks';
import Logo from './Logo';
import Wrapper from '../assets/wrappers/BigSidebar';
import { Link, useLocation } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { logoutUser } from '../features/user/userSlice';

const BigSidebar = () => {

//   const { isSidebarOpen } = useSelector((store) => store.user);
const dispatch = useDispatch();
const location = useLocation();
const { pathname } = useLocation();

console.log(pathname.substring(1));
const [active, setActive] = useState("")

  useEffect(() => {
    console.log(pathname.substring(1));
    if (pathname.substring(1) == "") {
      setActive("/")
      return;
    }
    if (pathname.substring(1).includes("orders")) {
      setActive("orders")
      return;
    }
    if (pathname.substring(1).includes("customers")) {
      setActive("customers")
      return;
    }
    setActive(pathname.substring(1))
  }, [pathname])
  


  return (
    <Wrapper>
      <div
        className='sidebar-container show-sidebar'
        // className={
        //   isSidebarOpen
        //     ? 'sidebar-container '
        //     : 'sidebar-container show-sidebar'
        // }
      >
        <div className='content'>
          <header>
            <Link to={"/"}>
              <Logo />
            </Link>
          </header>
          <NavLinks setActive={setActive} active={active} />
        </div>
      </div>
    </Wrapper>
  );
};
export default BigSidebar;