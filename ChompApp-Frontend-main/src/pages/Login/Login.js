import React, {useEffect, useState} from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import User from "../../models/User";
import AuthenticationService from "../../services/AuthenticationService";
import { setCurrentUser } from "../../store/actions/user";
import "./Login.css";
import { motion } from "framer-motion";



const Login = () =>{

     const [user, setUser] = useState(new User("","","","")
     );
     const [loading, setLoading] = useState(false);
     const [submitted, setSubmitted] = useState(false);
     const [errorMessage, setErrorMessage] = useState("");
     const navigate = useNavigate();


     const dispatch = useDispatch();

     const currentUser = useSelector((state) => state.user);

     useEffect(() => {
       if (currentUser?.id) {
         navigate("/");
       }
     }, []);

     const handleChange = (e) => {
       const { name, value } = e.target;

       setUser((prevState) => {
         return {
           ...prevState,
           [name]: value,
         };
       });
     };

     const handleLogin = (e) => {
       e.preventDefault();

       setSubmitted(true);

       setLoading(true);

       AuthenticationService.login(user)
         .then((response) => {
           dispatch(setCurrentUser(response.data));
           navigate("/");
           window.location.reload();
         })
         .catch((error) => {
           console.log(error);
           setErrorMessage("username or password is not valid");
           setLoading(false);
         }); 
     };

    return (
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0, transition: { duration: 0.3 } }}
      >
        <div className="container mt-5 signup login">
          <div className="card ms-auto me-auto p-3 custom-card">
            {errorMessage && (
              <div className="alert alert-danger">{errorMessage}</div>
            )}
            <form
              onSubmit={(e) => handleLogin(e)}
              noValidate
              className={submitted ? "was-validated" : ""}
            >
              <div className="form-group mb-4">
                <label htmlFor="user-name" className="mb-2">
                  Email:
                </label>
                <input
                  type="email"
                  name="email"
                  className="form-control"
                  placeholder="Email"
                  required
                  value={user.email}
                  onChange={(e) => handleChange(e)}
                />
                <div className="invalid-feedback">Username is required.</div>
              </div>

              <div className="form-group">
                <label htmlFor="password" className="mb-2">
                  Password:
                </label>
                <input
                  type="password"
                  name="password"
                  className="form-control"
                  placeholder="Password"
                  required
                  value={user.password}
                  onChange={(e) => handleChange(e)}
                />
                <div className="invalid-feedback">Password is required.</div>
              </div>

              <button className="btn btn-success w-100 mt-3" disabled={loading}>
                Sign In
              </button>
            </form>

            <Link
              to="/signup"
              className="btn btn-link"
              style={{ color: "darkgrey" }}
            >
              Create an Account
            </Link>
          </div>
        </div>
      </motion.div>
    );
}
export default Login