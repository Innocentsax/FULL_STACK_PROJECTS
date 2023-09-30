import Electricity from "../../assets/icons/Electricity.svg";
import axios from "axios";
import { json, Link,useNavigate } from "react-router-dom";
import { useContext, useState, useEffect } from "react";
import { MyContext } from "../../statemanagement/ComponentState";
import appApi from "../../apis/AppApi.js";
import { ToastContainer } from 'react-toastify';
import { notifyError, notifySuccess, notifyWarning } from '../notification/Toastify';
import LoadingSpin from "react-loading-spin";
import AppApi from "../../apis/AppApi.js";
import { spinnerColor, spinnerSize,spinnerNumberOfRotation } from "../../includes/Config";


const BuyElectricity = () => {
    const navigate = useNavigate();
    // const [formData, setFormData] = useState({});
    const [amount, setAmount] = useState(null);
    const [walletPin, setWalletPin] = useState(null)
    const[isLoading, setIsLoading] = useState(false)
    const[isLoadingMeterName, setIsLoadingMeterName] = useState(false)
    const [meterType, setMeterType] = useState("");
    const meterTypes = ["prepaid","postpaid"]
    const [billers, setBillers] = useState([]);
    const [billersIDs, setBillersIDs] = useState([]);
    const [billersAndBillersIDs, setBillersAndBillersIDs] = useState([]);
    const [selectedBiller, setSelectedBiller] = useState("");
    const [selectedBillerServiceID, setSelectedBillerServiceID] = useState("");
    const [accountName, setAccountName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [meterNumber, setMeterNumber] = useState("");
    const [requestId, setRequestId] = useState("");
    const [formatedElectAmount , setFormatedElectAmount] = useState(null);


    useEffect(() => {
        AppApi
            .get("/api/v1/wallet/electricity-services")
            .then((response) => {
                const billerNames = response.data.content.map((biller) => biller.name + "," +  biller.serviceID);
                setBillers(billerNames);
            })
            .catch((error) => console.log(error));
    }, []);

    useEffect(() => {
        if (meterNumber.length  === 10 || meterNumber.length === 13){
            setIsLoadingMeterName(true);
            getAccountName();
        }
    }, [meterNumber]);

    const getAccountName = ()=>{
        const config = {
            headers: {
                Authorization: "Basic " + btoa("haykay364@gmail.com:haykay"),
            },
        };
        const data = {
            billersCode: meterNumber,
            serviceID: selectedBillerServiceID,
            type: meterType
        };
        axios
        // appApi
            .post("https://sandbox.vtpass.com/api/merchant-verify", data, config)
            // .post("/api/v1/wallet/verify-merchant", data, config)
            .then((response) => {
                setAccountName(response.data.content.Customer_Name);
                setIsLoadingMeterName(false);
            })
            .catch((error) => console.log(error));

    }

    const handleServiceChange = (e) => {
        const seperator = e.target.value.split(",");
        setSelectedBiller(seperator[0]);
        setSelectedBillerServiceID(seperator[1]);
    };

    const handleElectricityAmount = (e)=>{
        if(e.target.value!=""){
            let unformatedElectAmount = parseInt(e.target.value.replace(",",""));
            setAmount(unformatedElectAmount);
            setFormatedElectAmount(unformatedElectAmount.toLocaleString());
        }
        else{
            setFormatedElectAmount("");
        }
    }

    const handleWalletPin = (e) =>{
        setWalletPin(e.target.value);
    }
    const handleAmount = (e) =>{
        setAmount(e.target.value);
    }

    const handleMeterType = (e) => {
        setMeterType(e.target.value);
        console.log(meterType);
    };
    const handlePhoneNumber = (e) =>{
        setPhoneNumber(e.target.value);
    }
    const handleMeterNumber = (e) => {
        setMeterNumber(e.target.value);
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        setIsLoading(true);
        const data ={

            request_id: "string",
            serviceID: selectedBillerServiceID,
            billersCode: meterNumber,
            variation_code: meterType,
            amount: amount,
            phone: phoneNumber

        }
        appApi.post(`/api/v1/wallet/buy-electricity?pin=${walletPin}`,data)
            .then(res =>{
                console.log(res);
                setIsLoading(false);
                notifySuccess(res.data.response_description);
                navigate("/buy-electricity-success",{state:{amount:amount,purchaseCode:res.data.purchased_code,
                        serviceType:res.data.content.transactions.product_name}})
            })
            .catch(err =>{
                console.log(err);
                notifyError("Internal server error " + err)
                setIsLoading(false);
            })

    }
    const { pagename, setPageName } = useContext(MyContext);
    setPageName("Buy Electricity");

    return (
        <div class="row justify-content-center align-items-center">
            <div class="col-md-5">
                <form onSubmit={handleSubmit}>
                    <div class="payment-logo mb-3">
                        <img src={Electricity} />
                        <div className="mt-3 payment-info"><span>Buy Electricity Postpaid and Prepaid</span></div>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="billers" className="form-label">Select a Biller Service:</label>
                        <select className="form-select"  onChange={handleServiceChange}>
                            <option value="">Select an electricity service</option>
                            {billers.map((service) => (<option key={service} value={service}>{service}</option>))}
                        </select>
                    </div>

                    <div className="mb-3">
                        <label htmlFor="bankName" className="form-label">Meter Type</label>
                        <select className="form-select" aria-label="Select a bank" onChange={handleMeterType} required>
                                <option value="">Please select meter type</option>
                                {meterTypes.map((meter) => (<option value={meter} key={meter}> {meter}</option>))}
                        </select>
                    </div>
                    <div className="mb-3">
                        <label for="accountNumber" class="form-label">Meter Number</label>
                        <input type="text" class="form-control" id="note"  name="note"
                               placeholder="Enter meter number" required onChange={handleMeterNumber}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="accountName" className="form-label">Account Name</label>
                        <div type="text" disabled name="accountName" className="form-control accountName">
                            {accountName} {isLoadingMeterName &&
                            <LoadingSpin size={spinnerSize} primaryColor="white" numberOfRotationsInAnimation={spinnerNumberOfRotation}/>}</div>
                    </div>

                    <div className="mb-3">
                        <label for="accountNumber" class="form-label">Phone Number</label>
                        <input type="text" class="form-control" id="note"  name="note"
                               placeholder="Enter phone number" required onChange={handlePhoneNumber}/>
                    </div>
                    <div className="mb-3">
                        <label for="amount" class="form-label">Amount</label>
                        <input type="text" name="amount" class="form-control" value={formatedElectAmount}
                               id="amount" placeholder="Enter amount" onChange={handleElectricityAmount}/>
                    </div>
                    <div className="mb-3">
                        <label for="walletPin" class="form-label">Pin</label>
                        <input type="password" name="walletPin" class="form-control"
                               id="walletPin" placeholder="Enter pin"
                               onChange={handleWalletPin}  maxLength="4"/>
                    </div>

                    <div className="mb-3 button-margin ">
                        <button type="submit" class="btn btn-primary proceed c-submit-button">
                            { isLoading &&<LoadingSpin size={spinnerSize} primaryColor={spinnerColor} numberOfRotationsInAnimation={spinnerNumberOfRotation}/>}
                            Pay</button>
                    </div>
                </form>
                <div className="rectangle-2">
                    <ToastContainer />
                </div>
            </div>
        </div>

    );
}

export default BuyElectricity;

