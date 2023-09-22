import './Login.css';
import React from 'react'
import { useState } from 'react';
import { toast } from 'react-hot-toast'

export default function Login() {

  return (
    <div>
        <div className='container-fluid'>
        <div className='login-background row'>
            
            <div className='col left'>
                <div className='left-side'>
                    <LoginForm />
                </div>
            </div>

            <div className='right-side-div col-7'>
                    <div className='right-side-image'></div>
            </div>
        </div>
        </div>
    </div>
    
  )
}

function LoginForm() { 

    const emailIcon = '✉️'; 
    const passwordIcon = '🕕'; 
  
    const [userName, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const sendLoginRequest = async () => {
        const reqBody = {
            email: userName,
            password: password,
        };

     
        if(userName === "" && password === ""){
            alert("Email and Password must not be empty");
            return "";
        } else if (userName === "") {
            alert("Email is required!");
            return "";
        } else if(password === ""){
            alert("Password is required!");
            return "";
        } 
        
    try {
        const response = await fetch("http://localhost:3333/api/v1/user/login", {

            headers: {

                "Content-Type": "application/json",

            },

            method: "post",

            body: JSON.stringify(reqBody)

        })

        const {token} = await response.json()
        
        localStorage.setItem("token", token);
       

        if (response.status === 200) {

            if(localStorage.getItem("token") === "Please verify your account"){

                //toast.error("Invalid credential");
                

            } else {

                window.location.href = "dashboard"

                //toast.success("Successful Login");
              
            }

        }

        toast.error("invalid email or password")

       
} catch (error) {

    error = "Something went wrong! Check your network setting"

    toast.error(error);


}

}



// const tok = localStorage.getItem("token");
// console.log(tok);


    return (
        <div>
            <div className='form-container'>
                <div className='header-container'>
                    <h1 className='header-title'>Fintech.africa</h1>
                    <p className='login-message'>Hi, Welcome back</p>
                </div>
                <FormItem icon={emailIcon} name="email" placeHolder="Enter your email" type="email" id="email" value={userName} onChange={(e) => setUsername(e.target.value)}/>
                
                <FormItem icon={passwordIcon} name="Password" placeHolder="Enter your password" type = "password" value={password} onChange={(e) => setPassword(e.target.value)}/>

                <a href='#'><span className ='forget-pw'>Forgot Password?</span></a>
                <div className='login-button'>
                    
                    <button type='submit' className='btn' id='submit' onClick={() => sendLoginRequest()}>Login</button>
                </div>
                <div className='login'>Don't have an account? <a href='/signup'><span className ='create-account'>Create Account</span></a> </div>
            </div>    
            
        </div>

    )
}

function FormItem(props) {

    return (
       <form>
        <div className="form-group form-item">
            <label className='form-item' htmlFor={props.name}>{props.name}</label>
            <input onChange={props.onChange} value={props.value} type={props.type} className="form-control" id={props.name}  placeholder={props.icon + "   " + props.placeHolder}/>
        </div>
        </form>
      
    )

}