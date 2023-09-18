import React from 'react'
import { BannerStyle } from './bannerStyle.style'
import decadevs from '../../../assets/Decadevs.png'
import { Link } from 'react-router-dom'
import Button from '../../../common/Button'

export default function Banner() {
  return (
    <BannerStyle style={{ backgroundImage:`url(${decadevs})` }}>
      <div className='overlay'>
      </div>
      <div className='textDiv'>
<p className='firstText'>Join the Decadevs gist </p>
<p className='firstText'>space with the latest articles</p>
<p className='firstText'>in tech world</p>
      </div>
 <div  className='buttonDiv'>
 <Button
          className="button"
          text="Get Started"
          background="#34A853"
          color="white"
          url="/login"
          height= "44px"
          width = "130px"
        />
 <Button
          className="button"
          text="See Stories"
          background="#FFFFFF"
          color="#101828;"
          url="/stories"
          height= "44px"
          width = "130px"
        />
</div>
    </BannerStyle>
  )
}
