import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faFacebook, faTwitter, faInstagram, faLinkedin} from '@fortawesome/free-brands-svg-icons'
// import {Link} from 'react-router-dom'
import location from './img/location.png'
import phone from './img/phone.png'
import email from './img/email.png'
import envelope from './img/envelope.png'
// import ButtonMailto from './ButtonMailto';

import './Form.css'
function Text() {
  return (
    <div class='contact-info'>
      <h2 class='title'>Let's get in touch</h2>
      <h1 class='text'>Get in touch today to schedule your discovery call.</h1>
      <p class='text'>
        Just fill out this short form on the right to get started in your life
        changing journey. If you prefer, you can contact us below directly.
      </p>

      <div class='info'>
        <div class='information'>
          <img src={location} class='icon' alt='' />
          <p>7 Asajon way, Sangotedo, Lagos State, Nigeria</p>
        </div>
        <div class='information'>
      
          <img src={email} class='icon' alt='' />
          <p
            className='hand'
            onClick={() => (window.location = 'mailto:contact@fitnesso.com')}
          >
            contact@fitnesso.com
          </p>
        </div>
        <div class='information'>
          <img src={phone} class='icon' alt='' />
          <p
            className='hand'
            onClick={() => (window.location = 'tel: +234 807 651 8353')}
          >
            +234 567 893 5456
          </p>
        </div>
      </div>

      <div class='social-media'>
        <p>Connect with us :</p>
        <div class='social-icons'>
          <a href='#'>
            <FontAwesomeIcon icon={faFacebook} />
          </a>
          <a href='#'>
            <FontAwesomeIcon icon={faTwitter} />
          </a>
          <a href='#'>
            <FontAwesomeIcon icon={faInstagram} />
          </a>
          <a href='#'><FontAwesomeIcon icon={faLinkedin} /></a>
        </div>
      </div>
    </div>
  )
}

export default Text;
