import React, {useState} from "react";
import { Link, useNavigate } from "react-router-dom";
import { BLLink, ButtonP, CALink, ContainerV, LGCard, LabelHeaderV, VCard, VLPara, VPara } from "../Styled/Styled";
import axios from "axios";
import {useStateContext} from "../Context/ContextProvider";
import Backdrop from "@mui/material/Backdrop";
import CircularProgress from "@mui/material/CircularProgress";


function CheckYourEmail(){
    const context = useStateContext();
    const navigate = useNavigate();
    const email = context.email;
    const [loading, setLoading] = useState(false);
    const handleResubmit= async () =>{
        setLoading(true);
        await axios.post("http://localhost:9000/api/v1/password/forgot-password", {email})
            .then(response=>{
                navigate("/checkemail")
            })
            .catch(error=>{
                console.log(error)
            })
        setLoading(false);
    }
    return(
        <ContainerV>
            <VCard>
                <LabelHeaderV>Check your mail</LabelHeaderV>
                    <VPara>We sent a password reset link to your email. Please click the link to reset your password.</VPara>
                <Backdrop
                    sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                    open={loading}
                >
                    <CircularProgress color="inherit" />
                </Backdrop>
                    <VLPara>
                        Didn’t received an email?&nbsp;<CALink onClick={handleResubmit}>Click to Resend</CALink>
                    </VLPara>

                    <BLLink to={"/login"}>
                        Back to Login
                    </BLLink>
            </VCard>
        </ContainerV>
    );

}

export default CheckYourEmail;