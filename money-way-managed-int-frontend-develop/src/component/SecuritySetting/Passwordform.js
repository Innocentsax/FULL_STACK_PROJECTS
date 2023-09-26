import { useState } from "react";
import React from "react";
import axios from "axios";
import Backdrop from '@mui/material/Backdrop';
import CircularProgress from '@mui/material/CircularProgress';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';
import Stack from '@mui/material/Stack';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import Collapse from '@mui/material/Collapse';
import CloseIcon from '@mui/icons-material/Close';
import { useNavigate } from "react-router-dom";

import {
  ButtonP,
  Form,
  FormDiv,
  InputGrid,
  LabelInput,
  PGraphF,
  EyeIcon,
  SlashEyeIcon,
  InputGridDiv,
  IconGrid,
  IconFlex,
  ErrorMsg,
} from "../Styled/Styled";
import {useStateContext} from "../Context/ContextProvider";
import {IconButton} from "@mui/material";

const Passwordform = () => {
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const context= useStateContext();


  const [loading, setLoading]= useState(false);
  const [apiError, setApiError] = useState(false);
  const [apiErrorMessage, setApiErrorMessage] = useState("");
  const [apiSuccess, setApiSuccess] = useState(false);
  const [apiSuccessMessage, setApiSuccessMessage] = useState("");

  const [passwordMatch, setPasswordMatch] = useState(newPassword === confirmPassword);

  const [showPassword, setShowPassword] = useState(false);
  const [showPassword2, setShowPassword2] = useState(false);
  const [showPassword3, setShowPassword3] = useState(false);

  const handleOldPasswordChange = (event) => {
    setCurrentPassword(event.target.value);
  };

  const handleNewPasswordChange = (event) => {
    setNewPassword(event.target.value);
  };

  const handleConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
    setPasswordMatch(event.target.value === newPassword);
  };

  const togglePasswordVisibilityOldPass = () => {
    setShowPassword(!showPassword);
  };
  const togglePasswordVisibilityNewPass = () => {
    setShowPassword2(!showPassword2);
  };
  const togglePasswordVisibilityConfirmPass = () => {
    setShowPassword3(!showPassword3);
  };

  const handleChangePassword = async (e)=>{
    e.preventDefault();
    setLoading(true);

    const headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer '+ context.token
    };

    await axios.put("http://localhost:9000/api/v1/user/change-password", { newPassword, currentPassword }, { headers })
        .then(
            response=> {
              setApiSuccess(true)
              setApiSuccessMessage(response.data)
            }
        ).catch(
            error =>{
              setApiError(true)
              setApiErrorMessage(error.response.data.errorMessage)
            }
        )
    setLoading(false)
  };

  return (
   <div>
      {/* -----------------------------------Password Input - Div */}
      <FormDiv>
        <PGraphF>
          Password must contain at least 1 letter, 1 number, and 1 symbol.
          Minimum length is 8 characters
        </PGraphF>

        <LabelInput>Old Password</LabelInput>

        <InputGridDiv>
          <InputGrid
            type={showPassword ? "text" : "password"}
            value={currentPassword}
            onChange={handleOldPasswordChange}
          />
          <IconGrid
            className={`show-password-icon ${showPassword ? "visible" : ""}`}
            onClick={togglePasswordVisibilityOldPass}
          >
            <IconFlex>
            {showPassword ? <SlashEyeIcon /> : <EyeIcon /> }
            </IconFlex>
          </IconGrid>
        </InputGridDiv>

        <LabelInput>New Password</LabelInput>

        <InputGridDiv>
          <InputGrid
            type={showPassword2 ? "text" : "password"}
            value={newPassword}
            onChange={handleNewPasswordChange}
          />
          <IconGrid
            className={`show-password-icon ${showPassword2 ? "visible" : ""}`}
            onClick={togglePasswordVisibilityNewPass}
            >
            <IconFlex>
            {showPassword2 ? <SlashEyeIcon /> : <EyeIcon /> }
            </IconFlex>
          </IconGrid>
        </InputGridDiv>

        <LabelInput>Confirm Password</LabelInput>

        <InputGridDiv>
          <InputGrid
            type={showPassword3 ? "text" : "password"}
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
          />
          <IconGrid
            className={`show-password-icon ${showPassword3 ? "visible" : ""}`}
            onClick={togglePasswordVisibilityConfirmPass}
            >
            <IconFlex>
            {showPassword3 ? <SlashEyeIcon /> : <EyeIcon /> }
            </IconFlex>
          </IconGrid>
        </InputGridDiv>
        {passwordMatch ? null : <ErrorMsg>PASSWORD does not match</ErrorMsg>}

        <Backdrop
            sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
            open={loading}
        >
          <CircularProgress color="inherit" />
        </Backdrop>
        {apiError &&
            <Stack sx={{ width: '100%' }} spacing={2}>
              <Collapse in={apiError}>
                <Alert severity="error" action={<IconButton onClick={()=>setApiError(false)}> <CloseIcon fontSize="inherit" /></IconButton>} sx={{ mb: 2 }}>
                  <AlertTitle>Error</AlertTitle>
                  {apiErrorMessage} <strong>check it out!</strong>
                </Alert>
              </Collapse>
            </Stack>
        }
        {apiSuccess &&
            <Stack sx={{ width: '100%' }} spacing={2}>
              <Collapse in={apiSuccess}>
                <Alert severity="success" variant="outlined" action={<IconButton onClick={()=>setApiSuccess(false)}> <CloseIcon fontSize="inherit" /></IconButton>}  iconMapping={{
                  success: <CheckCircleOutlineIcon fontSize="inherit" />,
                }} sx={{ mb: 2 }}>
                  <AlertTitle>Success</AlertTitle>
                  {apiSuccessMessage}
                </Alert>
              </Collapse>
            </Stack>
        }

        <ButtonP type="submit" onClick={handleChangePassword}>Update</ButtonP>
      </FormDiv>
   </div>
  );
};

export default Passwordform;
