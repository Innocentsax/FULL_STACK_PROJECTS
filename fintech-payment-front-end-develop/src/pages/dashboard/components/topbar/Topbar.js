import React from 'react'
import "./topbar.css"

const Topbar = () => {
  return (
    <div className='topbar'>
        <div className="topbarWrapper">
            <div className="topLeft">
                <span className="logo">Fintech.africa</span>
            </div>
            <div className="topRight">
                <div className="topbarIconContainer">
                    <img src='./img/setting.jpg' alt='setting' className='topSetting'/>
                </div> 
               
                <div className="topbarIconContainer">
                    <img src='./img/Notification.jpg' alt='bell'/>
                    <span className="topIconBadge"></span>
                </div>
                <div className="topbarIconContainer">
                    <img src='./img/person.jpg' alt='person' className='topAvatar'/>
                </div>
                
            </div>
        </div>
    </div>
    
  )
}

export default Topbar