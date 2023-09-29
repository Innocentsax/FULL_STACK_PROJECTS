import React from 'react';
import './input.css';

const Input = ({id, label, type, placeholder}) => {
  return (
    <div className='form-group input-cont'>
        <label htmlFor={id} className='form-label visually-hidden'>{label}</label>
        <input type={type} placeholder={placeholder} id={id} className='form-control border-x-0 border-t-0 border-b-1 border-gray-200 rounded-none font-thin text-lg pl-0 pt-5 shadow-none outline-none'/>
    </div>
  )
}

export default Input;