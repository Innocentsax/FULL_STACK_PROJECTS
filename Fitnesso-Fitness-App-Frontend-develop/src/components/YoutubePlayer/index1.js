import React from 'react'
import "./youtube.css"

const Youtube = () => {
  const videoId = "";
  return (
    <div className="custom-youtube-player">
        <iframe 
        id="player" 
        type="text/html" 
        style={{ width:"100%", height:"100%" }}
        src="https://www.youtube.com/embed/YqFPuxCq0g4"
        frameborder="0"> 
        </iframe>
        
    </div>
  )
}

export default Youtube