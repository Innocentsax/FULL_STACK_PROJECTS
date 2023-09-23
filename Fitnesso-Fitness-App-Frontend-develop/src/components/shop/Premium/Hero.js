import React from "react";
import "./Top.css";
// import styled, { css } from 'styled-components';
import Typical from 'react-typical'


const Hero = () => {
  return (
    // <Top>
    <div className="fg1">
      <section>
        <div className = "bgImg">
          <div style={{ color: "#fff", fontSize: "70px" }} className="bg2">
            <div className="premium"><h4>
              <Typical
              loop={Infinity}
              steps={[
                "Premium Programs", 1500,
                "Daily Programs", 1500,
                "Home Workouts", 1500,
              ]}
              />
              </h4></div>
          </div>
        </div>



      </section>

      <section>
          <div className="feg">
          
            <button className="e" onClick = {() => window.location.href='/product' }>VIEW PRODUCTS</button>
            {/* <button className="f">NUTRITION</button>
            <button className="g">TRAINING</button> */}
          </div>
      </section>
    </div>
  );
};

export default Hero