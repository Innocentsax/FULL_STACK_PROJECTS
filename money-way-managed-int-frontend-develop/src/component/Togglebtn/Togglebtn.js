import React from 'react'
import "./togglebtn.css";


const Togglebtn = () => {
  return (
        <label className='switch'>
            <input type='checkbox'/>
            <span className='slider'></span>
        </label>
  )
}

export default Togglebtn