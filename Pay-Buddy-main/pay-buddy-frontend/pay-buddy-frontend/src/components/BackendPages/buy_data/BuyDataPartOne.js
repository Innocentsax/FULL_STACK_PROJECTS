import buyDataLogo from "../../../assets/images/buydata-logo.svg";
import { useNavigate} from "react-router-dom";
import appApi from "../../../apis/AppApi.js";
import { useEffect, useState } from "react";
import { notifyError ,notifySuccess, notifyWarning} from "../../notification/Toastify";
import { ToastContainer } from 'react-toastify';
import LoadingSpin from "react-loading-spin";
import { spinnerSize , spinnerColor,spinnerNumberOfRotation } from "../../../includes/Config";

const BuyDataPartOne = () => {

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
    const [dataDescription, setDataDescription] = useState(null);


//GET ALL NETWORKS
const getAllNetworks = () => {
    appApi.get("/api/v1/wallet/data-services")
    .then(res => {
        setNetworks(res.data.content)
    })
    .catch(err => console.log(err))
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
useEffect(()=>{
    getAllDataPlans(network);
},[network]);

const handleDataPlanChange = (e) =>{
    let dataVariationAndAmountAndDescription= e.target.value.split("+");
    setDataPlan(dataVariationAndAmountAndDescription[0]);
    setAmount(dataVariationAndAmountAndDescription[1])
    setDataDescription(dataVariationAndAmountAndDescription[2]);
}

const handlePhoneNumber = (e) =>{
    setPhoneNumber(e.target.value);
}
const handleWalletPin = (e) => {
    setWalletPin(e.target.value);
}

//BUY DATA PLAN 
const handleSubmit = (e) =>{
    e.preventDefault();
    setIsLoading(true);
    const data ={
        request_id: null,
        serviceID: network,
        "billersCode": "08012345678",
        variation_code: dataPlan,
        amount: amount,
        phone: "08011111111"
    }
    appApi.post(`/api/v1/wallet/buy-data-plan?pin=${walletPin}`,data)
    .then(res =>{
        console.log(res);
        setIsLoading(false);
        notifySuccess(res.data.response_description);

        const successMessage = {
            phoneNumber : phoneNumber,
            description : dataDescription
        }
        navigate("/buy-data-2",{state:successMessage});
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
                            <img src= {buyDataLogo}/>
                            <div className="mt-3 payment-info"><span>Enter your details to buy data</span></div>
                        </div>

                        <div className="mb-3">
                            <label for="phoneNumber" className="form-label">Phone Number</label>
                            <input type="text" className="form-control" required onChange={handlePhoneNumber}/>
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
                            <label htmlFor="dataPlan" className="form-label">Data Plan</label>
                            { isLoadingDataPlan &&<LoadingSpin size={spinnerSize} color={spinnerColor}
                             numberOfRotationsInAnimation={spinnerNumberOfRotation}/>}
                            {dataPlans && 
                            <select className="form-select" required onChange={handleDataPlanChange}>

                                <option value="">Select Data Plan</option>
                                {dataPlans.map(dataPlan =>(
                                <option value={`${dataPlan.variation_code}+${dataPlan.variation_amount}+${dataPlan.name}`}>{`${dataPlan.name}`}</option>
                                ))}
                            </select>

                            }
                            
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

export default BuyDataPartOne;