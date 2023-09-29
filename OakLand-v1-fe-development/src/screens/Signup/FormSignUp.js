import "./Signup.css";
import React, { useState } from "react";
import { useAuth } from "../../context/authcontext";
import Loader from "../../components/Loader/Loader";

const Signup = () => {
  const { registerConfig } = useAuth();
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    date_of_birth: "",
    address: "",
    gender: "",
    phoneNumber: "",
  });
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    await registerConfig(user);
    setIsLoading(false);
    setUser({
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      date_of_birth: "",
      address: "",
      gender: "",
      phoneNumber: "",
    });
  };

  return (
    <div className="signup_bg_image">
      <section className="signup_section">
        <div className="signregister">
          <div className="signup_col_1">
            <form className="signupForm " onSubmit={handleSubmit}>
              <h2 className="signup_h2">Sign Up</h2>
              <p className="signup_span">
                Enter your personal details to create account
              </p>
              <input
                type="text"
                name="firstName"
                value={user.firstName}
                onChange={handleChange}
                placeholder="First Name"
                required
              />
              <input
                type="text"
                name="lastName"
                value={user.lastName}
                onChange={handleChange}
                required
                placeholder="Last Name"
              />

              <input
                type="email"
                name="email"
                value={user.email}
                onChange={handleChange}
                placeholder="Email"
                required
              />

              <input
                type="password"
                name="password"
                value={user.password}
                onChange={handleChange}
                placeholder="password"
                required
              />

              <select
                name="gender"
                value={user.gender}
                onChange={handleChange}
                required
              >
                <option> Select Gender</option>
                <option value="MALE">MALE</option>
                <option value="FEMALE">FEMALE</option>
                <option value="OTHER">OTHER</option>
              </select>
              <input
                type="date"
                placeholder="Date of birth"
                name="date_of_birth"
                value={user.date_of_birth}
                onChange={handleChange}
              />

              <input
                type="text"
                name="phoneNumber"
                value={user.phone}
                onChange={handleChange}
                placeholder="mobile number"
              />

              <input
                type="text"
                name="address"
                value={user.address}
                onChange={handleChange}
                placeholder="Address"
              />
              <button type="submit" className="signup_btn">
                Sign Up
              </button>
              <p className="signup_small mb-0">
                Already have an account?
                <a href="/login">Log in</a>
              </p>
            </form>
            {isLoading && <Loader />}
          </div>
        </div>
      </section>
    </div>
  );
};

export default Signup;