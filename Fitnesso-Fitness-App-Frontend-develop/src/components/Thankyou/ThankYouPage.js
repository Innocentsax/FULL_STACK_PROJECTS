import React,{useState, useEffect} from 'react'
import './Thankyou.css'
import Footer from "../Main/Footer/Footer";
import Navbar from "../Main/Navbar/Navbar";
import { Link } from 'react-router-dom'

function ThankYouPage() {
  const API_BASE_URL = "https://fitnesso-app-new.herokuapp.com";

  const [reference, setReference] = useState("");
  const queryParams = new URLSearchParams(window.location.search);
  const getReference = queryParams.get("reference");
  console.log(getReference);

  const confirmPayment = async (takeReference) => {
    console.log(tokenCheck);
    const res = await fetch(`${API_BASE_URL}/transaction/success?reference=${takeReference}`, {
      method: "GET",
      mode: "cors",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
        "Content-Type": "application/json",
      }
    }).catch(err => {console.log(err);});
    const data = await res.json();
    console.log(data);
    return data;
  };

  useEffect(() => {
    console.log(getReference);
    setReference(getReference);
    const res = confirmPayment(reference);
    console.log(res);
    console.log(res.status);
  }, [reference, getReference]);

  return (
      
    <div>
    
      <div className='thanks'>
        <div>
        <h1 className='thanks-heading'>Thank You For Your Patronage</h1> 
        <button className='thank-btn'><Link to={"/"}>Go back Home</Link></button>
        </div>
    
      </div>
       
        <Footer></Footer>
    </div>
    
  )
}

export default ThankYouPage