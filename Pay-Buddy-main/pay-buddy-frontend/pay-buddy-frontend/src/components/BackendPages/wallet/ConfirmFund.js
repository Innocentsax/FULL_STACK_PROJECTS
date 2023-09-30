import React, { useEffect, useState } from 'react';
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

const ConfirmFund = () => {
    const {reference} = useParams()
    const navigate = useNavigate();

    useEffect(()=>{
        confirm(reference);
    },[])

    const confirm = (reference)=>{
        appApi.get(`api/v1/wallet/verifyPayment/${reference}/fundwallet`)
        .then(res => {
            console.log(res);
            navigate("/pay-buddy/dashboard");
        })
        .catch(err => {
            console.log(err);       
        });
    }
 return (
        <>
        </>
    );
}

export default ConfirmFund;
