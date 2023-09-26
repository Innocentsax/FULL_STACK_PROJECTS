import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import loginLogo from "../../asset/img/loginLogo.png";
import { HiOutlineMail, HiOutlineKey, HiOutlineUser } from "react-icons/hi";
import { HiOutlineLockClosed, HiOutlinePhone } from "react-icons/hi2";
import axios from "axios";
import Backdrop from "@mui/material/Backdrop";
import CircularProgress from "@mui/material/CircularProgress";
import Alert from "@mui/material/Alert";
import AlertTitle from "@mui/material/AlertTitle";
import Stack from "@mui/material/Stack";
import {
  BVNIcon,
  ButtonP,
  CALink,
  CAPara,
  CAParaO,
  ContainerLG,
  ErrorMsg,
  ErrorMsgOutside,
  EyeIcon,
  Form,
  GoodMsg,
  IconFlex,
  IconFlexn,
  IconGrid,
  IconGridL,
  InputGridDivL,
  InputGridL,
  KeyIcon,
  LGCard,
  LGLogo,
  LGLogoDiv,
  LabelHeaderI,
  LabelHeaderO,
  LabelInput,
  LockIcon,
  MailIcon,
  PhoneIcon,
  SlashEyeIcon,
  UserIcon,
} from "../Styled/Styled";


function Signup() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [bvn, setBvn] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [pin, setPin] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [loading, setLoading] = useState(false);
  const [apiError, setApiError] = useState(false);
  const [apiErrorMessage, setApiErrorMessage] = useState("");


  const [passwordMatch, setPasswordMatch] = useState(password === confirmPassword);


  const [showPass, setShowPass] = useState(false);
  const [showConPass, setShowConPass] = useState(false);
  const [showPin, setShowPin] = useState(false);


  const togglePasswordVisibilityPass = () => {
    setShowPass(!showPass);
  };
  const togglePasswordVisibilityConPass = () => {
    setShowConPass(!showConPass);
  };
  const togglePasswordVisibilityPin = () => {
    setShowPin(!showPin);
  };


  const handleConfirmPassword = (event) => {
    setConfirmPassword(event.target.value);
    setPasswordMatch(event.target.value === password);
  };


  const handleInputChange = (event) => {
    event.target.value = event.target.value.replace(/\D/, '');
  };


  const onSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    await axios
        .post("http://localhost:9000/api/v1/user/register", {
          email,
          password,
          bvn,
          firstName,
          lastName,
          pin,
          phoneNumber,
        })
        .then((response) => {
          navigate("/verify-email");
        })
        .catch((error) => {
          console.log(error);
          setApiError(true);
          setApiErrorMessage(error.response.data.message);
        });
    setLoading(false);
  };


//new lines for password validation


  const [isPasswordValid, setIsPasswordValid] = useState(false);


  const handlePasswordChange = (event) => {


    const newPassword = event.target.value;


    const hasMinimumLength = newPassword.length >= 8;
    const hasUppercase = /[A-Z]/.test(newPassword);
    const hasLowercase = /[a-z]/.test(newPassword);
    const hasNumber = /\d/.test(newPassword);
    const hasSpecialCharacter = /[!@#$%^&*(),.?":{}|<>]/.test(newPassword);
    const isValidPassword =
        hasMinimumLength && hasUppercase && hasLowercase && hasNumber && hasSpecialCharacter;


    setPassword(newPassword);
    setIsPasswordValid(isValidPassword);
  };


  return (
      <ContainerLG>
        <LGLogoDiv to={"/"}>
          <LGLogo
              src="https://res.cloudinary.com/dafxzu462/image/upload/v1687697811/Frame_8402Logo_-_MoneyWay_an1xnp.svg"
              alt="user profile avatar"
          />
          MoneyWay
        </LGLogoDiv>


        <LabelHeaderO>
          Get Started with<br></br>MoneyWay
        </LabelHeaderO>


        <LGCard>
          <LabelHeaderI>Get Started with MoneyWay</LabelHeaderI>


          <Form>


            <LabelInput>First Name</LabelInput>


            <InputGridDivL>
              <InputGridL
                  type="text"
                  value={firstName}
                  placeholder="Enter your first name"
                  onChange={(e) => setFirstName(e.target.value)}
              />


              <IconGridL>
                <IconFlexn>
                  <UserIcon />
                </IconFlexn>
              </IconGridL>
            </InputGridDivL>


            <LabelInput>Last Name</LabelInput>


            <InputGridDivL>
              <InputGridL
                  type="text"
                  value={lastName}
                  placeholder="Enter your last name"
                  onChange={(e) => setLastName(e.target.value)}
              />


              <IconGridL>
                <IconFlexn>
                  <UserIcon />
                </IconFlexn>
              </IconGridL>
            </InputGridDivL>


            <LabelInput>Email</LabelInput>


            <InputGridDivL>
              <InputGridL
                  type="email"
                  value={email}
                  placeholder="Enter your email"
                  onChange={(e) => setEmail(e.target.value)}
              />


              <IconGridL>
                <IconFlexn>
                  <MailIcon />
                </IconFlexn>
              </IconGridL>
            </InputGridDivL>


            <LabelInput>Phone Number</LabelInput>


            <InputGridDivL>
              <InputGridL
                  type="text"
                  value={phoneNumber}
                  placeholder="Enter phone number"
                  onChange={(e) => setPhoneNumber(e.target.value)}
                  minLength="11"
                  maxLength="11"
                  onInput={handleInputChange}
              />


              <IconGridL>
                <IconFlexn>
                  <PhoneIcon />
                </IconFlexn>
              </IconGridL>
            </InputGridDivL>


            <LabelInput>Password</LabelInput>


            <InputGridDivL>
              <InputGridL
                  type={showPass ? "text" : "password"}
                  value={password}
                  placeholder="Enter your password"
                  onChange={handlePasswordChange}
              />


              <IconGridL>
                <IconFlexn>
                  <LockIcon />
                </IconFlexn>
              </IconGridL>


              <IconGrid
                  className={`show-password-icon ${showPass ? "visible" : ""}`}
                  onClick={togglePasswordVisibilityPass}
              >
                <IconFlex>
                  {showPass ? <SlashEyeIcon /> : <EyeIcon /> }
                </IconFlex>
              </IconGrid>
            </InputGridDivL>
            {(isPasswordValid && password.length > 0) ? <GoodMsg>Strong.</GoodMsg> : <ErrorMsgOutside>Password must have at least 8 characters including one uppercase, lowercase, number, and special character.</ErrorMsgOutside>}


            <LabelInput>Confirm Password</LabelInput>


            <InputGridDivL>
              <InputGridL
                  type={showConPass ? "text" : "password"}
                  value={confirmPassword}
                  placeholder="Confirm your password"
                  onChange={handleConfirmPassword}
              />


              <IconGridL>
                <IconFlexn>
                  <LockIcon />
                </IconFlexn>
              </IconGridL>


              <IconGrid
                  className={`show-con-pass-icon ${showConPass ? "visible" : ""}`}
                  onClick={togglePasswordVisibilityConPass}
              >
                <IconFlex>
                  {showConPass ? <SlashEyeIcon /> : <EyeIcon /> }
                </IconFlex>
              </IconGrid>


              {passwordMatch ? null : <ErrorMsg>PASSWORD does not match</ErrorMsg>}
            </InputGridDivL>


            <LabelInput>Pin</LabelInput>


            <InputGridDivL>
              <InputGridL
                  type={showPin ? "text" : "password"}
                  value={pin}
                  placeholder="Enter pin"
                  onChange={(e) => setPin(e.target.value)}
                  minLength="4"
                  maxLength="4"
                  onInput={handleInputChange}
              />


              <IconGridL>
                <IconFlexn>
                  <KeyIcon />
                </IconFlexn>
              </IconGridL>


              <IconGrid
                  className={`show-pin-icon ${showPin ? "visible" : ""}`}
                  onClick={togglePasswordVisibilityPin}
              >
                <IconFlex>
                  {showPin ? <SlashEyeIcon /> : <EyeIcon /> }
                </IconFlex>
              </IconGrid>


            </InputGridDivL>


            <LabelInput>BVN</LabelInput>


            <InputGridDivL>
              <InputGridL
                  type="text"
                  value={bvn}
                  placeholder="Enter BVN"
                  onChange={(e) => setBvn(e.target.value)}
                  minLength="11"
                  maxLength="11"
                  onInput={handleInputChange}
              />


              <IconGridL>
                <IconFlexn>
                  <BVNIcon />
                </IconFlexn>
              </IconGridL>
            </InputGridDivL>




            <Backdrop
                sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={loading}
            >
              <CircularProgress color="inherit" />
            </Backdrop>
            {apiError && (
                <Stack sx={{ width: "100%" }} spacing={2}>
                  <Alert severity="error">
                    <AlertTitle>Error</AlertTitle>
                    {apiErrorMessage} <strong>check it out!</strong>
                  </Alert>
                </Stack>
            )}


            <ButtonP onClick={(e) => onSubmit(e)}>SignUp</ButtonP>
          </Form>


          <CAPara>
            Already have an account?&nbsp;<CALink to={"/login"}>Login</CALink>
          </CAPara>
        </LGCard>


        <CAParaO>
          Already have an account?&nbsp;<CALink to={"/login"}>Login</CALink>
        </CAParaO>
      </ContainerLG>
  );
}

export default Signup;



