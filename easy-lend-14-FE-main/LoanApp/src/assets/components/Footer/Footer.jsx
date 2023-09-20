import { FooterDiv,Footer_Container,Footer_ContainerDiv,LogoDiv,Img, LeftDiv,RightDiv,H1} from "../Styled/Styled";
import logo from "../Images/logo_.png"
import {Link} from 'react-router-dom'
import { FaFacebook, FaInstagram, FaTwitter, FaWhatsapp, FaYoutube } from 'react-icons/fa';
function Footer(){
    return (
        <FooterDiv>
            <Footer_Container>
            <LogoDiv style={{justifyContent:"center"}}>
                <Img src={logo} alt="logo" width={50} />
                <H1 style={{color:"white"}}>EasyLend</H1>
            </LogoDiv>
            <p style={{color:"#CCC"}}>For more enquires: helpsupport@easylend.com</p>

            </Footer_Container>
            <Footer_ContainerDiv>
                <LeftDiv>
                <p style={{color:"#CCC"}}>© 2023 EasyLend. All rights reserved</p>
                </LeftDiv>
                <RightDiv>
               
                <Link to="/"><FaInstagram /></Link>
                <Link to="/"><FaTwitter /></Link>
                <Link to="/"><FaYoutube /></Link>

                </RightDiv>

            </Footer_ContainerDiv>
        
        </FooterDiv>
    )
}

export default Footer;