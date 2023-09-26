import React from 'react';
import Usernav from '../Navbar/Usernav';
import Sidebar from '../Sidebar/Sidebar';
import { BFrame, BodyFrame, SideFrame, UserCon } from '../Styled/Styled';
import { Outlet } from 'react-router-dom';

const Userprofile = () => {
  return (
    <>
        <Usernav />
        <BodyFrame>
            <SideFrame>
                <Sidebar />
            </SideFrame>

                <Outlet/>
        </BodyFrame>
    </>
  )
}

export default Userprofile
