import { HiOutlineMail } from "react-icons/hi";
import { Link, useNavigate } from "react-router-dom";
import {
  ButtonP,
  ContainerLG,
  Form,
  IconFlexn,
  IconGridL,
  InputGridDivL,
  InputGridL,
  LGCard,
  LGLogo,
  LGLogoDiv,
  LabelHeaderV,
  LabelInput,
  MailIcon,
  VCard,
  VLink,
  VPara,
  VParaL,
} from "../Styled/Styled";
import axios from "axios";
import React, {useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import Backdrop from "@mui/material/Backdrop";
import {useStateContext} from "../Context/ContextProvider";

function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const {setUserEmail} = useStateContext();
  const navigate = useNavigate();

  const handleForgotPassword= async (e)=>{

    e.preventDefault()
    setLoading(true);
    await axios.post("http://localhost:9000/api/v1/password/forgot-password", {email})
        .then(response=>{
            setUserEmail(email);
          navigate("/checkemail")
        })
        .catch(error=>{
          console.log(error)
        })
    setLoading(false);
  }

  return (
    <ContainerLG>
      <Backdrop
          sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
          open={loading}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
      <LGLogoDiv to={"/"}>
        <LGLogo
          src="https://res.cloudinary.com/dafxzu462/image/upload/v1687697811/Frame_8402Logo_-_MoneyWay_an1xnp.svg"
          alt="user profile avatar"
        />
        MoneyWay
      </LGLogoDiv>

      <VCard>
        <LabelHeaderV>Forgot Password</LabelHeaderV>

        <Form>
          <VParaL>
            Enter the email associated with your account and we’ll send an email
            with instruction to reset your password
          </VParaL>

          <LabelInput >Email</LabelInput>

          <InputGridDivL>
            <InputGridL type="email" onChange={(e)=>setEmail(e.target.value)} placeholder="Enter your email" />

            <IconGridL>
              <IconFlexn>
                <MailIcon />
              </IconFlexn>
            </IconGridL>
          </InputGridDivL>
          <ButtonP onClick={handleForgotPassword}>Reset Password</ButtonP>
        </Form>
        <VLink to={"/login"}>Back to Login</VLink>
      </VCard>
    </ContainerLG>
  );
}

export default ForgotPassword;
