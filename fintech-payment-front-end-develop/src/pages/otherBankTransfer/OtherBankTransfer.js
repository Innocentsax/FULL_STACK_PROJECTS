import React, {useState, useRef, useEffect} from "react"
import { BsArrowLeft} from 'react-icons/bs';
import { Link, useNavigate } from "react-router-dom";
import Topbar from '../dashboard/components/topbar/Topbar';
import "./OtherBankTransfer.css"
import axios from 'axios';

const BASE_URL = "http://localhost:3333/api/v1";
const RESOLVE_ACCOUNT = "/transfer/otherbank-account-query";
const GET_BANKS = "/transfer/banks";
const OTHER_BANK_TRANSFER = "/transfer/other-bank"
const token = localStorage.getItem("token");

function OtherBankTransfer(){
    let [selectBank, setBank] = useState("Select bank");
    let [allBanks, setAllBanks] = useState([]);
    let [receiverName, setReceiverName] = useState("");
    let [isHidden, setIsHidden] = useState(true);
    let [bankCode, setBankCode] = useState ("");
    let [message, setMessage] = useState("");
    let [modal, setModal] = useState("close-modal");
    let [successButton, setSuccessButton] = useState("close-modal")

    let navigate = useNavigate();

    let accountNumberRef = useRef(0);
    let receiverNameRef = useRef(0);
    let amountRef = useRef(0);
    let pinRef = useRef(0);
    let narrationRef = useRef(0);

    useEffect(() => {
        (async ()=> {
            const config = {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            }
            try {
                const value = await axios.get(BASE_URL + GET_BANKS, config);
                const { data } = value;
                setAllBanks([...data])
                console.log(data);
            } catch (error) {
            

                const { response } = error;
                if (response.status === 400) {
                    setMessage(response.data.message);
                    setModal("modal-content");
                    console.log(error)
                    console.log(response.data)
                    console.log(response.status)
                }
            }
        })();
    }, [])
    const resolveReceiver = async (e) => {
        let accountNumber = accountNumberRef.current.value;
        let bankCode = e.target.value;
        setBankCode(bankCode);

        if (accountNumber.length === 0) {
            alert("Please input a valid number");
            return;
        }

        const body = {
            account_number: accountNumber,
            account_bank: bankCode
        }

        const config = {
            headers: {
                 Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        }

        try {
            const value = await axios.post(BASE_URL+RESOLVE_ACCOUNT, body, config);
            const { data } = value.data;
            setReceiverName(data.account_name);
            setIsHidden(false);
            console.log(value);
        } catch (error) {
            setMessage("Please confirm account number or/and bank");
            setModal("modal-content");
        }
    };

    function handleSubmit(e) {
        e.preventDefault();
        let accountNumber = accountNumberRef.current.value;
        let amount = amountRef.current.value;
        let pin = pinRef.current.value;
        let narration = narrationRef.current.value;
        let accountName = receiverNameRef.current.value;
        (async ()=> {
            const body = {
                accountNumber: accountNumber,
                bankCode: bankCode,
                amount: amount,
                pin: pin,
                narration: narration
            }
            const config = {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            }

            try {
                const value = await axios.post(BASE_URL + OTHER_BANK_TRANSFER, body, config);
                console.log(value);
                setSuccessButton("transfer-success");
            } 
            catch (error) {
                
                const { response } = error;
                if (response.status === 400) {
                    setMessage(response.data.message);
                    setModal("modal-content");
                    console.log(error)
                    console.log(response.data)
                    console.log(response.status)
                }
            }
        })();
    }

    function handleModal() {
        setModal("close-modal");
    }
    function transferSuccess() {
        navigate("/dashboard");
    }

    return (
        <div>
            <Topbar />
            <div className="container">
                <div className="previous-page">
                    <span>
                        <BsArrowLeft />
                    </span>
                   <Link to="/dashboard" className="goBack">Go back</Link>
                </div>
                <div className="profile-header">
                    <h5>transfer</h5>
                </div>
                <div className="transferContainer">
                    <p className="first-link"><a href="/local">Local Bank Transfer</a></p>
                    <p className="second-link"><a href="/other-transfer">Other Bank Transfer</a></p>
                </div>
                <hr className="new" />
                <div className="formWrapper">
                    <div className="formContainer">
                        <form onSubmit={handleSubmit}>
                            <label htmlFor="anumber">Account Number</label> <br />
                            <input type="text" className="anumber"  name="accountnumber" placeholder="Account number" ref={accountNumberRef}/>
                            <div className="dropdown">
                                <div className="dropdown-btn">
                                    <label htmlFor="sbank">Select Bank</label> <br />
                                    <select className="sbank" onChange={resolveReceiver} name="Select Bank">
                                    <option>{selectBank}</option>
                                {
                                    allBanks.sort((a, b) => a.name.toLowerCase() > b.name.toLowerCase() ? 1 : -1).map(bank => <option key={bank.code} value={bank.code}>{bank.name}</option>)
                                }
                                    </select>
                                </div>
                            </div>
                            <label hidden={isHidden}>Account Name</label> <br />
                            <input type="text"  value={receiverName} hidden={isHidden} disabled/>
                            <label>Amount</label> <br />
                            <input type="number" placeholder="Amount" ref={amountRef}/>
                            <label>Pin</label>
                            <input type="text"  placeholder="Pin" ref={pinRef}/>
                            <label>Narration</label>
                            <textarea  placeholder="message" ref={narrationRef}></textarea>
                        <div className="btnContainer"><button type="submit"  className="transferBtn">Send Money</button></div>
                        </form>
                    </div>
                </div>
                <div id={successButton}>
                <p>Transfer successful</p>
                <button onClick={ transferSuccess } id = "transfer-success-button">Ok</button>
            </div>
            <Modal handleModal = { handleModal } message = { message } modal = { modal }/>
            </div>
        </div>
    )
}

const Modal = (props)=> {
    return (
        <>
            <div id={props.modal} >
                <p>{ props.message }</p>
                <button id="close" onClick = {props.handleModal} > Close </button>
            </div>
        </>
    )
}
export default OtherBankTransfer