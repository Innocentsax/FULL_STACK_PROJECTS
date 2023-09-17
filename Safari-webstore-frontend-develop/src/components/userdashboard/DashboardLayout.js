import React from 'react';
import SideBar from "../sidebar/SideBar";
import './UserDashboardWrapper.css';
import Footer from '../Footer';

const DashboardLayout = ({ children }) => {

  const menu = [

    {
      icon: "fas fa-user",
      name: "Account",
      path: '/account/information',
      exact: true
    },
    {
 
      icon: "fas fa-address-book fa-2x",
      name: "Address Book",
      path: "/account/addressbook",
      exact: true
    },
    {
      icon: "fas fa-heart",
      name: "My Favourites",
      path: "/account/myfavourites",
      exact: true
    },

    {
        icon: "fas fa-gift",
        name: "My Orders",
        path: "/account/myorders",
        exact: true
      },
  ]

  return (
    <>
     <div className="dashboard-wrapper">
       
       <SideBar menuItems={menu} />
       {/* <div> */}
         { children }
       {/* </div> */}
       
     </div>
     <br /><br />
     <Footer />
     </>
  );
}

export default DashboardLayout;