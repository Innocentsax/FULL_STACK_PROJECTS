import React, { useState } from "react";
import axios from "axios";
import "./index.css";
import { useNavigate } from "react-router-dom";
import { useToasts } from "react-toast-notifications";

function ForgotPassword() {
  const [email, setEmail] = useState("");

  const { addToast } = useToasts();

  const navigate = useNavigate();
  const navigateToLogin = () => {
    navigate("/login");
  };

  const navigateToModal = () => {
    navigate("/resetPassword/PasswordResetModal");
  };

  const forgotPassword = async () => {
    const response = await axios.post(
      "https://mentorship-payment-app.herokuapp.com/api/v1/forgot-password",
      { email: email }
    );
    if (response.data.result !== null) {
      addToast("Password reset link sent to your email", {
        appearance: "success",
      });
      navigateToModal();
    } else {
      addToast(response.data.message, { appearance: "error" });
    }
    console.log(response);
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    forgotPassword();
  };
  const handleChange = (e) => {
    setEmail(e.target.value);
  };
  return (
    <div className='app__forgotPassword-parent'>
      <div className='flex-align'>
        <h1 className='app__forgotPassword-h1'>Forget Password</h1>
        <p className='app__forgotPassword-pTag'>
          Enter the email associated with your account and we'll <br />
          send an email with instructions to reset your password
        </p>
      </div>
      {/* <input type="text" placeholder= 'Enter your email' className = 'app__forgotPassword-input' title = "Email"  icon = "fa-solid fa-envelope"> </input> */}
      <label>Email</label>
      <div className='flex-align'>
        <form>
          <input
            type='email'
            placeholder='Enter your email'
            className='app__forgotPassword-input'
            value={email}
            onChange={handleChange}
          />
          <button
            action=''
            className='app__forgotPassword-resetBtn'
            name='Reset Password'
            onClick={handleSubmit}>
            {" "}
            Reset Password{" "}
          </button>
        </form>
        <button
          type='submit'
          action=''
          className='app__forgotPassword-backToLoginBtn'
          name='Back To Login'
          onClick={navigateToLogin}>
          {" "}
          Back To Login{" "}
        </button>
      </div>
    </div>
  );
}
export default ForgotPassword;
