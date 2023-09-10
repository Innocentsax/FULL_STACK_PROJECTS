import React from 'react'
import { useStateContext } from '../context/ContextProvider';
import StaffSidebar from '../components/staffComponent/StaffSidebar';
import StaffNavbar from '../components/staffComponent/StaffNavbar';
import { Outlet } from 'react-router-dom';

const Staff = () => {
  const { activeMenu } = useStateContext();
  return (
    <div className="flex relative dark:bg-main-dark-bg">
      {activeMenu ? (
        <div className="w-72 fixed sidebar dark:bg-secondary-dark-bg bg-white ">
          <StaffSidebar />
        </div>
      ) : (
        <div className="w-0 dark:bg-secondary-dark-bg">
          <StaffSidebar />
        </div>
      )}
      <div
        className={`dark:bg-main-dark-bg bg-main-bg min-h-screen w-full 
            ${activeMenu ? "md:ml-72" : "flex-2"}`}
      >
        <div className="fixed pt-0 md:static bg-main-bg dark:bg-main-dark-bg navbar w-full">
          <StaffNavbar />
        </div>

        <div>
          <Outlet />
        </div>
        
      </div>
    </div>
  );
}

export default Staff
