import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../reducers/authState";
import axios from "axios";
import { useLocation } from "react-router-dom";
import { useToasts } from "react-toast-notifications";
import style from "./ResetPassword.module.css";

const ResetPassword = () => {
  // eslint-disable-next-line
  const { loading, reset_Password } = useAuth();
  const initialValues = { newPassword: "", confirmPassword: "" };
  const [formValues, setFormValues] = useState(initialValues);

  const { addToast } = useToasts();

  const navigate = useNavigate();

  const [, setIsSubmit] = useState(false);
  // eslint-disable-next-line
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };
  const { search } = useLocation();
  const token = search.split("=")[1];
  const resetPassword = async () => {
    const response = await axios.post(
      `https://mentorship-payment-app.herokuapp.com/api/v1/reset-password?token=${token}`,
      {
        newPassword: formValues.newPassword,
        confirmPassword: formValues.confirmPassword,
      }
    );
    console.log(formValues);
    if (response.data.status === "OK") {
      addToast("Password Reset successful", { appearance: "success" }).then(
        navigate("/login")
      );
    } else {
      addToast(response.data.message, { appearance: "error" });
    }
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    resetPassword();
    setIsSubmit(true);
  };
  return (
    <div className={style.app__resetPassword_container}>
      <div className={style.app__resetPassword_form}>
        <h2 className={style.app__resetPassword_h2}>Reset Password</h2>

        <form>
          <div>
            <div className={style.inputField}>
              <label className={style.app__resetPassword_label}>
                New Password
              </label>

              <input
                type='password'
                placeholder='Enter your new Password'
                className={style.app__resetPassword_input}
                name='newPassword'
                onChange={handleChange}
                minLength={4}
              />
            </div>

            <div className={style.inputField}>
              <label className={style.app__resetPassword_label}>
                Confirm Password
              </label>
              <input
                type='password'
                placeholder='re-enter your Password'
                className={style.app__resetPassword_input}
                name='confirmPassword'
                onChange={handleChange}
                minLength={4}
              />
            </div>
          </div>

          <button
            action=''
            className={style.app__resetPassword_resetBtn}
            name='Reset Password'
            onClick={handleSubmit}>
            Reset Password
          </button>
        </form>
        <a href='/login' className={style.app__resetPassword_aTag}>
          Back To Login
        </a>
      </div>
      <div className={style.app__resetPassword_picture}> </div>
    </div>
  );
};
export default ResetPassword;
