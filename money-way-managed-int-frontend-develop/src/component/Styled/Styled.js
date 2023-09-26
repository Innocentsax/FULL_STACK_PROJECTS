import styled from "styled-components";
import { RxImage } from "react-icons/rx";
import { PiEyeLight, PiEyeSlashLight } from "react-icons/pi";
import { NavLink } from "react-router-dom";
import { Link } from "react-router-dom";
import { SlBell } from "react-icons/sl";
import {
  AiOutlineMenu,
  AiOutlineCloseCircle,
  AiOutlineUser,
} from "react-icons/ai";
import { HiOutlineMail } from "react-icons/hi";
import { FiLock, FiArrowDownLeft, FiArrowUpRight } from "react-icons/fi";
import { BsShieldLock } from "react-icons/bs";
import { PiKey } from "react-icons/pi";
import { MdOutlineLocalPhone } from "react-icons/md";
import { BsFillCheckCircleFill } from "react-icons/bs";
import { LuFilter } from "react-icons/lu";
import { FaRegCopy } from "react-icons/fa";




export const ContainerFBG = styled.div`
display: flex;
flex-direction: column;
align-items: center;
padding-top: 3%;
padding-bottom: 3%;
padding-left: 2%;
padding-right: 2%;
width: 100%;
height: 100%;
background-color: #f2f4f7;
overflow: auto;
margin-left: 240px;


@media screen and (max-width: 900px) {
margin-left: 0;
padding-top: 25px;
padding-left: 25px;
padding-right: 25px;
padding-bottom: 85px;
}
`;


export const ContainerFBGpd = styled.div`
display: flex;
flex-direction: column;
align-items: center;
padding-top: 3%;
padding-left: 2%;
padding-right: 2%;
padding-bottom: 20%;
width: 100%;
background-color: #f2f4f7;
overflow: auto;
margin-left: 240px;


@media screen and (max-width: 900px) {
margin-left: 0;
padding-top: 25px;
padding-left: 25px;
padding-right: 25px;
padding-bottom: 85px;
}
`;


export const ContainerV = styled.div`
display: flex;
flex-direction: column;
align-items: center;
padding-top: 200px;
padding-bottom: 3%;
padding-left: 2%;
padding-right: 2%;
width: 100%;
height: 100vh;
background-color: #eef4ff;


@media screen and (max-width: 767px) {
padding-top: 120px;
padding-left: 16px;
padding-right: 16px;
padding-bottom: 58px;
}
`;


export const ContainerLG = styled.div`
display: flex;
flex-direction: column;
align-items: center;
padding-top: 3%;
padding-bottom: 3%;
padding-left: 2%;
padding-right: 2%;
width: 100%;
height: 100vh;
background-color: #eef4ff;
overflow: scroll;


@media screen and (max-width: 767px) {
padding-top: 58px;
padding-left: 16px;
padding-right: 16px;
padding-bottom: 58px;
}
`;


export const LGCard = styled.div`
display: flex;
flex-direction: column;
align-items: center;
padding: 48px;
width: 479px;
height: auto;
background-color: #ffffff;
border-radius: 24px;
transition: 850ms ease;


@media screen and (max-width: 767px) {
width: 343px;
padding-top: 16px;
padding-left: 16px;
padding-right: 16px;
padding-bottom: 20px;
border-radius: 8px;
}
`;


export const VCard = styled.div`
display: flex;
flex-direction: column;
align-items: center;
padding: 48px;
width: 479px;
height: auto;
background-color: #ffffff;
border-radius: 24px;
transition: 850ms ease;


@media screen and (max-width: 767px) {
width: 343px;
padding-top: 48px;
padding-left: 16px;
padding-right: 16px;
padding-bottom: 48px;
border-radius: 8px;
}
`;


export const LabelHeader = styled.label`
font-size: 24px;
font-weight: 700;
color: #101828;
`;


export const LabelHeaderO = styled.label`
display: none;


@media screen and (max-width: 767px) {
display: flex;
font-size: 24px;
font-weight: 600;
text-align: center;
color: #101828;
margin-bottom: 32px;
}
`;


export const LabelHeaderI = styled.label`
font-size: 24px;
font-weight: 700;
color: #101828;
margin-bottom: 40px;


@media screen and (max-width: 767px) {
display: none;
}
`;


export const LabelHeaderV = styled.label`
font-size: 24px;
font-weight: 700;
color: #101828;
margin-bottom: 40px;
`;


export const FormCard = styled.div`
display: flex;
flex-direction: column;
align-items: center;
width: 687px;
height: auto;
margin-top: 3%;
padding-top: 25px;
padding-left: 25px;
padding-right: 25px;
padding-bottom: 25px;
border-radius: 8px;
background-color: #fff;
transition: 850ms ease-in-out;


@media screen and (max-width: 767px) {
width: 343px;
margin-top: 25px;
}
`;


export const CoverImgEdit = styled.img`
width: 100%;
min-height: 84px;
border-radius: 8px;
`;


export const AvatarDiv = styled.div`
display: flex;
flex-direction: column;
justify-content: center;
align-items: center;
width: 100px;
height: 100px;
border-radius: 50%;
background-color: #f9fafb;
margin-top: -50px;


@media screen and (max-width: 900px) {
width: 80px;
height: 80px;
margin-top: -40px;
}


@media screen and (max-width: 767px) {
width: 70px;
height: 70px;
margin-top: -35px;
}
`;


export const AvatarIcon = styled(RxImage)`
font-size: 28px;
color: #667085;


@media screen and (max-width: 900px) {
font-size: 26px;
}


@media screen and (max-width: 767px) {
font-size: 24px;
}
`;


export const Form = styled.form`
width: 100%;
`;


export const FormDiv = styled.div`
display: flex;
flex-direction: column;
width: 100%;
`;


export const PGraphF = styled.p`
width: 550px;
color: #98a2b3;
font-size: 14px;
font-weight: 200;
line-height: 1.5;
margin-top: 15px;
margin-bottom: 0;


@media screen and (max-width: 767px) {
width: 100%;
}
`;


export const InputGridDiv = styled.div`
display: grid;
grid-template-columns: 1fr;
grid-template-rows: 1fr;
grid-template-areas: "overlap";
`;


export const InputGridDivL = styled.div`
display: grid;
grid-template-columns: 1fr;
grid-template-rows: 1fr;
grid-template-areas: "overlap";
margin-top: 8px;
margin-bottom: 16px;
`;


export const InputGrid = styled.input`
grid-area: overlap;
align-self: center;
justify-self: center;
font-size: 14px;
width: 100%;
height: 44px;
border: 1px solid #98a2b3;
border-radius: 6px;
padding-left: 15px;
padding-right: 46px;
color: #101828;
appearance: none;
outline: none;


&::placeholder {
color: #98a2b3;
}
`;


export const InputGridL = styled.input`
grid-area: overlap;
align-self: center;
justify-self: center;
font-size: 14px;
width: 100%;
height: 44px;
border: 1px solid #98a2b3;
border-radius: 6px;
padding-left: 46px;
padding-right: 46px;
color: #101828;
appearance: none;
outline: none;


&::placeholder {
color: #98a2b3;
}
`;


export const IconGrid = styled.div`
grid-area: overlap;
align-self: center;
justify-self: end;
place-items: center;
height: auto;
padding-bottom: 0;
margin-right: 20px;
align-items: center;
`;


export const ErrorMsg = styled.div`
grid-area: overlap;
align-self: end;
justify-self: start;
font-size: 12px;
font-weight: 300;
color: #f00;
margin-bottom: -20px;
`;


export const ErrorMsgOutside = styled.div`
font-size: 12px;
font-weight: 300;
color: #f00;
margin-top: -10px;
margin-bottom: 10px;
`;


export const GoodMsg = styled.div`
grid-area: overlap;
align-self: end;
justify-self: start;
font-size: 12px;
font-weight: 300;
color: #0f0;
margin-top: -10px;
margin-bottom: 10px;
`;


export const IconGridL = styled.div`
grid-area: overlap;
align-self: center;
justify-self: start;
place-items: center;
height: auto;
padding-bottom: 0;
margin-left: 16px;
align-items: center;
`;


export const IconFlex = styled.div`
display: flex;
align-self: center;
justify-self: end;
cursor: pointer;
`;


export const IconFlexn = styled.div`
display: flex;
align-self: center;
justify-self: start;
`;


export const EyeIcon = styled(PiEyeLight)`
color: #98a2b3;
font-size: 20px;
`;


export const SlashEyeIcon = styled(PiEyeSlashLight)`
color: #98a2b3;
font-size: 20px;
`;


export const MailIcon = styled(HiOutlineMail)`
color: #98a2b3;
font-size: 20px;
`;


export const LockIcon = styled(FiLock)`
color: #98a2b3;
font-size: 20px;
`;


export const BVNIcon = styled(BsShieldLock)`
color: #98a2b3;
font-size: 20px;
`;


export const KeyIcon = styled(PiKey)`
color: #98a2b3;
font-size: 20px;
`;


export const PhoneIcon = styled(MdOutlineLocalPhone)`
color: #98a2b3;
font-size: 20px;
`;


export const UserIcon = styled(AiOutlineUser)`
color: #98a2b3;
font-size: 20px;
`;


export const FGLink = styled(Link)`
color: #1570ef;
font-family: "Inter", sans-serif;
font-weight: 400;
font-size: 14px;
text-decoration: none;
`;


export const FGPara = styled.p`
text-align: left;
margin-top: -16px;
transition: 850ms ease;


@media screen and (max-width: 767px) {
text-align: right;
}
`;


export const CALink = styled(Link)`
color: #3538cd;
font-family: "Inter", sans-serif;
font-weight: 600;
font-size: 14px;
text-decoration: none;
`;


export const VLink = styled(Link)`
color: #3538cd;
font-family: "Inter", sans-serif;
font-weight: 600;
font-size: 14px;
text-decoration: none;
margin-top: 40px;
`;


export const VPara = styled.p`
color: #101828;
font-family: "Inter", sans-serif;
font-weight: 400;
font-size: 14px;
text-align: center;
margin-top: 0;
`;


export const VParaL = styled.p`
color: #101828;
font-family: "Inter", sans-serif;
font-weight: 400;
font-size: 14px;
text-align: left;
margin-top: 0;
margin-bottom: 16px;
`;


export const VLPara = styled.p`
color: #101828;
font-family: "Inter", sans-serif;
font-weight: 400;
font-size: 14px;
text-align: center;
margin-top: 24px;
`;


export const CAPara = styled.p`
color: #101828;
font-family: "Inter", sans-serif;
font-weight: 400;
font-size: 14px;
text-align: center;
margin-top: 40px;


@media screen and (max-width: 767px) {
display: none;
}
`;


export const CAParaO = styled.p`
display: none;


@media screen and (max-width: 767px) {
display: flex;
color: #101828;
font-family: "Inter", sans-serif;
font-weight: 400;
font-size: 14px;
text-align: center;
margin-top: 40px;
}
`;


export const LabelInput = styled.label`
font-size: 14px;
font-weight: 600;
color: #101828;
margin-top: 25px;
margin-bottom: 10px;
`;


export const Input = styled.input`
font-size: 14px;
width: 100%;
height: 44px;
border: 1px solid #98a2b3;
border-radius: 6px;
padding-left: 15px;
padding-right: 15px;
color: #101828;
appearance: none;
outline: none;


&::placeholder {
color: #98a2b3;
}
`;


export const InputDes = styled.textarea`
font-size: 14px;
width: 100%;
height: 165px;
border: 1px solid #98a2b3;
border-radius: 6px;
padding-top: 10px;
padding-left: 15px;
padding-right: 15px;
color: #101828;
appearance: none;
outline: none;
font-family: inter;


&::placeholder {
color: #98a2b3;
}
`;


export const EmailInput = styled.input`
font-size: 14px;
width: 100%;
height: 44px;
background-color: #eaecf0;
border: none;
border-radius: 6px;
padding-left: 15px;
padding-right: 15px;
color: #101828;
appearance: none;
outline: none;


&::placeholder {
color: #98a2b3;
}
`;


export const TabDiv = styled.div`
display: flex;
flex-direction: row;
align-items: center;
justify-content: center;
width: 100%;
height: 45px;
border-bottom: 3px solid #dadada;
`;


export const ButtonTab = styled.button`
font-size: 14px;
font-weight: 400;
color: #101828;
background-color: #fff;
border: none;
width: 50%;
height: 100%;
cursor: pointer;


&.active {
font-weight: 400;
color: #3538cd;
border-bottom: 3px solid #3538cd;
}


@media screen and (max-width: 767px) {
font-size: 12px;
}
`;


export const TogDiv = styled.div`
width: 100%;
height: auto;
display: none;


&.active {
display: block;
}
`;


export const ButtonP = styled.button`
font-size: 14px;
font-weight: 600;
width: 100%;
height: 44px;
border: none;
color: #fff;
border-radius: 6px;
background-color: #3538cd;
margin-top: 32px;
`;


//---------------------------------------------User NavBar


export const NavBarCon = styled.div`
display: flex;
flex-direction: row;
align-items: center;
width: 100%;
height: 96px;
padding-left: 2%;
padding-right: 7%;
background-color: #fff;
position: fixed;
top: 0;
z-index: 1;


@media screen and (max-width: 900px) {
height: 56px;
padding-left: 5%;
}


@media screen and (max-width: 767px) {
height: 56px;
}
`;


export const NotiDiv = styled.a`
display: grid;
grid-template-columns: 1fr;
grid-template-rows: 1fr;
grid-template-areas: "overlap";
color: #000;
margin-right: 30px;
text-decoration: none;
cursor: pointer;
`;


export const NavRightDiv = styled.div`
display: flex;
flex-direction: row;
align-items: center;
justify-content: end;
width: 100%;


@media screen and (max-width: 900px) {
display: none;
}
`;


export const BellIcon = styled(SlBell)`
grid-area: overlap;
align-self: center;
justify-self: center;
font-size: 20px;
`;


export const NotiAlert = styled.div`
grid-area: overlap;
align-self: start;
justify-self: end;
margin-top: -2px;
margin-right: 2px;
width: 8px;
height: 8px;
border-radius: 50%;
background-color: #f61805;
`;


export const UserAvDiv = styled.a`
display: flex;
flex-direction: row;
justify-content: flex-end;
align-items: center;
color: #000;
text-decoration: none;
cursor: pointer;
`;


export const NavLogoDiv = styled(Link)`
display: flex;
flex-direction: row;
align-items: center;
font-family: "Inter", sans-serif;
font-weight: 600;
font-size: 16px;
color: #3538cd;
text-decoration: none;
`;


export const LGLogoDiv = styled(Link)`
display: flex;
flex-direction: row;
align-items: center;
font-family: "Inter", sans-serif;
font-weight: 700;
font-size: 32px;
color: #3538cd;
text-decoration: none;
margin-bottom: 80px;


@media screen and (max-width: 767px) {
font-size: 16px;
margin-bottom: 60px;
}
`;


export const LGLogo = styled.img`
width: 72px;
height: 72px;
margin-right: 16px;


@media screen and (max-width: 767px) {
width: 40px;
height: 40px;
margin-right: 8px;
}
`;


//---------------------------------------------Userprofile page


export const UserCon = styled.div`
display: flex;
flex-direction: column;
width: 100%;
height: 100vh;
position: fixed;
`;


export const BodyFrame = styled.div`
display: flex;
flex-direction: row;
width: 100%;
height: 100%;
margin-top: 96px;
position: fixed;
// overflow: scroll;
@media screen and (max-width: 900px) {
margin-top: 56px;
}
`;
export const SideFrame = styled.div`
height: 100%;
position: fixed;
transition: 850ms ease-in-out;


@media screen and (max-width: 900px) {
display: none;
}
`;
export const ProfileFrame = styled.div`
width: 100%;
height: 100%;
overflow: scroll;
`;


//-------------------------------------Successful Modals


export const CModal = styled.div`
display: flex;
flex-direction: column;
align-items: center;
width: 100%;
height: 100%;
background-color: rgba(0, 0, 0, 0.5);
position: absolute;
`;


export const ModalCard = styled.div`
display: flex;
flex-direction: column;
align-items: center;
justify-content: space-between;
width: 479px;
height: 346px;
padding: 48px;
border-radius: 24px;
background-color: #ffffff;
margin: auto auto;
transition: 850ms ease-in-out;


@media screen and (max-width: 500px) {
width: 100%;
height: 100%;
padding: 40% 17px 83px 17px;
border-radius: 0;
background-color: #f9fafb;
}
`;


export const GoodIcon = styled(BsFillCheckCircleFill)`
color: #34a853;
width: 48px;
height: 48px;
margin-bottom: 32px;
`;


export const LabelModal = styled.label`
font-size: 24px;
font-weight: 700;
color: #101828;
`;


export const ParaModal = styled.p`
color: #101828;
text-align: center;
font-size: 14px;
font-weight: 400;
margin-bottom: 32px;


@media screen and (max-width: 500px) {
margin-bottom: 50%;
}
`;


export const CloseBtn = styled.button`
display: flex;
align-items: center;
justify-content: center;
color: #101828;
background-color: #f2f4f7;
height: 44px;
width: 100%;
font-size: 14px;
font-weight: 400;
border-radius: 6px;
cursor: pointer;
text-decoration: none;
border: none;
`;


// ---------------------------------------------Sidebar style


export const SideCard = styled.div`
display: flex;
flex-direction: column;
width: 240px;
height: 100%;
background-color: #fff;
// border: 1px solid rgb(226, 226, 226);
`;


export const SideNavDiv = styled.div`
display: flex;
flex-direction: column;
padding-left: 40px;
padding-right: 40px;
padding-top: 20px;
padding-bottom: 20px;
`;


export const SideLine = styled.div`
width: 100%;
height: 1px;
margin-top: 20px;
// margin-bottom: 10px;
background-color: #dadada;
`;


export const SideNavLink = styled(NavLink)`
display: flex;
align-items: center;
padding-left: 20px;
color: #5c6881;
fill: #5c6881;
height: 40px;
width: 100%;
font-size: 16px;
text-decoration: none;
border-radius: 6px;
cursor: pointer;
text-decoration: none;


&.active {
color: #fff;
background-color: #3538cd;
}


&:hover {
color: #3538cd;
background-color: #eef4ff;
}
`;


export const BLLink = styled(NavLink)`
display: flex;
align-items: center;
justify-content: center;
font-size: 14px;
font-weight: 600;
width: 100%;
height: 44px;
border: none;
color: #fff;
border-radius: 6px;
background-color: #3538cd;
margin-top: 35px;
text-decoration: none;
`;


export const LogoutBTN = styled.button`
display: flex;
align-items: center;
padding-left: 20px;
color: #3538cd;
height: 40px;
width: 100%;
font-size: 16px;
text-decoration: none;
border-radius: 6px;
cursor: pointer;
text-decoration: none;
border: none;


@media screen and (max-width: 900px) {
padding-left: 0;
justify-content: center;
background-color: #eef4ff;
}
`;


//-------------------------------------Mobile Menu


export const MenuCard = styled.div`
display: none;


@media screen and (max-width: 900px) {
display: flex;
flex-direction: column;
width: 375px;
height: 522px;
background-color: #fff;
position: absolute;
top: 56px;
z-index: 1;
right: 0;
opacity: 0;
visibility: hidden;
transition: 500ms ease-in-out;
box-shadow: 0px 10px 12px 0px rgba(0, 0, 0, 0.02);


&.active {
opacity: 1;
visibility: visible;
transition: 500ms ease-in-out;
}
}


@media screen and (max-width: 500px) {
width: 100%;
}
`;


export const MenuDiv = styled.div`
display: flex;
flex-direction: column;
height: 100%;
padding-left: 32px;
padding-right: 32px;
padding-top: 32px;
padding-bottom: 0px;
`;


export const LogDiv = styled.div`
display: flex;
flex-direction: column;
padding-left: 32px;
padding-right: 32px;
padding-top: 18px;
padding-bottom: 18px;
`;


export const PGraphM = styled.p`
width: 100%;
color: #98a2b3;
font-size: 14px;
font-weight: 200;
line-height: 1.5;
margin-top: 32px;
`;


export const MIconFlex = styled.div`
display: none;
position: absolute;
right: 0;
width: 40px;
height: 40px;
margin-right: 5%;
font-size: 20px;


@media screen and (max-width: 900px) {
display: flex;
}
`;


export const MenuIcon = styled(AiOutlineMenu)`
width: 30px;
height: 30px;


@media screen and (max-width: 767px) {
width: 20px;
height: 20px;
}
`;


export const CloseIcon = styled(AiOutlineCloseCircle)`
width: 30px;
height: 30px;


@media screen and (max-width: 767px) {
width: 20px;
height: 20px;
}
`;


//-----------------------------------Profile Modals


export const PMCard = styled.div`
display: flex;
flex-direction: column;
width: 293px;
height: 238px;
background-color: #ffffff;
position: absolute;
top: 96px;
z-index: 1;
right: 25px;
opacity: 0;
visibility: hidden;
transform: translateY(-20px);
transition: 500ms ease;
box-shadow: 0px 10px 12px 0px rgba(0, 0, 0, 0.02);


&.active {
opacity: 1;
visibility: visible;
transform: translateY(0);
transition: 500ms ease;
}


@media screen and (max-width: 900px) {
display: none;
}
`;


export const PmHdiv = styled(NavLink)`
display: flex;
flex-direction: row;
justify-content: start;
// padding: 16px 16px 0 16px;
margin-top: 16px;
margin-left: 16px;
margin-right: 16px;
margin-bottom: -4px;
// border: 1px solid rgb(226, 226, 226);
text-decoration: none;
cursor: pointer;
text-decoration: none;
`;


export const LPdiv = styled.div`
display: flex;
flex-direction: column;
justify-content: center;
padding-bottom: 0;
// border: 1px solid rgb(226, 226, 226);
`;


export const LabelPM = styled.label`
font-size: 14px;
font-weight: 600;
color: #101828;
cursor: pointer;
`;


export const ParaPM = styled.p`
color: #98a2b3;
font-size: 14px;
font-weight: 400;
margin-top: 0px;
margin-bottom: 0px;
`;


export const LogoutBTNpm = styled.button`
display: flex;
align-items: center;
padding-left: 20px;
color: #5c6881;
background-color: #fff;
height: 40px;
width: 100%;
font-size: 16px;
text-decoration: none;
border-radius: none;
cursor: pointer;
text-decoration: none;
appearance: none;
border: none;


&:hover {
color: #3538cd;
// background-color: #EEF4FF;
}
`;


export const PmNavDiv = styled.div`
display: flex;
flex-direction: column;
justify-content: center;
padding-left: -4px;
margin-top: 6px;
// border: 1px solid rgb(226, 226, 226);
`;


export const PmNavLink = styled(NavLink)`
display: flex;
align-items: center;
padding-left: 20px;
color: #5c6881;
fill: #5c6881;
height: 40px;
width: 100%;
font-size: 16px;
text-decoration: none;
border-radius: 6px;
cursor: pointer;
text-decoration: none;


&.active {
color: #3538cd;
font-weight: 500;
}


&:hover {
color: #3538cd;
// background-color: #EEF4FF;
}
`;


//-----------------------------------Noti Modals


export const NotiCard = styled.div`
display: flex;
flex-direction: column;
width: 435px;
height: 393px;
background-color: #ffffff;
position: absolute;
top: 96px;
z-index: 1;
right: 25px;
opacity: 0;
visibility: hidden;
transform: translateY(-20px);
transition: 500ms ease;
box-shadow: 0px 10px 12px 0px rgba(0, 0, 0, 0.02);


&.active {
opacity: 1;
visibility: visible;
transform: translateY(0);
transition: 500ms ease;
}


@media screen and (max-width: 900px) {
display: none;
}
`;


export const NHdiv = styled.div`
display: flex;
flex-direction: column;
justify-content: center;
width: 100%;
height: 40px;
background-color: #fcfcfd;
padding-left: 24px;
// border: 1px solid rgb(226, 226, 226);
`;


//-----------------------------------Dashboard


export const DHDiv = styled.div`
display: flex;
flex-direction: column;
justify-content: start;
`;


export const LabelHeaderD = styled.label`
font-size: 24px;
font-weight: 700;
color: #101828;
text-align: left;
`;


export const DetailsDiv = styled.div`
display: flex;
flex-direction: row;
justify-content: space-between;
gap: 32px;
margin-top: 32px;
transition: 850ms ease;


@media screen and (max-width: 767px) {
flex-direction: column;
margin-top: 8px;
gap: 16px;
}
`;


export const WalletCard = styled.div`
display: flex;
flex-direction: row;
align-items: center;
justify-content: start;
width: 383px;
height: 138px;
background-color: #3538CD;
color: #ffffff;
padding-left: 34px;
padding-top: 16px;
padding-bottom: 16px;
padding-right: 34px;
border-radius: 24px;
transition: 850ms ease;


@media screen and (max-width: 767px) {
width: 343px;
height: 122px;
padding-top: 12px;
padding-bottom: 12px;
border-radius: 8px;
}
`;


export const SpentCard = styled.div`
display: flex;
flex-direction: row;
align-items: center;
justify-content: start;
width: 383px;
height: 138px;
background-color: #FEFDF0;
color: #101828;
padding-left: 34px;
padding-top: 16px;
padding-bottom: 16px;
padding-right: 34px;
border-radius: 24px;
transition: 850ms ease;


@media screen and (max-width: 767px) {
width: 343px;
height: 122px;
padding-top: 12px;
padding-bottom: 12px;
border-radius: 8px;
}
`;


export const WSDiv = styled.div`
display: flex;
flex-direction: column;
justify-content: space-between;
gap: auto;
height: 100%;
`;


export const WSDiv2 = styled.div`
display: flex;
flex-direction: column;
`;


export const WSDiv3 = styled.div`
display: flex;
flex-direction: row;
align-items: center;
`;


export const WSDiv4 = styled.div`
display: flex;
flex-direction: row;
align-items: center;
justify-content: space-between;
width: 100%;
color: #012A4A;
font-family: Inter;
font-size: 14px;
font-weight: 600;
`;


export const WSDiv5 = styled.div`
display: flex;
flex-direction: column;
justify-content: space-between;
gap: 24px;
width: 100%;
`;


export const WSDiv6 = styled.div`
display: flex;
flex-direction: column;
justify-content: end;
text-align: right;
`;


export const WSDiv7 = styled.div`
display: flex;
flex-direction: row;
align-items: center;
justify-content: end;
`;


export const W1Label = styled.label`
font-size: 14px;
font-weight: 400;
text-align: left;
`;


export const W2Label = styled.label`
font-size: 24px;
font-weight: 700;
text-align: left;
`;


export const W3Label = styled.label`
font-size: 14px;
font-weight: 400;
color: #FAC515;
text-align: left;
`;


export const W4Label = styled.label`
font-size: 14px;
font-weight: 400;
color: #B93815;
text-align: left;
`;




export const CHDiv = styled.div`
width: 790px;
min-height: 374px;
margin-top: 32px;
background-color: #ffffff;
padding-left: 0px;
padding-right: 16px;
padding-top: 16px;
padding-bottom: 16px;
transition: 850ms ease;


@media screen and (max-width: 767px) {
width: 343px;
min-height: 374px;
margin-top: 24px;
padding-left: 0px;
padding-right: 8px;
padding-top: 8px;
padding-bottom: 8px;


}
`;


export const THDiv = styled.div`
width: 790px;
min-height: 273px;
margin-top: 32px;
margin-bottom: 32px;
background-color: #ffffff;
padding-left: 40px;
padding-right: 40px;
padding-top: 16px;
padding-bottom: 16px;
transition: 850ms ease;


@media screen and (max-width: 767px) {
width: 343px;
min-height: 237px;
margin-top: 24px;
margin-bottom: 24px;
padding-left: 16px;
padding-right: 16px;
}
`;


export const FilterDiv = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  width: 98px;
  height: 40px;
  border-radius: 32px;
  border: 1px solid #EAECF0;
  background: #F9FAFB;
  color: #012A4A;
  font-family: Inter;
  font-size: 14px;
  font-weight: 400;


  @media screen and (max-width: 767px) {
    display: none;
  }
`;


export const TDiv = styled.div`
display: flex;
flex-direction: row;
align-items: center;
justify-content: center;
`;


export const FliterIcon = styled(LuFilter)`
color: #475467;
font-size: 20px;
margin-right: 8px;
`;


export const W5Label = styled.label`
font-size: 14px;
font-weight: 400;
color: #101828;
`;


export const W6Label = styled.label`
font-size: 14px;
font-weight: 400;
color: #98A2B3;
`;


export const W7Label = styled.label`
font-size: 14px;
font-weight: 400;
color: #34A853;
`;
export const W8Label = styled.label`
font-size: 14px;
font-weight: 400;
color: #B42318;
`;


export const BankLogo = styled.img`
width: 32px;
height: 32px;
margin-right: 24px;


@media screen and (max-width: 767px) {
margin-right: 8px;
}
`;


export const DebitIcon = styled(FiArrowUpRight)`
color: #B42318;
font-size: 20px;
margin-left: 8px;
`;


export const CreditIcon = styled(FiArrowDownLeft)`
color: #34A853;
font-size: 20px;
margin-left: 8px;
`;


export const CopyIcon = styled(FaRegCopy)`
color: #ffffff;
font-size: 12px;
margin-left: 15px;
cursor: pointer;
`;

