import React from 'react'
import "./Footer.css"

export default function Footer() {
  return (
    <div className="footer_section">
      <div className="footer_section_top">
        <div className="footer_section_top1">
          <div className="footer_img_section">
          <a href="home">
                    <img src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e808d31a7eca1775993ae82_Black.svg" alt="Fitnesso" />
                </a>
          </div>
          <div className="footer_social">
            <a href=""><div ><img className="img-tag1" src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e830050cf9bc2c348479b60_youtube.svg" alt="" /></div></a>
            <a href=""><div ><img className="img-tag1" src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e83002e40b6b8486e320705_instagram.svg" alt="" /></div></a>
            <a href=""><div ><img className="img-tag1" src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e82ffa4097a9061280e5e5a_004-linkedin-logo-button.svg" alt="" /></div></a>
            <a href=""><div ><img className="img-tag1" src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e82ffa4871a992216e6ceb9_001-facebook-logo-button.svg" alt="" /></div></a>
          </div>
        
        </div>
        <div className="footer_section_top2">
          <p className="footer_contact">
            CONTACT
          </p>
          <a href=""><p className="footer_mail">
            contact@fitnesso.com
          </p></a>
          <p className="footer_explore">
            LICENCING
          </p>
          <a href=""><p className="footer_link">
          Copyright Flow Ninja
          </p></a>
          <a href=""><p className="footer_link">
          Powered By Webflow
          </p></a>
          <a href=""><p className="footer_link">
            Licencing
          </p></a>
          <a href=""><p className="footer_link">
            Support
          </p></a>
        </div>
        <div className="footer_section_top3">
          <p className="footer_explore">
            EXPLORE
          </p>
          <a href=""><p className="footer_link">Products</p></a>
          <a href=""><p className="footer_link">Services</p></a>
          <a href="/blog"><p className="footer_link">Blog</p></a>
          {/*<BlogPost/>Blog*/}
          <a href=""><p className="footer_link">Contact Us</p></a>
        </div>
      </div>
      <div className="footer_section_bottom">
         <div ><img className="img-tag" src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e83076eb74fcd72dd21dc7b_paypal.svg" alt="" /></div>
         <div ><img className="img-tag" src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e830558ee6a95eb87d66778_Visa_Inc._logo.svg" alt="" /></div>
         <div ><img className="img-tag" src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e8306bc89fedf3fef441b48_amex-logo.svg" alt="" /></div>
         <div ><img className="img-tag" src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e8305e9097a90332d0e6f79_mc_symbol.svg" alt="" /></div>
         <div ><img className="img-tag" src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e8304eb6ceb87f7281f6fdc_Stripe_Logo%2C_revised_2016.svg" alt="" /></div>
      </div>
    </div>
  )
}
