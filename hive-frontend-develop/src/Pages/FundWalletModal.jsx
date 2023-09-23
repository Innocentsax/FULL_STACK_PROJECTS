import "../Pages/CSS/FundWalletModal.css";
import WalletService from "../service/WalletService";
import { useState } from "react";
import {useNavigate} from "react-router-dom";





function FundWalletModal(props) {
    const navigate = useNavigate();
    //create state to hold the response from the server
    //create state to hold the request to the server
    const [amount, setData] = useState (  "" );
    const [apiResponse, setApiResponse] = useState("");

    // Create JSON data object
    const requestData = {
        amount: amount
    };


    //get token from local storage to use in the header callback
    const token = localStorage.getItem("token");


    const handleSubmit = (event) => {

        event.preventDefault();
        WalletService.initializePaymentAndGetUrl(requestData,token)
            .then((response) => {
                setApiResponse(response.data.result);
                console.log(response);
                    // Redirect to the authorization URL
                window.open(response.data.result.data.authorization_url, "_blank");

                //Navigate to the wallet page
                if (response.status === 200) {
                    navigate("/wallet");
                }
                })
            .catch((error) => {
                console.log(error);
            });
    };

    const handleModalClose = () => {
        // Call the onClose callback function passed from parent
        props.onClose();
    }
    return (
       <div className="center-modal">
           <div className="payment-modal-payment-modal" style={props.style}>
               <div className="payment-modal-frame-9015x">
                   <p className="payment-modal-text">Fund Wallet</p>
               </div>
               <form className= "payment-modal-frame-8912x"
                     method="POST"
                     onSubmit={handleSubmit}
                     autoComplete={"off"}>

                       <div className="payment-modal-frame-9016x">
                           <div className="payment-modal-frame-8911x">
                               <div className="payment-modal-frame-8858x">
                                   <p className="payment-modal-text-3">Enter Amount</p>
                                   <input
                                       type="text"
                                       className="payment-modal-input-1"
                                       id="amount"
                                       name="amount"
                                       placeholder="Amount in Digits"
                                       value={amount}
                                       onChange={(event) => setData(event.target.value)}
                                   />
                               </div>
                           </div>
                       </div>

                   <button type="submit" className="payment-modal-button-default">
                       Make Payment via Paystack
                   </button>
                   <div type="" className="payment-modal-button-default2" onClick={handleModalClose}>
                       Close
                   </div>
               </form>
           </div>
       </div>
    );
}
FundWalletModal.defaultProps = {
    className: "",
    style: {},
};

export default FundWalletModal;

