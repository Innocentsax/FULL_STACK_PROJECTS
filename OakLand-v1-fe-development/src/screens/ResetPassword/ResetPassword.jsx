import "./ResetPassword.css";
import React, { useState } from "react";
import { useAuth } from "../../context/authcontext";

const ResetPassword = () => {
  const { ResetPasswordConfig } = useAuth();
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    ResetPasswordConfig(formData);
    setFormData({
      password: "",
    });
  };

  return (
    <div className="reset_bg_image">
      <form
        // id="formt
        className="resetForm "
        onSubmit={handleSubmit}
      >
        <h3 className="reset_h3">Reset Password</h3>

        <input
          type="password"
          name="password"
          //  {...register("email")}
          placeholder="New password"
          required
          onChange={handleChange}
        />

        <input
          type="password"
          name="password"
          placeholder="Confirm new password"
          required
          onChange={handleChange}
        />

        <button className="reset_btn" type="submit">
         Reset my password
        </button>
       
      </form>
    </div>
  );
};

export default ResetPassword;
