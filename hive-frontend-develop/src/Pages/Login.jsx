import React, {useEffect, useState} from "react";
import "./CSS/login.css";
import "../Pages/CSS/login.css";
import icon from "../Assets/Icon.png";
import HeaderBar from "../Component/HeaderBar";
import {Link} from 'react-router-dom'
import {FaSpinner} from 'react-icons/fa';
import LoginAndRegisterNavBar from "../Component/LoginAndRegisterNavBar/LoginAndRegisterNavBar";
import jwt_decode from "jwt-decode";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import SuccessPopModal from "../Component/SuccessPopModal";
import {useNavigate} from "react-router-dom";



const Login = defaultValue => {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [emailError, setEmailError] = useState("");
    const [passwordError, setPasswordError] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    // const [isLoggedIn, setIsLoggedIn] = useState(true);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [openSuccess, setOpenSuccess] = React.useState(false);
    const style = {

        position: "absolute",

        top: "50%",

        left: "50%",

        transform: "translate(-50%, -50%)",

        bgcolor: "background.paper",

        boxShadow: 24,

        p: 4,

        display: "inline-flex",

    };
    const verifyMessage = " Verify your email";
    let navigate = useNavigate();

    const instruction =

        "Hi there, you have to verify your email before you can login. Please check your email for the verification link.";

    const handleOpenSuccess = () => setOpenSuccess(true);

    const handleSuccessClose = () => {

        setOpenSuccess(false);
        localStorage.setItem("isLoggedIn", "false")

        console.log(localStorage.getItem("isLoggedIn"));

        // when the modal is closed, navigate to the login

        navigate("/login");

    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setEmailError("");
        setPasswordError("");
        setIsLoading(true);

        // Email validation
        if (!email.includes("@") || !email.includes(".")) {
            setEmailError("Please enter a valid email address");
            setIsLoading(false);
            return;
        }

        // Password validation
        if (password === "") {
            setPasswordError("Please enter a password");
            setIsLoading(false);
            return;
        } else if (password.length < 8) {
            setPasswordError("Password must be at least 8 characters long");
            setIsLoading(false);
            return;
        } else if (!password.match(/[A-Z]/)) {
            setPasswordError("Password must contain at least one uppercase letter");
            setIsLoading(false);
            return;
        } else if (!password.match(/[a-z]/)) {
            setPasswordError("Password must contain at least one lowercase letter");
            setIsLoading(false);
            return;
        } else if (!password.match(/[0-9]/)) {
            setPasswordError("Password must contain at least one number");
            setIsLoading(false);
            return;
        } else if (!password.match(/[^A-Za-z0-9]/)) {
            setPasswordError("Password must contain at least one symbol");
            setIsLoading(false);
            return;
        }
        try {
            const response = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({email, password}),
            });

            const data = await response.json();

            if (response.ok) {


                const decodedToken = jwt_decode(data.result.token);
                const fullName = decodedToken.fullName;
                const verifiedStatus = decodedToken.verifiedStatus;

                localStorage.setItem("token", data.result.token);
                localStorage.setItem("fullName", fullName);
                localStorage.setItem("verifiedStatus", verifiedStatus);


                const userDto = {token: data.result.token, isLoggedIn: true, fullName: fullName};

                localStorage.setItem("userDto", JSON.stringify(userDto));

                const roles = JSON.parse(window.atob(localStorage.getItem("token").split(".")[1]))
                    .roles;

                console.log(verifiedStatus);

                if (verifiedStatus!==true) {
                    handleOpenSuccess();
                    localStorage.setItem("isLoggedIn", "false");
                    return "";

                }
                localStorage.setItem("isLoggedIn", "true");
                console.log("i reached here");

                if (roles.includes("TASKER")) {
                    localStorage.setItem("userRole", "TASKER")
                    window.location.href = "/tasker/create-job";
                } else if (roles.includes("DOER")) {
                    localStorage.setItem("userRole", "DOER")
                    window.location.href = "/dashboard";
                }
            } else {
                throw new Error(data.message);
            }
        } catch (error) {
            console.error(error);
            setPasswordError("Email and Password do not match")
        } finally {
            setIsLoading(false);
        }
    };

    const [renderKey, setRenderKey] = useState(0);
    const [count, setCount] = useState(0);

    useEffect(() => {
        setCount((prevCount) => prevCount + 1);
    }, []);

    useEffect(() => {
        if (count === 1) {
            // window.location.reload();
        }
    }, [count]);
    return (
        <>
            {/*<LoginAndRegisterNavBar/>*/}
            <div key={renderKey} style={{padding: "7rem 6rem", backgroundColor: "#F2F4F7"}} className="login-container">

                <div className="form-box">
                    <h2>Hi, Welcome back</h2>
                    <form onSubmit={handleSubmit}>
                        {/*<div className="input-group">*/}
                            <div className="email">
                                <label>Email</label>
                                <input
                                    type="text"
                                    placeholder="Enter your email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                                {emailError && <div className="error">{emailError}</div>}
                            </div>
                            <div className="password-input">
                                <label>Password</label>
                                <input
                                    type="password"
                                    placeholder="Enter your password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                                {passwordError && <div className="error">{passwordError}</div>}
                            </div>
                            <div className="forgot-link">
                                <p>
                                    <Link className="forgot-password" to={"/forget-password"}>
                                        Forgot password?
                                    </Link>
                                </p>
                            </div>
                            <div className="login-button">
                                <button type="submit" disabled={isLoading}>{isLoading ?
                                    <FaSpinner className="spinner"/> : "Login"}</button>
                                <p>
                                    Don't have an account?
                                    <Link className="create-account-link" to={"/register"}>
                                        Create account
                                    </Link>
                                </p>
                            </div>
                        <Modal

                            open={openSuccess}

                            onClose={handleSuccessClose}

                            aria-labelledby="modal-modal-title"

                            aria-describedby="modal-modal-description"

                        >

                            <Box sx={style}>

                                <SuccessPopModal myProp={verifyMessage} message={instruction}/>

                            </Box>

                        </Modal>
                        {/*</div>*/}
                    </form>
                </div>
            </div>
        </>

    );
};

export default Login;

