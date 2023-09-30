import React from 'react'
import Logo from "../../../../assets/images/LOGOKEND-01.png";
import { FaBars, FaTimes } from 'react-icons/fa'
import { Link } from "react-router-dom";
import logo from "../../../../assets/images/logo.svg";
import harmburger from "../../../../assets/images/harmburger.png";


const Header = () => {
  const token = localStorage.getItem("token");
  return (
  <>
    <nav className="navbar fixed-top navbar-expand-lg navbar-light  nav-container">
    <div className="container">
      <Link className="navbar-brand" href="/"><img src={logo} alt="Logo" width="45" height="45" loading="lazy" /><b> Pay Buddy</b></Link>
      <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span className="" style={{color:"blue"}}><img src={harmburger} height={35}/></span>
      </button>
      <div className="collapse navbar-collapse navbar-home" id="navbarText">
        <ul className="navbar-nav justify-content-end mb-2 mb-lg-0">
          <li className="nav-item">
          <Link to ="/"  className="nav-link active">Home</Link>
          </li>
          {!token &&  <li className="nav-item">
          <Link to ="/login"  className="nav-link">Login</Link>
          </li> 
          }
         
          <li className="nav-item">
          <Link to ="/register"  className="nav-link">Register</Link>
          </li>
        </ul>
        
      </div>
    </div>
  </nav>
  </>
  );
}

export default Header