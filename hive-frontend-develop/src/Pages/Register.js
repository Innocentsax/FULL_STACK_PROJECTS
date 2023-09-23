import React, {useState} from "react";

import Input from "../Component/inputComp";

import "../Pages/CSS/register.css";

import {useForm} from "react-hook-form";

import {yupResolver} from "@hookform/resolvers/yup";

import * as yup from "yup";

import {useNavigate} from "react-router-dom";

import axios from "axios";

import LoginAndRegisterNavBar from "../Component/LoginAndRegisterNavBar/LoginAndRegisterNavBar";

import ErrorPopModal from "../Component/ErrorPopModal";

import Modal from "@mui/material/Modal";

import Box from "@mui/material/Box";

import SuccessPopModal from "../Component/SuccessPopModal";


const schema = yup.object({

    fullname: yup.string().required("Please Enter your Full Name"),

    phonenumber: yup

        .string()

        .required("Phone Number is Required")

        .matches(/^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/,

            "phone number must be valid"),

    email: yup

        .string()

        .required("Email Address is required")

        .matches(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,

            "email must be valid"),

    password: yup.string().min(6, "Password must be at least 6 characters"),

    address: yup.string().required("this field must be filled"),

    identification: yup

        .mixed()

        .test("fileSize", "File size is too large", (value) => {

            return !value || value[0].size <= 1048576; // 1 MB

        })

        .test("fileType", "Only image files are allowed", (value) => {

            return !value || value[0].type.includes("image/");

        })

        .nullable(true),

    role: yup.string().required("Please select a role"),

});


const Register = () => {

    // e.preventDefault();


    const [openErrorDialog, setOpenErrorDialog] = React.useState(false);

    const handleOpenErrorDialog = () => setOpenErrorDialog(true);

    const handleCloseErrorDialog = () => setOpenErrorDialog(false);

    const [errorMessage, setErrorMessage] = useState("");

    const [openSuccess, setOpenSuccess] = React.useState(false);

    const verifyMessage = " Verify your email";

    const instruction =

        "Hi there, a verification link has been sent to your email.";

    const handleOpenSuccess = () => setOpenSuccess(true);

    const {

        handleSubmit,

        register,

        formState: {errors},

    } = useForm({

        resolver: yupResolver(schema),

    });

    const formSubmit = async (data) => {

        try {

            const response = await axios.post("http://localhost:8080/auth/register", {

                fullName: data.fullname,

                email: data.email,

                phoneNumber: data.phonenumber,

                password: data.password,

                confirmPassword: data.password,

                address: data.address,

                role: data.role,

                validId: data.email,

            });

            if (response.created) {

                const result = await response.json();

                console.log(result);

                console.log("success");

                handleOpenSuccess();

            } else {

                // FIx the flow above

                handleOpenSuccess();

                console.error(`HTTP error: ${response.status}`);

            }

        } catch (error) {

            setErrorMessage(error.response.data.message);

            handleOpenErrorDialog();

            console.error(`Error: ${error}`);

        }

    };


    let navigate = useNavigate();


    const handleSuccessClose = () => {

        setOpenSuccess(false);

        // when the modal is closed, navigate to the login

        navigate("/login");

    };


    const handleFormSubmit = (event) => {

        event.preventDefault(); // Prevent the form from submitting

        handleSubmit(formSubmit)(event); // Call the form submission handler

    };


    const routeChange = () => {

        let path = `/login`;

        navigate(path);

    };


    //style for response modal

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


    return (

        <>

            {/*<LoginAndRegisterNavBar/>*/}

            <Modal

                open={openErrorDialog}

                onClose={handleCloseErrorDialog}

                aria-labelledby="modal-modal-title"

                aria-describedby="modal-modal-description"

            >

                <Box sx={style}>

                    <ErrorPopModal myProp={errorMessage}/>

                </Box>

            </Modal>

            <div className="sign-up">

                <form onSubmit={handleSubmit(formSubmit)}>

                    <div className="sign-up-title">

                        <h2>Sign up</h2>

                        <p>It's free and only takes a minute</p>

                    </div>

                    <Input

                        id="fullname"

                        label="Full Name"

                        type="text"

                        placeholder="Type Your Full Name"

                        register={{...register("fullname")}}

                        errorMessage={errors.fullname?.message}

                    />

                    <Input

                        id="email"

                        label="Email"

                        type="email"

                        placeholder="Type Your Email Address"

                        register={{...register("email")}}

                        errorMessage={errors.email?.message}

                    />

                    <Input

                        id="phonenumber"

                        label="Phone Number"

                        type="text"

                        placeholder="Type Your Phone Number"

                        register={{...register("phonenumber")}}

                        errorMessage={errors.phonenumber?.message}

                    />

                    <Input

                        id="password"

                        label="Password"

                        type="password"

                        placeholder="Type Your Password"

                        register={{...register("password")}}

                        errorMessage={errors.password?.message}

                    />

                    <Input

                        id="address"

                        label="Address"

                        type="text"

                        placeholder="Type Your  Address"

                        register={{...register("address")}}

                        errorMessage={errors.address?.message}

                    />

                    <div className="sign-up-role">

                        <label htmlFor="role">Role</label>

                        <br/>

                        <input

                            type="radio"

                            id="tasker"

                            name="role"

                            value="TASKER"

                            {...register("role")}

                        />

                        <label htmlFor="tasker">Tasker</label>

                        <br/>

                        <input

                            type="radio"

                            id="doer"

                            name="role"

                            value="DOER"

                            {...register("role")}

                        />

                        <label htmlFor="doer">Doer</label>

                        <br/>

                    </div>

                    <button type="submit" className="sign-up-btn">

                        Sign Up

                    </button>

                </form>


                <p>

                    Already have an account?{" "}

                    <span>

<a href="/login"> Login</a>

 </span>

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

        </>

    );

};


export default Register;