import sendMoneyLogo from "../../assets/images/sendmoney-logo.svg";
//import { bankList } from "../../data/SendMoneyData";
import { useNavigate } from "react-router-dom";
import { useContext, useState, useEffect } from "react";
import { MyContext } from "../../statemanagement/ComponentState";
import appApi from "../../apis/AppApi.js";
import LoadingSpin from "react-loading-spin";
import { spinnerSize,spinnerColor,spinnerNumberOfRotation } from "../../includes/Config";

const SendMoneyPartOne = () => {

    const[isLoading, setIsLoading] = useState(false)
    const [isDisabled , setIsDisabled] = useState(true);
    const navigate = useNavigate();
    const [bankLists, setBankLists] =useState("");
    const [accountName, setAccountName] = useState("");
    const [accountNumber, setAccountNumber] = useState('');
    const [bankCode, setBankCode] = useState(null)
    const [bankName, setBankName] = useState(null)
    const { pagename, setPageName} = useContext(MyContext);
    setPageName("Send Money");

    const bankListCallCount = 1;
    //BANK LIST
    useEffect(() =>{
        appApi.post("api/v1/wallet/getBankDetails")
        .then(response => {
           setBankLists(response.data)
        })
        .catch(err => console.log(err));   
    },[bankListCallCount])

     //ACCOUNT NUMBER
     let bankAccountDigit=1;
     const handleAccountNumber =(e) =>{
        setAccountNumber(e.target.value);
       console.log(e.target.value)
     }

     //ACCOUNT NAME  FUNCTION CALL
    useEffect(()=>{
        if (accountNumber.length===10){
            setIsLoading(true);
            getAccountName();
        }      
        
    },[accountNumber])

     //ACCOUNT NAME IMPLEMENTATION
     const getAccountName = ()=>{
        appApi.post(`api/v1/wallet/verifyAccountNumber?accountNumber=${accountNumber}&bankCode=${bankCode}`)
        .then(response => {
            console.log(response)
           setAccountName(response.data)
           setIsLoading(false);
           setIsDisabled(false);

            let bankDetails ={
                accountName:response.data,
                accountNumber:accountNumber,
                bankCode: bankCode,
                bankName : bankName
            }
            console.log(bankDetails);
          localStorage.setItem("bankDetails", JSON.stringify(bankDetails));
           
        })
        .catch(err => console.log(err));

    }

    const handleSubmit = (e) => {
        e.preventDefault();
        navigate("/pay-buddy/send-money-2");

    }
    const handleBankCode = (e) =>{
        let bankcodeAndName= e.target.value.split("+")
        setBankCode(bankcodeAndName[0]);
        setBankName(bankcodeAndName[1])
    }
    
    return ( 
        <div class="row justify-content-center align-items-center mt-5">
            <div class="col-md-5">
                <form onSubmit={handleSubmit}>
                    <div class="payment-logo mb-3">
                    <img src={sendMoneyLogo} />
                    <div className="mt-3 payment-info"><span>Enter your details to send money</span></div>
                    </div>
                    {bankLists &&
                    
                    <div class="mb-3">
                        <label for="bankName" class="form-label">Bank Name</label>
                        <select class="form-select" aria-label="Select a bank"
                         required onChange={handleBankCode}>
                            {bankLists.map((bank)=>
                                <option key={bank.code} value={`${bank.code}+${bank.name}`}>{bank.name}</option>
                            )}
                              
                        </select>
                    </div>}
                          
                    <div class="mb-3">
                        <label for="accountNumber" class="form-label">Account Number</label>
                        <input type="text" class="form-control" id="accountNumber"
                          value ={accountNumber} onChange={(e)=>handleAccountNumber(e)} placeholder="2211221111"
                         required/>
                    </div>
                    <div class="mb-3">
                        <label for="accountName" class="form-label">Account Name</label>
                        <div type="text" disabled name="accountName" class="form-control accountName" id="accountName">
                            {accountName} { isLoading &&<LoadingSpin size={spinnerSize} primaryColor={spinnerColor} numberOfRotationsInAnimation={spinnerNumberOfRotation}/>}</div>
                    </div>
                    <div class="mb-3 button-margin">
                        <button type="submit" className="btn btn-primary proceed c-submit-button" disabled={isDisabled}>Proceed</button>
                    </div>
                </form>
            </div>
        </div>
     );
}
 
export default SendMoneyPartOne;