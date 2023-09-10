import React from 'react'
import { useStateContext } from '../context/ContextProvider';
import { Outlet } from 'react-router-dom';
import AdminSidebar from '../components/adminHrComponent/AdminSidebar';
import AdminNavbar from '../components/adminHrComponent/AdminNavbar';

const Admin = () => {
  const { activeMenu } = useStateContext();
  return (
    <div className="flex relative dark:bg-main-dark-bg">
      {activeMenu ? (
        <div className="w-72 fixed sidebar dark:bg-secondary-dark-bg bg-white ">
          <AdminSidebar />
        </div>
      ) : (
        <div className="w-0 dark:bg-secondary-dark-bg">
          <AdminSidebar />
        </div>
      )}
      <div
        className={`dark:bg-main-dark-bg bg-main-bg min-h-screen w-full 
            ${activeMenu ? "md:ml-72" : "flex-2"}`}
      >
        <div className="fixed pt-0 md:static bg-main-bg dark:bg-main-dark-bg navbar w-full">
          <AdminNavbar />
        </div>

        <div>
          <Outlet />
        </div>
        
      </div>
    </div>
  );
}

export default Admin
