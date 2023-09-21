// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import axios from 'axios';
import swal from 'sweetalert';
import preloader from '../CreateAccount/image/preloader.gif'
import { Link } from "react-router-dom";
import google from './icons/google.svg'
import facebook from './icons/facebook.svg'
import { BackgroundImage, ButtonForm, CenterDiv, Container, Fieldset, Form, FormDiv, I, IconDiv, Loader, Preloader } from "../Styled/Styled";

const PORT = 8999;

const LoginForm = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: ""
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

if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      swal('ALERT', 'Invalid email format', 'error');
    }   else {
      try {
        setLoading(true);

        const url = `http://localhost:${PORT}/api/v1/auth/authenticate`;
        const response = await axios.post(url, formData);

        setLoading(false);

        const result = response.data;
        const Token =response.data.data.accessToken;
        const message = result.data.message;
      
        console.log(result)
        localStorage.setItem("TOKEN",Token)
        const userDetails=response.data.data
        localStorage.setItem("userDetails",JSON.stringify(userDetails))
        
         swal("ALERT",message,"success")


         request_meethod("/")



        
      } catch (err) {
        setLoading(false);
        const message = err.response.data.data.message;


        if(message=="Account is Disabled"){
          swal('ALERT',message, 'error');
          localStorage.setItem("email",formData.email)
          request_meethod(formData.email,"email","/verify-user")
        
        }else if(message=="User Not Found"){
          swal('ALERT',message, 'error');

        }else{
          swal('ALERT',"Try again", 'error');
        }
    

      }
    }
  };

  const request_meethod =(url)=>{
      var x=0;
      setInterval(() => {
        x++;
        if(x==3){

        
          window.location.replace(url)
        }
        
      }, 1000);
  }

  return (
    <Container>
      {(loading) ?
      <Preloader>
        <Loader src={preloader}></Loader>
      </Preloader>
      
      : null}
         <FormDiv>
          <Form onSubmit={handleSubmit}>
            <h3>Login to your account</h3>
            <p style={{margin:"0px",marginBottom:"50px"}}>Lorem ipsum dolor sit amet consectetur adipisicing elit</p>
        
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
                <legend>Password</legend>
                <input
                  type="password"
                  value={formData.password}
                  onChange={handleChange}
                  name="password"
                />
              </Fieldset>
              <p>
              <Link to="/user/forgetpassword/authenticate/" style={{marginBottom:"20px",textDecoration:"none"}}>Forget Password</Link>
              </p>

            <ButtonForm>
              Login
            </ButtonForm>
            <CenterDiv>
              <p>Don't have an Account? <Link to="/signup">Register</Link></p>
            </CenterDiv>
            <center><p>or</p></center>
            <CenterDiv>
            
              <IconDiv>
                <img src={google} width={20} alt="" /> <I>Google</I>
              </IconDiv>
            </CenterDiv>
            <p></p>
            <CenterDiv>
            
            <IconDiv>
              <img src={facebook} width={20} alt="" /> <I>Facebook</I>
            </IconDiv>
          </CenterDiv>
          </Form>
        </FormDiv>
        <BackgroundImage>
        </BackgroundImage>

    </Container>

  )
}

export default LoginForm;