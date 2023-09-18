import React , {useState} from 'react'
import {baseEndpoint} from "../globalresources/Config";
import {useNavigate, useParams } from "react-router-dom";
import PasswordResetForm from "./PasswordResetForm";
import ResponseMessage from "../globalresources/modals/ResponseMessage";

const ResetPassword = () => {
  const {token} = useParams();
  const navigate = useNavigate();

  const [isVerifiedToken, setIsVerifiedToken] = useState(false)
  const [responseMessage, setResponseMessage] = useState(null);
  const [isSpinning, setisSpinning] = useState(false);

  const [email, setEmail] = useState("")
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const tokenVerify = (token) => {

    fetch(baseEndpoint + "/api/v1/auth/verify-token/" + token, {
      method: "GET"
    }).then(response => {
      return response.json()
    }).then((data) => {
      console.log(data);
      let emailFromToken = data.email
      if (emailFromToken) {
        setEmail(emailFromToken);
        setIsVerifiedToken(true)
      } else {
        setResponseMessage("Invalid token");
      }
    }).catch(error => {
      console.log(error.message);
      setResponseMessage("Invalid token");
    });

  }
  tokenVerify(token);

  const handleChange = (e) => {
    setNewPassword(e.target.value);
    setConfirmPassword(e.target.value);
  }
  const handlePasswordResetSubmit = (e) => {
    e.preventDefault()

    let data = {
      email: email,
      newPassword: newPassword,
      confirmPassword: confirmPassword
    }
    resetPassword(data);
  }
  const resetPassword = (data) => {
    fetch(baseEndpoint + "/api/v1/auth/reset-password", {
      method: "POST",
      headers: {
        "content-type": "application/json"
      },
      body: JSON.stringify(data)
    }).then(response => {
      console.log(response);
      setResponseMessage("Password has been reset ");
      setisSpinning(false);
      setTimeout(() => {
        navigate("/login")
      }, 3000);
    }).catch(error => {
      console.log(error.message);
      setResponseMessage("error : " + error.message + "- Password not reset");
      setisSpinning(false);
    });
  };


  return (
      <>
        <div className="token-verification-status">
          {!isVerifiedToken && !responseMessage && <h1 className="text-success">Token Verification in progress</h1>}
          {!isVerifiedToken && responseMessage && <h1 className="text-danger"> Invalid token</h1>}
        </div>

        {isVerifiedToken &&
            <PasswordResetForm spinner={isSpinning}
                               handlePasswordResetSubmit={handlePasswordResetSubmit}
                               handleChange={handleChange}/>
        }

        {responseMessage && <ResponseMessage message={responseMessage}/>}
      </>
  )
}
export default ResetPassword;
