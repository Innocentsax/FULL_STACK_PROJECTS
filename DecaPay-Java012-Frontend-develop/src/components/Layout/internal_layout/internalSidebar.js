import React, { useState } from "react";
import { Link } from "react-router-dom";
import Logout from "../../dashboard/logout/Logout";
import {elementSelector} from "../../../globalresources/elementSelector";

function InternalSidebar() {
  const [logoutModal, setLogoutModal] = useState(false);
  const [pageCount, setPageCount] = useState(0);

  const  handlePageRendering = () => {
    setPageCount(pageCount+1);

  }

  // elementSelector(".dashboard").addEventListener("click",function (){
  //   return handlePageRendering();
  // });


  return (
    <main>
      <nav
        id="sidebarMenu" key={pageCount}
        className="collapse d-lg-block sidebar collapse bg-white"
      >
        <div className="position-sticky">
          <div className="list-group list-group-flush mx-3 mt-4">
            <br />
            <a
              href="/decapay/dashboard"
              className="list-group-item dashboard list-group-item-action py-2 ripple"
            >
              &nbsp;&nbsp;
              <img className="vector-S7b" src="/assets/vector-Tyb.png" />
              &nbsp;&nbsp;<span>Dashboard</span>
            </a>
            <br />
            <Link
              to="/decapay/budget-list"
              className="list-group-item list-group-item-action py-2 ripple"
            >
              &nbsp;&nbsp;
              <img className="vector-S7b" src="/assets/budget-FTP.png" />
              &nbsp;&nbsp;<span>Budget</span>
            </Link>
            <br />
            <Link
              to="/decapay/budget-category"
              className="list-group-item list-group-item-action py-2 ripple"
            >
              &nbsp;&nbsp;
              <img className="vector-S7b" src="/assets/check-box-Gzm.png" />
              &nbsp;&nbsp;
              <span>Budget Category </span>
            </Link>
            <br />
            <div className="list-group-item list-group-item-action py-2 ripple">
              &nbsp;&nbsp;
              <img className="" src="/assets/logout-Gu7.png" />
              &nbsp;&nbsp;
              <span
                onClick={() => {
                  setLogoutModal(true);
                }}
              >
                Logout
              </span>
            </div>
            
          </div>

          

        </div>
       

     
      </nav>

      {logoutModal && <Logout 
        logoutModal ={logoutModal}
        setLogoutModal = {setLogoutModal}
        />}


    </main>
  );
}

export default InternalSidebar;
