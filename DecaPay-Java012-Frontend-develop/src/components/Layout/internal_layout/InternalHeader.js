import React, {useEffect, useState} from "react";
import { Link, useNavigate } from "react-router-dom";
import InternalSidebar from "./internalSidebar";
import "./InternalHeader.css"
import { Dropdown, DropdownButton, DropdownItem } from 'react-bootstrap';

const InternalHeader = () => {
  const navigate = useNavigate();


  const[firstname, setFirstName] = useState("");
  const[lastname, setLastName] = useState("");

  const url = localStorage.getItem("imagePath")


  useEffect(()=>{
    const token= localStorage.getItem("token")==null?navigate("/login"):"";
    setFirstName(localStorage.getItem("firstName"))
    setLastName(localStorage.getItem("lastName"))
  },[]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <header>
      <InternalSidebar />

      <nav
        id="main-navbar"
        className="navbar navbar-expand-lg navbar-light bg-white fixed-top"
      >
        {/* Container wrapper */}
        <div className="container-fluid">
          {/* Toggle button */}
          <button
            className="navbar-toggler"
            type="button"
            data-mdb-toggle="collapse"
            data-mdb-target="#sidebarMenu"
            aria-controls="sidebarMenu"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <i className="fas fa-bars" />
          </button>
          {/* Brand */}
          <a className="navbar-brand" href="#!">
            &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
            <img
              src="/assets/cardpayment-vHf.png"
              width={50}
              alt=""
              loading="lazy"
            />
            <span class="logo-text">Decapay</span>
          </a>
          {/* Search form */}
          {/* Right links */}
          <br />
          <ul className="navbar-nav ms-auto d-flex flex-row">
            {/* Notification dropdown */}
            <li className="nav-item dropdown">
              <ul
                className="dropdown-menu dropdown-menu-end"
                aria-labelledby="navbarDropdownMenuLink"
              >
                <li>
                  <a className="dropdown-item" href="#!">
                    Some news
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#!">
                    Another news
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#!">
                    Something else here
                  </a>
                </li>
              </ul>
            </li>

            {/* Avatar */}
            <div>

            </div>
            <li className="nav-item dropdown">
              <a
                className="nav-link dropdown-toggle hidden-arrow d-flex align-items-center"
                href="#!"
                id="navbarDropdownMenuLink"
                role="button"
                data-mdb-toggle="dropdown"
                aria-expanded="false"
              >
                <img
                  src= {url}
                  className="rounded-circle"
                  height={22}
                  alt="/assets/avatar.png"
                  loading="lazy"
                />
                &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                <h3 className="profile-name">{firstname +" "+ lastname} &#x2304;</h3>
                &nbsp;&nbsp; &nbsp;&nbsp;
              </a>
              <ul
                className="dropdown-menu dropdown-menu-end"
                aria-labelledby="navbarDropdownMenuLink"
              >
                <li>
                  <Link to="/decapay/upload" className="dropdown-item">
                    Upload Photo
                  </Link>
                </li>
                <li>
                  <Link to="profile" className="dropdown-item">
                    My profile
                  </Link>
                </li>
                <li>
                  <a className="dropdown-item" onClick={handleLogout}>
                    Logout
                  </a>
                </li>
              </ul>
            </li>
          </ul>
        </div>
        {/* Container wrapper */}
      </nav>

      <nav className="navbar navbar-expand-lg bg-body-tertiary">
        <div className="container-fluid">
          <a
            className="navbar-brand"
            href="src/decapay/src/components/Layout/external_layout/Header#!"
          >
            Navbar
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon" />
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <a
                  className="nav-link active"
                  aria-current="page"
                  href="src/decapay/src/components/Layout/external_layout/Header#"
                >
                  Home
                </a>
              </li>
              <li className="nav-item">
                <a
                  className="nav-link"
                  href="src/decapay/src/components/Layout/external_layout/Header#!"
                >
                  Link
                </a>
              </li>
              <li className="nav-item dropdown">
                <a
                  className="nav-link dropdown-toggle"
                  href="src/decapay/src/components/Layout/external_layout/Header#!"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  Dropdown
                </a>
                <ul className="dropdown-menu">
                  <li>
                    <a
                      className="dropdown-item"
                      href="src/decapay/src/components/Layout/external_layout/Header#!"
                    >
                      Action
                    </a>
                  </li>
                  <li>
                    <a
                      className="dropdown-item"
                      href="src/decapay/src/components/Layout/external_layout/Header#!"
                    >
                      Another action
                    </a>
                  </li>
                  <li>
                    <hr className="dropdown-divider" />
                  </li>
                  <li>
                    <a
                      className="dropdown-item"
                      href="src/decapay/src/components/Layout/external_layout/Header#!"
                    >
                      Something else here
                    </a>
                  </li>
                </ul>
              </li>
              <li className="nav-item">
                <a className="nav-link disabled">Disabled</a>
              </li>
            </ul>
            <form className="d-flex" role="search">
              <input
                className="form-control me-2"
                type="search"
                placeholder="Search"
                aria-label="Search"
              />
              <button className="btn btn-outline-success" type="submit">
                Search
              </button>
            </form>
          </div>
        </div>
      </nav>
      {/* Navbar */}
    </header>
  );
};

export default InternalHeader;
