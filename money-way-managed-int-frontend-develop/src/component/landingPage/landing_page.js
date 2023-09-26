import React, { useEffect, useState } from "react";
import logo from "../../asset/desktop logo.PNG";
import mobilelogo from "../../asset/mobile logo.PNG";
import hero from "../../asset/heroImage.PNG";
import { AiOutlineMenu } from "react-icons/ai";
import "./landing.css";
import securityFolder from "../../asset/secret-folder.svg";
import transactionArrow from "../../asset/TransactionArrow.svg";
import transaction from "../../asset/Transaction.svg";
import security from "../../asset/Security.svg";
import cashtocash from "../../asset/CardToCash.png";
import how_it_work from "../../asset/how it works.png";
import footlogo from "../../asset/footerLogo.PNG";
import { IoIosClose } from "react-icons/io";
import { BiLogoFacebook,BiLogoTwitter,BiLogoLinkedin,BiLogoGoogle } from "react-icons/bi";
import {Link} from "react-router-dom";

import { FiTwitter } from "react-icons/fi";


function CustomButton(props){
  return (<div   style={props.style} className={props.className+ " mybtn"}>
{props.children}
  </div>)
}


function MobileMenu(props){
  return (
    <div>
      {/* header */}
    <div className="menu-header">
     <span><img src={mobilelogo} /></span>
     <span ><IoIosClose onClick={props.closeMenu} className="close-icon"/></span>
    </div>
    <div className="mobile-menu-content">
    <span>Features</span>
    <span>About</span>
    <span>Contact Us</span>
    <span> <Link to="/login"> Login</Link> </span>
    <CustomButton className="mobile-button"> <Link to="/signup">Create an account</Link> </CustomButton>
    </div>
  
    </div>
  )
}



 export default function LandingPage(props){

  const [mobileMenu, setMobileMenu] = useState(false);

   const resize = ()=>{

    if(window.innerWidth> 1050){
      setMobileMenu(false)
    }
   }

  const closeMenu = ()=>{
      setMobileMenu(!mobileMenu)
  }

   useEffect(()=>{
    window.addEventListener("resize", resize);

    return ()=>{

      window.removeEventListener("resize", resize);
    }

   },)


    return (<div className="landing_paging">
        
        <div className="first_section">
        {/* landing page nav-bar */}
      <div className="nav_bar"> 
       {/* image */}
       <div className="image_landing"><img width="211px" height={"64px"} src={logo}/></div>
       <div className="menu_landing">
       <span> Home</span>
       <span>Features</span>
       <span>About</span>
       <span>Contact Us</span>
       </div>
       <div className="btns">
        <span className="mobile_menu">< AiOutlineMenu onClick={closeMenu} className="mobile-menu-icon"/></span>
        <div className="landing_buttons">
        <span className="login_landing"> <Link to="/login"> Login</Link> </span>
        <CustomButton > <Link to="/signup"> Create an account</Link></CustomButton></div>
       </div>
      {mobileMenu && <div className="mobile-menu-container">
      <MobileMenu  closeMenu={closeMenu}/>
      </div>}
      </div>
     {/* hero section */}
     <div className="hero-section">
     <div className="hero-text">
      <h1> Quick and easy payment platform for all your transaction</h1>
      <p>Save and manage all your transaction in one place, easy payment anytime and anyday</p>
      <CustomButton ><Link to="/signup"> Create an account</Link></CustomButton>
     </div>
     <div className="hero-image-div">
      <img  src={hero}/>
     </div>

     </div>
     {/* second section */}
     <div className="second-section">
      {/* text section */}
      <div>
      <div className="second-section-text">
        <h2> Get the convenience of transacting with our services</h2>
        <p> This platform was design to make sending of funds very easy and seamless</p>
      </div>
      {/* grid section */}
      <div className="second-section-grid">
        <div>
          <span><img src={securityFolder}/></span>
          <span>Keeping Secrecy</span>
        </div>
        <div>
        <span><img src={transaction}/></span>
          <span>Convenient transaction</span>
        </div>
        <div>
        <span><img src={transactionArrow}/></span>
          <span> Free transaction</span>
        </div>
        <div>
        <span><img src={security}/></span>
          <span>Security guaranteed</span>
        </div>

      </div>

     
     </div>
  

     </div>

       {/* the black side */}
       <div className="second-section-black">

        <div>

        <div className="second-section-black-image"><img src={cashtocash}  alt="picture"/></div>
        <div className="second-section-black-text">
        <h3>Choose how you want to make transfer </h3>
        <p> Simple, Secure, and Seamless</p>
        </div>
        </div>

        </div>


          {/* How it works  */}
<div className="how-it-work">

<div>

<div className="how-it-work-text">
    <h1>How it works</h1>
    <p>Discover the fascinating process behind our innovative solution.</p>
<div className="how-it-work-text-inner">
  <div>
    <span style={{display:"block"}}>STEP ONE</span>
      <h2>Sign in or Sign up your account</h2>
      <p>Join our community and experience a world of endless possibilities.</p>
  </div>
  <div>
    <span style={{display:"block"}}>STEP TWO</span>
    <h2>Click transfer on your dashboard</h2>
    <p>Take the next step </p>
  </div>
  <div>
    <span style={{display:"block"}}>STEP THREE</span>
      <h2>Select your desired band and send</h2>
      <p>Choose your favorite payment mode and transact</p>
  </div>

</div>

</div>
<div className="how-it-work-image">
<img src={how_it_work}  alt="picture"/>
</div>
</div>

</div>


{/* hear our customer section */}
<div className="hear-from-customer">
<div>
  <div className="the-header">
      <h2>Hear from our customers</h2>
      <p><em>Discover what our satisfied customers have to say about their experience with us.</em></p>

  </div>
  <div className="testimonies">
      <div>
          <p className="name">Adekola Johnson</p>
          <p className="testimony">"MoneyWay has been a game-changer for my financial management. It has made it so much easier to track my expenses and investments."</p>
      </div>
      <div>
          <p className="name">Alexis Spice</p>
          <p className="testimony">"Using the fintech app has given me greater control over my finances. I love the personalized insights and budgeting features."</p>
      </div>
      <div>
          <p className="name">Daniel Chukwuebuka</p>
          <p className="testimony">"I highly recommend MoneyWay to everyone. Its seamless integration with my bank accounts and intuitive interface have made financial planning effortless."</p>
      </div>
      <div>
          <p className="name">Gwen Chinoko</p>
          <p className="testimony">"MoneyWay has become an essential part of my financial routine. It has helped me achieve my savings goals and stay on top of my expenses."</p>
      </div>
      <div>
          <p className="name">Emem Isong</p>
          <p className="testimony">"I've never felt more in control of my finances since using MoneyWay. It's simple to use and offers valuable insights."</p>
      </div>
      <div>
          <p className="name">Ibrahim Abdul</p>
          <p className="testimony">"This fintech application has exceeded my expectations. It has truly revolutionized the way I manage and grow my money."</p>
      </div>

  </div>



</div>


</div>
{/* subscriber on our newsletter */}
<div className="subscribe-newsletter">

    <div>
        <h2>Subscribe on our Newsletter</h2>
        <p className="before-form">Stay informed and never miss an update! Join our newsletter to receive the latest news, exclusive offers, and valuable insights delivered right to your inbox.</p>
        <form className="landing-form">
            <div>
                <input type={"email"} placeholder="Email address"/>
                <button>Subscribe</button>
            </div>
        </form>
        <p className="after-form">Be part of our growing community of subscribers and gain access to exciting content and promotions. Sign up now!</p>
    </div>

</div>
{/* footer */}
<div className="landing-footer">
<div className="footer-content">
<div className="footer-logo-holder">
  <img src={footlogo} width="211px" alt="footer logo"/>
  <p><small>© 2022 All rights reserved</small></p>
</div>
<div className="footer-text">
  <div>
   <span>Home</span>
   <span>About Us</span>

   <span>Features</span>
   <span>Contact Us </span>
  </div>
  <div>
  <span>Privacy Policy</span>
  <span>Terms of Condition</span>
  <span>Legal</span>
  <span>Help</span>


  </div>
</div>

<div className="footer-icon">
  <div>
 <span> <BiLogoFacebook  style={{color:"white"}}/> </span>
 <span> <BiLogoTwitter  style={{color:"white"}}/> </span>

 <span> <BiLogoLinkedin style={{color:"white"}}/> </span>
 <span> <BiLogoGoogle style={{color:"white"}}/> </span>
  </div>
  <p>English version</p>
</div>


</div>


</div>




      </div>

    </div>);
}