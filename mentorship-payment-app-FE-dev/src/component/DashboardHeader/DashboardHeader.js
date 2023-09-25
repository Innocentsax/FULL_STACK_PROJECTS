import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./DashboardHeader.css";
import { ReactComponent as Pic } from "./images/pic.svg";
import { ReactComponent as Bell } from "./images/bell.svg";
import { ReactComponent as Red } from "./images/red.svg";
import { FiSettings } from "react-icons/fi";
import { FaBars, FaTimes } from "react-icons/fa";
import { NavLink } from "react-router-dom";

const DashboardHeader = () => {
  const [click, setClick] = useState(false);

  const handleClick = () => {
    setClick(!click);
  };

  const token = localStorage.getItem("accessToken");
  const name = localStorage.getItem("name");

  const navigate = useNavigate();

  if (!token) {
    navigate("/login");
  }

  function logout() {
    localStorage.clear();
    navigate("/");
  }

  return (
    <nav className='navbar_b '>
      <div className='nav-container navbar-wrapper'>
        <div className='navbar__logo'>
          <span>
            <h1>Fintech.africa</h1>
          </span>
        </div>
        <div className='menu-icon' onClick={handleClick}>
          {click ? <FaTimes className='icons' /> : <FaBars className='icons' />}
        </div>
        <div className={click ? "navbar__right active" : "navbar__right"}>
          <ul className='nav-links'>
            {/* {menuItems.map((item) => ( */}
            <li>
              <NavLink exact={true} className='li sett' to='/setting'>
                <FiSettings className='icon' />
              </NavLink>
            </li>
            <li>
              <NavLink exact={true} className='li atBell' to='/notification'>
                <Bell />
                <span className='sideBadge'>
                  <Red />
                </span>
              </NavLink>
            </li>
            <li>
              <NavLink exact={true} className='li' to='/profile-picture'>
                <Pic />
              </NavLink>
            </li>
            <li>
              <span>{name}</span>
            </li>
            {/* ))} */}
          </ul>
          <button className='logout_btn' onClick={logout}>
            logout
          </button>
        </div>
      </div>
    </nav>
  );
};

export default DashboardHeader;
