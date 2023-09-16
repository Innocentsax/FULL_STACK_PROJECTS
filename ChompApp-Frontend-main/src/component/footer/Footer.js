import React from 'react'
import logo from "../../asset/logo.svg"
import "./Footer.css"

const Footer = () => {
  return (
    <footer>
      <div className="footer__wrapper container">
        <div className="footer__left">
          <img src={logo} alt="logo" />
          <p>
            Takeaway & Delivery template <br /> for small - medium businesses.
          </p>
        </div>
        <div className="footer__right">
          <div className="footer__right-flex">
            <ul>
              <h5>COMPANY</h5>
              <li>Home</li>
              <li>Order</li>
              <li>FAQ</li>
              <li>Contact</li>
            </ul>
          </div>
          <div className="footer__right-flex">
            <ul>
              <h5>TEMPLATE</h5>
              <li>Style Guide</li>
              <li>Changelog</li>
              <li>Licence</li>
              <li>Webflow University</li>
            </ul>
          </div>
          <div className="footer__right-flex">
            <ul>
              <h5>FLOWBASE</h5>
              <li>More Cloneables</li>
            </ul>
          </div>
        </div>
      </div>
      <hr className="container" />
      <div className="footer__bottom container">
        <p>
          Built by <a href="#">Flowbase</a>. Powered by <a href="#">Webflow</a>
        </p>

        <div className="footer__icon">
            
        </div>
      </div>
    </footer>
  );
}

export default Footer