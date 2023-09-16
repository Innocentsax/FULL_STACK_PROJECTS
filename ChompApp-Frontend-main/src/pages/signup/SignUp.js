import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import User from "../../models/User";
import AuthenticationService from "../../services/AuthenticationService";
import "./Signup.css"
import { motion } from "framer-motion";



 const SignUp = () => {

    const [user, setUser] = useState(new User("","","","","","",""));
    const [loading, setLoading] = useState(false);
    const [submitted, setSubmitted] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate()

    const currentUser = useSelector(state => state.user);

    useEffect(() => {
        if (currentUser?.userId) {
          navigate("/");
        }
    }, [])

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
        setSubmitted(true);
        if (
          !user.firstName ||
          !user.lastName ||
          !user.email ||
          !user.username ||
          !user.password  
          // !user.gender
        ) {
          return;
        }
        setLoading(true);

        AuthenticationService.register(user).then(() => {
            navigate("/login")
            console.log(user);
        }).catch((error) => {
            console.log(error);
            if(!error?.response?.status === 409){
                 setErrorMessage("Username or password is not valid.");
            } else {
               setErrorMessage("Unexpected error occurred."); 
            }
            setLoading(false);
        })
        

    }

    return (
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0, transition: { duration: 0.3 } }}
      >
        <div className="container mt-3 form-container signup">
          <div className="card ms-auto me-auto p-3 custom-card">
            <h1 className="headerText"> Register</h1>
            {errorMessage && (
              <div className="alert alert-danger">{errorMessage}</div>
            )}
            <form
              onSubmit={(e) => handleRegister(e)}
              noValidate
              className={submitted ? "was-validated" : ""}
            >
              <div className="form-group mb-3">
                <label className="mb-2" htmlFor="firstName">
                  FirstName:
                </label>
                <input
                  type="text"
                  name="firstName"
                  className="form-control"
                  placeholder="FirstName"
                  required
                  value={user.firstName}
                  onChange={(e) => handleChange(e)}
                />
                <div className="invalid-feedback">FirstName is required.</div>
              </div>

              <div className="form-group mb-3">
                <label className="mb-2" htmlFor="name">
                  LastName:
                </label>
                <input
                  type="text"
                  name="lastName"
                  className="form-control"
                  placeholder="LastName"
                  required
                  value={user.lastName}
                  onChange={(e) => handleChange(e)}
                />
                <div className="invalid-feedback">LastName is required.</div>
              </div>

              <div className="form-group mb-3">
                <label className="mb-2" htmlFor="name">
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
                <div className="invalid-feedback">Email is required.</div>
              </div>

              <div className="form-group mb-3">
                <label className="mb-2" htmlFor="name">
                  Gender:
                </label>
                <select
                  class="form-select form-select-md mb-3"
                  aria-label=".form-select-lg example"
                  required
                  value={user.gender}
                  name="gender"
                  onChange={(e) => handleChange(e)}
                >
                  <option selected>Gender</option>
                  <option value="MALE">Male</option>
                  <option value="FEMALE">Female</option>
                </select>
              </div>

              <div className="form-group mb-3">
                <label className="mb-2" htmlFor="name">
                  Username:
                </label>
                <input
                  type="text"
                  name="username"
                  className="form-control"
                  placeholder="Username"
                  required
                  value={user.username}
                  onChange={(e) => handleChange(e)}
                />
                <div className="invalid-feedback">Password is required.</div>
              </div>
              <div className="form-group mb-3">
                <label className="mb-2" htmlFor="name">
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
              <button
                onClick={(e) => handleRegister(e)}
                type="submit"
                className="btn btn-success w-100 mt-3"
                disabled={loading}
              >
                Sign In
              </button>
            </form>
            <Link
              to="/login"
              className="btn btn-link"
              style={{ color: "darkgrey" }}
            >
              Already have an Account? LogIn
            </Link>
          </div>
        </div>
      </motion.div>
    );
}

export default SignUp;
