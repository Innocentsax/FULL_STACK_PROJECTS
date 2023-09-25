import React from "react";
import "./SignUp.css";
import { userModel } from "../../models/userModel";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import Spinner from "./Spinner";

const SignUp = () => {
  const [input, setInput] = useState(userModel);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();
  const navigateToVerifyReg = () => {
    navigate("/verify-email");
  };

  // const localhostUrl = "http://localhost:9005/api/v1/user/register";
  const herokuUrl =
    "https://mentorship-payment-app.herokuapp.com/api/v1/user/register";
  const currentUrl = herokuUrl;

  function handleInput(e) {
    e.preventDefault();
    const { name, value } = e.target;
    setInput({ ...input, [name]: value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    if (
      input.firstName === "" ||
      input.lastName === "" ||
      input.email === "" ||
      input.password === "" ||
      input.phoneNumber === "" ||
      input.bvn === "" ||
      input.confirmPassword === "" ||
      input.pin === ""
    ) {
      return setError("Please fill all marked fields appropriately");
    }

    setLoading(true);
    axios
      .post(currentUrl, {
        firstName: input.firstName,
        lastName: input.lastName,
        email: input.email,
        phoneNumber: input.phoneNumber,
        bvn: input.bvn,
        password: input.password,
        confirmPassword: input.confirmPassword,
        pin: input.pin,
      })

      .then((res) => {
        console.log(res.data);
        navigateToVerifyReg();
        setError("");
        setLoading(false);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <div>
      <div className="reg">
        <div className="register">
          <h2 className="register-logo">Fintech.africa</h2>
          <h2 className="register-header">Get Started with Fintech</h2>
          <div className="reg-input-div">
            <form className="register-form" onSubmit={handleSubmit}>
              {error && input.firstName === "" ? (
                <p>
                  First Name<span className="error-astx">*</span>
                </p>
              ) : (
                <p>First Name</p>
              )}
              <input
                type="text"
                name="firstName"
                autoComplete="off"
                value={input.firstName}
                onChange={handleInput}
                placeholder="Enter your First Name"
                minLength={3}
                // required
              />
              <p></p>
              {error && input.lastName === "" ? (
                <p>
                  Last Name<span className="error-astx">*</span>
                </p>
              ) : (
                <p>Last Name</p>
              )}
              <input
                type="text"
                name="lastName"
                autoComplete="off"
                value={input.lastName}
                onChange={handleInput}
                placeholder="Enter your Last Name"
                minLength={3}
                // required
              />
              {error && input.email === "" ? (
                <p>
                  Email<span className="error-astx">*</span>
                </p>
              ) : (
                <p>Email</p>
              )}
              <input
                type="email"
                name="email"
                autoComplete="off"
                value={input.email}
                onChange={handleInput}
                placeholder="Enter your Email"
                // required
              />
              {error && input.phoneNumber === "" ? (
                <p>
                  Phone Number<span className="error-astx">*</span>
                </p>
              ) : (
                <p>Phone Number</p>
              )}
              <input
                type="text"
                name="phoneNumber"
                autoComplete="off"
                value={input.phoneNumber}
                onChange={handleInput}
                placeholder="Enter your Phone Number"
                minLength={10}
                maxLength={15}
                // required
              />
              {error && input.bvn === "" ? (
                <p>
                  BVN<span className="error-astx">*</span>
                </p>
              ) : (
                <p>BVN</p>
              )}
              <input
                type="text"
                name="bvn"
                autoComplete="off"
                value={input.bvn}
                onChange={handleInput}
                placeholder="Enter your BVN"
                minLength={11}
                maxLength={11}
                // required
              />
              {input.bvn.match(/[a-zA-Z]/) ? (
                <p className="error-msg">Enter valid BVN</p>
              ) : null}
              {error && input.password === "" ? (
                <p>
                  Password<span className="error-astx">*</span>
                </p>
              ) : (
                <p>Password</p>
              )}
              <input
                type="password"
                name="password"
                value={input.password}
                onChange={handleInput}
                placeholder="Enter password"
                minLength={6}
                // required
              />
              {error && input.confirmPassword === "" ? (
                <p>
                  Confirm Password<span className="error-astx">*</span>
                </p>
              ) : (
                <p>Confirm Password</p>
              )}
              <input
                type="password"
                name="confirmPassword"
                value={input.confirmPassword}
                onChange={handleInput}
                placeholder="Confirm password"
                // required
              />
              {input.confirmPassword !== input.password &&
              input.confirmPassword !== "" ? (
                <p id="error-msg">Password do not match</p>
              ) : null}
              {error && input.pin === "" ? (
                <p>
                  Pin<span className="error-astx">*</span>
                </p>
              ) : (
                <p>Pin</p>
              )}
              <input
                type="password"
                name="pin"
                value={input.pin}
                onChange={handleInput}
                placeholder="Enter transaction pin"
                minLength={4}
                // required
              />
              <button>Sign Up</button>
              {loading ? (
                <div className="spinner">
                  <Spinner />
                </div>
              ) : null}

              {error && <p id="error">{error}</p>}
              <h3>
                Already have an account?{" "}
                <a href="/login" className="btn-span">
                  Login
                </a>
              </h3>
            </form>
          </div>
        </div>
        <div className="register-img">
          <img alt="" src="images/reg-banner.png" />
        </div>
      </div>
    </div>
  );
};

export default SignUp;
