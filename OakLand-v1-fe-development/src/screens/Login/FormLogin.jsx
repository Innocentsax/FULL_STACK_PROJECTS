import "./Login.css";
import React, { useState } from 'react';
import { useAuth } from "../../context/authcontext";
import Loader from "../../components/Loader/Loader";
import { useNavigate, useLocation } from "react-router-dom";


const Login = () => {
  const navigate = useNavigate()
  const location = useLocation()
  const { LoginConfig } = useAuth();
  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    await LoginConfig(formData, location, navigate);
    setIsLoading(false);
    setFormData({
      email: "",
      password: "",
    });
  };


  return (
    <div className="login_bg_image">
        <div className="login-form-container flex items-center">
          <form className="loginForm" onSubmit={handleSubmit}>
            <h2 className="login_h2">Login</h2>
            <input
              type="email"
              name="email"
              placeholder="Email"
              required
              onChange={handleChange}
            />
            <input
              type="password"
              name="password"
              placeholder="password"
              required
              onChange={handleChange}
            />
            <button className="login_btn" type="submit">Login</button>
            <p className="login_small mb-0">
              <a href="/forgotpassword">Forgotten password?</a>
            </p>
            <p className="login_small mb-0">
              Don't have an account ?<a href="/signup">Sign up</a>
              </p>
      </form>
      {isLoading && <Loader />}
    </div>
</div>
);
}

export default Login