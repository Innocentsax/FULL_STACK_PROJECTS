import React, { useState } from "react";
import "./SignUp.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import Loader from "../../globalresources/Loader";
import ResponseMessage from "../../globalresources/modals/ResponseMessage";
import {baseEndpoint} from "../../globalresources/Config";

function SignUp() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({});

  const [responseMessage, setResponseMessage] =useState(null);
  const [isSpinning, setisSpinning]=useState(false);

  //Post Method
  const handleChange = (e) => {
    e.persist();
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    setResponseMessage(null);
    setisSpinning(true);

    console.log(formData);

    /* Post Method */

    try {
      const response = await axios.post(
        baseEndpoint+"/api/v1/auth/register",
        formData
      );
      console.log(response);
      setisSpinning(false);
      setResponseMessage("Registration successful");

      setTimeout (()=>{ navigate("/login");},3000)
     
    } catch (error) {
      console.log(error.message);
      setisSpinning(false);
      setResponseMessage("error : "+ error.message + "- an error has occurred");
    }

    // fetch("http://127.0.0.1:8082/", {
    //   method: "POST",
    //   headers: { "Content-Type": "application/json" },
    //   body: JSON.stringify(data.formData),
    // }).then((res) => {
    //   console.log("Saved to DataBase");
    //   return res.json();
    // }).then((res) => {
    //   console.log(res);
    // });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div class="sign-up-decapay-3kM">
        <div class="frame-8671-Yh7">
          <div class="frame-8670-u1s">
            <img class="pay-ped" src="./assets/pay-YWm.png" />
            <Link to="/">
              {" "}
              <p class="decapay-koB">DecaPay</p>{" "}
            </Link>
          </div>
          <div class="frame-8669-UjB">
            <p class="create-an-account-1z1">Create an account</p>
            <div class="frame-8668-vbB">
              <div class="frame-8666-HAq">
                <div class="frame-6-2eD">
                  <div class="frame-4-AkR">
                    <p class="first-name-vzV">First Name</p>
                    <input
                      class="frame-2-fBP"
                      placeholder="Enter your first name"
                      name="firstName"
                      type="text"
                      onChange={handleChange}
                    />
                  </div>
                  <div class="frame-6-LYR">
                    <p class="last-name-JVF">Last Name</p>
                    <input
                      class="frame-2-3Sq"
                      placeholder="Enter your last name"
                      name="lastName"
                      type="text"
                      onChange={handleChange}
                    />
                  </div>
                  <div class="frame-7-wHK">
                    <p class="email-6g1">Email</p>
                    <input
                      class="frame-2-35T"
                      placeholder="Enter your email"
                      name="email"
                      type="email"
                      onChange={handleChange}
                    />
                  </div>
                  <div class="frame-8-wAq">
                    <p class="phone-number-6pR">Phone Number</p>
                    <input
                      class="frame-2-Efj"
                      placeholder="Enter your phone number"
                      name="phoneNumber"
                      type="number"
                      onChange={handleChange}
                    />
                  </div>
                  <div class="frame-5-Lim">
                    <p class="password-confirmation-Jfb">Password</p>
                    <input
                      class="frame-3-qQd"
                      placeholder="Enter Password"
                      name="password"
                      type="password"
                      onChange={handleChange}
                    />
                  </div>
                  <div class="frame-5-Lim">
                    <p class="password-confirmation-Jfb">Confirm Password</p>
                    <input
                      class="frame-3-qQd"
                      placeholder="Confirm Password"
                      name="confirmPassword"
                      type="password"
                      onChange={handleChange}
                    />
                  </div>
                </div>
                <button className="frame-3-jku"  value="Sign Up"
                        type="submit">
                  Sign up <Loader status={isSpinning}/>
                </button>
              </div>
              <p class="already-have-an-account-login-FDT">
                <span class="already-have-an-account-login-FDT-sub-0">
                  Already have an account?{" "}
                </span>
                <Link to="/login">
                  <span class="already-have-an-account-login-FDT-sub-1">
                    Login
                  </span>
                </Link>
              </p>
            </div>
          </div>
        </div>
      </div>
      {responseMessage && <ResponseMessage message={responseMessage}  />}
    </form>
  );
}

export default SignUp;
