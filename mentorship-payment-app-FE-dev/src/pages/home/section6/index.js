import React from 'react'
import "./index.css";

const Section6 = () => {
  return (
    <div className='section6-container'>
        <div className=" mail-container">
            <h1>Subscribe on our Newsletter</h1>
            <p className="lore-p-1">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cursus in orci
                quis eleifend id. Adipiscing cras scelerisque aliquet nisi, velit,
                aliquam tortor vestibulum.
            </p>
            <div className="email-input-style">
                <input type="email" placeholder="@ Email" name="email" className="email-port"></input>
                <button className="subscribe-btn">Subscribe</button>
            </div>
            <p className="lore-p-2">We will never spam you. Only useful mails with promo and events</p>
      </div>
    </div>
  )
}

export default Section6;