import React from 'react'
import "./youtube.css"

const Youtube3 = () => {
  return (
    <div className="custom-youtube-player">
        <iframe 
        id="player" 
        type="text/html" 
        style={{ width:"100%", height:"100%" }}
        src="https://www.youtube.com/embed/YdB1HMCldJY"
        frameborder="0"> 
        </iframe>
        
    </div>
  )
}

export default Youtube3