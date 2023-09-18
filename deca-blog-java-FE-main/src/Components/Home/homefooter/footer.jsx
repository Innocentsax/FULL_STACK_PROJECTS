import React from "react";
import { Link } from "react-router-dom";
import logo from "../../../assets/footerLogo.png";
import Logo from "../../../common/Logo";
import { FooterStyle } from "./footer.style";

export default function Footer() {
  return (
    <FooterStyle>
      <div class="footermini">
          <span className="span">
          <Logo/>
          </span>
          <p className="landingfooterText foot">
            DecaBlog is platform for budding software developers at Decagon to
            explore technical writing and leverage the community to ask insightful
            questions and get answers - to connect and to help one another achieve
            the world-class developer dream
          </p>

          <p className="foot footlast">Copyright ©2022. All rights reserved. Privacy & Terms.</p>
      </div>
    </FooterStyle>
  );
}
