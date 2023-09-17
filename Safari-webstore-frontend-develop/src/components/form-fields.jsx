import React from 'react';

const InputField = ({type, placeholder,name, value,required, id,error,changeHandler, ...props})=>{
    return(
        <input className = 'input-field' type={type} placeholder={placeholder} name={name} value = {value} required={required} id={id} error={error} onChange = {changeHandler}/>
    );
}

const SelectField = ()=>{
    return (<></>);
}

const CheckBox = ({name, value, id,checked,changeHandler, ...props})=>{
    return (<input type='checkbox' name={name} value={value} id={id} props checked={checked} className='check-box' onChange={changeHandler}/>);
}

const Label = ({elementId, id, text})=>(
    <label htmlFor={elementId} id={id} className='label'>{text}</label>
);


export {
    InputField, SelectField, CheckBox,Label,
};