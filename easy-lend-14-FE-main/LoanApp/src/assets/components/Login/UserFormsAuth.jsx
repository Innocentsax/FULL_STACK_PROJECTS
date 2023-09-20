
import {FormPage,Img,H1,
    FormDiv,Form,FormLogo,ImgLoader,LogoDiv2,
    Preloader,
    LabelInput,
    LabelI,
    Input,
    FormImage,
    Botton


} from '../Styled/Styled'
import {useState} from 'react';
import logo from '../Images/logo_.png'
import { Link  } from 'react-router-dom'
import {PORT,loader} from '../../Utils/AppUtils'
import swal from 'sweetalert'
import axios from 'axios'
const UserFormsAuth =()=>{


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
      }
      const handleSubmit = async (event) => {
        event.preventDefault();
    
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
          swal('ALERT', 'Invalid email format', 'error');
        }   else {
          try {
            setLoading(true);
            
            const url = `http://localhost:${PORT}/api/v1/auth/login`;
            const response = await axios.post(url, formData);
            setLoading(false);
    
            console.log(response)
          
            const Token =response.data.data.accessToken;
            const email =response.data.data.email;
            const registrationStatus = response.data.data.registrationStatus;
            const registrationStage = response.data.data.registrationStage;
           
            localStorage.setItem("TOKEN",Token)
              
            localStorage.setItem("email",email)
         
            localStorage.setItem("registrationStatus",registrationStatus)
            swal("ALERT","Succesful Login","success")
            const userDetails=response.data.data;
            console.log(userDetails)
            localStorage.setItem("userDetails",JSON.stringify(userDetails))
            if(!registrationStatus){
                throw new Error("Err")
            }

            else if(registrationStatus && registrationStage>=6){
                request_meethod("/dashboard")
                
            }else if(registrationStage<6){
               
                request_meethod("/confirm")
            }
    
          } catch (err) {
            setLoading(false);
            console.log(err)
            const data= err.response.data.data.message
            if(data=="Network Error"){
                swal('ALERT',"Try Again", 'error');
            }else{
            const message = err.response.data.data;
        
             if(message.message=="User Not Activated"){
              swal('ALERT',message.message, 'error');
              localStorage.setItem("email",formData.email)
           
              window.location.href="/verify-message" 
            }
            else{
              swal('ALERT',message.message, 'error');
            }
        
        }
          }
        }
      };
    
      const request_meethod =(url)=>{
          var x=0;
          setInterval(() => {
            x++;
            if(x==3){
              window.location.href=url
            }
            
          }, 1000);
      }
    return(
        <>
        {(loading) ? 
        <Preloader>
            <ImgLoader src={loader} width={50}></ImgLoader>
        </Preloader>

        :
        
      
        <FormPage>
            <FormDiv>
                <Form onSubmit={handleSubmit} method="post">
                <FormLogo>
                <LogoDiv2>
                <Img src={logo} alt="logo" width={50} />
                <H1>EasyLend</H1>
            </LogoDiv2>
                </FormLogo>
                <h1>Welcome back to EasyLend</h1>
              
                
                <LabelInput>
              
                    <LabelI>
                        Email Address
                        <Input type="email" 
                        onChange={handleChange} 
                        value={formData.email} 
                        name="email"
                        placeholder="Enter your email"></Input>
                    </LabelI>
                    <LabelI>
                        Password
                        <Input type="password" 
                         name="password"
                         onChange={handleChange} 
                        value={formData.password} 
                        placeholder="*****************************"
                       
                        ></Input>
                    </LabelI>
                    <Link to="/reset" >
                        Forget password
                    </Link>
                   <p></p>
                   
                     <Botton>Login</Botton>
                  


                </LabelInput>
                <p> {"Don't have an account"} ? <Link style={{color:"blue"}} to="/signup">Sign up here</Link></p>

                </Form>

            </FormDiv>
            <FormImage>

            </FormImage>

        </FormPage>
}
        
        </>
    )
}
export default UserFormsAuth;

