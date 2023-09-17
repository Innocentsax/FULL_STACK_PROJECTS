import React from "react";
import { Link } from "react-router-dom";
// import FacebookIcon from "@mui/icons-material/Facebook";
// import InstagramIcon from "@mui/icons-material/Instagram";
import TwitterIcon from "@mui/icons-material/Twitter";
import footer from "../stylesheets/footer.module.css";
import "../styles/search.css";
import logo from "../logo.svg";

export default function Footer() {
  return (
    <>
      <div className={footer.container}>
        <img src={logo} className={footer.logo} alt="Logo" />
        <div className={footer.grid}>
          <Link className={footer.link} to="/">
            About Us
          </Link>
          <Link className={footer.link} to="/">
            Contact
          </Link>
          <Link className={footer.link} to="/">
            Terms and Conditions
          </Link>
        </div>
        <div className={footer.grid}>
          <div>
            <Link className={footer.link} to="/">
              <div className={footer.linkContainer}>
                <i
                  className={`fab fa-facebook-f footer.icon ${footer.icon}`}
                ></i>
                Facebook
              </div>
            </Link>
            <Link className={footer.link} to="/">
              <div className={footer.linkContainer}>
                <TwitterIcon fontSize="small" className={footer.icon} />
                Twitter
              </div>
            </Link>
            <Link className={footer.link} to="/">
              <div className={footer.linkContainer}>
                <i className={`fab fa-instagram ${footer.icon}`}></i>
                {/* <InstagramIcon fontSize="small" className={footer.icon} /> */}
                Instagram
              </div>
            </Link>
          </div>
        </div>
        <div className={footer.grid}>
          <Link className={footer.link} to="/">
            Subscribe to our newsletter
          </Link>
          <div className="input-text">
            <input type="text" placeholder="Email Address" style={footer} />
            <div>OK</div>
          </div>
        </div>
        <div className={footer.grid}>
          <Link className={footer.link} to="/">
            497 Evergreen Rd, Roseville
          </Link>
          <Link className={footer.link} to="/">
            CA 95673 +44 345 678 903
          </Link>
          <Link className={footer.link} to="/">
            adobexd@gmail.com
          </Link>
        </div>
      </div>
    </>
  );
}
