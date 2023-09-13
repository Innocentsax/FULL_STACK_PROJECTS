import React from 'react'
import main from '../assets/images/main.svg';
import Wrapper from '../assets/wrappers/LandingPage';
import { Logo } from '../components';
import { Link } from 'react-router-dom';
import InstagramIcon from '@mui/icons-material/Instagram';
import YouTubeIcon from '@mui/icons-material/YouTube';
import TwitterIcon from '@mui/icons-material/Twitter';


const Landing = () => {
  
  return (
    <Wrapper>
      <nav>
        <div className='nav-center'>
          <Logo />
          {/* <Navbar /> */}
          {/* <button type='button' className='toggle-btn' onClick={toggle}>
            <FaAlignLeft />
          </button> */}
          <div className=''>
            <Link to='#' className='nav-btn about-btn'>
              about
            </Link>
            <Link to='/register' className='nav-btn'>
              login
            </Link>
            <Link to='/register' className='btn'>
              get started
            </Link>
          </div>
        </div>
      </nav>
      <div className='landing-section'>
        <div className='container page'>
          <div className='background'>
            <svg width="710" height="626" viewBox="0 0 710 626" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M41.2418 0H753V507.24C753 507.24 189.04 774.45 41.2418 507.24C-51.5522 339.475 41.2418 0 41.2418 0Z" fill="#9B8AFB"/>
            </svg>

          </div>
          {/* info */}
          <div className='info'>
            <h1>
              Streamline communication,<br/> track progress, and make<br/> data-driven decisions
            </h1>
            <p>
              Experience seamless collaboration and increased productivity with our user-friendly platform, designed to meet the needs of modern businesses of all sizes.
            </p>
            <Link to='/register' className='btn btn-hero'>
              get started
            </Link>
          </div>
          <img src={main} alt='job hunt' className='img main-img' />
        </div>
        <footer className="section footer">
          <div className='section-center'>

            <Logo />
            <ul className="footer-links">
              <li>
                <Link href="#" className="footer-link scroll-link">about</Link>
              </li>
              <li>
                <Link href="#" className="footer-link scroll-link">privacy</Link>
              </li>
              <li>
                <Link href="#" className="footer-link scroll-link">FAQ</Link>
              </li>
              
            </ul>
            <hr />
            <div className="footer-bottom">
              <p className="copyright">
                All rights reserved. &copy; <span>{new Date().getFullYear()}</span> Adire.
              </p>

              <div>
                <ul className="footer-icons">
                  <li>
                    <a href="https://www.twitter.com" target="_blank" rel="noopener noreferrer" className="footer-icon">
                      <InstagramIcon className="footer-svg" />
                    </a>
                  </li>
                  <li>
                    <a href="https://www.twitter.com" target="_blank" rel="noopener noreferrer" className="footer-icon">
                      <TwitterIcon className="footer-svg" />
                    </a>
                  </li>
                  <li>
                    <a href="https://www.twitter.com" target="_blank" rel="noopener noreferrer" className="footer-icon">
                      <YouTubeIcon className="footer-svg" />
                    </a>
                  </li>
                </ul>
                <p className='support'>Help@adire.com</p>
              </div>

            </div>
          </div>  
        </footer>
      </div>
    </Wrapper>
  );
};

export default Landing;