import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import loginLogo from "../../asset/img/loginLogo.png";
import { HiOutlineMail, HiOutlineKey } from "react-icons/hi";
import axios from "axios";
import { useStateContext } from "../Context/ContextProvider";
import Backdrop from "@mui/material/Backdrop";
import CircularProgress from "@mui/material/CircularProgress";
import Alert from "@mui/material/Alert";
import AlertTitle from "@mui/material/AlertTitle";
import Stack from "@mui/material/Stack";
import {
    ButtonP,
    CALink,
    CAPara,
    CAParaO,
    ContainerLG,
    EyeIcon,
    FGLink,
    FGPara,
    Form,
    IconFlex,
    IconFlexn,
    IconGrid,
    IconGridL,
    InputGrid,
    InputGridDiv,
    InputGridDivL,
    InputGridL,
    LGCard,
    LGLogo,
    LGLogoDiv,
    LabelHeader,
    LabelHeaderI,
    LabelHeaderO,
    LabelInput,
    LockIcon,
    MailIcon,
    SlashEyeIcon,
} from "../Styled/Styled";


function Login() {
    const navigate = useNavigate();
    const { setToken, setFirstName, setUserEmail, setLastName } =
        useStateContext();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [apiError, setApiError] = useState(false);
    const [apiErrorMessage, setApiErrorMessage] = useState("");


    const [showPass, setShowPass] = useState(false);


    const togglePasswordVisibilityPass = () => {
        setShowPass(!showPass);
    };




    const onSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        await axios
            .post("http://localhost:9000/api/v1/auth/login", { email, password })
            .then((response) => {
                setUserEmail(response.data.email);
                setToken(response.data.token);
                setFirstName(response.data.firstName);
                setLastName(response.data.lastName);
                navigate("/user");
            })
            .catch((error) => {
                setApiError(true);
                setApiErrorMessage(error.response.data.message);
            });
        setLoading(false);
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


            <LabelHeaderO>Hi, Welcome back</LabelHeaderO>


            <LGCard>
                <LabelHeaderI>Hi, Welcome back</LabelHeaderI>
                <Form>
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


                    <LabelInput>Password</LabelInput>


                    <InputGridDivL>
                        <InputGridL
                            type={showPass ? "text" : "password"}
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter your password"
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


                    <FGPara><FGLink to={"/forgotpassword"}>Forgot password?</FGLink></FGPara>


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

                    <ButtonP onClick={(e) => onSubmit(e)}>Login</ButtonP>
                </Form>

                <CAPara>
                    Don't have an account?&nbsp;<CALink to={"/signup"}>Create account</CALink>
                </CAPara>
            </LGCard>

            <CAParaO>
                Don't have an account?&nbsp;<CALink to={"/signup"}>Create account</CALink>
            </CAParaO>

            {/* </div> */}
        </ContainerLG>
    );
}


export default Login;



