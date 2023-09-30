import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import {Button} from "react-bootstrap";
import axios from 'axios';
import toast from 'react-hot-toast';

function Email(props) {
     const [email, setEmail] = useState("");
     const [isClicked, setIsClicked] = useState(true)
const emailInput = async(e)=>{
    e.preventDefault()
    setIsClicked(false)
    try {
        const {data} = await axios.post("http://127.0.0.1:8080/api/v1/auth/forgot-password", {email});
        if(data?.error){
            console.log(data.error)
            toast.error(data.error)
        }else{
            toast.success("Check your email for password reset instructions")
            if(toast.success){
                setEmail("")
            }
        }
    } catch (error) {
        console.log(error)
    }
};
    return (
        <>
            <Modal show={props.open} onHide={props.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Email</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="mb-3">
                        <label htmlFor="username" className="form-label form-label-c">Email address</label>
                        <input type="email" className="form-control form-control-c" id="username" value={email} onChange={(e)=> {
                            setIsClicked(true)
                            setEmail(e.target.value)
                        }
                        }
                               name="username" placeholder="name@companyemail.com"/>
                    </div>
                    <div className="mb-3 mt-3">
                         <button className="btn btn-primary c-submit-button btn-c" style={{cursor: `${isClicked ? "pointer" : "not-allowed"}`}} onClick={emailInput}>Send</button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default Email;