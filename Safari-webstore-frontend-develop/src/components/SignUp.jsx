import React, {useState, useEffect, useContext} from 'react';
import {doRegistration} from '../services/auth-service';
import {CheckBox, InputField, Label} from './form-fields';
import Alert from "./Alert";
import { UserContext } from '../context/UserContext';

const SignUp = () => {
    const [fields, setFields] = useState({
        email: '',
        password: '',
        firstName: '',
        lastName: '',
        confirmPassword: '',
        newsletter: ''
    });
    const{login} = useContext(UserContext)
    const [alertBox, setAlertBox] = useState({state:false, message:'',type:'error'});
    const [disabledButton, setDisabledButton] = useState('');

    useEffect(() => {
        fields.password.length > 6  && !(fields.password.localeCompare(fields.confirmPassword))
            ? setDisabledButton('')
            : setDisabledButton('disabled');
    }, [fields]);

    const handleChange = (event) => {
        const {name, value, checked} = event.target;
        setFields((fields) => ({...fields, [name]: name !== 'newsletter' ? value : checked}));
    };

    return <section className='sign-up'>
        <h5 className='form-title'>CREATE ACCOUNT</h5>
        {alertBox.state && <Alert text={alertBox.message} variant={alertBox.type}/>}
        <form method='POST' onSubmit={(e) => doRegistration(e, fields, setDisabledButton, setAlertBox, login)}>
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

            <Label elementId='password' text='create password'/>
            <InputField
                type='password'
                value={fields.password}
                placeholder=''
                name='password'
                id='password'
                changeHandler={handleChange}
            />
            <Label elementId='confirm-password' text='confirm password'/>
            <InputField
                type='password'
                value={fields.confirmPassword}
                placeholder=''
                name='confirmPassword'
                id='confirm-password'
                changeHandler={handleChange}
            />

            <CheckBox name='newsletter' id="newsletter" checked={fields.newsletter} changeHandler={handleChange}/>
            <Label elementId="newsletter" text="i want to receive safari newsletters with the best deals & offers"/>
            <button type='submit' className='btn-block btn-block--contained' disabled={disabledButton}>create account
            </button>
        </form>
    </section>;
}

export default SignUp;