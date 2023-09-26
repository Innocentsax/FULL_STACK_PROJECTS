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
import Pinmodal from "./Pinmodal";
import {useStateContext} from "../Context/ContextProvider";
import {IconButton} from "@mui/material";

const Pinform = () => {
  const navigate = useNavigate()
  const [oldPin, setOldPin] = useState("");
  const [newPin, setNewPin] = useState("");
  const [confirmNewPin, setConfirmNewPin] = useState("");
  const [loading, setLoading]= useState(false);
  const [apiError, setApiError] = useState(false);
  const [apiErrorMessage, setApiErrorMessage] = useState("");
  const [apiSuccess, setApiSuccess] = useState(false);
  const [apiSuccessMessage, setApiSuccessMessage] = useState("");

  const [pinMatch, setPinMatch] = useState(newPin === confirmNewPin);

  const context = useStateContext();

  const [showPin, setShowPin] = useState(false);
  const [showPin2, setShowPin2] = useState(false);
  const [showPin3, setShowPin3] = useState(false);





  const handleOldPinChange = (event) => {
    setOldPin(event.target.value);
  };

  const handleNewPinChange = (event) => {
    setNewPin(event.target.value);
  };

  const handleConfirmPinChange = (event) => {
    setConfirmNewPin(event.target.value);
    setPinMatch(event.target.value === newPin);
  };

  const togglePasswordVisibilityOldPass = () => {
    setShowPin(!showPin);
  };
  const togglePasswordVisibilityNewPass = () => {
    setShowPin2(!showPin2);
  };
  const togglePasswordVisibilityConfirmPass = () => {
    setShowPin3(!showPin3);
  };

  const handleInputChange = (event) => {
    event.target.value = event.target.value.replace(/\D/, '');
  };

  const handleSubmit = async (e)=>{
    e.preventDefault();
    setLoading(true);

    const headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer '+context.token
    };

    await axios.put("http://localhost:9000/api/v1/user/change-transaction-pin", { oldPin, newPin, confirmNewPin }, { headers })
        .then(
            response=>{
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
    <Form>
      {/* -----------------------------------Password Input - Div */}
      <FormDiv>
        <PGraphF>
            Minimum length is 4 characters
        </PGraphF>

        <LabelInput>Old Pin</LabelInput>

        <InputGridDiv>
          <InputGrid
            type={showPin ? "text" : "password"}
            value={oldPin}
            onChange={handleOldPinChange}
            minLength="4"
            maxLength="4"
            onInput={handleInputChange}
          />
          <IconGrid
            className={`show-password-icon ${showPin ? "visible" : ""}`}
            onClick={togglePasswordVisibilityOldPass}
          >
            <IconFlex>{showPin ? <SlashEyeIcon /> : <EyeIcon />}</IconFlex>
          </IconGrid>
        </InputGridDiv>

        <LabelInput>New Pin</LabelInput>

        <InputGridDiv>
          <InputGrid
            type={showPin2 ? "text" : "password"}
            value={newPin}
            onChange={handleNewPinChange}
            minLength="4"
            maxLength="4"
            onInput={handleInputChange}
          />
          <IconGrid
            className={`show-password-icon ${showPin2 ? "visible" : ""}`}
            onClick={togglePasswordVisibilityNewPass}
          >
            <IconFlex>
              {showPin2 ? <SlashEyeIcon /> : <EyeIcon />}
            </IconFlex>
          </IconGrid>
        </InputGridDiv>

        <LabelInput>Confirm Pin</LabelInput>

        <InputGridDiv>
          <InputGrid
            type={showPin3 ? "text" : "password"}
            value={confirmNewPin}
            onChange={handleConfirmPinChange}
            minLength="4"
            maxLength="4"
            onInput={handleInputChange}
          />
          <IconGrid
            className={`show-password-icon ${showPin3 ? "visible" : ""}`}
            onClick={togglePasswordVisibilityConfirmPass}
          >
            <IconFlex>
              {showPin3 ? <SlashEyeIcon /> : <EyeIcon />}
            </IconFlex>
          </IconGrid>
        </InputGridDiv>
        {pinMatch ? null : <ErrorMsg>PIN does not match.</ErrorMsg>}
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

        <ButtonP type="submit" onClick={handleSubmit}>Update</ButtonP>
      </FormDiv>
    </Form>
  );
};

export default Pinform;
