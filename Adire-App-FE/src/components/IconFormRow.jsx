const IconFormRow = ({ type, name, value, handleChange, labelText, placeholder, isReadOnly, children }) => {
    return (
      <div className='form-row input_container'>
        <label htmlFor={name} className='form-label'>
          {labelText || name}
        </label>
        {children}
        <input
          id={name}
          readOnly={isReadOnly}
          type={type}
          name={name}
          value={value}
          placeholder={placeholder}
          onChange={handleChange}
          // autoComplete="off"
          className='form-input'
        />
      </div>
      
    );
};

export default IconFormRow;