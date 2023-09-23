import { useState } from "react";

import { Footer } from "../Component/Footer";
import HeaderBar from "../Component/HeaderBar";
import { useParams } from "react-router-dom";
import PasswordService from "../service/PasswordService";

const Reset = () => {
  const [data, setData] = useState({
    newPassword: "",
    confirmPassword: "",
  });
  const [validate, setValidate] = useState("");
  const { token } = useParams();
  const [showAlert, setShowAlert] = useState(false);
  const [apiResponse, setApiResponse] = useState(null);

  const handlePasswordChange = (e) => {
    const value = e.target.value;
    setData({ ...data, [e.target.name]: value });
    const passwordRegex =
      /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\d@$!%*#?&]{8,}$/;
    setValidate(passwordRegex.test(e.target.value));
  };

  const option = {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (data.newPassword !== data.confirmPassword) {
      setValidate("Passwords do not match");
    } else {
      PasswordService.resetPassword(token, data)
        .then((response) => {
          setApiResponse(response.data.result);
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });

      setShowAlert(true);
    }
  };

  return (
    <div className="container">
      <HeaderBar />
      <div className="auth-wrapper">
        <div className="">
          <div className="d-flex flex-column align-content-end">
            <div className="auth-body">
              <p className="auth-head">Reset Password</p>
              <div className="auth-form-container text-start">
                <form
                  className="auth-form"
                  method="POST"
                  onSubmit={handleSubmit}
                  autoComplete={"off"}
                >
                  <div className="email">
                    <label htmlFor="newPassword" className="form-header">
                      New Password
                    </label>
                    <input
                      type="password"
                      className="form-control-password"
                      id="newPassword"
                      name="newPassword"
                      value={data.newPassword}
                      placeholder="Enter your new password"
                      onChange={(e) => handlePasswordChange(e)}
                    />
                    {/* <p>The token is: {token}</p> */}
                  </div>

                  <div className="email">
                    <label htmlFor="confirmPassword" className="form-header">
                      Confirm Password
                    </label>
                    <input
                      type="password"
                      className="form-control-password"
                      id="confirmPassword"
                      name="confirmPassword"
                      value={data.confirmPassword}
                      placeholder="Confirm password"
                      onChange={(e) => handlePasswordChange(e)}
                    />
                  </div>
                  <div>{validate}</div>

                  <div className="text-center">
                    <button type="submit" className="submit-password-reset">
                      Reset Password
                    </button>
                  </div>
                  {showAlert && (
                    <div className="alert-success" role="alert">
                      Password reset successful. You can login now!
                    </div>
                  )}
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
      {/*<div className="footer">*/}
      {/*  <Footer />*/}
      {/*</div>*/}
    </div>
  );
};

export default Reset;
