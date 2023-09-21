import styled from "styled-components";
import bannerUrl from '../HomePage/image/bg-connect.png'
import bg from '../CreateAccount/image/registerbg.png'
// HEADER

export const HeaderStyle = styled.header`
width:100%;
height:100px;
background:white;
display:flex;
justify-content:center;
align-items:center;

`
export const Headers = styled.div`
width:85%;
height:auto;
display:flex;
justify-content:space-between;
align-items:center; = 
`
export const LogoDiv = styled.div`
width:25%;
height:100px;
display:flex;
align-items:center;


`
export const Account = styled.div`
width:40%;
height:100px;
display:flex;
align-items:center;
justify-content:flex-end;
text-decoration:none;
font-size:16px;
@media(max-width:600px){
    width:70%
}
`
export const Button = styled.button`
width:146px;
height:54px;
background:#ff5722;
color:white;
margin-left:30px;
border-radius:4px;
border:0px;
cursor:pointer

`


// EVENT
export const EventDatas =styled.div`
width:30%;
height:auto;
background:white;
margin-bottom:30px;
@media(max-width:600px){
    width:100%
}

`

export const ContentEvent= styled.div`
width:100%;
height:144px;
display:flex;
justify-content:space-between;

`

//MAIN 


export const BannerDiv =styled.div`
width:100%;
height:660px;
background-image:url(${bannerUrl});
display:flex;
justify-content:center;
align-items:center;
flex-direction:column
`
export const PositionForm = styled.div`
position:relative;
width:100%;
height:170px;
display:flex;
justify-content:center;
bottom:80px;
@media(max-width:600px){
    height:auto

    }
`
export const InsideDiv = styled.div`
background:#10375c;
border-radius:10px;
width:75%;
height:100%;
display:flex;
padding-right:20px;
padding-left:20px;
display:flex;
align-items:center;
@media(max-width:600px){
    height:auto

    }
`
export const DivRoleContent = styled.div`
width:100%;
height:auto;
display:flex;
justify-content: center;
@media(max-width:600px){

flex-direction:column;
}
`
export const Label = styled.label`
height:45px;
color:white;
font-family:sans-serif;
color:#ccc;
input {
    width: 286px;
    height:45px;
    border-radius:10px;
    margin-top:10px;
    padding-left:16px;
    border:0px;

}
select{
    height:45px;
    margin-top:10px;
    width:250px;
    border:0px;
    border-radius:10px;
    padding:16px;
    outline:none;
    color:#676767;
}

@media(max-width:600px){

    width:90%;
    height:70px;
    margin-top:20px;
    margin-bottom:20px;

    }
`
export const ButtonArrow  =styled.button`
width: 84px;
height: 71px;
background: #FF5722;
border-radius: 10px;
margin-top: 7px;
display:flex;
justify-content:center;
align-items:center;
border:0px;
outline:none;

@media(max-width:600px){
    margin:10px;
    height:50px;
    margin-left:40px
}
`
export const UpcomingEvents = styled.div`
width:100%;
height:auto;
display:flex;
flex-direction:column;
background:#f8f8f8;
align-items:center;
`
export const EvnetsBody =styled.div`
width:85%;
height:auto;
`
export const RoleEventFilter = styled.div`
width:100%;
display:flex;
justify-content:space-between;

@media(max-width:600px){
    h1{
        font-size: 20px;
    }
}
`
export const Div =styled.div`
width:26%;
height:50px;
display:flex;
justify-content:space-around;
align-items:center;

@media(max-width:600px){
    width:50%
}

`
export const ButtonFilter=styled.div`
width:150px;
height:40px;
background:rgba(255,87,34,0.1);
border-radius:4px;
outline:none;
cursor:pointer;
display:flex;
justify-content:center;
align-items:center;
margin-top: 60px;

span{
    color:rgba(37,45,66,0.85);
    font-size:13px;
}
@media(max-width:600px){
    width:40%;
    font-size:10px
}
`
export const Events =styled.div`
width:100%;
height:auto;
display:flex;
flex-wrap:wrap;
justify-content:space-between;
img{
    width:100%;
    height:200px;

}

@media(max-width:600px){}
`
export const ButtonLoad=styled.button`
width:200px;
height:50px;
background:#ff5722;
outline:none;
color:white;
font-size:16px;
border:0px`



// footer


export const FooterDiv =styled.footer`
width:100%;
height:400px;
background:#10375c;
display:flex;
align-items:center;
justify-content:center;

@media (max-width:700px){
    height:auto;

    flex-direction:column;
}
`
export const LetfDivEvent =styled.div`
width:25%;
height:300px;

p{
    color: white;
    font-family:sans-serif;
    font-size:16px;
    line-height: 26px;
    font-weight: 100;

}

@media (max-width:700px){
    height:auto;
    width:70%;
    margin:20px;
}
`
export const RightDivEvent =styled.div`
width:60%;
height:300px;
display:flex;
margin-left: 20%;
@media (max-width:700px){
    height:auto;
    width:70%;
    flex-direction:column;
}
`

export const DivFooter =styled.div`
width:45%;
height:40px;
align-items:center;
display:flex;
margin-left: 47%;
justify-content:space-between;

@media (max-width:700px){
    width:70%;
}
`

export const DivInsideRight =styled.div`
width:25%;
height:auto;
margin-top: 2%;
p{
    color:#D9DBE1;
    font-family:sans-serif;
    font-weight: 100;
    font-size:16px;
    line-height: 26px;
}
label{
    width:250px;
    height:40px;
    display:flex;
    background:#fff;
    color: white;
    opacity:0.2;
    border-radius:8px;
    align-items:center;
    justify-content:space-around;
}
label input{
    width:80%;
    height:90%;
    border-radius:8px;
    color: white;
    border:0px;
    outline:none;
}
@media (max-width:700px){
    width:70%;
}

`
// create account

export const Preloader =styled.div`
width:100%;
height:100vh;
position:fixed;
top:0px;
left:0px;
background:rgba(0,0,0,0.5);
display:flex;
justify-content:center;
align-items:center
`
export const Loader =styled.img`
width:40px;
height:40px;

`

export const Container =styled.div`
width:100%;
height:auto;
background: #F8F9FA;
display:flex;
@media(max-width:600px){
  flex-direction:column;
}
`

export const FormDiv =styled.div`
width:40%;
height: auto;
display: flex;
justify-content: center;
margin-bottom: 30px;
@media(max-width:600px){
    width:100%;
}
`
export const Form =styled.form`
width:80%;
height:auto;

font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
color: #252D42;
`
export const BackgroundImage = styled.div`
width:60%;
height:850px;
background-image:url(${bg});
background-size:cover;
background-repeat:no-repeat;
background-position: none;
@media(max-width:600px){
  display:none
}
`
export const Fieldset = styled.fieldset`
margin-bottom:25px;
height:50px;
border: 1px solid rgba(37, 45, 66, 0.29);
border-radius: 4px;
color:#252D42;
input{
  width:100%;
  height:100%;
  border:0px;
  outline:none;
  font-size:15px;
  background: #F8F9FA;

}
select{
  width:100%;
  height:100%;
  border:0px;
  outline:none;
  font-size:15px;
  background: #F8F9FA; 
}

`

export const ButtonForm =styled.button`
width: 100%;
height: 48px;
outline:none;
border:0px;
margin-bottom:30px;

background:#FF5722; 

border-radius: 4px;
transition-duration:0.5s;
color:white;
font-size:17px;

&:hover{
  background:pink;
}
`

export const ButtonFormWhite =styled.button`
width: 100%;
height: 48px;
outline:none;
border:0px;
margin-bottom:30px;

background: pink; 

border-radius: 4px;
transition-duration:0.5s;
color:white;
font-size:17px;

&:hover{
  background:#FF5722;
}
`

export const CenterDiv =styled.div`
display:flex;
justify-content:center;
align-items:center;
width:100%;

p{

color:#003366;
a{
  color:#FF5722;
  text-decoration:none;

}

}
`


export const IconDiv =styled.div`
width:100%;
height:30px;
display:flex;
align-items:center;
justify-content:center;
border:1px solid red;
padding:10px
`

export const I =styled.div`
width:95%;
height:100%;
display:flex;
align-items:center;
justify-content:center;
color:red
`


export const View = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const Containers = styled.div`
  width: 50%;
  height: 400px;
  box-shadow: 0px 0px 2px #ccc;
  display:flex;
  justify-content: center;
  align-items: center;
`;

export const Text = styled.p`
  color: green;
  font-size: 30px;
  font-weight:bold;
  font-family: sans-serif;
  text-align: center;
  border-radius:10px
`;
export const ModalCalender =styled.div`
margin-bottom:30px
`
export const DetailedEvent =styled.div`
width:100%;
height: auto;
margin-top: 100px;
display: flex;
justify-content: space-between;
`
export const EventDate = styled.div`
width: 100%;
height: 76px;
font-weight: 600;
font-size: 20px;
line-height: 37px;
color: #252D42;
display: flex;
justify-content: space-between
`



export const GobackDiv = styled.div`
width:85%;
height:400px;
display:flex;
justify-content:space-between
`
export const Divs =styled.div`
width:40%;
height:auto;
`

export const BuyModal =styled.div`
width: 382px;
height: 315px;
background:white;
border-radius: 10px;
padding:30px;
margin-top:70px
`
export const EventName =styled.div`

width: 567px;
height: 152px;
margin-top:128px
`

export const Caption = styled.div`
width: 525px;
height: 99px;
font-style: normal;
font-weight: 400;
font-size: 24px;
line-height: 33px;
padding-top: 32px;
`




export const Setup=styled.div`

width:100%;
height:auto;
margin-bottom:30px`
export const Details =styled.div`
width:100%;
height:auto;
display:flex;
justify-content:space-between
`

export const DiviNFO =styled.div`
width:40%;
height:150px;
`
export const AccountNFO=styled.div`
width:40%;
height:150px;
display:flex;
align-items:center;
justify-content:flex-end;
a{
text-decoration:none;
color:#FF5722
}

`
export const Select =styled.select`
width:100%;
height:100%;
border:0px;
outline:none;
cursor:pointer
`

export const FormAccount =styled.form`
width:86%;
height:auto;
background:white;
padding:90px
`
export const MessageResponse =styled.div`
width:99%;
background:#FEFBE8;
padding-left:10px;
padding-top:20px;
padding-bottom:20px;
margin-bottom:15px;
display: flex;
align-items: center;
font-size:13px

`

export const Description = styled.div`
width: 567px;
height: 44px;
font-weight: 800;
font-size: 32px;
line-height: 44px;
color: #252D42;
`
export const DetailedDescription = styled.div`
margin-top: 24px;
width: 100%;
height: auto;
font-weight: 400;
font-size: 16px;
line-height: 27px;
color: #252D42;
`
export const EventDesc = styled.div`
width: 100%;
height: auto;
`
export const Divs2 =styled.div`
width:60%;
height:auto;
`
export const EventLocation =styled.div`
width: 100%;
height: 44px;
font-weight: 800;
font-size: 32px;
line-height: 44px;
color: #252D42;
`

export const StartDate = styled.div`
width: 98px;
height: 78px;
`
export const StartDateName = styled.div`
width:98px;
font-weight: 600;
font-size: 20px;
line-height: 37px;
color: #252D42;
`
export const StartDateValue = styled.div`
width:98px;
font-weight: 200;
font-size: 16px;
line-height: 37px;
color: #252D42;
`
export const MapOfEvents =styled.div`
margin-top: 24px;
width: 100%;
height: 385px;
div{
 position:relative   
}
`
export const ShareSocial =styled.div`
margin-top: 85px;
width: 100%;
height: 44px;
font-weight: 800;
font-size: 32px;
line-height: 44px;
color: #252D42;
`
export const OtherEvents =styled.div`
width: 100%;
height: auto;
`
export const SecondDiv = styled.div`
width: 37%;
height: auto;
`
export const EventMap =styled.div`
width: 100%;
`



export const ContentText =styled.div`
margin-top:70px;
 display:flex;
justify-content:space-between;
align-item:center;

button{
   color:black;
   width:100px;
   height:30px;
   margin:40px
}


`

export const ContentBox = styled.div`
background: rgba(0, 50, 47, 0.05);
display:flex;
justify-content:space-between;
width:100%;
height:auto;
padding:50px;

border-bottom:1px solid #C4C4C4;
button{
 margin-left:400px;
 text-align:center;
 background: rgba(255, 87, 34, 1);
 border:none;


}

` 


export const spanDiv = styled.div`
color:blue;
display:flex
align-items:center;
`

export const Content = styled.div`
width:95%;
height:auto;
margin-bottom:100px;
padding-top:40px;

` 
export const Box = styled.div`
display:flex;
justify-content:space-evenly;
align-items:center;
text-align:center;
width:200px;
height:60px;
margin:5px;
background:#FFFFFF;
`

export const T = styled.div`
margin-top:100px;
font-weight: 700;
font-size: 24px;
line-height: 33px;
color: #000000;
`

export const Fieldsets = styled.fieldset`
margin-top:20px;
height:50px;
width:40%;
border: 1px solid rgba(37, 45, 66, 0.29);
border-radius: 4px;
box-sizing:box;

input{
width:100%;
height:20px;
border:0px;
outline:none;
font-size:15px;


}

`


export const Forms =styled.form`


display:flex;
backgroud:blue;
justify-content:space-between;
align-items:center;



font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

`
export const ContentBackground =styled.div`
width:100%;
height:auto;
display:flex;
justify-content:center`

export const Items =styled.div`
width:100%;
height:70px;
margin-bottom:30px;
margin-left:10px;


`


export const ContainerTicket =styled.div`
width:100%;
`



export const DivTicket=styled.div`
width:83%;

`
export const TextTicket =styled.div`
color:white;
width:25%;
height:100px;
display:flex;
align-items:center;
font-size:18px;
margin-top:50px;
font-weight:600;
text-decoration:none;
  `
 export const Span = styled.div`
  color:white;
  font-size:56px;
  font weight:700px;
  margin-top:130px;
  `
 

export const SetUp2 =styled.div`
  width: 85%;
  height: auto;
  display: flex;
  justify-content: space-between;
`
export const SetUp3 =styled.div`
  width: 83%;
  height: auto;
  display: flex;
  justify-content: space-between;
  background: white;
  padding: 1%
`
export const ConfirmTitle =styled.div`
  width: 60%;
  height: auto;
  margin-top: 0;
`
export const EditDetails =styled.div`
  margin-top:10px;
  width: 30%;
  height: auto;
  display: flex;
  flex-direction: row;
  justify-content: right;
  align-content: center;
`
export const EditBox =styled.div`
  margin-top: 30px;
  align-items: center;
  width: 111px;
  height: 47px;
  display: flex;
  justify-content: center;
  background: #F2F4F7;
  border: 1px solid #101828;
  border-radius: 6px;
`
export const AllEvent =styled.a`
  margin-top: 30px;
  margin-left: 24px;
  
`
export const Line = styled.div`
  width: 85%;
  margin-top: 25px; 
  border: 1px solid #C4C4C4;
`
export const OtherConfirmation =styled.div`
  width: 85%;
  height: auto;
  margin-bottom: 50px;
`
export const TheDetails =styled.div`
  margin-bottom: 20px;
`
export const TheDetails2 =styled.div`
  margin-bottom: 26px;
  p{
    font-weight: 400;
    font-size: 14px;
    line-height: 14px;
    letter-spacing: 0.15px;
    color: #667085;
  }
  span{
    font-weight: 600;
    font-size: 16px;
    line-height: 16px;
    letter-spacing: 0.15px;
    color: #252D42;
  }
`
export const Filter = styled.div`
  width: 35%;
  height: auto;
  display: flex;
  justify-content: right;
  align-items: center;
`
export const FilterContent =styled.div`
  margin-right: 10px;
  width: 35%;
  height: 25%;
  border: 1px solid #555A68;
  border-radius: 5px;
`
export const HorLine =styled.div`
  background: #EAECF0;
  transform: rotate(-90deg);
  width: 22px;
  height: 2px;
`
export const TheContent =styled.div`
  display: flex;
  align-items: center;
  padding-left: 7%;
  padding-right: 7%;
  height: 100%;
`
export const CreatedEvents = styled.div`
  
  width: 320px;
  height: 230px;
  background: white;
  margin-right: 2%;
  img{
    width:100%;
    height: 100%;
  }
`
export const FirstDetails =styled.div`
  width: 23%;
  height: auto;
`
export const SecondDetails =styled.div`
  width: 30%;
  height: auto;
`
export const ThirdDetails =styled.div`
  width: 12%;
  height: auto;
  display: flex;
  align-items: flex-end;
  flex-direction:column;
`
export const Active =styled.div`
  margin-bottom: 20px;
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 4px 8px;
  gap: 4px;
  width: 65px;
  height: 30px;
  background: #F6FEF9;
  border: 1px solid #A6F4C5;
  border-radius: 16px;
  p{
    font-weight: 400;
    font-size: 12px;
    line-height: 22px;
    letter-spacing: 0.15px;
    color: #34A853;
  }
`
export const InActive =styled.div`
  margin-bottom: 20px;
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
 
  gap: 4px;
  width: 95px;
  height: 30px;
  background: #F6FEF9;
  border: 1px solid #A6F4C5;
  border-radius: 16px;
  a{
    font-weight: 400;
    font-size: 12px;
    line-height: 22px;
    letter-spacing: 0.15px;
    text-decoration:none;
    color: red;
  }
`
export const TicketSold =styled.div`
  margin-bottom: 20px;
  height: 22px;
  font-style: normal;
  font-weight: 400;
  font-size: 13px;
  line-height: 22px;
  letter-spacing: 0.15px;
  text-decoration-line: underline;
  color: #FF5722;
  text-decoration:none

`

