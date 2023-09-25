import React from "react";
import { useState } from "react";
import { useAuth } from "../../reducers/authState";
import { useNavigate } from "react-router-dom";

import "./login.css";

const Login = () => {
  // validation
  // eslint-disable-next-line
  const { loading, login } = useAuth();
  const navigate = useNavigate();

  const initialValues = { email: "", password: "" };

  // const { dispatch } = useContext(AuthContext);
  const [formValues, setFormValues] = useState(initialValues);
  const [formErrors, setFormErrors] = useState({ email: "", password: "" });
  const [, setIsSubmit] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    validate(formValues);
    login({
      email: formValues.email,
      password: formValues.password,
    });

    navigate("/dashboard");
    setIsSubmit(true);
  };

  const validate = (values) => {
    const regex =
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    const validEmail = regex.test(values.email);
    console.log(validEmail, values);

    if (validEmail === false) {
      setFormErrors({ ...formErrors, email: "invalid email " });
      console.log(formErrors.email);
    }

    if (values.password === "" || values.password.length === 0) {
      setFormErrors({ ...formErrors, password: "password is required" });
      console.log(formErrors.password);
    }
  };

  return (
    <div className="app__Login-container">
      <div className="app__Login-details">
        <h2 className="app__Login-title">Fintech.africa</h2>

        <h2 className="app__Login-subtitle">Hi, Welcome back</h2>
        {/* <form > */}
        <div>
          <div className="inputField">
            <label className="app__Login-Label">Email</label>
            <input
              id="email"
              value={formValues.email}
              name="email"
              title="Email"
              placeholder="✉️ Enter your email"
              onChange={handleChange}
            />

            {/* <div className='app__login-icons'>
          <FontAwesomeIcon icon="fa-solid fa-envelope" />
          </div> */}
          </div>

          <div className="inputField">
            <label className="app__Login-Label">Password</label>
            <input
              id="password"
              value={formValues.password}
              name="password"
              type="password"
              title="Password"
              placeholder="🔒 Enter your password"
              onChange={handleChange}
            />
            {/* <div className='app__login-passwordIcon'>
          <FontAwesomeIcon icon="fa-solid fa-lock"/>
          </div> */}
          </div>
        </div>

        <a href="/forgotPassword" className="app__forgotPassword">
          Forgot password?
        </a>

        <div className="app__login-buttonDiv">
          <button className="app__login-Button" onClick={handleSubmit}>
            Login
          </button>
        </div>
        {/* </form> */}
        <span className="loginSpan">
          Don't have an accout?{" "}
          <a href="/signup" className="app__create-account">
            Create an account
          </a>
        </span>
      </div>
      <div className="app__Login-banner"></div>
    </div>
  );
};

export default Login;
