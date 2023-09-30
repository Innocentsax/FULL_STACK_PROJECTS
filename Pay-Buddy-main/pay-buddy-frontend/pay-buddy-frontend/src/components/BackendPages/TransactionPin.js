import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import {Button} from "react-bootstrap";
import { ToastContainer } from 'react-toastify';
import {useNavigate, useParams } from 'react-router-dom';
import appApi from "../../apis/AppApi";
import "./TransactionPin.css";
import LoadingSpin from "react-loading-spin";
import { notifyError, notifySuccess, notifyWarning } from '../notification/Toastify';

function TransactionPin(props) {
    const [createPin, setCreatePin] = useState("");
    const [confirmCreatePin, setConfirmCreatePin] = useState("");
    const [isError, setIsError] = useState(false)
    const [errorMessage, setErrorMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [formData, setFormData] = useState({});

    let isPinUpdated = localStorage.getItem("isPinUpdated");
    if(isPinUpdated =="false"){
        isPinUpdated =false;
    }

    const handleChange = (e) =>{
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    }


const TransactionPinInput = async(e)=>{
    e.preventDefault()
   if(createPin !== confirmCreatePin){
    setIsError(true)
    setErrorMessage("Pin do not match")
   }else{
    setIsLoading(true);

    if(!isPinUpdated){
        formData["oldPin"]="0000";
    }
    console.log(formData);
    appApi.put("/api/v1/wallet/updateWalletPin",formData)
    .then(res => {
        console.log(res);
        notifySuccess("Pin updated");
        setCreatePin("");
        setConfirmCreatePin("");
        setIsLoading(false);
    })
    .catch(err =>{
        console.log(err);
        notifyError("Error");
    });
 };
}
    return (
        <>
            <Modal show={props.open} onHide={props.handleClose}>
                <Modal.Body>
                <form onSubmit={TransactionPinInput}>
                <div className='container_modal mt-1' >
                    <div> 
                        <h3 className="create-transaction-h3" style={{fontWeight: "bold"}}>{isPinUpdated && <span>Update</span> }{!isPinUpdated && <span>Create</span> }Transaction Pin</h3>
                        <p className="secured-transaction-p_tag">
                        {isPinUpdated && <span>Update</span> }{!isPinUpdated && <span>Create</span> } a transaction pin to be able to make a <br></br>secured transaction
                        </p>
                    </div>
                    {isPinUpdated &&
                        <div className="mb-3" style={{fontWeight: "bold"}}>
                            <label htmlFor="username" className="form-label">Old Pin</label>
                            <input maxLength="4" type="password" className="form-control form-control-c"
                             id="username"  onChange={handleChange}
                            name="oldPin" placeholder="4 digit transaction pin" required/>
                        </div>
                    }
                        <div className="mb-3" style={{fontWeight: "bold"}}>
                            <label htmlFor="username" className="form-label">New Pin</label>
                            <input maxLength="4" type="password" className="form-control form-control-c" 
                            id="username" onChange={handleChange}
                             name="newPin" placeholder="4 digit transaction pin" required/>
                        </div>
                        <div className="mb-3" style={{fontWeight: "bold"}}>
                            <label htmlFor="username" className="form-label form-label-c">Confirm Pin</label>
                            <input maxLength="4" type="password" className="form-control 
                            form-control-c" id="username"
                            onChange={handleChange}
                            name="confirmNewPin" placeholder="4 digit transaction pin" required/>
                        </div>
                        {isError && <div style={{color: "red"}}>{errorMessage}</div>}
                        <div className="mb-3 mt-5">
                        <button className="btn btn-c btn-primary c-submit-button" onClick={TransactionPinInput}> { isLoading &&<LoadingSpin size="40px" color="white" numberOfRotationsInAnimation={3}/>}{isPinUpdated && <span>Update</span> }{!isPinUpdated && <span>Create</span> }</button>
                    </div>
                    </div>
                </form>
                
                </Modal.Body>
            </Modal>
            <div className="rectangle-2">
                <ToastContainer />
      </div>
        </>
    );
}

export default TransactionPin;