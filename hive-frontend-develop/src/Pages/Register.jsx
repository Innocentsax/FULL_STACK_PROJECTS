import React from 'react'
import { useNavigate } from 'react-router-dom';

function Register () {

    const navigate = useNavigate();

    const handleClick = () => {
        // navigate user to the email verification page
        navigate('/emailVerificationPage');
    };

        return (
        <>
            <div className="sign-up">
                <h2>welcome to the signup doer page now u can write all your code here</h2>
            </div>

            <div>
                <button onClick={handleClick} style={{margin: '50px', padding: '4px'}}>
                    Register
                </button>
            </div>
        </>    
        );

}
 
export default Register;