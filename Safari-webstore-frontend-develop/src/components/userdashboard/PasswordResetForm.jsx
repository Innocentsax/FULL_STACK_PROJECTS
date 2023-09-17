import React, {useState, useEffect} from 'react';
import {InputField, Label} from "../form-fields";


const PasswordResetForm = ({fields, handleChange})=>{

    useEffect(() => {
     //
    }, []);

    return(
        <div className="account-information__form--password-reset slideInDown">
            <form method="POST">
                <Label elementId='current-password' text='current password'/>
                <InputField
                    type='password'
                    value={fields.password}
                    placeholder=''
                    name='password'
                    id='current-password'
                    changeHandler={handleChange}
                />
                <Label elementId='newPassword' text='new password'/>
                <InputField
                    type='password'
                    value={fields.newPassword}
                    placeholder=''
                    name='newPassword'
                    id='newPassword'
                    changeHandler={handleChange}
                />
                <Label elementId='confirm-password' text='confirm new password'/>
                <InputField
                    type='password'
                    value={fields.confirmNewPassword}
                    placeholder=''
                    name='confirmNewPassword'
                    id='confirm-password'
                    changeHandler={handleChange}
                />
            </form>
        </div>
    )
}

export default PasswordResetForm;