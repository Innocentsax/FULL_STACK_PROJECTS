import React, { useRef } from "react";
import navbar from "../stylesheets/navbar.module.css";
import { Link } from "react-router-dom";
import { TextField } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import Logo from "../logo.svg";

export default function NavBar({
  sidebarState,
  setSideBarState,
  toggleSidebar,
}) {
  return (
    <>
      <div className={navbar.container}>
        <div
          className={navbar.item}
          onClick={() => toggleSidebar(setSideBarState(!sidebarState))}
        >
          <i className="fas fa-bars"></i>
        </div>
        <div className={navbar.item}>
          <Link className={navbar.link} to="/">
            Home
          </Link>
        </div>
        <div className={navbar.item}>
          <Link className={navbar.link} to="/clothes">
            Clothes
          </Link>
        </div>
        <div className={navbar.item}>
          <Link className={navbar.link} to="/shoes">
            Shoes
          </Link>
        </div>
        <div className={navbar.item}>
          <Link className={navbar.link} to="/accessories">
            Accessories
          </Link>
        </div>
        <div className={navbar.item}>
          <Link className={navbar.link} to="/logo">
            <img src={Logo} alt="logo" />
          </Link>
        </div>
        <div className={navbar.item}>
          <TextField
            id="standard-basic"
            variant="standard"
            placeholder="Search..."
            className={navbar.inputStyle}
            InputProps={{
              endAdornment: <SearchIcon />,
            }}
          />
        </div>
        <div className={navbar.item}>
          <Link className={navbar.link} to="/account">
            <i class="fas fa-user fa-lg"></i>
          </Link>
        </div>
        <div className={navbar.item}>
          <Link className={navbar.link} to="/cart">
            <i class="fas fa-cart-plus fa-lg" style={{ color: "#ED165F" }}></i>
          </Link>
        </div>
        <div className={navbar.item} id={navbar["fav-icon"]}>
          <Link className={navbar.link} to="/favourite">
            <i class="fas fa-heart fa-lg"></i>
          </Link>
        </div>
      </div>
    </>
  );
}
