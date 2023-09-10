import React, {useState} from "react";
import image from "../assets/images/loginImage.png";
import { FcGoogle } from "react-icons/fc";
import {FaSpinner} from 'react-icons/fa';
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useStateContext } from "../context/ContextProvider";

const Login = () => {
  const {
    setToken,
    setEmployee,
  } = useStateContext();

  const navigate = useNavigate(); 
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [emailError, setEmailError] = useState("")
  const [passwordError, setPasswordError] = useState("")
  const [isLoading, setIsLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault();
    setEmailError("");
    setPasswordError("");
    setIsLoading(true)

    // Email validation
    if (!email.includes("@") || !email.includes(".")) {
      setEmailError("Please enter a valid email address");
      setIsLoading(false);
      return;
    }

    // Password validation
    if (password === "") {
      setPasswordError("Please enter a password");
      setIsLoading(false);
      return;
  } 
  // else if (password.length < 6) {
  //     setPasswordError("Password must be at least 6 characters long");
  //     setIsLoading(false);
  //     return;
  // } else if (!password.match(/[A-Z]/)) {
  //     setPasswordError("Password must contain at least one uppercase letter");
  //     setIsLoading(false);
  //     return;
  // } else if (!password.match(/[a-z]/)) {
  //     setPasswordError("Password must contain at least one lowercase letter");
  //     setIsLoading(false);
  //     return;
  // } else if (!password.match(/[0-9]/)) {
  //     setPasswordError("Password must contain at least one number");
  //     setIsLoading(false);
  //     return;
  // }

  try {
    const response = await axios.post('http://localhost:8080/api/login', {
      email,
      password
    });
    console.log(response)
    // navigate("/staff")
    console.log(response.data); // Handle the login response
    setToken(response.data.token);
    const person = response.data.employee
    setEmployee(person);
    if (response.data.employee.role.name === "ADMIN" || response.data.employee.role.name === "HR"){
      navigate("/admin")
    } else if (response.data.employee.role.name === "STAFF"){
      navigate("/staff")
    }
  } catch (error) {
    navigate("/login")
    console.error(error);
  }
  console.log(email, password);
  }
  
  return (
    <div className="bg-white">
      <div className="row justify-content-center">
        <div className="col-lg-6 pt-14 ">
          <p className=" md:px-5 mx-28 font-bold md:text-2xl text-lg">HR Support Center</p>

          <div className="p-5 md:mx-28 mt-20">
            <div className="mb-4">
              <h1 className="h4 text-3xl text-gray-900 font-bold">Hi There</h1>
              <p className="text-lightFont">Login to your portal</p>
            </div>
            <div className="mb-4">
              <button
                href="#"
                className="flex h-44 items-center justify-center btn-user btn-block w-full border-2 border-slate-200 mt-10"
              >
                <FcGoogle size={30} />
                Sign in with Google
              </button>
              <div className="flex items-center justify-center py-4">
                <hr className="my-2 w-full " />
                <p className="px-4">OR</p>
                <hr className="my-2 w-full" />
              </div>
            </div>
            <form className="user">
              <div className="form-group">
                <label htmlFor="email" className="col-sm-2 col-form-label">
                  Email
                </label>
                <input
                  type="email"
                  className="form-control form-control-user"
                  id="email"
                  name="email"
                  aria-describedby="emailHelp"
                  placeholder="Enter Email Address..."
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
                {emailError && <div className="text-red-400 text-lg mb-2 ml-2">{emailError}</div>}
              </div>
              <div className="form-group">
                <label htmlFor="password" className="col-sm-2 col-form-label">
                  Password
                </label>
                <input
                  type="password"
                  className="form-control form-control-user"
                  id="password"
                  name="password"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
                {passwordError && <div className="text-red-400 text-lg mb-2 ml-2">{passwordError}</div>}
              </div>
              <Link to="/forgot-passwword">
              <div className="mt-2">
                <p className="col-sm text-primaryColor">Forgot Password?</p>
              </div>
              </Link>
                <button
                  type="submit"
                  className="rounded-md h-44 px-6 font-bold text-white bg-primaryColor btn-block w-full mt-10"
                  onClick={handleSubmit}
                >
                  {isLoading ? <FaSpinner style={{animation: "spin 1s linear infinite"}} /> : "Login"}
                </button>
            </form>
          </div>
        </div>
        <div className="d-none d-md-block col-lg-6">
          <img
            src={image}
            className="img-fluid h-screen w-full"
            alt=""
          />
        </div>
      </div>
    </div>
  );
};

export default Login;
