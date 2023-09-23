import React from 'react'
import "./youtube.css"

const HeaderVideo = () => {
  return (
    <div className="custom-youtube-player">
        <iframe 
        id="player" 
        type="text/html" 
        style={{ width:"100%", height:"100%" }}
        src="https://www.pexels.com/video/a-man-exercising-in-the-gym-4920813/iL11s"
        frameborder="0"> 
        </iframe>
    </div>
  )
}

export default HeaderVideo