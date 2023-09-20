import axios from 'axios';
import { useEffect, useState } from 'react';

import { PORT, loader, activeIcon, inact } from '../../Utils/AppUtils';
import { Preloader, ImgLoader, BodyToken, Modar,ButtonEmail } from '../Styled/Styled';
import { useLocation } from 'react-router-dom';

const Token = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const token = queryParams.get('token');

  const email = localStorage.getItem("email")



  useEffect(() => {
    const fun = async () => {
      try {
        setHoled(false);
        const url = `http://localhost:${PORT}/api/v1/auth/verify-user`;
        const response = await axios.get(url, {
          params: {
            token: token,
          },
        });
        setHoled(true);
        const statusCode = response.status;
        console.log(statusCode)
        if (statusCode === 200) {
          setCode(statusCode);
          setMainMessage('USER ACTIVATED');
        } else {
          setCode(statusCode);
          setMainMessage('Try again or check verification Link');
        }
      } catch (error) {
        setCode(500);
        setHoled(true);
        console.error(error);
        setMainMessage('Try again or check verification Link');
      }
    };
    fun();
  }, [token]);

  const [mainMessage, setMainMessage] = useState('');
  const [statusCode, setCode] = useState(0);
  const [loading, setHoled] = useState(false);

  if (email == null || email === 'undefined') {
    window.location.replace('/login');
    // Since this line redirects, you might not need to return anything here.
    // If you still want to return something, you can return null or an empty component.
    return null;
  }

  const login =()=>{
    window.location.href="/login"
  }

  return (
    <>
      {!loading ? (
        <Preloader>
          <ImgLoader src={loader} width={50} />
        </Preloader>
      ) : (
        <>
          <BodyToken>
            <Modar>
              <img src={statusCode === 200 ? activeIcon : inact} alt="" width={50} />
              <h1>{statusCode === 200 ? 'Successful' : 'Error'}</h1>
              <p>{mainMessage}</p>
              <p>
                Welcome to Customer Support! Our platform is designed to provide you with an
                exceptional user experience
              </p>
              <ButtonEmail type ="button" onClick={login} >Go to Login</ButtonEmail>
            </Modar>
          </BodyToken>
        </>
      )}
    </>
  );
};

export default Token;
