import React from 'react';

const Alert=({text,variant})=>{
    let _variant = variant || 'error';

    return(
        <div className={_variant === 'success'?'alert alert--success':'alert alert--error'}>{text}</div>
    );
}

export default Alert;