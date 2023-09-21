import React, { useState } from 'react'
import styled from 'styled-components'
import {MdOutlineCancel} from 'react-icons/md'

function TicketSummary() {
const [isModalOpen,setIsModalOpen ]=useState(false);

  return (
    <TicketSummaryContainer>
        <button onClick={()=>setIsModalOpen(true)}>open modal </button>
        <TicketSummaryModal isOpen={isModalOpen}>
            <TicketSummaryModalDiv>
                <TicketText>
                    <p>Tickets Summary</p> 
                    <MdOutlineCancel
              style={{ color: "#101828", cursor: "pointer" }}
              onClick={() => setIsModalOpen(false)}
            />
                </TicketText>
                        <TicketAmtBox>
                        <span>Total</span><p>N3600</p>
                        </TicketAmtBox>
                        <Ticket>
                            <TicketName>
                                <p>VVIP Tickets</p> <span>-3 x 300000</span>
                            </TicketName>
                            <TicketAmt>
                                    <p>N300000</p>
                            </TicketAmt>
                        </Ticket>
                        <Ticket>
                            <TicketName>
                                <p>VVIP Tickets</p> <span>-3 x 300000</span>
                            </TicketName>
                            <TicketAmt>
                                    <p>N300000</p>
                            </TicketAmt>
                        </Ticket>
                        <Ticket>
                            <TicketName>
                                <p>VVIP Tickets</p> <span>-3 x 300000</span>
                            </TicketName>
                            <TicketAmt>
                                    <p>N300000</p>
                            </TicketAmt>
                        </Ticket>
            </TicketSummaryModalDiv>
     </TicketSummaryModal>
    </TicketSummaryContainer>
  )
}

export default TicketSummary

const TicketSummaryContainer = styled.div`
width:100%;
background: #E5E5E5;
height:100vh;
position:relative:
.modal-open{
  display:flex;
}
.modal-close{
   display:none;
}
`
const TicketSummaryModal = styled.div`
width: 435px;
height: 416px;
background: #FFFFFF;
 position:absolute;
 display:flex;
 justify-content:center;
 left:50%;
 top:50%;
 transform: translate(-50%,-50%);
 -webkit-box-shadow: 0 5px 15px rgba(0,0,0,0);
 -moz-box-shadow: 0 5px 15px rgba(0,0,0,0);
 -o-box-shadow: 0 5px 15px rgba(0,0,0,0);
 box-shadow: 0 5px 15px rgba(0,0,0,0);
 transform: translate(-50%, -50%);
 transition: opacity 0.3s;
 opacity: ${props => (props.isOpen ? 1 : 0)};


`
const TicketSummaryModalDiv= styled.div`
width:80%;
height:auto;

`
const TicketText = styled.div`
display:flex;
justify-content:space-between;
align-items:center;
p{
font-family: 'Inter';
font-style: normal;
font-weight: 600;
font-size: 16px;
line-height: 140%;
letter-spacing: 0.15px;
color: #101828;
}
`
const TicketAmtBox= styled.div`
width:100%;
height:60px;
padding:10px;
background: #F9FAFB;
span{
    font-family: 'Inter';
font-style: normal;
font-weight: 400;
font-size: 16px;
line-height: 140%;
letter-spacing: 0.15px;
color: #98A2B3
}
p{ 
   
    font-weight: 500;
font-size: 24px;
line-height: 140%;
margin:0px;

color: #101828

}

`
const  Ticket = styled.div`
 display:flex;
 justify-content:space-between; 
 border-bottom:1px solid  #EEEEEE;

`
const TicketName= styled.div`
width:100%;
height:70px;
padding:10px;
align-items:center;
// background: yellow;
font-style: normal;
font-weight: 400;
font-size: 16px;
line-height: 140%;
letter-spacing: 0.15px;
color: #101828;
span{
    color: #98A2B3 
}

`
const TicketAmt= styled.div`
width:100%;
height:60px;
padding:10px;
// background: pink;
display:flex;
justify-content:flex-end;
align-items:center;
`
