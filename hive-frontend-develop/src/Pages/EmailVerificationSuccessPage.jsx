import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';

const EmailVerificationSuccessPage = ({ token }) => {
    const {verificationStatus, setVerificationStatus} = useState(null);

    useEffect(() => {
        const verifyEmail = async () => {
            try {
                const response = await axios.get('/verifyRegistration', {
                    params: { token },
                });
                setVerificationStatus(response.data.message);
            } catch (error) {
                setVerificationStatus(error.response.data.message);
            }
        };
        verifyEmail();
    }, [setVerificationStatus, token]);
 
    return (
        <div className='container'>
            
            <h1>
                <FontAwesomeIcon icon = {faCheckCircle} color='green' />
                Email Verification Successful
            </h1>
            <h2>{verificationStatus}</h2>
            
                <Link to="/login">
                <button>Login</button>
                </Link>
    
        </div>
    );
};

export default EmailVerificationSuccessPage;