import classes from './Signup.module.css';
import { useNavigate } from 'react-router-dom'; 
import { useState} from 'react';
import User from '../modal/User';
import { useSelector } from 'react-redux';
import AuthenticationService from '../../services/AuthenticationService'


const Signup = () =>{

    const [user, setUser] = useState(new User("","","","",""))
    const [loading, setLoading] = useState(false)
    const [submitted, setSubitted] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const navigate = useNavigate()

    const currentUser = useSelector(state => state.user)

    const handleChange = (e) => {
        const {name, value} = e.target;

        setUser((prevState) => {
            return{
                ...prevState, 
                [name] : value,
            }     
        })

    }

    const handleRegister = (e) => {
        e.preventDefault();

        setSubitted(true);


        AuthenticationService.register(user).then(() => {
            navigate("/login")
        }).catch((error) => {
            console.log(error);
            if(!error?.response?.status === 409){
                setErrorMessage("Username or password is not valid")
            } else {
                setErrorMessage("Unexpected error")
            }
            setLoading(false)
        })
    }


    return(
    <div className={classes.signupcontainer}>

        <h1>Create Account</h1>

        <p className={classes.error}>{errorMessage}</p>

        <form onSubmit={(e) => handleRegister(e)} >

            <div className={classes.field}>
            <input value={user.firstName} name="firstName" onChange={(e) => handleChange(e)} type="text" />
            <span></span>
            <label>First Name <span>*</span></label>
            </div>


            <div className={classes.field}>
            <input
            value={user.lastName} name="lastName" onChange={(e) => handleChange(e)} 
            type="text"/>
            <span></span>
            <label>Last Name <span>*</span></label>
            </div>


            <div className={classes.field}>
            <input value={user.email} name="email" onChange={(e) => handleChange(e)} 
            type="email"/>
            <span></span>
            <label>Email <span>*</span></label>
            </div>


            <div className={classes.field}>
            <input 
            value={user.password} name="password" onChange={(e) => handleChange(e)}
            type="password"/>
            <span></span>
            <label>Password <span>*</span></label>
            </div>


            <div className={classes.field}>
            <input 
            value={user.confirmPassword} name="confirmPassword" onChange={(e) => handleChange(e)}
            type="password"/>
            <span></span>
            <label>Confirm password <span>*</span></label>
            </div>  

            <button onClick={(e) => handleRegister(e)} type='submit'>Submit</button>

            <div className={classes.signin_link}>Already have an account?  <a href="/login">Sign In</a></div>
            
        </form>
    </div>
    )

}
export default Signup;