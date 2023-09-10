import axios from "axios";
import React from "react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const ForgetPassword = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("")
  const [emailError, setEmailError] = useState("")
  const [isLoading, setIsLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault();
    setEmailError("");
    setIsLoading(true)

    // Email validation
    if (!email.includes("@") || !email.includes(".")) {
      setEmailError("Please enter a valid email address");
      setIsLoading(false);
      return;
    }

  try {
    const response = await axios.post('http://localhost:8080/password/forgot-password/', {
      email
    });
    console.log(response.data); // Handle the login response
    navigate("/reset-password")
  } catch (error) {
    console.error(error);
  }
  console.log(email);
  }
  return (
    <div className="flex flex-col items-center">
      <div>
        <h1 className="font-bold text-2xl mb-24 text-center mt-20">HR Support Center</h1>
      </div>
      <div className="bg-white h-351 md:w-451 p-4">
        <h1 className="font-bold text-2xl">Forgot Password</h1>
        <p className="py-2">
          Enter the email associated with your account and we'll send an email
          with instructions to reset your password.
        </p>
        <div>
          <form>
            <div className="form-group">
              <label htmlFor="email" className="col-sm-2 col-form-label">
                Email
              </label>
              <input
                type="email"
                className="form-control form-control-user"
                id="email"
                name="email"
                aria-describedby="emailHelp"
                placeholder="Enter your email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              {emailError && <div className="text-red-400 text-lg mb-2 ml-2">{emailError}</div>}
            </div>
            <button 
            className="rounded-md h-44 px-6 font-bold text-white bg-primaryColor btn-block w-full mt-10"
            onClick={handleSubmit}>
              Reset Password
            </button>
          </form>
        </div>
        <Link to="/login">
          <button className="col-sm-2 py-2 text-primaryColor w-full">
            Back to Login
          </button>
        </Link>
      </div>
    </div>
  );
};

export default ForgetPassword;
