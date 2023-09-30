import React from 'react';
import { Link, Outlet } from "react-router-dom";
import Header  from "./Header";
import Footer from "./Footer";
import Sidebar from './Sidebar';
import TopNavbar from './TopNavbar';

const Layout = () => {
        let mainWidth = "";
        let float = "none";
        if(window.innerWidth>990){
                mainWidth =  window.innerWidth-240;
                float = "right";
        }

    return ( 
            <>
            <header>
             <Sidebar/>
             <TopNavbar />
            </header>
             <main style={{marginTop: '58px',padding:'15px'
             ,width:mainWidth,float:float}}>
                    <Outlet />
            </main>
      {/* <Footer /> */}
      </>
     );
}
 
export default Layout;