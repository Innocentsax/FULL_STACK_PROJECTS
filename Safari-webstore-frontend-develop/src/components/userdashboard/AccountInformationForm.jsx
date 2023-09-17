import React, {useEffect} from 'react';
import {CheckBox, InputField, Label} from "../form-fields";
// import {config} from '../../services/baseUrl';
import axios from 'axios';

const AccountInformationForm = ({showResetPassword, fields, handleChange})=>{

    useEffect( () => {
   

    }, [fields]);


    return(
        <div className='account-information__form--account-information'>
            {/*{alertBox.state && <Alert text={alertBox.message} variant={alertBox.type}/>}*/}
            <form method='POST' onSubmit={(e) => alert('called')}>
                <Label elementId='first-name' text='first name'/>
                <InputField
                    type='text'
                    value={fields.firstName}
                    placeholder=''
                    id='first-name'
                    name='firstName'
                    changeHandler={handleChange}
                />
                <Label elementId='last-name' text='last name'/>
                <InputField
                    type='text'
                    value={fields.lastName}
                    placeholder=''
                    id='last-name'
                    name='lastName'
                    changeHandler={handleChange}
                />
                <Label elementId='email' text='email'/>
                <InputField
                    type='email'
                    value={fields.email}
                    placeholder=''
                    id='email'
                    name='email'
                    changeHandler={handleChange}
                />
                <Label elementId='gender' text='gender'/>
                    
                <select className='input-field-select' name="gender" id="gender" value={fields.gender} onChange={handleChange}>
                    <option value="male">male</option>
                    <option value="female">female</option>
                </select>
                <Label elementId='dateOfBirth' text='date of birth'/>
                <InputField
                    type='date'
                    placeholder=''
                    name='dateOfBirth'
                    id='dateOfBirth'
                    changeHandler={handleChange}
                    value = {fields.dateOfBirth}
                />
                <span style={{cursor:'pointer', color:'#ED165F', margin:'5px auto'}} className='btn-text' onClick={()=> showResetPassword(show=>!show)}>Change password</span>
                <br/>
                <CheckBox name='newsletter' id="newsletter" checked={fields.newsletter} changeHandler={handleChange}/>
                <Label elementId="newsletter" text="newsletter subscription"/>

            </form>
        </div>
    );
}

export default AccountInformationForm;