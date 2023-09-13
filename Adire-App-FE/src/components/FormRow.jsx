const FormRow = ({ type, name, value, handleChange, labelText, isReadOnly, min, children }) => {
    return (
      <div className='form-row'>
        <label htmlFor={name} className='form-label'>
          {labelText || name}
        </label>
        {children}
        <input
          min={min}
          id={name}
          type={type}
          name={name}
          value={value}
          readOnly={isReadOnly}
          onChange={handleChange}
          // autoComplete="off"
          className='form-input form-input-default'
        />
      </div>
      
    );
  };
  export default FormRow;