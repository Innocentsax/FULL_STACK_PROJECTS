import styled from "styled-components";
import './RegistrationAlert.css'

export default function RegistrationAlert(props){
    return(
        <BackgroundDiv>
            <h1 >Events</h1>
            <AlertWelcome >{props.msg}</AlertWelcome>
            <AlertName >{props.username}</AlertName>
            <AlertLogo>{props.logo}</AlertLogo>
            <AlertMessage>{props.message}</AlertMessage>
            <AlertFurtherMessage>{props.caption}</AlertFurtherMessage>
        </BackgroundDiv>
    )
}
const BackgroundDiv = styled.div`
  margin-top: 10%;
  margin-left: 30%;
  width: 40%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px;
  gap: 32px;
  position: relative;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(255, 87, 34, 0.5);
`;

const AlertWelcome =styled.div`
  width: 50%;
  margin-top: -20px;
  height: auto;
  font-weight: 400;
  font-size: 15px;
  line-height: 20px;
  text-align: center;
  color: #21334F;
`
const AlertName =styled.div`
  width: 85%;
  margin-top: -20px;
  height: auto;
  font-weight: 700;
  font-size: 24px;
  line-height: 140%;
  text-align: center;
  color: #101828;
`
const AlertMessage =styled.div`
  font-weight: 400;
  font-size: 32px;
  line-height: 140%;
  text-align: center;
  letter-spacing: 0.25px;
  color: #34A853;
`
const AlertFurtherMessage =styled.div`
  width: 90%;
  margin-top: -20px;
  height: auto;
  font-weight: 400;
  font-size: 20px;
  line-height: 20px;
  text-align: center;
  color: #101828;
`
const AlertLogo =styled.div`
  font-size: 70px;
  color: #FF5722;
`
