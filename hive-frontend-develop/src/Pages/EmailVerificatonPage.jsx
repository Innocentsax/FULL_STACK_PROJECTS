import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faCheckDouble} from '@fortawesome/free-solid-svg-icons';
import {Link} from 'react-router-dom';

const EmailVerificatonPage = ({token}) => {
    const verifyToken = new URLSearchParams(window.location.search).get("token");
    let {verificationStatus, setVerificationStatus} = useState(true);


    useEffect(() => {
        const verifyToken = new URLSearchParams(window.location.search).get("token");
        if (verifyToken) {
            axios
                .get(`http://localhost:8080/auth/verifyRegistration?token=${verifyToken}`)
                .then((response) => {
                    console.log("VERIFICATION IN PROGRESS");
                    if (response.data.isSuccessful === true) {
                        verificationStatus = true;
                    }
                    console.log(response.data);
                })
                .catch((error) => {
                    console.log(error);
                    console.log("VERIFICATION IN error");
                });
        }
    }, []);
    // useEffect(() => {
    //
    //     axios.get()


        // const verifyEmail = async () => {
        //     try {
        //         console.log("VERIFICATION IN PROGRESS");
        //         const response = await axios.get(`http://localhost:8080/auth/verifyRegistration?token=${verifyToken}`)
        //         if (response.data.isSuccessful === true) {
        //             verificationStatus = true;
        //         }
        //     } catch (error) {
        //         verificationStatus = false;
        //         console.log("VERIFICATION IN ERRO");
        //         console.log(verifyToken);
        //     }
        // };
        // verifyEmail();
    // }, [setVerificationStatus, token]);
    return (

        <h2>I habddkmkdm dfdkfnmkdfndkfndnfk</h2>
        // verificationStatus ? (<react className='container'>
        //     <react className="modal">
        //         <react className="overlay"></react>
        //         <react className="modal-content"><h1><FontAwesomeIcon icon={faCheckDouble} color='green'/> Email
        //             Verification Successful </h1> <h2>{verificationStatus}</h2> <Link to="/login">
        //             <button>Login</button>
        //         </Link></react>
        //     </react>
        // </react>) : <h2>{verificationStatus}</h2>

    )


};
export default EmailVerificatonPage;