/* eslint-disable jsx-a11y/anchor-is-valid */
import React, { useState, useEffect } from "react";
import "./navBar.css";
import { useNavigate } from "react-router-dom";

const NavBar = () => {
  const [toggleMenu, setToggleMenu] = useState(false);
  const [screenWidth, setScreenWidth] = useState(window.innerWidth);

  useEffect(() => {
    const changeWidth = () => {
      setScreenWidth(window.innerWidth);
    };

    window.addEventListener("resize", changeWidth);
    return () => {
      window.removeEventListener("resize", changeWidth);
    };
  }, []);

  const navigate = useNavigate();

  const toggleNav = () => {
    setToggleMenu(!toggleMenu);
  };

  return (
    <div className="nav-header">
      <div className="header">
        <header className="header-header">
          <div className="header-logo">
            <h2 className="logo">Fintech.africa</h2>
          </div>
          {toggleMenu ? (
            <button onClick={toggleNav} className="btn">
              X
            </button>
          ) : (
            <button onClick={toggleNav} className="btn">
              |||
            </button>
          )}
          {(toggleMenu || screenWidth > 500) && (
            <nav className="header-nav">
              <ul className="header-ul">
                <li className="header-li">
                  <a onClick={() => navigate("/")}>
                    <span>Home</span>
                  </a>
                </li>
                <li className="header-li">
                  <a onClick={() => navigate("/feature")}> Feature </a>
                </li>
                <li className="header-li">
                  <a onClick={() => navigate("/About")}> About </a>
                </li>
                <li className="header-li">
                  <a onClick={() => navigate("/contact-us")}> Contact Us </a>
                </li>
              </ul>
              <div className="header-end">
                <ul className="header-end-ul">
                  <li className="header-end-li">
                    <a onClick={() => navigate("/login")}> Login </a>
                  </li>
                </ul>
                <button onClick={() => navigate("/signup")}>
                  Create an account
                </button>
              </div>
            </nav>
          )}
        </header>
      </div>
    </div>
  );
};

export default NavBar;
