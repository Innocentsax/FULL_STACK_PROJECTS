import React from "react";
import { useState } from "react";
import Select, { components } from 'react-select';

import {
  ContainerFBGpd,
  LabelHeader,
  FormCard,
  TogDiv,
  LabelInput,
  Form,
  FormDiv,
  InputGridDiv,
  InputGrid,
  IconGrid,
  ButtonP,
  IconFlex,
  SlashEyeIcon,
  EyeIcon,
  Input,
  InputDes,
} from "../Styled/Styled";
import {useStateContext} from "../Context/ContextProvider";
import axios from "axios";
import CircularProgress from "@mui/material/CircularProgress";
import Backdrop from "@mui/material/Backdrop";
import Stack from "@mui/material/Stack";
import Collapse from "@mui/material/Collapse";
import Alert from "@mui/material/Alert";
import {IconButton} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";


const { Option } = components;
const Fundwallet = () => {

    const [pin, setPin] = useState("");
    const [showPin, setShowPin] = useState(false);

    const [description, setDescription] = useState("");
    const [accountNumber, setAccountNumber] = useState("");
    const [accountBank, setAccountBank] = useState("");
    const [amount, setAmount] = useState("");

    const [loading, setLoading] = useState(false);
    const context = useStateContext();

    const [apiSuccess, setApiSuccess] = useState(false);
    const [apiSuccessMessage, setApiSuccessMessage] = useState("");

    const [apiError, setApiError] = useState(false);
    const [apiErrorMessage, setApiErrorMessage] = useState("");

    const bankOptions = [
        { value: '011', label: 'First Bank' },
        { value: '058', label: 'Guaranty Trust Bank' },
        { value: '044', label: 'Access Bank' },
        { value: '232', label: 'Sterling Bank' },
        { value: '033', label: 'UBA' },
        { value: '057', label: 'Zenith' }
    ];

    const handleOptionChange =(option)=>{
        setAccountBank(option);
    }

    const headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer '+ context.token
  };

    const handleFundWallet = async (e) =>{

      e.preventDefault();
      setLoading(true)

      await axios.post("http://localhost:9000/api/v1/transaction/fund-wallet", {pin, accountNumber, description, amount, accountBank}, { headers })
          .then(response=>{
              setApiSuccess(true)
              setApiSuccessMessage(response.data.message)
          })
          .catch(error=>{
              setApiError(true)
              setApiErrorMessage(error.response.data.errorMessage)
         })
      setLoading(false)
    }

    const handlePinChange = (event) => {
        setPin(event.target.value);
      };

      const togglePasswordVisibilityPin = () => {
        setShowPin(!showPin);
      };

      const handleInputChange = (event) => {
        event.target.value = event.target.value.replace(/\D/, '');
      };

  return (
    <ContainerFBGpd>
      <LabelHeader>Fund Wallet</LabelHeader>
      <Backdrop
          sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
          open={loading}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
      <FormCard>
        <Form>
          {/* -----------------------------------Password Input - Div */}
          <FormDiv>
            <LabelInput htmlFor="login-input">Amount</LabelInput>
            <Input type="number" value={amount} onChange={(e)=>setAmount(e.target.value)} placeholder="Enter an amount" />
            <LabelInput htmlFor="login-input">Bank</LabelInput>
            <Select value={accountBank} onChange={handleOptionChange} options={bankOptions} />
            <LabelInput htmlFor="login-input">Account Number</LabelInput>
            <Input type="number" value={accountNumber} onChange={(e)=>setAccountNumber(e.target.value)} placeholder="Enter account number" />

            <LabelInput>Pin</LabelInput>

            <InputGridDiv>
              <InputGrid
                type={showPin ? "text" : "password"}
                value={pin}
                onChange={handlePinChange}
                minLength="4"
                maxLength="4"
                onInput={handleInputChange}
                placeholder="Enter pin"
              />
              <IconGrid
                className={`show-password-icon ${showPin ? "visible" : ""}`}
                onClick={togglePasswordVisibilityPin}
              >
                <IconFlex>{showPin ? <SlashEyeIcon /> : <EyeIcon />}</IconFlex>
              </IconGrid>
            </InputGridDiv>

            <LabelInput htmlFor="login-input">Description</LabelInput>
            <InputDes 
            name="description"
            placeholder="Write a short description"
            value={description} onChange={(e)=>setDescription(e.target.value)}></InputDes>

              {apiError &&
                  <Stack className="mt-2" sx={{ width: '100%' }} spacing={2}>
                      <Collapse in={apiError}>
                          <Alert severity="error" action={<IconButton onClick={()=>setApiError(false)}> <CloseIcon fontSize="inherit" /></IconButton>} sx={{ mb: 2 }}>
                              {apiErrorMessage}
                          </Alert>
                      </Collapse>
                  </Stack>
              }
              {apiSuccess &&
                  <Stack className="mt-2" sx={{ width: '100%' }} spacing={2}>
                      <Collapse in={apiSuccess}>
                          <Alert severity="success" variant="outlined" action={<IconButton onClick={()=>setApiSuccess(false)}> <CloseIcon fontSize="inherit" /></IconButton>}  iconMapping={{
                              success: <CheckCircleOutlineIcon fontSize="inherit" />,
                          }} sx={{ mb: 2 }}>
                              {apiSuccessMessage}
                          </Alert>
                      </Collapse>
                  </Stack>
              }
            <ButtonP type="submit" onClick={handleFundWallet}>Continue</ButtonP>
          </FormDiv>
        </Form>
      </FormCard>
    </ContainerFBGpd>
  );
};
export default Fundwallet;
