import { useEffect, useState } from "react";
import {Link, Navigate, useNavigate,useParams} from "react-router-dom";
import "../../Pages/welcome.css";
import successScreen from "../../../assets/images/successScreen.svg";

    const AccountVerified =() =>{
    const navigate = useNavigate();
    const gotoLogin= ()=>{
        navigate("/login");
    }

    return (
        <div className="welcome__parent">
          <div className="welcome__content">
            <img src={successScreen} className="img-fluid" />
              <div className="successMessage">
                <h1>Congratulations</h1>
                <p>Your email has been verified !</p>
                <button onClick={gotoLogin}>Continue</button>
            </div>
          </div>
        </div>
      );
    
}
  
   

export default AccountVerified;