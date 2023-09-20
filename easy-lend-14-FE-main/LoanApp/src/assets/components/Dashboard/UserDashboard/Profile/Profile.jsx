import Header from "../../../Header/Header";
import {  Flex, Input } from "../../../Styled/Styled";
import { App, SidedTfLX } from "../DashboardStyled";
import styled from 'styled-components'
import {Link, useLocation} from 'react-router-dom'
import SidebarD from "../SidebarD";
import { useState,useEffect } from "react";
import {  Label, LendingContainer, WrapContent} from "../../RegisterDashboard/Styled-dashboard";
import { FaDochub, FaUser } from "react-icons/fa";
import { frameImg } from "../../../../Utils/AppUtils";



const Profile = ()=>{
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);

    const [state,setState] =useState(true)
  
    const task = queryParams.get('task');
    useEffect(() => {
        if (task === "profile") {
           
          setState(true);
        } 
         if (task === "document") {
          setState(false);
        }
    }, [task]);
      console.log(state)

      const Info = (prop)=>{
        return (

            <div style={{
                        width:"100%",
                        padding:"20px",
                        height:"auto"
                    }}>
                        
                        <h1 style={{fontSize:"25px"}}>{prop.account}</h1>
                        <span>{prop.text}</span>
                    </div>
                    
                             )
                        }
    return (
        <>
        <Header navbar={[]} />
        <Flex>
            <SidebarD />
            <App>
          

            <LendingContainer style={{background:"#f8f8f8"}}>
              <ProfileContent>
                <Side>
                <Link to="?task=profile"><SidedTfLX><FaUser /><span style={{color: "#2D00F7"}}>&nbsp;Profile</span></SidedTfLX></Link> 
                <Link to="?task=document">
  <SidedTfLX><FaDochub /><span >&nbsp;Document</span></SidedTfLX>
</Link>

                </Side>
               
                {(state)? 
                         <>
                          <Content>
                   <Info account={"Account"} text={"Manage your EasyLend account"} />
                   
                   
                   
                    <Wrap>
                        <ImageBitmap>
                        <ProfileImage>
                        <img src={frameImg} alt="" />

                    </ProfileImage>
                        </ImageBitmap>
                   

                    <WrapContent style={{width:"90%"}}>
                    <Label>First Name</Label>
                    <Input
                      type="text"
                      name="name"
                      value="Jack"
                      readonly="true"
                       placeholder="First Name"
                     
                   >
                      

                      </Input>
                
                  </WrapContent>
                  <WrapContent style={{width:"90%"}}>
                    <Label>Last Name</Label>
                    <Input
                      type="text"
                      name="name"
                       value="William"
                       readonly="true"
                      placeholder="Last Name"
                   >
                      

                      </Input>
                
                  </WrapContent>
                  <WrapContent style={{width:"90%"}}>
                    <Label>Email Address</Label>
                    <Input
                      type="text"
                      name="name"
                      placeholder="Email Address"
                      readonly="true"
                      value="chiorlujack@gmail.com"
                     
                   >
                      

                      </Input>
                
                  </WrapContent>
                  <WrapContent style={{width:"90%"}}>
                  <Label>Phone number</Label>
                    <Input
                      type="text"
                      name="name"
                      placeholder="Phone number"
                     
                   >
                      

                      </Input>
                
                  </WrapContent>
                  </Wrap>
                  </Content>
                  </>

: 
<>
<Content>
<Info  account={"Document"} text={"View your documents upload"}/>

<Document>
</Document>
</Content>
</>
}

               
               

              </ProfileContent>
          

            </LendingContainer>


            </App>
        </Flex>

        
        </>

    );
}

export default Profile;

const ProfileContent =styled.div`
width:100%;
height:100%;
display:flex;
background:#f8f8f8

`

const Side =styled.div`
width:20%;
height:100%;
border-right:1px solid #ccc;
a{
    text-decoration:none
}

`


const Content =styled.div`
width:80%;
height:600px;
display:flex;
padding:20px;
justify-content:center;
flex-direction:column;

`
const ProfileImage =styled.div`
width:100px;
margin-top:20px;
border:5px solid #fff;
height:100px;
border-radius:100px;
img{
    width:100%;
    height:100%;
    border-radius:100px
}
`

const Wrap =styled.div`
width:100%;
height:auto;

display:flex;
justify-content:center;
flex-direction:column;
`

const Document =styled.div`
width:100%;
height:400px;
background:red
`
const ImageBitmap =styled.div`
width:100%;
display:flex;
justify-content:center;
`