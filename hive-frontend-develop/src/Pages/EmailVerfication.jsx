import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Footer } from "../Component/Footer";
import PasswordService from "../service/PasswordService";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheckDouble } from "@fortawesome/free-solid-svg-icons";

const EmailVerification = () => {
    // const verifyToken = new URLSearchParams(window.location.search).get("token");
    const [verificationStatus, setVerificationStatus] = useState(false);

    useEffect(() => {
        const verifyToken = new URLSearchParams(window.location.search).get("token");

        if (verifyToken) {
            axios
                .get(`http://localhost:8080/auth/verifyRegistration?token=${verifyToken}`)
                .then((response) => {
                    console.log("VERIFICATION IN PROGRESS");
                    if (response.data.isSuccessful === true) {
                        setVerificationStatus(true);
                    }
                    console.log(response.data);
                })
                .catch((error) => {
                    console.log(error);
                    console.log("VERIFICATION IN ERROR");
                });
        }
    }, []);

    return (
        <>
            {verificationStatus ? (
                <div className="container">
                    <div className="auth-wrapper" style={{height:"202px"}}>
                        <div className="">
                            <div className="">
                                <div className="auth-body">
                                    <p className="auth-head">Verification Successful</p>
                                    <div className="auth-form-container text-start">
                                     <Link to="/login">
                                         <button type="" className="submit-password-reset">
                                             Login
                                         </button>
                                     </Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    {/*<div className="footer">*/}
                    {/*<Footer />*/}
                    {/*</div>*/}
                </div>
            ) : (
                <h2>Verification Failed</h2>
            )}
        </>
    );
};

export default EmailVerification;
