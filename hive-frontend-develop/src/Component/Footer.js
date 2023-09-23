import React from 'react'
import Logo from '../Assets/logo.svg'
import Instagram from '../Assets/Instagram.svg'
import Twitter from '../Assets/Twitter.svg'
import YouTube from '../Assets/Youtube.svg'


//new addition
export const Footer = () => {
  return (
    <div className="footer-container">
        <div className="footer-container-first">
          <div className="footer-logo-container">
              <img src={Logo} alt="Hive logo navebar" />
          </div>
          <div className="footer-links-container">
            <div>About</div>
            <div>Privacy</div>
            <div>FAQ</div>
          </div>
        </div>
        <div className='footer-container-second'>
          <div className='divider'></div>
          
        </div>
        <div className='footer-container-link'>
          <div className="footer-copy-container">
              <p className="footer-text">© 2023 Hive. All rights reserved.</p>
          </div>
          <div className="footer-social-container">
              <div className="footer-icon">
                <img src={Instagram} alt="instagram icon Hive"/>
                <img src={Twitter} alt="twitter icon Hive"/>
                <img src={YouTube} alt="youtube icon Hive"/>
              </div>
              <span>Help@hive.com</span>
          </div>
        </div>
        
        
    </div>
  );
};

export default Footer;
