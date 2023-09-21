
import axios from "axios";
import { BackgroundImage, ButtonForm, CenterDiv, Container, Fieldset, Form, FormDiv, Loader, Preloader } from "../../Styled/Styled";
import { useState,useEffect } from "react";
import { Link } from "react-router-dom";
import swal from "sweetalert";
import { useSearchParams } from 'react-router-dom';



export default  function  PasswordForm () {
    const PORT =8999
  
    const [searchParams] = useSearchParams();
    const token = searchParams.get('token');
    console.log(token)
    const [check,setCheck] =useState(false)

    const [formData, setFormData] = useState({
        password: "",
        cpassword:""
      });
      const [loading, setLoading] = useState(false);
      const [message,setMessage] = useState("")
      const [showLogin,setShowLogin] = useState("")
   
  
  
        useEffect(() => {
            try{
    const validateUrl =`http://localhost:${PORT}/api/v1/auth/verify-password-token`;
   axios.get(validateUrl,{
        headers: { 'Access-Control-Allow-Origin': '*' },
        params:{
            token: token
        }
    }).then(response => {
        setCheck(true)
        console.log(1)
        console.log(response.response.data.data.message)
  
      })
      .catch(error => {
        console.error(error);
        const messages =error.response.data.data.message
        if(message=="Token Expired"){
            setShowLogin("/user/forgetpassword/authenticate")
        }
        setMessage(messages)
        setCheck(false)
        
      });
    }
    catch(e){
        console.log(error.response.data.data.message)
        setMessage(error.response.data.data.message)
        setCheck(false)
    }

    },[])

   


const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value
    }));
  };

  const handleSubmit = (event)=>{
    event.preventDefault();
    if(cpassword==formData.password){
        try{
    const response =axios.post(`http://localhost:${PORT}/password/reset`,{formData})

    const result = response.data;
    const message = result.data.message;
    console.log(result)

        }catch(err){
            console.log(err)
        }
    }else{
        swal("ALERT!","Password does not match","error");

    }
  }
    
    return (
        <>
         <Container>
      {(loading) ?
      <Preloader>
        <Loader src={preloader}></Loader>
      </Preloader>
      
      : null}

 
         <FormDiv>
         {(check) ?
          <Form  onSubmit={handleSubmit}>
            <h2>Reset Password</h2>
            <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. 
                Rem asperiores veniam ab quas
                Commodi, velit cumque?</p>
          
            <Fieldset>
                <legend>Enter Password </legend>
                <input
                  type="password"
                  value={formData.password}
                  onChange={handleChange}
                  name="email"
                />
              </Fieldset>
              <Fieldset>
                <legend>Confirm Password </legend>
                <input
                  type="password"
                  value={formData.cpassword}
                  onChange={handleChange}
                  name="email"
                />
              </Fieldset>
            <ButtonForm>
              Create New Passord
            </ButtonForm>
            <CenterDiv>
              <p>Don't have an Account? <Link to="/login">Login</Link></p>          
            </CenterDiv>
            
          </Form>
               : 
               
               
               <h1 style={{fontFamily:"sans-serif"}}>{message}</h1> }
        </FormDiv>

    
        <BackgroundImage>
        </BackgroundImage>

    </Container>
        </>
 
    )
}

