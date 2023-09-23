import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Footer } from "../Component/Footer";
import PasswordService from "../service/PasswordService";


const ForgetPassword = () => {
  const [email, setEmail] = useState({ email: "" });
  const [validate, setValidate] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const [apiResponse, setApiResponse] = useState(null);

  const handleEmailChange = (e) => {
    const value = e.target.value;
    setEmail({ ...email, [e.target.name]: value });

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    setValidate(emailRegex.test(e.target.value));
  };

  const option = {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Passwords match, do something here like submit form
    PasswordService.forgotPassword(email, option)
      .then((response) => {
        setApiResponse(response.data.result);
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
    setShowAlert(true);
  };

  return (
    <div className="container">
      <div className="auth-wrapper">
        <div className="">
          <div className="">
            <div className="auth-body">
              <p className="auth-head">Forgot Password</p>
              <p className="auth-description">
                Enter the email associated with your account and we’ll send an
                email with instruction to reset your password
              </p>
              <div className="auth-form-container text-start">
                <form
                  className="auth-form"
                  method="POST"
                  onSubmit={handleSubmit}
                  autoComplete={"off"}
                >
                  <div className="email">
                    <p className="form-header">Email</p>
                    <div className="form-control-div">
                      <image src="/src/Assets/email.png"></image>
                      <input
                      type="email"
                      className="form-control"
                      id="email"
                      name="email"
                      value={email.email}
                      placeholder="Enter your email"
                      onChange={(e) => handleEmailChange(e)}
                    />
                    </div>

                    {validate ? (
                      <p className="isValid">Email is valid</p>
                    ) : (
                      <p className="isNotValid">Email is invalid</p>
                    )}
                  </div>

                  <div className="text-center">
                    <button type="submit" className="submit-password-reset">
                      Forgot Password
                    </button>
                  </div>
                  {apiResponse}
                  {showAlert && (
                    <div className="alert-success" role="alert">
                      Click the reset link sent to your email to reset your
                      password
                    </div>
                  )}
                </form>

                <hr />
                <div className="auth-option">
                  <Link className="text-link" to="/login">
                    Back to Login
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      {/*<div className="footer">*/}
      {/*<Footer />*/}
      {/*</div>*/}
    </div>

  );
};

export default ForgetPassword;
