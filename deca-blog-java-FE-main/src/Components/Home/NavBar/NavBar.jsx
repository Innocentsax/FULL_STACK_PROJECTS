import React from 'react'
import Logo from '../../../common/Logo'
import { NavBarStyle } from './NavBar.style'
import icon1 from '../../../assets/home.png'
import icon2 from '../../../assets/users.png'
import icon3 from '../../../assets/Notification.png'
import icon4 from '../../../assets/Icon.png'
import Button from '../../../common/Button'


export default function NavBar() {
  return (
    <NavBarStyle>
<Logo/>
<div className='iconDiv'>
<img src={icon1} alt="" />
<img src={icon2} alt="" />
<img src={icon3} alt="" />
</div>
<div >
<input type="text" placeholder="Search" className='searchDiv'/>
<img src={icon4} alt="" className='iconImage'/>
</div>
        <Button
          className="button"
          text="Get Started"
          background="#34A853"
          color="white"
          url="/login"
          height= "44px"
          width = "130px"
        />
    </NavBarStyle>
  )
}
