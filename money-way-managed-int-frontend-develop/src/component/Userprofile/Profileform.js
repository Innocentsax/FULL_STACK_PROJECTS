import React, { useState } from "react";

import {
  ContainerFBGpd,
  LabelHeader,
  FormCard,
  CoverImgEdit,
  AvatarDiv,
  AvatarIcon,
  Form,
  FormDiv,
  LabelInput,
  Input,
  EmailInput,
  ButtonP,
} from "../Styled/Styled";
import { useStateContext } from "../Context/ContextProvider";
import axios from "axios";
import Backdrop from "@mui/material/Backdrop";
import CircularProgress from "@mui/material/CircularProgress";
import Stack from "@mui/material/Stack";
import Collapse from "@mui/material/Collapse";
import Alert from "@mui/material/Alert";
import { IconButton } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import AlertTitle from "@mui/material/AlertTitle";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";

const Profileform = () => {
  const context = useStateContext();
  const [firstName, setFirstname] = useState(context.firstName);
  const [lastName, setLastname] = useState(context.lastName);
  const [email, setEmail] = useState(context.email);
  const [phoneNumber, setPhoneNumber] = useState("");
  const [address, setAddress] = useState("");

  const [loading, setLoading] = useState(false);
  const [apiError, setApiError] = useState(false);
  const [apiErrorMessage, setApiErrorMessage] = useState("");
  const [apiSuccess, setApiSuccess] = useState(false);
  const [apiSuccessMessage, setApiSuccessMessage] = useState("");
  const {setUserEmail, setFirstName, setLastName} = useStateContext();

  const updateProfile = async (e) => {
    e.preventDefault();
    setLoading(true);

    const headers = {
      "Content-Type": "application/json",
      Authorization: "Bearer " + context.token,
    };

    await axios
      .put(
        "http://localhost:9000/api/v1/user/profile/update-profile",
        { firstName, lastName, email, phoneNumber, address },
        { headers }
      )
      .then((response) => {
        setApiSuccess(true);
        setApiSuccessMessage(response.data);

        setFirstName(firstName);
        setLastName(lastName);
        setUserEmail(email)
      })
      .catch((error) => {
        console.log(error);
        setApiError(true);
        setApiErrorMessage(error.response.data.errorMessage);
      });
    setLoading(false);
  };

  return (
    <ContainerFBGpd>
      <LabelHeader>Profile</LabelHeader>

      <FormCard>
        <CoverImgEdit
          src="https://res.cloudinary.com/dafxzu462/image/upload/v1687740198/Rectangle_315profilecoverbg_pllpwe.png"
          alt="cover-image"
        />

        <AvatarDiv>
          <AvatarIcon />
        </AvatarDiv>

        <Form>
          {/* -----------------------------------Login Email Input - Div */}
          <FormDiv>
            <LabelInput htmlFor="login-input">First Name</LabelInput>
            <Input
              type="text"
              id="profile-input"
              placeholder="Enter first name"
              value={firstName}
              onChange={(e) => setFirstname(e.target.value)}
            />

            <LabelInput htmlFor="login-input">Last Name</LabelInput>
            <Input
              type="text"
              id="profile-input"
              placeholder="Enter last name"
              value={lastName}
              onChange={(e) => setLastname(e.target.value)}
            />

            <LabelInput htmlFor="login-input">Address</LabelInput>
            <Input
              type="text"
              id="profile-input"
              placeholder="Enter your address"
              value={address}
              onChange={(e) => setAddress(e.target.value)}
            />

            <LabelInput htmlFor="login-input">Phone Number</LabelInput>
            <Input
              type="number"
              id="profile-input"
              placeholder="Enter your phone number"
              value={phoneNumber}
              onChange={(e) => setPhoneNumber(e.target.value)}
            />

            <LabelInput htmlFor="login-input">Email</LabelInput>
            <EmailInput
              type="email"
              id="email-input"
              placeholder="youremail@domain.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <Backdrop
              sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
              open={loading}
            >
              <CircularProgress color="inherit" />
            </Backdrop>
            {apiError && (
              <Stack sx={{ width: "100%" }} spacing={2}>
                <Collapse in={apiError}>
                  <Alert
                    severity="error"
                    action={
                      <IconButton onClick={() => setApiError(false)}>
                        {" "}
                        <CloseIcon fontSize="inherit" />
                      </IconButton>
                    }
                    sx={{ mb: 2 }}
                  >
                    <AlertTitle>Error</AlertTitle>
                    {apiErrorMessage} <strong>check it out!</strong>
                  </Alert>
                </Collapse>
              </Stack>
            )}
            {apiSuccess && (
              <Stack sx={{ width: "100%" }} spacing={2}>
                <Collapse in={apiSuccess}>
                  <Alert
                    severity="success"
                    variant="outlined"
                    action={
                      <IconButton onClick={() => setApiSuccess(false)}>
                        {" "}
                        <CloseIcon fontSize="inherit" />
                      </IconButton>
                    }
                    iconMapping={{
                      success: <CheckCircleOutlineIcon fontSize="inherit" />,
                    }}
                    sx={{ mb: 2 }}
                  >
                    <AlertTitle>Success</AlertTitle>
                    {apiSuccessMessage}
                  </Alert>
                </Collapse>
              </Stack>
            )}

            <ButtonP type="submit" onClick={updateProfile}>
              Update
            </ButtonP>
          </FormDiv>
        </Form>
      </FormCard>
    </ContainerFBGpd>
  );
};

export default Profileform;
