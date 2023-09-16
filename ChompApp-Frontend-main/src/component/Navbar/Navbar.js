import React, { useState, useEffect } from "react";
import "./Navbar.css";
import { FaBars, FaTimes } from "react-icons/fa";
import { Link, NavLink, useNavigate } from "react-router-dom";
import logo from "../../asset/logo.svg";
import { useDispatch, useSelector } from "react-redux";
import { clearCurrentUser } from "../../store/actions/user";
import { Role } from "../../models/Role";
import CartService from "../../services/CartService";
import {AiOutlineShoppingCart} from "react-icons/ai"

const Navbar = () => {
  const [click, setClick] = useState(false);
  const [cartQuantity, setCartTotalQuantity] = useState([]);

  useEffect(() => {
    CartService.getAllCartItem().then((resp) => {
      setCartTotalQuantity(resp.data.quantity);
    });
  }, []);


  const currentUser = useSelector(state => state.user)

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const logout = () => {
      dispatch(clearCurrentUser());
      navigate("/login")
    }

  const handleClick = () => {
    setClick(!click);
  };

 const menuItems = [
   {
     title: "Home",
     url: "/",
   },
   {
     title: "Order",
     url: "/fullMenu",
   }
 ];
  return (
    <nav className="navbar ">
      <div className="container navbar-wrapper">
        <div className="navbar__logo">
          <Link to="/">
            <img src={logo} alt="logo" />
          </Link>
          {currentUser?.roles[0].name === Role.ROLE_ADMIN && (
            <ul className="nav-links">
              <li>
                <NavLink to="/admin" className="nav-link">
                  Admin Page
                </NavLink>
              </li>
            </ul>
          )}
        </div>

        <div className="menu-icon" onClick={handleClick}>
          {click ? <FaTimes className="icons" /> : <FaBars className="icons" />}
        </div>

        {!currentUser && (
          <div className={click ? "navbar__right active" : "navbar__right"}>
            <ul className="nav-links">
              {menuItems.map((item) => (
                <li key={item.title}>
                  <NavLink exact={true} className="li" to={item.url}>
                    {item.title}
                  </NavLink>
                </li>
              ))}
              <NavLink to="/signup">
                <button className="btn btn-success">SignUp</button>
              </NavLink>
              <NavLink to="/login">
                <button className="btn btn-success">LogIn</button>
              </NavLink>
            </ul>
          </div>
        )}

        {currentUser && (
          <div className={click ? "navbar__right active" : "navbar__right"}>
            <ul className="nav-links">
              {menuItems.map((item) => (
                <li key={item.title}>
                  <NavLink exact={true} className="li" to={item.url}>
                    {item.title}
                  </NavLink>
                </li>
              ))}
            </ul>
            <ul className="currentUser-wrapper">
              <h6>
                Hello,
                <Link to="/userDetails">
                  {" "}
                  <span>{" " + currentUser.username} </span>
                </Link>
              </h6>
              <li>
                <button
                  className="btn btn-success logout"
                  onClick={() => logout()}
                >
                  Logout
                </button>
              </li>
              {currentUser?.roles[0].name === Role.ROLE_PREMIUM && (
                <>
                  <Link to="/cart">
                    <div className="cartIcon-wrapper">
                      <AiOutlineShoppingCart className="icon" />
                    </div>
                  </Link>
                  <p>{cartQuantity}</p>
                </>
              )}
            </ul>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
