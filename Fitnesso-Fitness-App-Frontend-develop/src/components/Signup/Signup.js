import React, { useState} from 'react'
import { useForm} from 'react-hook-form';
import Footer from "../Main/Footer/Footer";
import Navbar from "../Main/Navbar/Navbar";
import { IconButton, Modal, Typography} from "@mui/material";
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import Box from "@mui/material/Box";
import CircularProgress from '@mui/material/CircularProgress';
import { Link } from 'react-router-dom'
import './signup.css';
const Signup = () => {
    const [values, setValues] = React.useState({
        password: '',
        showPassword: false,
    });
    const [generalError, setGeneralError] = useState();
    const { register, handleSubmit, formState: {errors} } = useForm();
    const[uname, setUname] = useState();
    const[suggestName, setSuggestName] = useState();
    const[showSuggestion, setShowSuggestion] = useState(false);
    const[isLoading, setIsLoading] = useState(false)
    const handleClickShowPassword = () => {
        setValues({
            ...values,
            showPassword: !values.showPassword,
        });
    };
    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    };
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const style = {
        position: 'absolute',
        fontfamily: 'sans-serif',
        borderRadius: '2em',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        backgroundColor: 'white',
        color:'black',
        boxShadow: 24,
        p: 4,
    };
    const backendValidator = (e, a) =>{
        if (e === "Not a valid email"){
            setIsLoading(false)
            setGeneralError("Invalid Email")
        }else if (e === "Email not registered"){
            setIsLoading(false)
            setGeneralError("Invalid Email")
        }else if (e.startsWith("Mail server connection failed")){
            setIsLoading(false)
            setGeneralError("Registration failed try again")
        }else if(e === "Not a valid phone Number"){
            setIsLoading(false)
            setGeneralError("Invalid Phone Number")
        }else if(e === "email taken"){
            setIsLoading(false)
            setGeneralError("Account with email already exists")
        }else if(e.startsWith("userName is taken")){
            setIsLoading(false)
            setGeneralError("UserName is already taken")
            let n = e.split(",")
            setSuggestName("available username suggestions: "+n[1])
            setShowSuggestion(true)
            console.log("suggest",suggestName)
        }else if(e === "Successful"){
            setIsLoading(false)
            setGeneralError()
            setUname(a)
            handleOpen()
        }else {
            setIsLoading(false)
            setGeneralError("Try Again")
        }
    }
    //consume API
    const onSubmit = async (data)=>{
        let res =await fetch("https://fitnesso-app-new.herokuapp.com/person/register",{
        // let res =await fetch("http://localhost:9067/person/register",{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(data)
        });
        let info = await res.json();
        console.log(info);
        setIsLoading(true);
        let received = info.message;
        console.log("message: ", received)
        setShowSuggestion(false)
        backendValidator(received, data.userName)
    }
    return (
        <div>
            <Navbar/>
            <div className='main'>
                <div className='signup-container'>
                    <h3 className={"sign"}>Create Account</h3>
                    <h5 className={"generalError"}>{generalError}</h5>
                    {/* {showSuggestion ? (<h5 className={"suggestion"}>{"("+suggestName+")"}</h5>) : (<span></span>)} */}
                    {isLoading ? (<div className={"loader"}><CircularProgress disableShrink /></div>) : (
                        <form className='form1' onSubmit={handleSubmit(onSubmit)}>
                            <div className={"forminput"}>
                                <input type="text" className='inputers' align="center"
                                       placeholder='First Name' name='firstName' {...register("firstName", { required: true, minLength: 2})} aria-describedby="component-error-text"/>
                                <input type="text" className='inputers twin' align="center"
                                       placeholder='Last Name' name='lastName' {...register('lastName', { required: true, minLength: 2  })} aria-describedby="component-error-text"/>
                            </div>
                            <div className={"errors"}>
                                <h6 className={"errorbox"}>{errors.firstName && <span>First Name is Required</span>}</h6>
                                <h6 className={"errorbox"} >{errors.lastName && <span>Last Name is Required</span>}</h6>
                            </div>
                            <div className={"forminput"}>
                                <input type="text" className='inputers' align="center"
                                       placeholder='Username' name='userName'  {...register('userName',
                                    { required: true })} aria-describedby="component-error-text" />
                                <span className='passwordinput twin' >
                                <input type={values.showPassword ? "text" : "password" } {...register('password', { required: "Password is Required", minLength: {value: 8} })} className={"passwordinput1"} align="center"
                                       placeholder='Password' name='password' aria-describedby="component-error-text"/>
                                <IconButton
                                    aria-label="toggle password visibility"
                                    onClick={handleClickShowPassword}
                                    onMouseDown={handleMouseDownPassword}
                                    edge="end">
                                    {values.showPassword ?  <Visibility/> : <VisibilityOff/> }
                                </IconButton>
                            </span>
                            </div>
                            <div className={"errors"}>
                                <h6 className={"errorbox"}>{errors.userName && <span>Username is Required</span>}</h6>
                                <h6 className={"errorbox"} >{errors.password && <span>invalid password(minimum of 8 characters)</span>}</h6>
                            </div>
                            <div className={"forminput"}>
                                <input name='email'  type="email" className='inputers' align="center"
                                       placeholder='Email' {...register('email',
                                    { required: true, pattern:{value:/^\w+([-]?\w+)*@\w+([-]?\w+)*(\.\w{2,3})+$/} })}
                                       aria-describedby="component-error-text"/>
                                <input name='phoneNumber'  type="number" className='inputers twin' align="center"
                                       placeholder='Phone Number' {...register('phoneNumber',
                                    { required: "Phone Number is Required", pattern:{value:/^(?:(?:\\+|0{0,2})91(\s*[\\-]\s*)?|[0]?)?[789]\d{9}$/} })}
                                       aria-describedby="component-error-text"/>
                            </div>
                            <div className={"errors"}>
                                <h6 className={"errorbox"} >{errors.email && <span>Invalid email address</span>}</h6>
                                <h6 className={"errorbox"} >{errors.phoneNumber && <span>invalid Phone Number</span>}</h6>
                            </div>
                            <div className='reg fromgender' aria-describedby="component-error-text">
                                <label className='gender' id="demo-row-radio-buttons-group-label"><strong>Gender:</strong></label>
                                <input name='gender' value="Male" type="radio" {...register('gender', { required: "Gender is Required" })} />  <label  className='gender'>Male </label>
                                <input  name='gender' value="Female"  type="radio" {...register('gender', { required: "Gender is Required" })}/>  <label  className='gender'>Female </label>
                                <input  name='gender'  value="Others" type="radio" {...register('gender', { required: "Gender is Required" })}/>  <label  className='gender'>Other</label>
                            </div>
                            <div className={"errors"}>
                                <h6 >{errors.gender && <span>click to select a gender</span>}</h6>
                            </div>
                            <div className={" fromgender"}>
                                <input type="date" name='dateOfBirth' className='inputers' align="center"
                                       placeholder='Date of Birth' {...register('dateOfBirth',
                                    { required: "Date of Birth is Required" })} aria-describedby="component-error-text" />
                            </div>
                            <div className={"errors"}>
                                <h6>{errors.dateOfBirth && <span>Date of Birth is Required</span>}</h6>
                            </div>
                            <div className={"fromgender"}>
                                <button type='submit' className='sender'>Create Account</button>
                            </div>
                            <hr/>
                            <div className="fromgender">
                                <h5>Already have an account?</h5>
                                <a href="/login" className='btn-sm buttn'>LOGIN</a>
                            </div>
                        </form>
                    ) }
                </div>
                <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                    <Box sx={style}>
                        <Typography className={'modalbody'} id="modal-modal-description" sx={{ mt: 2 }}>
                            Hello {uname}, Please Continue to Create Address
                        </Typography>
                        <button className={"modalcontinue"}><Link to={"/createaddress/"+uname}>Continue</Link></button>
                    </Box>
                </Modal>
            </div>
            <Footer/>
        </div>
    )
}
export default Signup