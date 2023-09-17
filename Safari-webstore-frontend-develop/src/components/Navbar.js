import React, { useState } from "react";
import { Link } from "react-router-dom";
import { NavLink } from "react-router-dom";
import { useCart } from "../utilities/CartContext";

function Navbar() {
  const [click, setClick] = useState(false);
  const handleClick = () => setClick(!click);
  const closeMobileMenu = () => setClick(false);
  const currentItemInCart = useCart();
  const userNavLink = localStorage.getItem('token') !== null ? 
      (JSON.parse(localStorage.getItem('isLoggedIn'))).role === 'ADMIN' ?'/admin/dashboard': '/account/information'
      : '/signin-signup' ;
  const search = async (e) => {
    if (e.key === "Enter")
      window.location.href = `/search?keyword=${e.target.value}`;
  };

  return (
    <>
      <nav className="navbar">
        <div className="navbar-container">
          <div className="menu-icon" onClick={handleClick}>
            <i className={click ? "fas fa-times" : "fas fa-bars"} />
          </div>
          <ul className={click ? "nav-menu active" : "nav-menu"}>
            <li className="nav-item">
              <NavLink
                exact
                activeClassName="active"
                to="/"
                className="nav-links"
                onKeyPress={closeMobileMenu}
              >
                Home
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                activeClassName="active"
                to="/categories/clothes"
                className="nav-links"
                onClick={closeMobileMenu}
              >
                Clothes
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                activeClassName="active"
                to="/categories/shoes"
                className="nav-links"
                onClick={closeMobileMenu}
              >
                Shoes
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                activeClassName="active"
                to="/categories/accessories"
                className="nav-links"
                onClick={closeMobileMenu}
              >
                Accessories
              </NavLink>
            </li>

            <li>
              <Link
                to="/sign-up"
                className="nav-links-mobile"
                onClick={closeMobileMenu}
                to={userNavLink}
                // className='nav-links-mobile'
              >
                Account
              </Link>
            </li>
          </ul>
          <div>
            <Link to="/" className="navbar-logo" onClick={closeMobileMenu}>
              <img src="../images/navbar/Logo.png" alt="safari-logo" />
            </Link>
          </div>
          <ul className={click ? "nav-menu-right active" : "nav-menu-right"}>
            <li className="nav-item">
              <form onSubmit={(e) => e.preventDefault()}>
                <input
                  type="text"
                  placeholder="Search..."
                  onKeyPress={search}
                  className="searchbox"
                />
              </form>
            </li>
            <li className="nav-item">
              <i class="fas fa-search search-icon"></i>
            </li>
            <li className="nav-item">
              <NavLink
                activeClassName="active-icon"
                to={userNavLink}
                className="nav-links"
              >
                <i class="fas fa-user"></i>
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                activeClassName="active-icon"
                to="/cart"
                className="nav-links"
              >
                <div className="cart-numbers">{currentItemInCart}</div>
                <i class="fas fa-shopping-cart"></i>
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                activeClassName="active-icon"
                to="/favourites"
                className="nav-links"
              >
                <i class="fas fa-heart"></i>
              </NavLink>
            </li>
          </ul>
        </div>
      </nav>
    </>
  );
}

export default Navbar;
