
import {FormPage,Img,H1,
    FormDiv,Form,FormLogo,
    SelectType,
    ButtonType,LogoDiv2,
    ButtonType2,
    LabelInput,
    LabelI,
    Input,
    FormImage,
    Preloader,
    ImgLoader,
    Botton
} from '../Styled/Styled'
import logo from '../Images/logo_.png'
import { Link  } from 'react-router-dom'
import {useState} from 'react'
import {PORT,loader} from '../../Utils/AppUtils'
import axios from 'axios'
import swal from 'sweetalert'
const Userforms =()=>{

  console.log(localStorage.getItem("email"))

  
        const [formData, setFormData] = useState({
          fullName: "",
          email: "",
          password: "",
          cpassword: "",
          
        });
        const [loading, setLoading] = useState(false);
        const [type, setButton] = useState("");
      
        const [passwordMatch, setPasswordMatch] = useState(true);
      
      
    
        const handleChange = (event) => {
          const { name, value } = event.target;
          setFormData((prevFormData) => ({
            ...prevFormData,
            [name]: value
          }));
          console.log(passwordMatch+type)
          if (name === 'cpassword') {
            setPasswordMatch(value === formData.password);
          }
        };
        const handleButton = (event) => {
          const selectedOption = event.target.value;
          setButton(selectedOption);
        }; 
        
        const handleSubmit = async (event) => {
          event.preventDefault();
        
        
          if (formData.fullName.length < 3) {
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
      
              console.log(111)
             
         
        
      
              const response = await axios.post(`http://localhost:${PORT}/api/v1/auth/register/LENDER`, formData);
      
              setLoading(false);
              localStorage.setItem("email",formData.email);
              const status = response.data.data.registrationStatus;
            
              console.log(response);
              console.log(status);
        
              if (response.status === 201 && status==false) {

                window.location.href="/verify-message"
              }
            } catch (err) {
              setLoading(false);
              if (err.response && err.response.data && err.response.data.data && err.response.data.data.message) {
                console.log(err);
                swal('ALERT', err.response.data.data.message, 'error');
              } else {
                console.log(err);
                swal('ALERT', 'Try Again Later', 'error');
              }
            }
          }
        };  
    return(
        <>
          {(loading) ? 
        <Preloader>
            <ImgLoader src={loader} width={50}></ImgLoader>
        </Preloader>

        :
        <FormPage>
            <FormDiv style={{height:"900px"}}>
                <Form onSubmit ={handleSubmit}>
                <FormLogo>
                <LogoDiv2>
                <Img src={logo} alt="logo" width={50} />
                <H1>EasyLend</H1>
            </LogoDiv2>
                </FormLogo>
                <h1>Create a new Account</h1>
                <SelectType>
                <ButtonType value="BORROWER"  onClick={handleButton}>
                    Borrower
                        
                        </ButtonType>
                        <ButtonType2 value="LENDER" onClick={handleButton}>
                            Lender
                        </ButtonType2>
                </SelectType>
                <LabelInput>
                <LabelI>
                        Full Name
                        <Input 
                        type="text" 
                        placeholder="Enter your full names"
                        name="fullName"
                        value={formData.fullName}
                        onChange={handleChange}
                        ></Input>
                    </LabelI>
                    <LabelI>
                        Email Address
                        <Input 
                        type="email" 
                        placeholder="Enter your email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}

                        ></Input>
                    </LabelI>
                    <LabelI>
                        Password
                        <Input type="password" 
                        placeholder="*****************************"
                        value={formData.password}
                        onChange={handleChange}
                        name="password"
                        ></Input>
                    </LabelI>
                    <LabelI>
                        Confirm Password
                        <Input 
                        type="password" 
                        placeholder="*****************************"
                        value={formData.cpassword}
                        name="cpassword"
                        onChange={handleChange}
                        ></Input>
                    </LabelI>
                    <LabelI style={{display:"flex"}}>
                        <input type="checkbox" style={{margin:"10px",width:"30px"}} />
                        <p style={{fontSize:"14px",color:""}}>
                        By continuing you agree to EasyLend‘s Terms of Service and Privacy Policy,
                        </p>

                     </LabelI>
                     <Botton>Sign up</Botton>
                  


                </LabelInput>
                <p>Already have an account ? <Link  style={{color:"blue"}} to="/login">Sign in here</Link></p>

                </Form>

            </FormDiv>
            <FormImage>

            </FormImage>

        </FormPage>
}
        
        </>
    )
}
export default Userforms;

