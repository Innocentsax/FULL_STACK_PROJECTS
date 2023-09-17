import React from "react";
import SignIn from "./SignInPage";
import SignUp from "./SignUp";
import main from "../stylesheets/signinSignup.module.css";

export default function SignInSignOutPage() {
  return (
    <>
      <div className={main.container}>
        <SignIn/>
        <SignUp/>
      </div>
    </>
  );
}
