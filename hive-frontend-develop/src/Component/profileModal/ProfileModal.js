import React from 'react'
import './ProfileModal.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faSignOutAlt } from '@fortawesome/free-solid-svg-icons';

export const ProfileModal = () => {
  return (
    <div className='profileModal'>
        <div className='profileModal-container'>
        <div className='email-username-container'>
            <div className='email-username'>
            <h4 className='username'>Judith Obinna </h4>
            <p className='email'>judithObinna@gmail.com</p>
            </div> 
        </div>
        <hr/>
        <div className='account-logout-container'>
            <div className='account'>
            <a href="#">
              <FontAwesomeIcon icon={faUser} color="black" />
            </a>
            <p>Account</p>
            </div>
            <div className='logout'>
            <a href="#">
              <FontAwesomeIcon icon={faSignOutAlt} color="black" />
            </a>
            <p>Logout</p>
            </div>
        </div>
        </div>
    </div>
  )
}
