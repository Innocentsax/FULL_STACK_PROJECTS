// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import axios from 'axios';
import swal from 'sweetalert';
import preloader from './image/preloader.gif';
import './css/register.css'

import { Link } from "react-router-dom";
import { BackgroundImage, ButtonForm, CenterDiv, Container, Fieldset, Form, FormDiv, Loader, Preloader } from "../Styled/Styled";

// eslint-disable-next-line no-unused-vars
const PORT = 8999;

const FormRegister = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    cpassword: "",
    phone: ""
  });
  const [loading, setLoading] = useState(false);
  const [url, setUrl] = useState("");

  const [passwordMatch, setPasswordMatch] = useState(true);



  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value
    }));
    if (name === 'cpassword') {
      setPasswordMatch(value === formData.password);
    }
  };
  const handleUrlChange = (event) => {
    const selectedOption = event.target.value;
    setUrl(selectedOption);
  }; 
  
  const handleSubmit = async (event) => {
    event.preventDefault();
  
    if (formData.firstName.length < 3) {
      swal('ALERT', 'Name is required', 'error');
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      swal('ALERT', 'Invalid email format', 'error');
    } else if (formData.password.length < 6) {
      swal('ALERT', 'Password must be at least 6 characters long', 'error');
    } else if (formData.password !== formData.cpassword) {
      swal('ALERT', 'Password does not match', 'error');
    } else {
      try {
        setLoading(true);

   

        const response = await axios.post(`http://localhost:${PORT}/api/v1/auth/register/${url}`, formData);

        setLoading(false);
        const result = response.data;
  
        let message = result.data.message;
        console.log(message);
  
        if (message === 'Registration Successful') {
          swal('ALERT!', 'Kindly Check your inbox to verify your Email', 'success');
        }
      } catch (err) {
        setLoading(false);
        if (err.response && err.response.data && err.response.data.data && err.response.data.data.message) {
          console.log(err.response.data.data.message);
          swal('ALERT', err.response.data.data.message, 'error');
        } else {
          swal('ALERT', 'Try Again Later', 'error');
        }
      }
    }
  };  
  return (
    <Container>
      {(loading) ?
      <Preloader>
        <Loader src={preloader}></Loader>
      </Preloader>
      
      : null}
         <FormDiv>
          <Form onSubmit={handleSubmit}>
            <h2>Create an account</h2>

            <p style={{margin:"0px",marginBottom:"50px"}}>Event Goer Registration</p>

            <p style={{margin:"0px",marginBottom:"50px"}}>Connect To All Events Around You</p>

           

            <Fieldset>
              <legend>Register As</legend>
              <select
                value={formData.registerAs}
                onChange={handleUrlChange}
                name="registerAs"
              >
                <option value="eventGoer">Event Goer</option>
                <option value="eventCreator">Event Creator</option>
              </select>
            </Fieldset>


              <Fieldset>
                <legend>First Name</legend>
                <input
                  type="text"
                  value={formData.firstName}
                  onChange={handleChange}
                  name="firstName"
                />
              </Fieldset>

              <Fieldset>
                <legend>Last Name</legend>
                <input
                  type="text"
                  value={formData.lastName}
                  onChange={handleChange}
                  name="lastName"
                />
              </Fieldset>

              <Fieldset>
                <legend>Email</legend>
                <input
                  type="email"
                  value={formData.email}
                  onChange={handleChange}
                  name="email"
                />
              </Fieldset>

              <Fieldset>
                <legend>Phone Number</legend>
                <input
                  type="number"
                  value={formData.phone}
                  onChange={handleChange}
                  name="phone"
                />
              </Fieldset>

              <Fieldset>
                <legend>Date of Birth</legend>
                <input
                  type="date"
                  value={formData.dateOfBirth}
                  onChange={handleChange}
                  name="dateOfBirth"
                />
              </Fieldset>

              <Fieldset>
                <legend>Password</legend>
                <input
                    type="password"
                    value={formData.password}
                    onChange={handleChange}
                    name="password"
                />
              </Fieldset>

              <Fieldset>
                <legend>Confirm Password</legend>
                <input
                    type="password"
                    value={formData.cpassword}
                    onChange={handleChange}
                    name="cpassword"
                />
                {!passwordMatch && <p className="error">***Passwords do not match***</p>}
              </Fieldset>



            <ButtonForm>
              Create Account
            </ButtonForm>
            <CenterDiv>
              <p>Already Registered ? <Link to="/login">Login</Link></p>
            </CenterDiv>
          </Form>
        </FormDiv>
        <BackgroundImage>
        </BackgroundImage>

    </Container>

  )
}

export default FormRegister;

