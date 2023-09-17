import React, { useState} from 'react'
import signinStyle from '../stylesheets/signin.module.css';
import { Link,useHistory } from "react-router-dom";
import axios from 'axios'
import { useCookies } from 'react-cookie';
const SignIn = () => {
    const [userEmail,setUserEmail]= useState('')
    const [password,setPassword]= useState('')
    const[isLoading,setIsLoading]= useState(false)
    const [error,setError]= useState(null)
    const [cookies,setCookie]=useCookies(['user'])
    const [rememberMe,setRememberMe]= useState(false);
    const History = useHistory()
    function handleSubmit(e){
        e.preventDefault();
        setError(null)
     setIsLoading(true);
     console.log(userEmail)
     axios.post('http://localhost:8080/authenticate',{email:userEmail,password:password})
     .then(response=>{
         setIsLoading(false)
         setCookie('token',response.data.token,{path:'/'})
         History.push('/')
     })
     .catch(error=>{
        setIsLoading(false)
        console.log(error.response.status)
        if(error.response.status===401){
            setError(error.response.data.message+": Invalid credentials")
        }else{
            setError("Something went wrong.Try again later")
        }
    })
    }
    return ( 
        <div className={signinStyle.container}>
            <div className={signinStyle.top}>
                <h3><strong>Hello there!</strong></h3>
                <small>Please sign in or create account to continue</small>
            </div>
            <div className={signinStyle.bottom}>
                <h5>Sign in</h5>
                <form className={signinStyle.form} onSubmit={handleSubmit}>
                {error && <><small className={signinStyle.error}>{error}</small></>}
                    <div className={signinStyle.formGroup}>
                    <label>
                      Email 
                    </label>
                    <input type="email" value={userEmail} onChange={(e)=>setUserEmail(e.target.value)} required/>
                    </div>
                    <div className={signinStyle.formGroup}>
                    <label>
                      Password
                    </label>
                    <input type="password" value={password} onChange={(e)=>setPassword(e.target.value)} required/>
                    </div>
                    <div className={signinStyle.formGroup}>
                    <input type="checkbox" value={rememberMe} onChange={(e)=>setRememberMe(e.target.checked)}/>
                    <label>
                        Remember my details
                    </label>
                    </div>
                    <div className={signinStyle.formGroup}>
                    <input type="submit" value={isLoading?'Loading...':'submit'}/>
                    </div>
                    <Link to="/forgotPassword" className={signinStyle.forgotPassword}>
                    Forgot password
                    </Link>
                </form>
            </div>
        </div>
     );
}
export default SignIn;