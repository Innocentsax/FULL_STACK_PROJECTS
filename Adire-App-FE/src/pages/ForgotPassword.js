import React, { useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Wrapper from '../assets/wrappers/ForgotPassword'
import { Card, IconFormRow } from '../components'
import { EmailInputIcon } from '../utils/SVGIcons'
import { useSelector, useDispatch } from 'react-redux';
import { handleChange } from '../features/user/userSlice';
import { forgotPassword } from '../features/user/userSlice'
import { toast } from 'react-toastify'


const ForgotPassword = () => {
    const { isLoading, forgotPasswordMail, resetPasswordStatus } = useSelector((store) => store.user);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    console.log(forgotPasswordMail);

    const handleEmailInput = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        console.log(name, value);
        dispatch(handleChange({ name, value }));
    };

    const dispatchForgotPassword = async () => {
        await dispatch(forgotPassword({ email: forgotPasswordMail }));
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        if (!forgotPasswordMail) {
          toast.error('Please Fill Out All Fields');
          return;
        }
        await dispatch(forgotPassword({ email: forgotPasswordMail }));
        
        navigate("/reset-password", {state : forgotPasswordMail});
    };
        
    

return (
    <Card>
        <Wrapper>
            <div>
                <div className="content">
                    <span className="title">Forgot Password</span>
                    <p className="message">
                        Enter the email associated with your account and we’ll send an email with instruction to reset your password
                    </p>
                </div>
                <div className="actions">
                    {/* email field */}
                    <IconFormRow
                        type='email'
                        name='forgotPasswordMail'
                        labelText='email'
                        value={forgotPasswordMail}
                        placeholder="Enter your email"
                        handleChange={handleEmailInput}
                    >
                    <EmailInputIcon />
                    </IconFormRow>
                    <button 
                        onClick={handleSubmit} 
                        className="btn btn-block" 
                        type="button"
                    >
                        {isLoading ? 'loading...' : 'reset password'}
                    </button>
                    <div className='btn-container'>
                        <Link to={"/register"} className="link-btn" type="button">back to login</Link>
                    </div>
                </div>
            </div>
        </Wrapper>
    </Card>
  )
}

export default ForgotPassword