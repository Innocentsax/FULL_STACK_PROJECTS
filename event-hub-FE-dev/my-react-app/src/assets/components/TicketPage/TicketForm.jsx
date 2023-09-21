import styled from "styled-components";
import bg from '../TicketPage/bg-connect.png'
import '../TicketPage/ticket.css'


import React from 'react'
import { Link } from "react-router-dom";
 import   {BsArrowLeftShort} from 'react-icons/bs';

import { ContentText, ContainerTicket,T, TextTicket,Span,Fieldsets,Forms, DivTicket,ContentBackground, } from "../Styled/Styled";
import Tickets from "./Tickets";

function TicketForm() {
  return (
     <ContainerTicket>
        
        <BackgroundImageTicket>
            <DivTicket>
            <TextTicket>
   <BsArrowLeftShort/> <Link to='/' style={{color :'white', textDecoration :'none'}} >Go Back</Link>
 
          </TextTicket>
        <Span>EKO ALL NIGHT POOL PARTY FESTIVAL</Span>
          </DivTicket>
      </BackgroundImageTicket>
      <ContentBackground>
        <DivTicket>




      <ContentText>
            <p  style={{fontSize:'32px'}}>Fill in your details as required</p>
             <button >View map</button> 
     </ContentText>

      <Forms>
       <Fieldsets>
        <legend>FirstName</legend>
        <input type="text" />
       </Fieldsets>
        <Fieldsets>
        <legend>email</legend>
        <input type="text" />
       </Fieldsets>
       </Forms>
       <T>Tickets</T>
       <Tickets />
       </DivTicket>
       </ContentBackground>
   

     
     </ContainerTicket>

  )

}


export default TicketForm

const BackgroundImageTicket= styled.div`
background-image:url(${bg});
width:100%;
height:600px;
marigin-bottom:30px;
display:flex;
justify-content:center

`