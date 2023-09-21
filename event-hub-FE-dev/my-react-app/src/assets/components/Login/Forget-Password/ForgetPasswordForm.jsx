import { useState } from "react";
import { 
  BackgroundImage, 
  ButtonForm, 
  CenterDiv, 
  Container, 
  Fieldset, 
  Form, FormDiv, 
  Loader, 
  Preloader } from "../../Styled/Styled";
import { Link } from "react-router-dom";
import axios from "axios";
import preloader from '../../CreateAccount/image/preloader.gif'
import swal from "sweetalert";
const ForgetPasswordForm = ()=>{
  const PORT =8999
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
    
            const url = `http://localhost:${PORT}/password/forgot-password`;
            const response = await axios.get(url,  {
              params: {
              email: formData.email,
            }
          },);
    
            setLoading(false);
    
            const result = response.data;
            const message = result.data.message;
            localStorage.setItem(formData.email,"email")
            console.log(result);
            console.log(message);
            swal("ALERT","CHECK YOUR EMAIL ","success")
         
          } catch (err) {
            setLoading(false);
            const message = err.data.data.message
            console.log(message)
          
            
            swal('ALERT',message, 'error');
          }
        }
      };
    
    
        return (
            <>
            <Container>
          {(loading) ?
          <Preloader>
            <Loader src={preloader}></Loader>
          </Preloader>
          
          : null}
             <FormDiv>
              <Form onSubmit={handleSubmit} >
                <h2>Forget Password </h2>
              
                  <Fieldset>
                    <legend>Enter Email </legend>
                    <input
                      type="email"
                      value={formData.email}
                      onChange={handleChange}
                      name="email"
                    />
                  </Fieldset>
                <ButtonForm>
                  Click to reset password
                </ButtonForm>
                <CenterDiv>
                  <p>Don't have an Account? <Link to="/signup">Register</Link></p>          
                </CenterDiv>
              </Form>
            </FormDiv>
            <BackgroundImage>
            </BackgroundImage>
    
        </Container>
        

        </>
        )
    
}

export default ForgetPasswordForm;
