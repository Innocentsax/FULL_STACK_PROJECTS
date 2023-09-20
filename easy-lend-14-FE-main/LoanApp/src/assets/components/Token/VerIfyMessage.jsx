

import axios from 'axios';
import {  useState } from 'react';


import {sms,loader,PORT} from '../../Utils/AppUtils'
import { Preloader,ImgLoader,ButtonEmail,BodyToken,Modar} from '../Styled/Styled';
import { useLocation } from 'react-router-dom';


const VerifyMessage = () => {

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const message = queryParams.get('message-auth');
  console.log(message)



  const email = localStorage.getItem('email');
  if(email==null || email =="undefined"){
    window.location.replace("/login")
   
  }

  const [mainMessage, setMainMessage] = useState('');
  const [loading, setLoading] = useState(true);

  const resendEmail =(e)=>{
    setLoading(false)
    e.preventDefault()
  
     axios.get(`http://localhost:${PORT}/api/v1/auth/new-verification`,
     {
        headers: { 'Access-Control-Allow-Origin': '*' },
        params: {
          email: email,
        },
      })
      .then(response => {
          setLoading(true)
        console.log(response.data);
        console.log(response.status);

          if (response.status == 200) {
            setMainMessage(response.data.data);
           
          }
      })
      .catch(error => {
        setLoading(true)
        console.error(error);
        setMainMessage("Try again or check verification Link");
      });

  
}


return (

    <>
     {(!loading) ? 
        <Preloader>
            <ImgLoader src={loader} width={50}></ImgLoader>
        </Preloader>

        :
        

   
    <BodyToken>
        <Modar onSubmit={resendEmail}>
            <img src={sms} alt="" width={50} />
           
        <p>{(mainMessage=="")  ?
        
        <p>We have sent you an email for you to confirm your email
if you haven’t received it in a few minutes.</p>
:mainMessage
     }
</p>
        <ButtonEmail >Click to resend Mail</ButtonEmail>
        </Modar>
       
      
    </BodyToken>
     }
    </>

)
}

export default VerifyMessage;
