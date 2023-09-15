import React from "react";
import classes from "./About-us.module.css";
import mainimage from "../../img/poster-above-grey-couch-in-minimal-living-room-interior-with-lam-e1606471719809.jpg";
import signature from "../../img/name-1536x201.png";
import bottomimage from "../../img/interior-of-modern-living-room-with-sofa-3-d-rendering-e1606471745983.jpg";
import Breadcrumb from "./Breadcrumb";
import AboutHero from "./About-hero";
import FindYourFurniture from "./FindYourFurniture";
import Faq from "./Faq";
import Discount from "./Discount";
import Admin from "../admin/Admin";

const AboutUs = () => {
  return (
    <div className={classes.about_us_container}>
       <Breadcrumb classes={classes}/>
        <AboutHero classes={classes} signature={signature} mainimage={mainimage}/>
        <FindYourFurniture classes={classes}/>
        <Faq classes={classes} bottomimage={bottomimage}/>
        <Discount classes={classes}/>
</div>
  );
}
export default AboutUs;