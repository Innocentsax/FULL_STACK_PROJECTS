import React from "react";
import { FaFacebookF } from "react-icons/fa";
import { FaTwitter } from "react-icons/fa";
import { FaLinkedinIn } from "react-icons/fa";
import { FaGoogle } from "react-icons/fa";
import { Link } from "react-router-dom";
import "./index.css";

const Footer = () => {
  return (
    <footer className="footer-container">
      <div className="foot-style">
        <div className="footer-heading footer-1">
          <h4>Fintech.africa</h4>
        </div>
        <div className="footer-heading_middle">
          <Link color="white" to="/">
            Home
          </Link>
          <Link color="white" to="/about">
            About Us
          </Link>
          <Link color="white" to="/features">
            Features
          </Link>
          <Link color="white" to="/contactUs">
            Contact Us
          </Link>
        </div>
        <div className="icons">
          <FaFacebookF className="icon" />
          <FaTwitter className="icon" />
          <FaLinkedinIn className="icon" />
          <FaGoogle className="icon" />
        </div>
      </div>
      <div className="footer__bottom">
        <p>&copy; {new Date().getFullYear()} All rights reserved </p>
        <div className="footer-heading_middle">
          <p>Privacy Policy</p>
          <p>Terms of Condition</p>
          <p>Legal</p>
          <p>Help</p>
        </div>
        <div className="footer-heading footer-3">
          <p>English Version</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
