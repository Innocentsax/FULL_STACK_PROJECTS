import React from "react";
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Userprofile from "./component/Userprofile/Userprofile";
import Profileform from "./component/Userprofile/Profileform";
import SecuritySetting from "./component/SecuritySetting/SecuritySetting";
import Fundwallet from "./component/Fundwallet/Fundwallet";
import LandingPage from "./component/landingPage/landing_page";

import Login from "./component/Login/login";
import CheckEmail from "./component/Email/checkyouremail";
import VerifyEmail from "./component/Email/emailverification";
import ForgotPassword from "./component/Password/forgotpassword";
import ResetPassword from "./component/Password/resetpassword";
import Signup from "./component/Signup/signup";
import Payments from "./component/payment/Payments";
import InternetPayments from "./component/payment/InternetPayments";
import Transfer from "./component/transfer/Transfer";
import AirtimePayment from "./component/payment/AirtimePayent";
import VerificationLinkExpired from "./component/Email/VerificationLinkExpired";
import AccountAlreadyVerified from "./component/Email/AccountAlreadyVerified";
import Dashboard from "./component/Dashboard/Dashboard";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LandingPage />} />

          <Route path="/user" element={<Userprofile />}>
            <Route index element={<Dashboard />} />
            <Route path="profile" element={<Profileform />} />
            <Route path="security" element={<SecuritySetting />} />
            <Route path="payment" element={<Payments />} />
            <Route path="payment/airtime" element={<AirtimePayment />} />
            <Route path="payment/internet" element={<InternetPayments />} />
            <Route path="fundwallet" element={<Fundwallet />} />
            <Route path="transfer" element={<Transfer />} />
          </Route>

          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/verify-email" element={<VerifyEmail />} />
          <Route
            path="/verify-email-link-expired"
            element={<VerificationLinkExpired />}
          />
          <Route
            path="/account-already-verified"
            element={<AccountAlreadyVerified />}
          />
          <Route path="/forgotpassword" element={<ForgotPassword />} />
          <Route path="/checkemail" element={<CheckEmail />} />
          <Route path="/resetpassword" element={<ResetPassword />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
