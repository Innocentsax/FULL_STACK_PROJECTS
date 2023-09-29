import React, { useState } from 'react';
import { FaRegUser, FaRegAddressCard } from "react-icons/fa";
import { AiOutlineInbox, AiOutlineHeart, AiOutlineCloseCircle } from "react-icons/ai";
import { BiEditAlt } from "react-icons/bi";
import SideBarElement from '../SideBarElement/SideBarElement';
import "./sideBar.css";
import { useAuth } from '../../context/authcontext';

const SideBar = () => {
  const { Logout } = useAuth();
  const [currentPage, setCurrentPage] = useState('default');

  const handlePageChange = (page) => {
    setCurrentPage(page);
  }
  
  return (
    <div className='bg-white drop-shadow-md rounded-md lg:h-100'>
      <div className='user-sidebar'>
        <SideBarElement name="My Oakland Account" icon={<FaRegUser />} to="/dashboard" dsh='active'/>
        <SideBarElement name="Orders" icon={<AiOutlineInbox />} to="/open-orders" />
        <SideBarElement name="Favourites" icon={<AiOutlineHeart to="/"/>}/>
        <SideBarElement name="Edit Profile" icon={<BiEditAlt />} to="/dashboard-acc-info"/>
        <SideBarElement name="Address Book" icon={<FaRegAddressCard />} to="/addressbook"/>
        <SideBarElement name="Close Account" icon={<AiOutlineCloseCircle to="/"/>}/>
      </div>
      
        <div className='flex justify-center border-t-2 border-gray-200 p-3 mt-2 w-100'>
          <button className='text-[#917307] text-xl font-bold-900 hover:p-3 hover:font-bold-900 hover:rounded-md hover:drop-shadow-md hover:bg-[#e6d69d]'>LOGOUT</button>
        </div>
    </div>
  )
}

export default SideBar;