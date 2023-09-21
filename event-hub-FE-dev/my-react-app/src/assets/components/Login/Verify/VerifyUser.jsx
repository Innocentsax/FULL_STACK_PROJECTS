import React, { useState } from "react";
import styled from "styled-components";
import axios from 'axios';
import swal from 'sweetalert';
import preloader from '../../CreateAccount/image/preloader.gif'
import bg from '../../CreateAccount/image/registerbg.png'
import { Link } from "react-router-dom";
import Header from "../../HomePage/Header/Header";
import Footer from "../../HomePage/Footer/Footer";
const PORT = 8999;


let emailValue = "";
const VerifyUser =  () => {
   if(localStorage.getItem("email")==null){
    window.location.replace("/login")
   }
  
 const [email, setEmail] = useState(localStorage.getItem("email"));
 const [loading, setLoading] = useState(Boolean);
 
  const handleSubmit =  async (event) => {
    event.preventDefault();
    emailValue=email

      try {
        setLoading(true);
        
        const url = `http://localhost:${PORT}/api/v1/auth/new-verification-link`;
        const response = await axios.get(url, 
          {
            params: {
              email: emailValue,
            },});

        setLoading(false);

        const result = response.data;
        const message = result.data.message;
        console.log(result);
        console.log(message);
   
    }
    catch(e){

    }
  }
  // }else{
  //   window.location.replace("/login")
  // }
  

  return (
    <>
    <Header />
    <Container>
      {(loading) ? <Preloader><Loader src={preloader}></Loader></Preloader>: null}
         <FormDiv>
          <Form onSubmit={handleSubmit}>
            <h3>Verify User</h3>
            <p style={{margin:"0px",marginBottom:"50px"}}>
                Click the Link  to resend mail to confirm your email
            </p>
        
            <Button>
              Click to resend Link
            </Button>
            <CenterDiv>
              <p>Don't have an Account? <Link to="/signup">Register</Link></p>
            
            </CenterDiv>
         
            
        
          </Form>
        </FormDiv>
        <BackgroundImage>
        </BackgroundImage>

    </Container>
    <Footer />
    </>

  )

}
export default VerifyUser;

const Preloader =styled.div`
width:100%;
height:100vh;
position:fixed;
top:0px;
left:0px;
background:rgba(0,0,0,0.5);
display:flex;
justify-content:center;
align-items:center
`
const Loader =styled.img`
width:40px;
height:40px;

`

const Container =styled.div`
width:100%;
height:auto;
background: #F8F9FA;
display:flex;
@media(max-width:600px){
  flex-direction:column;
}
`

const FormDiv =styled.div`
width:40%;
height:auto;
display:flex;
justify-content:center;
margin-bottom:30px;
@media(max-width:600px){
  width:100%;
  
}
`
const Form =styled.form`
width:80%;
height:auto;

font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
color: #252D42;
`
const BackgroundImage = styled.div`
width:60%;
height:850px;
background-image:url(${bg});
background-size:cover;
background-repeat:no-repeat;
background-position: none;
@media(max-width:600px){
  display:none
}
`

const Button =styled.button`
width: 100%;
height: 48px;
outline:none;
border:0px;
margin-bottom:30px;
background: #FF5722;
border-radius: 4px;
transition-duration:0.5s;
color:white;
font-size:17px;
&:hover{
  background:pink
}
`
const CenterDiv =styled.div`
display:flex;
justify-content:center;
align-items:center;
width:100%;

p{

color:#003366;
a{
  color:#FF5722;
  text-decoration:none;

}

}
`

