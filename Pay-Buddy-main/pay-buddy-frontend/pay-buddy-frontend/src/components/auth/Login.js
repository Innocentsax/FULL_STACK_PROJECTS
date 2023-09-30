import React, {useRef, useState, useEffect} from "react";
import logo from "../../assets/images/logo.svg";
import Email from "./authenticationManager/Email";
import {useGoogleLogin} from '@react-oauth/google';
import googleLogo from "../../assets/images/google-logo.png";
import axios from "axios";
import {Link, useNavigate,useParams} from "react-router-dom";
import appApi from "../../apis/AppApi";
import { ToastContainer } from 'react-toastify';
import { notifyError, notifySuccess, notifyWarning } from '../notification/Toastify';
import LoadingSpin from "react-loading-spin";
import { baseURL } from "../../apis/AppApi";
import { baseURLFE } from "../../apis/AppApi";
import loginScreenImage from "../../assets/images/loginImagejpg.jpg";
import { spinnerSize} from "../../includes/Config";
import { spinnerColor } from "../../includes/Config"
import { spinnerNumberOfRotation } from "../../includes/Config";

function Login() {
  const [responseStatus, setResponseStatus] = useState(null);
  const[isLoading, setIsLoading] = useState(false)
  const navigate= useNavigate();
  const [open, setOpen] = useState(false);
  const handleClose = () => setOpen(false);
  const handleOpen = () => setOpen(true);
  const [oauth2Response, setOauth2Response] = useState(null);


  const token = localStorage.getItem("token");

  if(token){
      navigate("/pay-buddy/dashboard");
  }

  let user = {}

  const [formData, setFormData] = useState({});
  let   loginRef = useRef();

  const handleChange = (e) =>{
    e.persist();
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  }

  const login = useGoogleLogin({
    onSuccess: (codeResponse) => setOauth2Response(codeResponse),
    onError: (error) => console.log('Login Failed:', error)
  });


  const localLogin = (data) => {
    axios.post(`${baseURL}/api/v1/auth/login`,data)
        .then(res => {
          console.log(res.data.token);
          loginRef = loginRef.current.reset();
          localStorage.setItem("token",res.data.token)
          localStorage.setItem("user",JSON.stringify(res.data))
          // if(res.data.loginCount === 1){
          //   localStorage.setItem("count",res.data.loginCount)
          // }
          notifySuccess("Login suceessful");
         //navigate("/pay-buddy/dashboard");
         window.location.href=`${baseURLFE}/pay-buddy/dashboard`;
          setIsLoading(false);
        })
        .catch(err => {
          notifyError(err.response.data.message[0]);
          setResponseStatus(err.response.data.message[0])
          setIsLoading(false);
        });
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    setIsLoading(true);
    localLogin(formData);
  }

  const socialLogin = (data) =>  {
    axios.post(`${baseURL}/api/v1/auth/social-login`,data)
        .then(res => {
            localStorage.setItem("token",res.data.jwtToken)
            notifySuccess("Login suceessful");
            //navigate("/pay-buddy/dashboard");
            window.location.href=`${baseURLFE}/pay-buddy/dashboard`;
        })
        .catch(err => {
          notifyError("Internal server error!");
        });
  }

  useEffect(
      () => {
        if (oauth2Response) {
          console.log(oauth2Response);
          axios
              .get(`https://www.googleapis.com/oauth2/v1/userinfo?access_token=${oauth2Response.access_token}`, {
                headers: {
                  Authorization: `Bearer ${user.access_token}`,
                  Accept: 'application/json'
                }
              })
              .then((res) => {
                const userDetails = res.data;
                console.log(userDetails);
                user ={
                  email: userDetails.email,
                  firstName : userDetails.family_name,
                  lastName : userDetails.given_name,
                  password: "",
                  picture: userDetails.picture

                }

                localStorage.setItem("user",JSON.stringify(user));
                socialLogin(user);
               // navigate("/pay-buddy/dashboard");
              })
              .catch((err) => console.log(err));
        }
      },
      [ user ]
  );

  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-md-4 c-padding scrol-vertical col-100-percent">
          <Link to ="/"><img src={logo} alt="Logo" width="72" height="72" /></Link>
          <h6 className="h6 mb-3 mt-3 display-6 title-text" style={{color:"#000"}}>Login</h6>
          <span>Enter your details to access your account</span>

          <div className="mb-3 mt-3 form-control align-items-center social-login form-control-c" onClick={() => login()}>
            <center>
              <img src={googleLogo} alt="Google" width="25" height="25"/>
              <span> Login with Google</span>
            </center>
          </div>

          <div className="form-control line-break">
            <div className="">Or</div>
          </div>

          <form onSubmit={handleSubmit} ref={loginRef}>
            <div className="mb-3 mt-5">
              <label htmlFor="username" className="form-label">
                Email address
              </label>
              <input
                  type="text"
                  className="form-control form-control-c"
                  id="username"
                  name="email"
                  placeholder="name@companyemail.com"
                  onChange={handleChange} required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="password" className="form-label text-start">
                Password
              </label>
              <div className="float-end font-color-600">
                <a onClick={handleOpen} href="#!">
                  Forgot Password
                </a>
              </div>
              <input
                  type="password"
                  className="form-control form-control-c"
                  id="password"
                  name="password"
                  placeholder="Password123@"
                  onChange={handleChange} required
              />
            </div>

            <div className="mb-3 mt-5">
              {responseStatus && <div className="text-danger">{responseStatus}</div>}
              <button className="btn btn-primary c-submit-button">
                { isLoading &&<LoadingSpin size={spinnerSize} primaryColor={spinnerColor} numberOfRotationsInAnimation={spinnerNumberOfRotation}/>}<span>Sign in</span></button>
            </div>

            <div className="mb-3">
              Not a member? <span className="font-color-600"><Link to="/register">SignUp</Link></span>
            </div>
          </form>
        </div>

        <div className="col-md-8  bg-color-600 banner p-5 fixed-position right-screen-side">
            <div className="right-div-design">     
              <img src = {loginScreenImage}  className="img-fluid" style={{height:"100vh"}}/>
              
                <ToastContainer />

          </div>
        </div>
      </div>

      <Email open={open} handleClose={handleClose} handleOpen={handleOpen} />
    </div>
  );
}

export default Login;