import React, { useState } from 'react';
import {loginUser} from '../services/userAuth';
import "./Login.css";
import axios from "axios";
import { Link, useNavigate } from 'react-router-dom'

const LoginUser = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [bearer, setBearer] = useState(false);
    const navigate = useNavigate();


    async function sendLoginRequest(e) {
        e.preventDefault();
        const reqBody = {
            username: username,
            password: password,
        };

        const url = 'https://fitnesso-app-new.herokuapp.com/person/login';

//         const url = 'http://localhost:9067/person/login';
        const homeurl = "https://fitnessoapp1.web.app/";

        try {
            const loginResponse = await axios.post(url, reqBody);
            localStorage.clear();
            localStorage.removeItem("token")
            // console.log((loginResponse.data));
            localStorage.setItem(
                "personData",
                JSON.stringify(loginResponse.data.userInfo)
              );
              localStorage.getItem("personData")

            localStorage.setItem("token", loginResponse.data.token);
            localStorage.setItem("role", loginResponse.data.role);
            localStorage.setItem("username", loginResponse.data.userInfo.userName);
            //localStorage.setItem("userinfo", loginResponse.data.userInfo.json().stringify());

            window.location.replace(homeurl)

        } catch (e) {
            console.log(e);
            alert("Incorrect username or password!");
            setPassword(""); setUsername("");
            return navigate("/login");
        }

    }
    
    const [disabledButton, setDisabledButton] = React.useState(false);
    return(
        <div className="login-container">
            <div className='login'>
                <p className="sign" align="center">LOGIN</p>
                <form className='form1'>
                    <input type="text" className='username' align="center" 
                    placeholder='Username' value={username} onChange={(event) => setUsername(event.target.value)} required/>
                    
                    <input type="password" className='password' align="center" 
                    placeholder='Password' value={password} onChange={(event) => setPassword(event.target.value)} required/>
                    
                    <button type='submit' className='submit' disabled={disabledButton} onClick={sendLoginRequest} href="/">Login</button>
                    
                    <p className="forgot" align="center"><a href="#">Forgot Password?</a> </p><br></br>
                    <p className="register" align="center">No account? <a href="/signup">Register here!</a></p><br></br>
                    <p className="cancel" align="center"><a href="/">Cancel</a> </p><br></br>
                </form>
            </div>
        </div>
    );
};
export default LoginUser;
