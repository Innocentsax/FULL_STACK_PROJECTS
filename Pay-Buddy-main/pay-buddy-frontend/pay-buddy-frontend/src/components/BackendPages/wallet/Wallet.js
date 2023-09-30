import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import {Button} from "react-bootstrap";
import { ToastContainer } from 'react-toastify';
import axios from 'axios';
import toast from 'react-hot-toast';
import { Navigate, useNavigate, useParams } from 'react-router-dom';
import appApi from "../../../apis/AppApi";
import "../TransactionPin.css";
import { loadAuth2WithProps } from 'gapi-script';
import LoadingSpin from "react-loading-spin";
import { spinnerSize,spinnerColor,spinnerNumberOfRotation } from "../../../includes/Config";

function Wallet(props) {
    const navigate= useNavigate();
    const [amount, setAmount] = useState("");
    const[isLoading, setIsLoading] = useState(false)
    const[formattedAmount, setFormmattedAmount] = useState(null);


    const handleChange = (e) =>{
        if(e.target.value!=""){
            let unformattedAmount = parseInt(e.target.value.replace(",",""));
            setAmount(unformattedAmount);
            setFormmattedAmount(unformattedAmount.toLocaleString());
        }
        else{
            setFormmattedAmount("");
        }
       
    }

    const loadWallet = (data) =>{
        appApi.post(`api/v1/wallet/fundWallet?amount=${amount}`)
        .then(res => {
            const payStackFundWalletUrl= res.data;
            window.location.href=payStackFundWalletUrl
            setIsLoading(false);
        })
        .catch(err => {
            console.log(err);
            setIsLoading(false);
        });
    }

    function handleSubmit(e){
        e.preventDefault();
        setIsLoading(true);
        loadWallet(amount);
    }



 return (
        <>
            <Modal show={props.open} onHide={props.handleClose}>
                {/* <Modal.Header closeButton>
                </Modal.Header> */}
                <Modal.Body>
                <form onSubmit={handleSubmit}>
                <div className='container_modal mt-1' >
                    <div> 
                        <h3 className="create-transaction-h3" style={{fontWeight: "bold"}}>Fund Account</h3>
                        {/* <p className="secured-transaction-p_tag">
                            Load Wallet <br></br>secured transaction
                        </p> */}
                    </div>
                        <div className="mb-3" style={{fontWeight: "bold"}}>
                            {/* <label htmlFor="username" className="form-label">Create Pin</label> */}
                            <input type="text" className="form-control" id="amount" value={formattedAmount}
                                name="amount" placeholder="Enter Amount"  onChange={handleChange} required/>
                        </div>
    
                        <div className="mb-3 mt-5">
                        <button className="btn btn-primary" type="submit"> { isLoading &&<LoadingSpin  size={spinnerSize} primaryColor={spinnerColor} numberOfRotationsInAnimation={spinnerNumberOfRotation}/>} Load Wallet</button>
                    </div>
                    </div>
                </form>
               
                </Modal.Body>
            </Modal>
        </>
    );
}

export default Wallet;