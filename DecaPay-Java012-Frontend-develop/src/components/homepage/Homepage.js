import React from "react";
import "./Homepage.css";
import {Link} from "react-router-dom";

function Homepage(){
    return (
        <div class="homepage-decapay-JFj">
  <div class="frame-14-3DK">
    <div class="frame-15-ktR">
      <img class="cardpayment-Juw" src="./assets/cardpayment-nn5.png"/>
      <p class="decapay-36q">DecaPay</p>
    </div>
    <div class="frame-17-9vZ">
      <div class="auto-group-75ah-WFK">
        <Link to='/' class="home-2zM">Home</Link>
        <div class="how-it-works-nCq">How It Works</div>
        <div class="contact-us-vZw">Contact Us</div>
      </div>
      <Link to='/login' class="frame-16-GNu">Get Started </Link>
    </div>
  </div>
  <div class="frame-20-wV3">
    <div class="frame-19-TTP">
      <div class="frame-18-yRj">
        <div class="effectively-manager-your-budget-XTF">
        Effectively manage 
        <br/>
        your budget
        </div>
        <div class="the-platform-gives-a-personal-budget-plan-and-how-you-can-spend-your-money-e25">
        The platform gives a personal budget plan and how
        <br/>
        you can spend your money.
        </div>
      </div>
      <Link to='/login' class="frame-16-9Dj">Get Started</Link>
    </div>
    <img class="budget-management-Eky" src="./assets/budget-management.png"/>
  </div>
  <div class="frame-29-Mqb">
    <div class="frame-21-VB7">
      <div class="spend-your-budgets-meaningfully-2Rw">Spend your budgets meaningfully</div>
      <div class="understand-how-you-spend-and-track-budget-wYu">Understand how you spend and track budget</div>
    </div>
    <div class="frame-28-GbB">
      <div class="frame-23-QhP">
        <div class="frame-22-Z4V">
          <img class="budget-estimation-tMf" src="./assets/budget-estimation.png"/>
          <div class="calculate-your-budget-JRP">Calculate your budget</div>
        </div>
      </div>
      <div class="frame-25-bfP">
        <div class="frame-24-Lss">
          <img class="receipt-uAH" src="./assets/receipt.png"/>
          <div class="generate-monthly-receipt-ReR">Generate monthly receipt</div>
        </div>
      </div>
      <div class="frame-26-w6y">
        <div class="frame-27-tH7">
          <img class="delivery-tracking-qy3" src="./assets/delivery-tracking.png"/>
          <div class="easily-track-budget-mbo">Easily track budget</div>
        </div>
      </div>
    </div>
  </div>
  <div class="frame-12-5Mb">
    <div class="frame-30-CBK">
      <img class="group-XjP" src="./assets/group-cfK.png"/>
      <p class="decapay-fah">DecaPay</p>
    </div>
    <div class="divider-CqX">
    </div>
    <div class="auto-group-mn7s-YPb">
      <p class="tutorbuddy-all-rights-reserved-6R7">© 2022 TutorBuddy. All rights reserved</p>
      <div class="social-links-bMs">
        <img class="social-icons-white-instagram-jDB" src="./assets/social-icons-white-instagram.png"/>
        <img class="social-icons-white-twitter-sKP" src="./assets/social-icons-white-twitter.png"/>
        <img class="social-icons-white-youtube-Qq7" src="./assets/social-icons-white-youtube.png"/>
      </div>
    </div>
  </div>
</div>
    );
}

export default Homepage;