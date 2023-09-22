import './fintech.css';
import React from "react";
import { FaRegUser } from 'react-icons/fa';
import { FaEnvelope } from 'react-icons/fa';
import { FaPhoneAlt } from 'react-icons/fa';
import { BsShieldLock } from 'react-icons/bs';
import { useRef, useState, useEffect } from 'react';
import axios from '../../api/axios';



export default function Signup() {
    return (
      <>
        <div className='container-fluid p-0 m-0'>
            <div className='signup-background row'>
                
                 <div className='col left'>
                     <div className='left-side'>
                         <SignupForm/>
                     </div>
                 </div>
    
                 <div className='right-side-div col-7 m-0'>
                         <div className='right-side-img'></div>
                 </div>
            </div>
         </div> 
          
      
      </>
      
    )
}
  
const FIRSTNAME_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const LASTNAME_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const BVN_REGEX = /^[0-9]+/;
const EMAIL_REGEX = /^[a-zA-Z0-9.]+@+[a-zA-Z0-9]+.+[A-z]/;
const PHONENUMBER_REGEX = /^[0-9]+/;
const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const PIN_REGEX = /^[0-9a-z]+/i;

const SIGNUP_URL = "http://localhost:3333/api/v1/user/registration";

function SignupForm() {
    const userRef = useRef();
    const errRef = useRef();

    const [firstName, setFirstName] = useState('');
    const [validName, setValidName] = useState(false);
    const [firstNameFocus, setFirstNameFocus] = useState(false);

    const [lastName, setLastName] = useState('');
    const [validLastName, setValidLastName] = useState(false);
    const [lastNameFocus, setLastNameFocus] = useState(false);

    const [bvn, setBvn] = useState('');
    const [validBvn, setValidBvn] = useState(false);
    const [bvnFocus, setBvnFocus] = useState(false);

    const [email, setEmail] = useState('');
    const [validEmail, setValidEmail] = useState(false);
    const [emailFocus, setEmailFocus] = useState(false);

    const [phoneNumber, setPhoneNumber] = useState('');
    const [validPhoneNumber, setValidPhoneNumber] = useState(false);
    const [phoneNumberFocus, setPhoneNumberFocus] = useState(false);

    const [password, setPassword] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    const [passwordFocus, setPasswordFocus] = useState(false);

    const [confirmPassword, setConfirmPassword] = useState('');
    const [validConfirmPassword, setValidConfirmPassword] = useState(false);
    const [confirmPasswordFocus, setConfirmPasswordFocus] = useState(false);

    const [transactionPin, setTransactionPin] = useState('');
    const [validPin, setValidPin] = useState(false);
    const [pinFocus, setPinFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        setValidName(FIRSTNAME_REGEX.test(firstName));
    }, [firstName])

    useEffect(() => {
        setValidLastName(LASTNAME_REGEX.test(lastName));
    }, [lastName])

    useEffect(() => {
        setValidBvn(BVN_REGEX.test(bvn));
    }, [bvn])

    useEffect(() => {
        setValidEmail(EMAIL_REGEX.test(email));
    }, [email])

    useEffect(() => {
        setValidPhoneNumber(PHONENUMBER_REGEX.test(phoneNumber));
    }, [phoneNumber])

    useEffect(() => {
        setValidPassword(PASSWORD_REGEX.test(password));
        setValidConfirmPassword(password === confirmPassword);
    }, [password, confirmPassword])

    useEffect(() => {
        setValidPin(PIN_REGEX.test(transactionPin));
    }, [transactionPin])

    useEffect(() => {
        setErrMsg('');
    }, [firstName, lastName, email, phoneNumber, bvn, password, confirmPassword, transactionPin])

    const handleSubmit = async (e) => {
        e.preventDefault();
        const v1 = FIRSTNAME_REGEX.test(firstName);
        const v2 = LASTNAME_REGEX.test(password);
        const v3 = EMAIL_REGEX.test(password);
        const v4 = PHONENUMBER_REGEX.test(password);
        const v5 = BVN_REGEX.test(password);
        const v6 = PASSWORD_REGEX.test(password);
        const v7 = PIN_REGEX.test(password);
        // if (!v1 || !v2 || !v3 || !v4 || !v5 || !v6 || !v7) {
        //     setErrMsg("Invalid Entry");
        //     return;
        // }
        try{
          const response = await axios.post(
            SIGNUP_URL, ({ firstName, lastName, email, phoneNumber, bvn, password, confirmPassword, transactionPin}),
            {
              headers: { 'Content-Type': 'application/json'},
            }
          );
          console.log(response)
          console.log(JSON.stringify(response))
          setSuccess(true)
          window.location.href='/email-verification'

        } catch(err) {
            if (!err?.response) {
                setErrMsg('No Server Response');
            } else if (err.response?.status >= 400 && err.response?.status < 409) {
              setErrMsg('Registration Failed')
            } else if (err.response) {
              setErrMsg('Email already in use');
            }
            errRef.current.focus();
        }
      };

      return (
        <>
        {success ? (
                <section>
                  <h1>Success! Check your email for confirmation</h1>
                  <p>
                    <a href='/login'>sign in</a>
                  </p>
                </section>
           ) : (
        
        
          <div className='form-container'>
            <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
            <div className='header-name'>
                <p className='header-tag'>Fintech.africa</p>
                <p className='header-text'>Get Started with Fintech</p>
            </div>
            <form onSubmit={handleSubmit}>
              <div className='form-items'>
                  <FormItem Icon={FaRegUser} name="firstName" placeHolder= "Enter your first name" type = "text" label ="First Name" changeFunction = {(e) => setFirstName(e.target.value)}/>
                  <FormItem Icon={FaRegUser} name="lastName" placeHolder="Enter your last name" type = "text" label ="Last Name" changeFunction = {(e) => setLastName(e.target.value)}/>
                  <FormItem Icon={FaEnvelope} name="email" placeHolder="Enter your email" type = "email" label ="Email" changeFunction = {(e) => setEmail(e.target.value)}/>
                  <FormItem Icon={FaPhoneAlt} name="phoneNumber" placeHolder="Enter phone number" type = "text" label ="Phone Number" changeFunction = {(e) => setPhoneNumber(e.target.value)}/>
                  <FormItem  name="bvn" placeHolder="Enter BVN" type = "text" label ="BVN" changeFunction = {(e)=>setBvn(e.target.value)}/>
                  <FormItem Icon={BsShieldLock} name="password" placeHolder="Enter your password" type = "password" label ="Password" changeFunction = {(e) => setPassword(e.target.value)}/>
                  <FormItem Icon={BsShieldLock} name="confirmPassword" placeHolder="Confirm password" type = "password" label ="Confirm Password" changeFunction = {(e) => setConfirmPassword(e.target.value)}/>
                  <FormItem name="transactionPin" placeHolder="Enter transaction pin" type = "text" label ="Pin" changeFunction = {(e) => setPinFocus(e.target.value)}/>
              </div>
              <div className='btn-container'>
                <div className='signup-button'><button type='submit' className='new-btn'> Sign Up</button></div>
              </div>
            </form>
            <div className='btn-container'>
              <div className='login'>Already have an account? <a href='/login'><span className ='login-span'>Login</span></a> </div>
            </div>
          </div> 
           )}   
        </>
      )
}
  
function FormItem(props) {
      const {Icon} = props;
      return (
          <div className="form-group forms-items">
              <label className='forms-items' htmlFor={props.name}>{props.label}</label>
              <div className='input-container'>
                <span>
                {
                  Icon && (
                    <Icon />
                  )
                }
                </span>
                <input type={props.type} className="form-control input" id={props.name}  placeholder={props.placeHolder} onChange= {(e) => props.changeFunction(e) }/>
              </div>
          </div>
      )
}
