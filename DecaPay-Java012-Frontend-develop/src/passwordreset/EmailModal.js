import React, {useState} from 'react';
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import Loader from "../globalresources/Loader";
import {baseEndpoint} from "../globalresources/Config";


const EmailModal = (props) => {
    const [userEmail, setUserEmail] = useState("");

    const [responseMessage, setResponseMessage] =useState(null);
    const [isSpinning, setisSpinning]=useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();
        setisSpinning(true);
        setResponseMessage(null);
        const forgetPasswordRequest = {email : userEmail};
        sendPasswordRequestMail(forgetPasswordRequest);
    }
    const sendPasswordRequestMail = (data) => {
        fetch(baseEndpoint+"/api/v1/auth/forgot-password",{
            method:"POST",
            headers:{
                "content-type":"application/json"
            },
            body:JSON.stringify(data)
        }).then(response=>{
            console.log(response);
            let status= response.status==200?"Password reset link sent":"Email address not found";

            setResponseMessage(status);
            setisSpinning(false);
            setUserEmail("");

        }).catch(error=>{
            console.log(error.message);
            setResponseMessage("error : "+ error.message + "- Password reset link not sent");
            setisSpinning(false);
        });
    };

    {
        return (
            <div>
                <Modal
                    open={props.open}
                    onClose={props.handleClose}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                    <Box className="modal-box">
                       <div><span class="modal-title">Reset Password</span> <button className="btnClose" onClick={props.handleClose}>X</button></div>
                        <form onSubmit={handleSubmit}>
                            <input type="email" className="form-input" name="email"
                                   value={userEmail}
                                   placeholder="Enter your registered Email Address"
                            onChange={(e)=>setUserEmail(e.target.value)}/>
                            {responseMessage && <span className="text-success responseStatus">{responseMessage}</span>}
                            <button className="form-button" type="submit">
                                Send Password Reset Link
                                <Loader status={isSpinning}/>
                            </button>
                        </form>



                    </Box>
                </Modal>
            </div>
        );
    }
}

export default EmailModal;