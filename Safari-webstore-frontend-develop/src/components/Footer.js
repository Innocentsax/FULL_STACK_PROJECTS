import React from 'react';
import '../styles/Layout/_footer.scss';
// import './css/BackToTop.css'
import '../styles/Components/_button.scss'
import { Link } from 'react-router-dom';
import { BackTop } from 'antd';

function Footer() {
  return (
    <div className='footer-container'>
      <div class='footer-links'>
        <div className='footer-link-wrapper'>
          <div class='footer-link-items'>
          <img src="https://safari-web-store.web.app/images/navbar/Logo.png" alt="Footer Logo" />
          </div>
        </div>
        <div className='footer-link-wrapper'>
          <div class='footer-link-items'>

            <Link to='/sign-up'>About Us</Link>
            <Link to='/'>Contact</Link>
            <Link to='/'>Terms & Conditions</Link>
          </div>
        </div>
        <div className='footer-link-wrapper'>
          <div class='footer-link-items'>
            <Link to='/'>Facebook</Link>
            <Link to='/'>Twitter</Link>
            <Link to='/'>Instagram</Link>
          </div>
        </div>
        <div className='footer-link-wrapper'>
          <div class='footer-link-items'>
          <form className="">
            <input className='footer-input' name='email' type='email' placeholder='Email Address'
            />
            <input type="submit" value="OK" class="subscribe-button"></input>
          </form>
          </div>
        </div>
        <div className='footer-link-wrapper'>
          <div class='footer-link-items'>
          <p>Safari web store<br/>
              +23401234567890<br />
              safari@localhost.com 
              </p>
          </div>
        </div>
      < BackTop>
        <button className ="backtotop" id="backtotop" title="Go to top"><img src="/images/backtotop-icon.svg" alt="backtotop" className="top-icon"/></button>
      </BackTop>

      </div>
    </div>
  );
}

export default Footer;
