
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { Container, BackgroundImage, FormDiv, Text, View, Form } from '../../Styled/Styled';
import swal from 'sweetalert';
import Header from '../../HomePage/Header/Header';
import Footer from '../../HomePage/Footer/Footer';
import styled from 'styled-components';
const PORT = 8999;

const Token = () => {
  const { token } = useParams();

  const email = localStorage.getItem('email');
  const [mainMessage, setMainMessage] = useState('');
  const [loading,setHoled] = useState(false)


try{
  useEffect(() => {
    setHoled(false)
    const url = `http://localhost:${PORT}/api/v1/auth/verify-email`;
    axios
      .get(url, {
        headers: { 'Access-Control-Allow-Origin': '*' },
        params: {
          token: token,
        },
      })
      .then(response => {
        setHoled(true)
        console.log(response.data);
        console.log(response.status);

          if (response.status == 200) {
            setMainMessage("USER ACTIVATED");
            return;
          }
      })
      .catch(error => {
        setHoled(true)
        console.error(error);
        setMainMessage("Try again or check verification Link");
      });
  }, []);
}catch(e){
  swal("ALERT","TRY AGAIN","error")

}

  return (
    <>
    <Header />
  
    <Container>
      <FormDivs>
        <Text>{(!loading) ? ".....": mainMessage}</Text>
      </FormDivs>
      <BackgroundImage>
        </BackgroundImage>

       
      </Container>

    <Footer />
    </>
  );
};

export default Token;

const FormDivs=styled.div`
display:flex;
align-items:center;
justify-content:center;
width:40%;
height:auto;
margin-bottom:30px;
@media(max-width:600px){
  width:100%;
  
}
`


