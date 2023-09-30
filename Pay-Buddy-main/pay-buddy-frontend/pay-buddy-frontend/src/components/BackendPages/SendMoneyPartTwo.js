import sendMoneyLogo from "../../assets/images/sendmoney-logo.svg";
import axios from "axios";
import { bankList } from "../../data/SendMoneyData";
import { json, Link,useNavigate } from "react-router-dom";
import { useContext, useState } from "react";
import { MyContext } from "../../statemanagement/ComponentState";
import appApi from "../../apis/AppApi.js";
import { ToastContainer } from 'react-toastify';
import { notifyError, notifySuccess, notifyWarning } from '../notification/Toastify';
import LoadingSpin from "react-loading-spin";
import { spinnerSize , spinnerColor,spinnerNumberOfRotation } from "../../includes/Config";

const SendMoneyPartTwo = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({});
    const [amount, setAmount] = useState(null);
    const [walletPin, setWalletPin] = useState(null)
    const[isLoading, setIsLoading] = useState(false)
    const [formatAmount, setFormatAmount]= useState(null)
    
    const handleChange = (e) =>{
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    }

    const handleAmount = (e) =>{
        if(e.target.value!=""){
            let unFormatAmount = parseInt(e.target.value.replace(",",""));
            setAmount(unFormatAmount);
            setFormatAmount(unFormatAmount.toLocaleString());

        }
        else{
            setFormatAmount("");
        }
    }

    const handleSubmit = (e) =>{
        e.preventDefault();
        formData["accountName"] = bankDetails.accountName;
        formData["accountNumber"] = bankDetails.accountNumber;
        formData["bankCode"] = bankDetails.bankCode;
        formData["amount"] = amount;
        setIsLoading(true);
        sendMoney(formData);
    }

    const sendMoney =(data) =>{
        appApi.post("api/v1/wallet/sendMoney",data)
        .then(res => {
            console.log(res);
            setIsLoading(false);
            notifySuccess("Transaction successful");
            navigate("/send-money-success",{state:{amountSent:formData["amount"],beneficiary:`${formData["accountName"] }`}});
        })
        .catch(err => {
            console.log(err.response.data.error);
            setIsLoading(false);
            if(err.response.data.error){
                notifyError(err.response.data.error);
            }
            else{
                notifyError(err.response.data);
            }        
        })
    }

    let bankDetails = JSON.parse(localStorage.getItem("bankDetails"));
    let user =JSON.parse(localStorage.getItem("user"));
    console.log(bankDetails);
    if(bankDetails["accountName"]=="") navigate("/pay-buddy/send-money-1"); 

    const { pagename, setPageName } = useContext(MyContext);
    setPageName("Send Money");
    
    return ( 
        <div class="row justify-content-center align-items-center mt-5">
            <div class="col-md-5">
                <form onSubmit={handleSubmit}> 
                    <div class="payment-logo mb-3">
                    <Link to="/pay-buddy/send-money-1">
                        <img src={sendMoneyLogo} />
                    </Link>
                    <div className="mt-3 payment-info"><span>Enter your details to send money</span></div>
                    </div>
                    <div className="mb-3">
                        <div className="row border-dash">
                            <div className="col-2">From</div>
                            <div className="col-10">{`${user.firstName} ${user.lastName}`}</div>
                        </div>
                        <div className="row border-dash">
                            <div className="col-2">To</div>
                            <div className="col-10">{`${bankDetails.accountName} ${bankDetails.accountNumber}`}</div>
                        </div>
                        <div className="row border-dash">
                            <div className="col-2">Bank</div>
                            <div className="col-10">{bankDetails.bankName}</div>
                        </div>     
                    </div>
                    <div className="mb-3">
                        <label for="accountNumber" class="form-label">Note (Optional)</label>
                        <input type="text" class="form-control" id="note"  name="note" placeholder="Enter a transaction note" />
                    </div>
                    <div className="mb-3">
                        <label for="amount" class="form-label">Amount</label>
                        <input type="text" name="amount" class="form-control" value={formatAmount}
                        id="amount" placeholder="Enter an amount" onChange={handleAmount} />
                    </div>
                    <div className="mb-3">
                        <label for="walletPin" class="form-label">Pin</label>
                        <input type="password" name="walletPin" class="form-control"
                         id="walletPin" placeholder="Enter transaction pin" 
                         onChange={handleChange}  maxLength="4"/>
                    </div>

                    <div className="mb-3 button-margin">
                        <button type="submit" class="btn btn-primary c-submit-button">{ isLoading &&<LoadingSpin size={spinnerSize} primaryColor={spinnerColor} numberOfRotationsInAnimation={spinnerNumberOfRotation}/>} Pay</button>
                    </div>
                </form>
                <div className="rectangle-2">
                    <ToastContainer />
                </div>
            </div>
        </div>
     );
}
 
export default SendMoneyPartTwo;