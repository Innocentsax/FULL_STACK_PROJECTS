import React, { useState } from "react";
import { Route, Link, Outlet } from "react-router-dom";
import logOut from "../../Login/logout";
import Footer from "../Footer/Footer";
import Dashboard from "./Dashboard";
import "./UserDashboard.css";

const UsersDashboard = () => {
  const homeurl = "https://fitnessoapp1.web.app/";

  const [sideBarResponsive, setSideBarResponsive] = useState(true);
  const handleResponsiveness = () => {
    setSideBarResponsive(true);
  };
  const toggleSidebar = () => {
    setSideBarResponsive(false);
  };

  return (
    <>
      <div className="users__dashboard__container">
        <nav className="users__dashboard__navbar">
          <div className="users__dashboard__sidebar__img">
            <Link to="/">
              <img
                src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e808ce7dc544553a7f1b1e4_Black.svg"
                alt="logo"
              />
            </Link>
            <h1 style={{ marginRight: "10px" }}> </h1>
          </div>
          <div className="users__dashboard__nav_icon" onClick={toggleSidebar}>
            <i className="fa fa-bars" aria-hidden="true"></i>
          </div>
          <div className="users__dashboard__navbar__left">
            {/* <a href="#">Subscribers</a>
        <a href="#">Video Management</a>
        <a className="users__dashboard__active_link" href="#">Admin</a>  */}
          </div>
          <div className="users__dashboard__navbar__right">
            <a href="#">
              <i className="fa fa-search" aria-hidden="true"></i>
            </a>
            <a href="#">
              <i className="fa fa-clock" aria-hidden="true"></i>
            </a>
            <a className="users__dashboard__avartar__link" href="#">
              {/* <AccountCircleIcon className="users__dashboard__avartar"/> */}
              {/* <i className="fa fa-user-circle-o" aria-hidden="true"></i>  */}
            </a>
          </div>
        </nav>
        <div className="db-main-section">
          <div className="users-db-sidebar-container">
            <div className="users__dashboard__sidebar__title">
              <i
                onClick={handleResponsiveness}
                className="fa fa-times"
                id="sidebarIcon"
                aria-hidden="true"
              ></i>
            </div>
            <div className="users__dashboard__sidebar__menu">
              <div className="users__dashboard__sidebar__link">
                <i className="fa fa-home"></i>
                <a href="/userdashboard">My Dashboard</a>
              </div>
              <h2>Account</h2>
              <div className="users__dashboard__sidebar__link">
                <i className="fa fa-user" aria-hidden="true"></i>
                <a href="/userdashboard/edit-user-info">Edit Account Info</a>
              </div>
              <div className="users__dashboard__sidebar__link">
                <i className="fa fa-heart"></i>
                <a href="/userdashboard/user-faves">Favorites</a>
              </div>
              <div className="users__dashboard__sidebar__link">
                <i className="fa fa-handshake"></i>
                <a href="/userdashboard/orders">Orders</a>
              </div>
              <h2>Others</h2>
              <div className="users__dashboard__sidebar__link">
                <i className="fa fa-question"></i>
                <a href="#">FAQ</a>
              </div>
              <div className="users__dashboard__sidebar__link">
                <i className="fa fa-office"></i>
                <a href="#">Know more</a>
              </div>
              <div className="users__dashboard__sidebar__logout">
                <i className="fa fa-power-off"></i>
                <a
                  href="#"
                  onClick={logOut}
                >
                  Log out
                </a>
              </div>
            </div>
          </div>
          <div className="db-main-main">
            <main>
              <Outlet />
            </main>
          </div>
          <div className="db-main-img">
            <img
              src="https://images.unsplash.com/photo-1576678927484-cc907957088c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"
              alt="img-loading"
            />
          </div>
        </div>
        <Footer />
      </div>
    </>
  );
};
export default UsersDashboard;
