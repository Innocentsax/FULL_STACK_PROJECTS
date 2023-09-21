import styled from "styled-components";
import Footer from "../HomePage/Footer/Footer";
import Header from "../HomePage/Header/Header";
import { BannerDiv, Divs,GobackDiv, EventName,UpcomingEvents,EvnetsBody,Fieldset, ButtonForm } from "../Styled/Styled";

export default function Checkout(){
    const handleChange=()=>{
        alert(1)
    }
return (
    <>
    <Header />
    <BannerDiv style={{height:"250px"}}>
    <GobackDiv >
                <Divs style={{width:"100%"}}>
                    <p className='back'>← Go Back</p>
                <EventName style={{width:"100%"}}>
                    <h2 className='h1'>Eko All Night Pool Party Festival</h2>
                </EventName>
                </Divs>
                
                </GobackDiv> 

    </BannerDiv>
    <UpcomingEvents>
            <EvnetsBody>
               
                <center style={{marginBottom:"100px"}}><h1 style={{fontFamily:"sans-serif",fontSize:"50px"}}>Checkout</h1>
                <span style={{color:"#ccc"}}>Time Left 5:11</span>
                </center>
                <RoleCheckout>
                    <ContactInfo>
                        <h2>Contact Information</h2>
                        <Fieldset>
                <legend>Name</legend>
                <input
                  type="text"
                  value={"Temple jack"}
                  onChange={handleChange}
                  name="firstName"
                />
                    </Fieldset>
                    <Fieldset>
                <legend>Email</legend>
                <input
                  type="text"
                  value={"Joshua Osasua"}
                  onChange={handleChange}
                  name="firstName"
                />
                    </Fieldset>

                    </ContactInfo>
                    <TicketSumarry>
                    <H1Summary>
                            <h3>Ticket Summary</h3>

                        </H1Summary>
                        <H1Summary>
                            <span>Attendee</span>
                            <h3>Ticket Summary</h3>

                        </H1Summary>
                        <H1Summary>
                        <span>DateTime</span>
                            <h3>Ticket Summary  &nbsp;&nbsp;&nbsp;&nbsp;04:00PM</h3>

                        </H1Summary>
                        <H1Summary>
                        <span>Type</span>
                            <h3>Ticket Summary</h3>

                        </H1Summary>

                        <H1Summarys>
                     
                        <h1>Total</h1>
                        <h2><strike>N</strike>5,000&nbsp;&nbsp;</h2>

                        </H1Summarys>
                        <ButtonForm style={{width:"95%",marginLeft:"20px",marginRight:"20px",marginTop:"20px"}}>
                            Buy Ticket
                        </ButtonForm>

                    </TicketSumarry>

                </RoleCheckout>

            
                    </EvnetsBody>
                    </UpcomingEvents>


    <Footer />

    </>
)
}

const RoleCheckout =styled.div`
width:100%;
height:auto;
display:flex;
justify-content:space-between
`
const ContactInfo=styled.div`
width:40%;
height:300px;
`
const TicketSumarry=styled.div`
width:50%;
height:auto;
background:rgba(0, 50, 47, 0.03);
margin-bottom:50px
`
const H1Summary =styled.div`
width:100%;
height:auto;
padding-top:20px;
padding-bottom:20px;
border-bottom: 1px solid #C4C4C4;

h1,h2,h3, span{
    padding-left:15px;
    font-style: normal;
    font-weight: 500;
    line-height: 20px;
}

`

const H1Summarys =styled.div`
width:100%;
height:auto;
display:flex;
justify-content:space-between;
padding-top:10px;
padding-bottom:10px;
border-bottom: 1px solid #C4C4C4;

h1,h2,h3, span{
    padding-left:15px;
    font-style: normal;
    font-weight: 500;
    line-height: 20px;
}
`