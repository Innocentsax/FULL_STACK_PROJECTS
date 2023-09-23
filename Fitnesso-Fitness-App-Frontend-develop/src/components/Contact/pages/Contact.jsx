import React from 'react'
import Form from '../Form'
import Text from '../Text'
import shape from '../img/shape.png'

import './Contact.css'
function Contact() {
  return (
    <div class='contact-page-container'>
      <span class='big-circle'></span>
      <img src={shape} class='square' alt='A cool image' />
      <div class='contact-page-form'>
        <Text />
        <Form />
      </div>
    </div>
  )
}

export default Contact
