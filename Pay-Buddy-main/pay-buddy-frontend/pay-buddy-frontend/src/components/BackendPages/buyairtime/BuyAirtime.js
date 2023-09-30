
import sendMoneyLogo from "../../../assets/images/buyairtime-logo.svg";
import { useNavigate} from "react-router-dom";
import appApi from "../../../apis/AppApi.js";
import { useEffect, useState } from "react";
import { notifyError ,notifySuccess, notifyWarning} from "../../notification/Toastify";
import { ToastContainer } from 'react-toastify';
import LoadingSpin from "react-loading-spin";
import { spinnerSize,spinnerColor,spinnerNumberOfRotation } from "../../../includes/Config";

const BuyAirtimePartOne = () => {

    const[isLoading, setIsLoading] = useState(false)
    const[isLoadingDataPlan, setIsLoadingDataPlan] = useState(false)
    const [networks, setNetworks] = useState(null);
    const [dataPlans, setDataPlans] = useState(null);
    const [networkType, setNetworkType] =useState(null);

    const [phoneNumber, setPhoneNumber] = useState(null);
    const [walletPin, setWalletPin] = useState(null);
    const [network, setNetwork] = useState(null);
    const [dataPlan, setDataPlan] = useState(null);
    const [amount, setAmount] = useState(null);
    const [formatAmount, setFormatAmount]= useState(null)


//GET ALL NETWORKS
const getAllNetworks = () => {
    appApi.get("/api/v1/wallet/airtime-services")
    .then(res => {
        setNetworks(res.data.content)
    })
    .catch(err => {
        console.log(err);
    });
}

useEffect(()=>{
    getAllNetworks();

},[networkType]);


//GET ALL DATA PLANS
const getAllDataPlans = (networkType) => {
    appApi.get(`/api/v1/wallet/data-services/${networkType}`)
    .then(res => {
        console.log(res.data.content.varations);
        setDataPlans(res.data.content.varations)
        setIsLoadingDataPlan(false);
    })
    .catch(err => console.log(err))

}
const handleNetworkChange = (e) =>{
    setIsLoadingDataPlan(true);
    setNetwork(e.target.value);
}

const handlePhoneNumber = (e) =>{
    setPhoneNumber(e.target.value);
}
const handleWalletPin = (e) => {
    setWalletPin(e.target.value);
}
const handleAmount =(e)=>{
    if(e.target.value!=""){
        let unFormatAmount = parseInt(e.target.value.replace(",",""));
        setAmount(unFormatAmount);
        setFormatAmount(unFormatAmount.toLocaleString());

    }
    else{
        setFormatAmount("");
    }
}

//BUY AIRTIME 
const handleSubmit = (e) =>{
    e.preventDefault();
    setIsLoading(true);
    const data ={
        request_id: null,
        serviceID: network,
        amount: amount,
        phone: "08011111111"
    }
    appApi.post(`/api/v1/wallet/buy-airtime?pin=${walletPin}`,data)
    .then(res =>{
        console.log(res);
        setIsLoading(false);
        notifySuccess(res.data.response_description);
        navigate("/buy-airtime-success",{state:{amount:amount,phoneNumber:phoneNumber}})
    })
    .catch(err =>{
        console.log(err);
        notifyError("Internal server error " + err)
        setIsLoading(false);
    })

}

    const navigate = useNavigate();

        return (
            <div className= "row justify-content-center align-items-center mt-5">
                <div className="col-md-5">
                    <form onSubmit={handleSubmit}>
                        <div className= "payment-logo mb-3">
                            <img src= {sendMoneyLogo} alt=""/>
                            <div className="mt-3 payment-info"><span>Enter your details to buy airtime</span></div>
                        </div>

                        <div className="mb-3">
                            <label for="network" className="form-label">Network</label>
                            {networks &&
                            <select className="form-select" onChange={handleNetworkChange} required>
                            <option value="">Select Network</option>
                             {networks.map(network=> (
                             <option value={network.serviceID}>{network.name}</option>
                           ))}
                         
                            </select>
                          
                        }
                        </div>

                        <div className="mb-3">
                            <label for="phoneNumber" className="form-label">Phone Number</label>
                            <input type="text" className="form-control" required onChange={handlePhoneNumber}/>
                        </div>
                        <div className="mb-3">
                            <label for="amount" className="form-label">Amount</label>
                            <input type="text" className="form-control"  value={formatAmount}
                            required onChange={handleAmount}/>
                        </div>

                        <div className="mb-3">
                            <label htmlFor="pin" className="form-label">Pin</label>
                            <input type="password" className="form-control" required onChange={handleWalletPin} maxLength="4"/>
                        </div>

                        <div className="mb-3 button-margin">
                            <button type="submit" className="btn btn-primary proceed c-submit-button"> { isLoading &&<LoadingSpin size={spinnerSize} primaryColor={spinnerColor} numberOfRotationsInAnimation={spinnerNumberOfRotation}/>} Proceed</button>
                        </div>
                    </form>
                    <div className="rectangle-2">
                        <ToastContainer />
                    </div>
                </div>
            </div>
        );
}

export default BuyAirtimePartOne;