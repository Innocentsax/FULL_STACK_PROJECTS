import React, {useEffect,useState} from 'react';
import axios from 'axios'
import { Navigate, useNavigate, useParams } from 'react-router-dom';
import toast from 'react-hot-toast';

function PasswordResetForm({email}) {
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [isError, setIsError] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const { token } = useParams()
    const navigate = useNavigate()

    const Password = async(e)=>{
        e.preventDefault()
        if (newPassword !== confirmPassword){
            setIsError(true)
            setErrorMessage("Passwords do not match")
        }
        else{
            try {
                const data = await axios.post(`http://127.0.0.1:8080/api/v1/auth/reset-password/${token}`, {newPassword, confirmPassword});
                console.log("data is ", data)
                if (data.status === 200){
                    setConfirmPassword("")
                    setNewPassword("")
                    toast.success(data.data.description)
                    setTimeout( () => {
                        navigate("/login")
                    }, 2000)
                }
                
            } 
            
            catch (error) {
                console.log(error)
                toast.error("Something went wrong")
            }
        }
    };

    return (
        <div className="container">
            <div className="row justify-content-center align-items-center">
                <div className="col-6">

                    <h4 className="h4 mb-3 mt-3">Password Reset</h4>

                    <div className="mb-3">
                        <label htmlFor="new-password" className="form-label">New Password</label>
                        <input type="password" className="form-control" id="new-password" placeholder="Enter Password" value={newPassword} onChange={(e)=> {
                            setNewPassword(e.target.value)
                            setIsError(false)

                            }
                        } />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="confirm-password" className="form-label">Confirm Password</label>
                        <input type="password" className="form-control" id="confirm-password"  placeholder="Enter Password" value={confirmPassword} onChange={(e)=>{
                            setConfirmPassword(e.target.value)
                            setIsError(false)
                        } 
                    }/>
                    </div>
                    {isError && <div style={{color: "red"}}>{errorMessage}</div>}
                    <div className="mb-3 mt-5">
                        <button className="btn btn-primary c-submit-button" onClick={Password}>Reset</button>
                    </div>

                </div>
            </div>

        </div>
    );
}

export default PasswordResetForm;