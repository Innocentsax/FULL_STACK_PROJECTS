import React from 'react'
import NavBar from "../../components/Pages/layout/header/Header";
import person from '../../assets/african-american-man-paying-with-credit-card-online-while-making-orders-via-mobile-internet-making-transaction-using-mobile-bank-application.jpeg'
import Footer from '../Pages/layout/footer/Footer';
import collectPayment from '../../assets/images/Collect-Payments-for-Multiple.gif';
import Wallet from '../../assets/icons/Wallet.svg'
import Data from '../../assets/icons/Data.svg'
import Electricity from '../../assets/icons/Electricity.svg'
import Airtime from '../../assets/icons/Payment.svg'
import "./Home.css";
import { Link } from 'react-router-dom';

const Home = () => {
  const fluidWidth = {width:"100%"}
  return (
    <div class="showcase-area">
      <NavBar />
      <div className='container mt-5 mb-5 shadow-sm p-3 mb-5 rounded extra-margin'>
      <div className="banner">
        <img src={person}  className="img-fluid" />
      </div>
      <div className="row mt-5">
        <div className="col-md-6">
        <h1 className='display-5 bold-heading'><b>The Future is here</b></h1>
      <p class="lead">Bank with PAY-BUDDY! </p>
        </div>
     
    </div>

    <div class="row mt-5 ">
      <div className="col-md-6">
          <img src={collectPayment} alt="MultiplePayment" className='img-fluid'/>
      </div>
      <div className="col-md-6">
      <h6 className='display-6 bold-heading'>
      <b>We offer payments and utility services nationwide</b>
      </h6>
      <p className='lead text-justify'>
      Welcome to our online payment platform! With our secure and easy-to-use system, you can make payments quickly and conveniently from anywhere in the world. All transactions are processed in real-time, ensuring that your payments are processed quickly and efficiently.
      </p>

      <div className="row mt-5 services">
        <div className="col-md-6">
        <div class="shadow-sm p-3 mb-5 bg-body rounded">
          <h5><img src={Wallet}></img> &nbsp;Send Money</h5>
          <p className="lead">Send money instantly to family and friends without any hidden charges.</p>
        </div>
        </div>
        <div className="col-md-6">
        <div class="shadow-sm p-3 mb-5 bg-body rounded">
          <h5><img src={Airtime}></img> &nbsp;Buy Airtime</h5>
          <p className="lead">Never run out of airtime to reach important people in your life.</p>
          </div>
        </div>
        <div className="col-md-6">
        <div class="shadow-sm p-3 mb-5 bg-body rounded">
          <h5><img src={Data}></img> &nbsp;Buy Data</h5>
          <p className="lead">Data is life! Buy instant data to stay connected to your world. Get a 20% discount...</p>
          </div>
        </div>
        <div className="col-md-6">
        <div class="shadow-sm p-3 mb-5 bg-body rounded">
          <h5><img src={Electricity}></img> &nbsp;Buy Electricity</h5>
          <p className="lead">Never run out of power! Prepaid and postpaid services available</p>
          </div>
        </div>
      </div>
       <center>
          <a href="/login" class="btn">Get started</a>
        </center>
      </div>
    </div>

      </div>

          <Footer />
        </div>
        
  )
}

export default Home