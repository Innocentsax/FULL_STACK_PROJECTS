import React, { useState, useEffect } from 'react';
import { IconFormRow } from '../components';
import Wrapper from '../assets/wrappers/RegisterPage';
import { toast } from 'react-toastify';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser, registerUser, toggleIsMember, googleRegister } from '../features/user/userSlice';
import { Link, useNavigate } from 'react-router-dom';
import { NameInputIcon, TelephoneInputIcon, PasswordInputIcon, EmailInputIcon } from '../utils/SVGIcons';
import { GoogleLogin } from '@react-oauth/google';
import { decodeJwt } from 'jose'

const initialState = {
  firstName: '',
  lastName: '',
  email: '',
  phoneNumber: '',
  password: '',
  confirmPassword: '',
  // isMember: true,
};

// 178688632571-jmhkftmvgd028u2frl7ehs7jvqempa0v.apps.googleusercontent.com ID
// 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   Secret


function Register() {
  
  const [values, setValues] = useState(initialState);
  const { user, isLoading, isMember } = useSelector((store) => store.user);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setValues({ ...values, [name]: value });
  };
  const onSubmit = async (e) => {
    e.preventDefault();
    const { firstName, lastName, email, phoneNumber, password, confirmPassword } = values;
    if (!email || !password || (!isMember && !firstName) || (!isMember && !lastName) || (!isMember && !phoneNumber)  || (!isMember && !confirmPassword)) {
      toast.error('Please fill out all fields');
      return;
    }
    if (!isMember && (password !== confirmPassword)) {
      toast.error('Password does not match');
      return;
    }
    if (isMember) {
      dispatch(loginUser({ email: email, password: password }));
      return;
    }
    await dispatch(registerUser({ firstName:firstName, lastName, email, phoneNumber, password, confirmPassword }));
    setValues(initialState);
    // if (isMember) {
    //   setValues(initialState);
    // }
    
  };

  useEffect(() => {
   
    if (user) {
      setTimeout(() => {
        navigate('/');
      }, 1000);
    }
  }, [user, navigate]); // eslint-disable-next-line


  return (
    <Wrapper >
        {/* <Logo /> */}
      <form className='form' onSubmit={onSubmit}>
        
        <h3>{isMember ? 'hi, welcome back' : 'create your account'}</h3>
        {/* first name field */}
        {!isMember && (
          <IconFormRow
            type='text'
            name='firstName'
            value={values.firstName}
            placeholder="Type your first name"
            labelText="first name"
            handleChange={handleChange}
          >
            <NameInputIcon />
          </IconFormRow>
        )}
        {/* last name field */}
        {!isMember && (
          <IconFormRow
            type='text'
            name='lastName'
            value={values.lastName}
            placeholder="Type your last name"
            labelText="last name"
            handleChange={handleChange}
          >
            <NameInputIcon />
          </IconFormRow>
        )}
        {/* email field */}
        <IconFormRow
          type='email'
          name='email'
          value={values.email}
          placeholder="Type your email"
          handleChange={handleChange}
        >
          <EmailInputIcon />
        </IconFormRow>
        {/* Phone Number field */}
        {!isMember && (
          <IconFormRow
          type='tel'
          name='phoneNumber'
          value={values.phoneNumber}
          labelText="phone number"
          placeholder="Enter your phone number"
          handleChange={handleChange}
        >
          <TelephoneInputIcon />
        </IconFormRow>
        )}

        {/* password field */}
        <IconFormRow
          type='password'
          name='password'
          value={values.password}
          placeholder="Enter your password"
          handleChange={handleChange}
        >
          <PasswordInputIcon />
        </IconFormRow>

        {/* confirm password field */}
        {!isMember && (
          <IconFormRow
          type='password'
          name='confirmPassword'
          value={values.confirmPassword}
          labelText="confirm password"
          placeholder="Enter your confirm password"
          handleChange={handleChange}
        >
          <PasswordInputIcon />
        </IconFormRow>
        )}

        <Link to={"/forgot-password"} className='forgot-password-text'>Forgot password?</Link>
        

        <button type='submit' className='btn btn-block' disabled={isLoading}>
          {isLoading ? 'loading...' : 'submit'}
        </button>
        <p>Or</p>
        
        {/* <button className="btn google-btn btn-block button">
          <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid" viewBox="0 0 256 262">
            <path fill="#4285F4" d="M255.878 133.451c0-10.734-.871-18.567-2.756-26.69H130.55v48.448h71.947c-1.45 12.04-9.283 30.172-26.69 42.356l-.244 1.622 38.755 30.023 2.685.268c24.659-22.774 38.875-56.282 38.875-96.027"></path>
            <path fill="#34A853" d="M130.55 261.1c35.248 0 64.839-11.605 86.453-31.622l-41.196-31.913c-11.024 7.688-25.82 13.055-45.257 13.055-34.523 0-63.824-22.773-74.269-54.25l-1.531.13-40.298 31.187-.527 1.465C35.393 231.798 79.49 261.1 130.55 261.1"></path>
            <path fill="#FBBC05" d="M56.281 156.37c-2.756-8.123-4.351-16.827-4.351-25.82 0-8.994 1.595-17.697 4.206-25.82l-.073-1.73L15.26 71.312l-1.335.635C5.077 89.644 0 109.517 0 130.55s5.077 40.905 13.925 58.602l42.356-32.782"></path>
            <path fill="#EB4335" d="M130.55 50.479c24.514 0 41.05 10.589 50.479 19.438l36.844-35.974C195.245 12.91 165.798 0 130.55 0 79.49 0 35.393 29.301 13.925 71.947l42.211 32.783c10.59-31.477 39.891-54.251 74.414-54.251"></path>
          </svg>
          google
        </button> */}
        

        <GoogleLogin
          className='google-btn'
          onSuccess={credentialResponse => {
            console.log(credentialResponse.credential);
            const payload = decodeJwt(credentialResponse?.credential);
            console.log({payload});
            dispatch(googleRegister({ accessToken: credentialResponse?.credential }));
          }}
          onError={() => {
            console.log('Login Failed');
            toast.error('Login Failed');
          }}
          useOneTap
        />
        <p className='text-small'>
          {isMember ? 'Don\'t have an account?' : 'Already have an account?'}
          <button type='button' onClick={() => dispatch(toggleIsMember())} className='member-btn'>
            {isMember ? 'Register' : 'Login'}
          </button>
        </p>
      </form>
    </Wrapper>
  );
}
export default Register;