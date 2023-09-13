import React from 'react'

const MeasurementFormRow = ({ type, name, value, handleChange, labelText }) => {
  
  return (
    <div className='form-row'>
      <label htmlFor={name} className='form-label'>
        {labelText || name}
      </label>
      <input
        id={name}
        type={type}
        name={name}
        value={value}
        onChange={handleChange}
        className='form-input form-input-default'
      />
    </div>
    
  );
}

export default MeasurementFormRow;