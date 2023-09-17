import React from "react";
import SignIn from "../components/SignIn";
import SignUp from "../components/SignUp";
import Footer from "../components/Footer";
// import {UserContext} from '../context/UserContext';
import { authKeysExist } from "../context/UserReducer";

const SignInSignUp = (props) => {
  React.useEffect(()=>{
  localStorage.getItem('token') !== null && props.history.push('/');
    // authKeysExist() && props.history.push('/');
  })
    return (
    <>
    <section className="sign-in-sign-up">
      <div className="page-title">
        <h2 className="page-title__main">Hello there!</h2>
        <p className="page-title__sub">
          Please Sign in or Create account to continue
        </p>
      </div>
      <div className='form-page'>
      <SignIn />
      <SignUp />
      </div>
    </section>
    <Footer />
    </>
  );
};

export default SignInSignUp;
