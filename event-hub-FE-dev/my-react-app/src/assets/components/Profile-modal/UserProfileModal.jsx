import React, { useState } from 'react';
import styled from 'styled-components';
import profile from '../Profile-modal/img/profile.png';
import paymentImg from '../Profile-modal/img/paymentImg.png';
import logoutLogo from  '../Profile-modal/img/Vector.png';
import { Link } from 'react-router-dom';



function UserProfileModal(props) {
  const TOKEN=localStorage.getItem("TOKEN")
  const [selectedFile, setSelectedFile] = useState(null);

  const change=()=>{
    window.location.href ="http://localhost:5173/user-event-created/0"
  }


  // const handleFileSelect = (event) => {
  //   event.persist();
  //   setSelectedFile(event.target.files[0]);
 
  //   console.log(selectedFile)
  //   console.log(event.target.files[0]);
  // }

  // const handleUpload = () => {
  //   const formData = new FormData();
  //   formData.append('file', selectedFile);

  //   axios.post("http://localhost:8999/api/v1/user/upload", formData, {
  //     headers: {
  //       'Content-Type': 'multipart/form-data',
  //       'Access-Control-Allow-Origin': '*',
  //       'Authorization': `Bearer ${TOKEN}`
  //     }
  //   }).then((response) => {
  //     // Handle the response
  //   }).catch((error) => {
  //     // Handle the error
  //   });
  


  return (
    <>
    

    

        <Modal >
          <ModalContent>
            <ModalHeader>
              <ModalDetails>
                <Img>
                <Src  src={props.profile} alt="Profile" />
                  {/* <input
                  type="file"
                  style={{ display: "none" }}
                  id="fileUpload"
                  name="file"
                  onChange={handleFileSelect}
                />
                    <button onClick={handleUpload}>Upload</button> */}
                </Img>
          
                <ProfileName>
                  <span>{props.name}</span>
                  <p>{props.email}</p>
                </ProfileName>
              </ModalDetails>
                <ModalInfo>
                    <PaymentImg><Link to="/"><img src={paymentImg}/></Link>
                    </PaymentImg>
                    <Link to="../" style={{textDecoration:"none"}}>
                        <ModalText><p>Home</p>
                        </ModalText></Link>


                </ModalInfo>
              <ModalInfo> 
                 <PaymentImg><Link to="http://localhost:5173/app/setup-account"><img src={paymentImg}/></Link>
                 </PaymentImg> 
                 <Link to="http://localhost:5173/app/setup-account" style={{textDecoration:"none"}}>
                 <ModalText><p>Payment Account</p>
                 </ModalText></Link>

                
            </ModalInfo>
            <ModalInfo> 
                 <PaymentImg><Link to="http://localhost:5173/user-event-created/0"><img src={paymentImg}/></Link>
                 </PaymentImg> 
                 <Link onClick={change}  style={{textDecoration:"none"}} >
                 <ModalText><p>My Events</p>
                 </ModalText></Link>

                
            </ModalInfo>
            <ModalInfo> 
                 <LogOutLogo> <Link to="http://localhost:5173/logout"><img src={logoutLogo}/></Link>
                 </LogOutLogo> 

                 <Link to="http://localhost:5173/logout" style={{textDecoration:"none"}}>
                 <ModalText><p style={{display:"flex", justifyContent:"flex-start"}}>Logout</p>
                 </ModalText>
                 </Link>
                 
            </ModalInfo>
            </ModalHeader>
            {/* <ModalCloseButton onClick={closeModal}>Close</ModalCloseButton> */}
          </ModalContent>
        </Modal>




    </>
  );
}

export default UserProfileModal;


const Main = styled.div`
  width: 100%;
  height: 100vh;
  background: #e5e5e5;
  color: red;
  position: relative;
`;


const ModalButton = styled.button`
  /* Style the button to open the modal */
`;

const Modal = styled.div`
  width: 293px;
  height: auto;
  display: flex;
  justify-content: center;
  background: #ffffff;
  position: absolute;
  left: 76%;
  top: 30%;

 

  transform: translate(-50%, -50%);
  border: 1px solid #999;
  box-shadow: 0 5px 5px rgba(182, 182, 182, 0.75);
  
`;

const ModalContent = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
 

  justify-content: center;

`;

const ModalHeader = styled.div`
  width: 80%;
  height: auto;

  text-align: left;
  color: white;
  padding-top: 13px;
`;

const ModalDetails = styled.div`
  width: 100%;
  height: 80px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #d0d5dd;
`;

const Img = styled.form`
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background-size: cover;
  background-repeat: no-repeat;
`;

const Src = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 50%;
`;

const ProfileName = styled.div`
padding-left:5px;
  span {
    color: #101828;
    font-weight: 600;
    font-size: 14px;
    font-family: 'Inter';
    line-height: 20px;
    font-style: normal;
  }
  p {
    color: #98a2b3;
    font-family: 'Inter';
    font-style: normal;
    font-weight: 400;
    font-size: 14px;
    line-height: 20px;
    margin: 0;
  }
`;

const ModalInfo = styled.div`
  color: red;
  width: 70%;
  display: flex;
  align-items: center;


`;

const PaymentImg = styled.div`
  display: flex;
  align-items: center;
  margin-right: 10px;
 

  img {
    width: 15px;
    height: 19px;
    margin-right: 5px;
  }
`;

const LogOutLogo= styled.div`
display: flex;
align-items: center;
margin-right: 10px;
img {
  width: 15px;
  height: 19px;
  margin-right: 5px;
}
` 


const ModalText = styled.p`
font-weight: bold;
color: black;
font-family: 'Inter';
font-style: normal;
font-weight: 400;
font-size: 14px;
line-height: 20px;
color: #101828;
padding-left:10px;
 margin:0px;
`;

const ModalX = styled.div`
 

  ${ModalInfo} {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
`;
// const ModalCloseButton=styled.div`
//  color :red;
// `