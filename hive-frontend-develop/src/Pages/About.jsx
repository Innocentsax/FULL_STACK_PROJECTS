import React, { useEffect, useState } from 'react';
import "./CSS/About.css";
import NavBar from "../Component/NavBar";
import Footer from "../Component/Footer";
import blackman from "../Assets/blackman.png";
import NotificationBox from "../Component/NotificationBox/NotificationBox";

const About = () => {

  const [welcomeText, setWelcomeText] = useState("");

  useEffect(() => {
    const welcome = "Weelcome to Hive";
    let i = 0;
    const intervalId = setInterval(() => {
      if (i < welcome.length) {
        setWelcomeText((prevText) => prevText + welcome.charAt(i));
        i++;
      } else {
        clearInterval(intervalId);
      }
    }, 150);
    return () => clearInterval(intervalId);
  }, []);
  
  return (
    
    <div className="about-container">
      <header>
        <NavBar />
      </header>
      <div className="about-section">
        <div className="about-title">
          <h1>ABOUT US</h1>
        </div>
        <div className="set">
          <div className="about-text1">
            <h2>{ welcomeText }</h2>
            <p>
              The platform that connects taskers and doers! We believe that
              everyone has tasks they need help with, and there are always
              people willing to lend a hand. That's why we created Hive, a place
              where you can easily find someone to take care of your to-do list,
              or make some extra income by completing tasks for others.
            </p>
          </div>
          <div className="img1-container">
            <img className="img2" src={blackman} alt="Founder" />
          </div>
        </div>
        <div className="section-content">
          <div className="set2">
            <div className="about-text2">
              <h2>Our Founder</h2>
              <p>
                Our founder, Cherechi Orika, came up with the idea for Hive when
                she realized how difficult it was to find reliable and
                affordable help for everyday tasks. She envisioned a platform
                where people could connect and help each other out, whether it's
                grocery shopping, cleaning, or delivery.
              </p>
            </div>
          </div>

          <div className="set3">
            <div className="about-text3">
              <h2>Getting Started</h2>
              <p>
                With Hive, it's easy to get started. As a tasker, you can create
                a profile and post a task you need help with. You'll be able to
                specify the type of service you need, the address, and the
                service rate options. Once you've posted your task, doers can
                view it and accept it if they're interested.<br />As a doer, you can
                also create a profile and browse through all the available
                tasks. You'll be able to view the details of each task, and
                decide if you want to accept it. Once you've completed a task,
                you'll receive payment through our platform.
              </p>
            </div>
          </div>
          <div className="set4">
            <div className="about-text4">
              <h2>User Friendly Platform</h2>
              <p>
                We take pride in our user-friendly platform, designed with both
                taskers and doers in mind. Our easy-to-use dashboard makes it
                simple to manage your tasks and track your payments. And if you
                ever need help, our support team is always here to assist you.
              </p>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default About;
