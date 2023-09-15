import React, { useState } from 'react'
import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import User from '../modal/User'
import AuthenticationService from '../../services/AuthenticationService'
import { setCurrentUser } from '../../store/actions/user'
import styles from './Login.module.css'


const Login = () => {
  const [user, setUser] = useState(new User("", "", "", "", ""))
  const [loading, setLoading] = useState(false)
  const [submitted, setSubitted] = useState(false)
  const [errorMessage, setErrorMessage] = useState("")
  const navigate = useNavigate()

  const dispatch = useDispatch();

  const currentUser = useSelector(state => state.user);

  useEffect(() => {
    if (currentUser?.id) {
      navigate("/")
    }
  }, [])




  const handleChange = (e) => {
    const { name, value } = e.target;

    setUser((prevState) => {
      return {
        ...prevState, [name]: value,
      }
    })
  }

  const handleLogin = (e) => {
    e.preventDefault();

    setSubitted(true);
    setLoading(true);

    AuthenticationService.login(user).then((response) => {
      dispatch(setCurrentUser(response.data));
      navigate("/")
    }).catch((error) => {
      console.log(error);
      setErrorMessage("username or password is not valid");
      setLoading(false)
    })
  }

  return (
    
    <div className={styles.logincontainer}>
      <h1>Sign In</h1>
      <form onSubmit={(e) => handleLogin(e)} noValidate>

      <p className={styles.error}>{errorMessage}</p>

        <div className={styles.field}>
          <input value={user.email} name="email" onChange={(e) => handleChange(e)}
            type="email" />
          <span></span>
          <label>Email</label>
        </div>

        <div className={styles.field}>
          <input value={user.password} name="password" onChange={(e) => handleChange(e)}
            type="password" />
          <span></span>
          <label>Password</label>
        </div>

        {/* <div className={styles.pass}>Forgot Password?</div> */}

        <button onClick={(e) => handleLogin(e)} type='submit'>Submit</button>

        <div className={styles.signup_link}>New here?  <a href="/signup">Sign Up</a></div>
      </form>
      
    </div>
  )
}

export default Login