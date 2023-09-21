import React, { useState } from "react";
import axios from 'axios';
import swal from 'sweetalert';
import preloader from './image/preloader.gif';

import { Link } from "react-router-dom";
import { BackgroundImage, ButtonForm, CenterDiv, Container, Fieldset, Form, FormDiv, Loader, Preloader } from "../Styled/Styled";

const PORT = 8999;

const CreatorForm = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    cpassword: "",
    phone: ""
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value
    }));
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
        const url = `http://localhost:${PORT}/api/v1/auth/register-event-creator`;
        const response = await axios.post(url, formData);
        setLoading(false);
        const result = response.data;
     
        let message = result.data.message;
        console.log(message)

        if (message === "Registration Successful") {
          swal('ALERT!', 'Kindly Check your inbox to verify your Email', 'success');
        } 
      } catch (err) {
        setLoading(false);
        if(err.response.data.data.message!=undefined){
          console.log(err.response.data.data.message);
          swal('ALERT', err.response.data.data.message, 'error');
        }else{
          swal('ALERT', "Try Again Later", 'error');
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
            <p style={{margin:"0px",marginBottom:"50px"}}>Event Creator Registration</p>
           


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
              </Fieldset>


            <ButtonForm>
              Create Account
            </ButtonForm>
            <CenterDiv>
              <p>Register As Event Goer <Link to="/signup">Register</Link></p>
            </CenterDiv>
            <CenterDiv>
              <p>Already Registerd? <Link to="/login">Login</Link></p>
            </CenterDiv>
          </Form>
        </FormDiv>
        <BackgroundImage>
        </BackgroundImage>

    </Container>

  )
}

export default CreatorForm;

