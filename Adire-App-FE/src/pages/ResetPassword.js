import React, { useState } from 'react'
import { Card, IconFormRow } from '../components'
import { PasswordInputIcon } from '../utils/SVGIcons'
import Wrapper from '../assets/wrappers/ResetPassword'
import { Link, useLocation } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux';
import { forgotPassword, resetPassword } from '../features/user/userSlice'
import VpnKeyOutlinedIcon from '@mui/icons-material/VpnKeyOutlined';
import { toast } from 'react-toastify'

const initialState = {
    otp: '',
    newPassword: '',
    confirmPassword: ''
};

const ResetPassword = () => {
    const [values, setValues] = useState(initialState);
    const { isLoading } = useSelector((store) => store.user);
    const dispatch = useDispatch();

    const { state : email } = useLocation();
    console.log(email);

    const handleChange = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        console.log(name, value);
        setValues({ ...values, [name]: value });
    };

    const resendForgotPassword = () => {
        console.log(email);
        console.log({ email: email });
        dispatch(forgotPassword({ email: email }));
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        const { otp, newPassword, confirmPassword } = values;
        if (!otp || !newPassword || !confirmPassword) {
          toast.error('Please fill out all fields');
          return;
        }
        if (newPassword !== confirmPassword) {
          toast.error('Password does not match');
          return;
        }
        console.log({ email, otp, newPassword, confirmPassword });
        dispatch(resetPassword({ email, otp, newPassword, confirmPassword }));
        
      };

    // {
    //     "confirmPassword": "string",
    //     "email": "string",
    //     "newPassword": "string",
    //     "otp": "string"
    // }
    
  return (
    <Card>
        <Wrapper>
            <div className="content">
                <h4 className="title">Reset Password</h4>
            </div>
            <div className="actions">
                {/* OTP field */}
                <IconFormRow
                    type='text'
                    name='otp'
                    labelText="OTP"
                    value={values.otp}
                    placeholder="Enter your otp"
                    handleChange={handleChange}
                >
                <VpnKeyOutlinedIcon className="form-icon" />
                </IconFormRow>

                {/* password field */}
                <IconFormRow
                    type='password'
                    name='newPassword'
                    labelText="new password"
                    value={values.newPassword}
                    placeholder="Enter your password"
                    handleChange={handleChange}
                >
                <PasswordInputIcon />
                </IconFormRow>

                {/* confirm password field */}
                <IconFormRow
                    type='password'
                    name='confirmPassword'
                    value={values.confirmPassword}
                    labelText="confirm password"
                    placeholder="Enter your confirm password"
                    handleChange={handleChange}
                >
                    <PasswordInputIcon />
                </IconFormRow>

                <button 
                    onClick={handleSubmit} className="btn btn-block" type="button" disabled={isLoading}
                >
                    reset password
                </button>
                <div>
                    <p className='title'>
                        Did not receive the email? Check your spam filter, or <br/>
                        <span onClick={resendForgotPassword} className='resend'>resend email</span>
                    </p>
                </div>
                {/* <div className='btn-container'>
                    <Link to={"/register"} className="link-btn" type="button">back to login</Link>
                </div> */}
            </div>
        </Wrapper>
    </Card>
  )
}

export default ResetPassword

