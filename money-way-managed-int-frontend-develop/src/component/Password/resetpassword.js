import React, { useState } from "react";

import {HiOutlineLockClosed} from "react-icons/hi2";
import loginLogo from "../../asset/img/loginLogo.png";
import ResetSuccessModal from "../Login/loginsuccessmodal";
import { ButtonP, ContainerLG, Form, IconFlexn, IconGridL, InputGridDivL, InputGridL, LGCard, LGLogo, LGLogoDiv, LabelHeaderI, LabelHeaderO, LabelInput, LockIcon, MailIcon } from "../Styled/Styled";
import axios from "axios";
import CircularProgress from "@mui/material/CircularProgress";
import Backdrop from "@mui/material/Backdrop";
import Stack from "@mui/material/Stack";
import Collapse from "@mui/material/Collapse";
import Alert from "@mui/material/Alert";
import {IconButton} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";
import AlertTitle from "@mui/material/AlertTitle";
import {useNavigate} from "react-router-dom";

function ResetPassword(){
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [loading, setLoading] = useState(false);

    const [apiSuccess, setApiSuccess] = useState(false);
    const [apiSuccessMessage, setApiSuccessMessage] = useState("");
    const navigate = useNavigate();

  const handleResetPassword= async (e)=>{
    e.preventDefault();
    setLoading(true);

    const queryString = window.location.search;
    const params = new URLSearchParams(queryString);
    const token = params.get('attribute-token');

    await axios.put("http://localhost:9000/api/v1/password/reset-password?token="+token, {newPassword, confirmPassword})
        .then(response=>{
          setApiSuccessMessage(response.data);
          setApiSuccess(true)
          navigate("/login")
        })
        .catch(error=>{
          console.log(error)
        })
    setLoading(false);
  }

    return(
        <ContainerLG>
      <LGLogoDiv to={"/"}>
        <LGLogo
          src="https://res.cloudinary.com/dafxzu462/image/upload/v1687697811/Frame_8402Logo_-_MoneyWay_an1xnp.svg"
          alt="user profile avatar"
        />
        MoneyWay
      </LGLogoDiv>

      <LabelHeaderO>Reset Password</LabelHeaderO>

      <LGCard>
        <LabelHeaderI>Reset Password</LabelHeaderI>
        <Form>
          <LabelInput>New Password</LabelInput>

          <InputGridDivL>
            <InputGridL
              type="password"
              onChange={(e)=>setNewPassword(e.target.value)}
              placeholder="Enter new password"
            />

            <IconGridL>
              <IconFlexn>
                <LockIcon />
              </IconFlexn>
            </IconGridL>
          </InputGridDivL>

          <LabelInput >Confirm Password</LabelInput>

          <InputGridDivL>
            <InputGridL
              type="password"
              onChange={(e)=>setConfirmPassword(e.target.value)}
              placeholder="Confirm password"
            />
            <IconGridL>
              <IconFlexn>
                <LockIcon />
              </IconFlexn>
            </IconGridL>
          </InputGridDivL>
          <Backdrop
              sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
              open={loading}
          >
            <CircularProgress color="inherit" />
          </Backdrop>
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
          <ButtonP onClick={handleResetPassword}>Reset Password</ButtonP>
        </Form>
      </LGCard>

    </ContainerLG>

    );
}

export default ResetPassword;