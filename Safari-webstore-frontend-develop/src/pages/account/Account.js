import React, { useState, useEffect } from "react";
import DashboardLayout from "../../components/userdashboard/DashboardLayout";
import PasswordReset from "../../components/userdashboard/PasswordResetForm";
import AccountInformation from "../../components/userdashboard/AccountInformationForm";
import { handleUserDetailsUpdate } from "../../services/user-service";
import axios from "axios";
import { config } from "../../services/baseUrl";

const Account = (props) => {
  const [alertBox, setAlertBox] = useState({
    state: false,
    message: "",
    type: "error",
  });
  const [showResetPasswordForm, setResetPasswordForm] = React.useState(false);
  const [fields, setFields] = useState({
    email: "",
    firstName: "",
    lastName: "",
    gender: "",
    password: "",
    newPassword: "",
    confirmNewPassword: "",
    newsletter: "",
    dateOfBirth: "",
  });

  const getUserDetails = async () => {
    const { baseURL } = config;

    try {
      const response = await axios.get(baseURL + "/user-details", {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });

      const userDetails = await response.data;
      // console.log(userDetails);
      setFields((fields) => ({ ...fields, ...userDetails }));
    } catch (exception) {
      setFields((fields) => ({ ...fields }));

      console.log(exception);
    }
  };

  useEffect(() => {
    getUserDetails();
  }, []);

  const handleChange = (event) => {
    const { name, value, checked } = event.target;
    setFields((fields) => ({
      ...fields,
      [name]: name !== "newsletter" ? value : checked,
    }));
  };

  return (
    <DashboardLayout>
      <section className="account-information">
        <h1 className="title ">Account Information</h1>
        <div className="account-information__forms">
          <AccountInformation
            showResetPassword={setResetPasswordForm}
            fields={fields}
            handleChange={handleChange}
          />
          {showResetPasswordForm && (
            <PasswordReset handleChange={handleChange} fields={fields} />
          )}
        </div>
        <button
          className="btn-block btn-block--contained btn--primary"
          onClick={() => handleUserDetailsUpdate(fields, showResetPasswordForm)}
        >
          save changes
        </button>
      </section>
    </DashboardLayout>
  );
};

export default Account;
