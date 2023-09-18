import React, { useState,useEffect } from "react";
import "./login.css";
import { Link, useNavigate } from "react-router-dom";
import EmailModal from "../../passwordreset/EmailModal";
import axios from "axios";
import ResponseMessage from "../../globalresources/modals/ResponseMessage";
import Loader from "../../globalresources/Loader";
import {baseEndpoint, baseUrl} from "../../globalresources/Config";

function Login() {
  const [isSpinning, setisSpinning]=useState(false);
  const navigate = useNavigate();

  useEffect(()=>{
    const token= localStorage.getItem("token")==null?"":navigate("/decapay/dashboard");
  },[]);

  const [formData, setFormData] = useState({});

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleChange = (e) => {
    e.persist();
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleClick = (e) => {
    e.preventDefault();
    setisSpinning(true);
    mainData(formData);
  };

  const mainData = async (data) => {
    try {
      const res = await axios.post(
        baseEndpoint+"/api/v1/auth/signin",
        data
      );

      const result = await res.data;
      localStorage.setItem("token", result.token);
      localStorage.setItem("phoneNumber", result.phoneNumber);
      localStorage.setItem("imagePath", result.imagePath);
      localStorage.setItem("lastName", result.lastName);
      localStorage.setItem("firstName", result.firstName);
      localStorage.setItem("userId", result.userId);
      localStorage.setItem("email", result.email);
      console.log(result);
      console.log(result.token);
      console.log(result.imagePath);
      setisSpinning(false);


      navigate("/decapay/dashboard");
    } catch (error) {
      console.log(error);
      setisSpinning(false);
    }
  };

  return (
    <>
      <form onSubmit={handleClick}>
        <div className="login-decapay-3rZ">
          <div className="frame-8671-N89">
            <div className="frame-8670-7Ld">
              <img className="pay-Eg9" src="./assets/pay-iFT.png" />
              <Link to="/">
                <p className="decapay-mg5">DecaPay</p>{" "}
              </Link>
            </div>
            <div className="frame-8669-JAD">
              <p className="login-Dny">Login</p>
              <div className="frame-8668-MeH">
                <div className="frame-8667-K5K">
                  <div className="frame-8666-GmF">
                    <div className="frame-8665-S9w">
                      <div className="frame-6-oFP">
                        <div className="frame-4-kRX">
                          <p className="email-f2h">Email</p>
                          <input
                            className="frame-2-B13"
                            name="email"
                            placeholder="Enter your email"
                            type="email"
                            onChange={handleChange}
                          />
                        </div>
                        <div className="frame-5-44q">
                          <p className="password-21f">Password</p>
                          <input
                            className="frame-3-kyF"
                            name="password"
                            placeholder="Enter your password"
                            type="password"
                            onChange={handleChange}
                          />
                        </div>
                      </div>
                      <div className="frame-8664-rFb">
                        <div className="rectangle-5-Q2D"></div>
                        <p className="remember-login-WL9">Remember login</p>
                      </div>
                    </div>
                    <button className="frame-3-dvZ" type="submit">Sign in
                      <Loader status={isSpinning}/>
                    </button>
                  </div>
                  <p className="forgot-password-L4H">
                    <a href="#!" onClick={handleOpen}>
                      Forgot password?
                    </a>
                  </p>
                </div>

                <p className="dont-have-an-account-create-account-GTj">
                  <span>Don’t have an account?</span>
                  {/* <span class="dont-have-an-account-create-account-GTj-sub-0">
                  <input
                    class="frame-3-dvZ"
                    value="Sign In"
                    type="submit"
                    onClick={handleClick}
                  />
                </div>
                <p class="forgot-password-L4H">Forgot password?</p>
              </div>
              <p class="dont-have-an-account-create-account-GTj">
                <span class="dont-have-an-account-create-account-GTj-sub-0">
                  Don’t have an account?{" "}
                </span> */}
                  <Link to="/signup">
                    {" "}
                    <span className="dont-have-an-account-create-account-GTj-sub-1">
                      Create Account{" "}
                    </span>{" "}
                  </Link>
                </p>
              </div>
            </div>
          </div>
        </div>
      </form>

      <div>
        {<EmailModal
          handleClose={handleClose}
          handleOpen={handleOpen}
          open={open}
        />}

      </div>
    </>
  );
}
export default Login;
