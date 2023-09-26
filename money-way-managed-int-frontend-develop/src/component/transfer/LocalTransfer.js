import React, {useState} from "react";
import Switch from '@mui/material/Switch';
import {useStateContext} from "../Context/ContextProvider";
import axios from "axios";
import CircularProgress from "@mui/material/CircularProgress";
import Backdrop from "@mui/material/Backdrop";
import Stack from "@mui/material/Stack";
import Collapse from "@mui/material/Collapse";
import Alert from "@mui/material/Alert";
import {IconButton} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";



const LocalTransfer = () => {
    const [accountNumber, setAccountNumber] = useState("");
    const [amount, setAmount] = useState("");
    const [pin, setPin] = useState("");
    const [description, setDescription] = useState("");
    const [saveBeneficiary, setSaveBeneficiary] = useState(false);

    const [loading, setLoading] = useState(false)

    const [apiError, setApiError] = useState(false);
    const [apiLTError, setLTApiError] = useState(false);
    const [apiErrorMessage, setApiErrorMessage] = useState("");
    const [apiLTErrorMessage, setLTApiErrorMessage] = useState("");
    const [apiSuccess, setApiSuccess] = useState(false);
    const [apiLTSuccess, setLTApiSuccess] = useState(false);
    const [apiSuccessMessage, setApiSuccessMessage] = useState("");
    const [apiLTSuccessMessage, setLTApiSuccessMessage] = useState("");


    const context = useStateContext();
    const handleChange = (event) => {
        setSaveBeneficiary(event.target.checked);
    };
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer '+ context.token
    };

    const handleVerifyAccountNumber= async (e)=>{
        const accNum = e.target.value;
        setAccountNumber(accNum);
        if(accNum.length === 10){
            await verifyAccountNumber(e, accNum)
        }
    }

    const verifyAccountNumber = async (e, accNum) =>{
        e.preventDefault()
        setLoading(true)

        await axios.get("http://localhost:9000/api/v1/transaction/verify-recipient?account-number="+accNum, { headers })
            .then(response=>{
                    setApiSuccess(true)
                    setApiSuccessMessage(response.data)
                }
            )
            .catch(error=>{
                    setApiError(true)
                setApiErrorMessage(error.response.data.errorMessage)
                }
            )
        setLoading(false)
    }

    const handleLocalTransfer= async (e) => {
        e.preventDefault()
        setLoading(true)

        await axios.post("http://localhost:9000/api/v1/transaction/local-transfer", {pin, accountNumber, description, amount, saveBeneficiary}, { headers })
            .then(response=>{
                setLTApiSuccess(true)
                setLTApiSuccessMessage(response.data)
            })
            .catch(error=>{
                console.log(error)
                setLTApiError(true)
                setLTApiErrorMessage(error.response.data.errorMessage)
            })
        setLoading(false)
    }

    return (
        <div className="overflow-y-auto mb-48">
            <form>
                {/*email*/}
                <div className = "flex flex-col">
                    <label className = "font-semibold text-sm" htmlFor="email-text-field">
                        Account Number
                    </label>
                    <input id = "email-text-field" onChange={handleVerifyAccountNumber} type = "text" placeholder = "Enter account number" className = "py-3 px-4 border mt-2 rounded-md"/>
                </div>
                {apiError &&
                    <Stack className="mt-2" sx={{ width: '100%' }} spacing={2}>
                        <Collapse in={apiError}>
                            <Alert severity="error" action={<IconButton onClick={()=>setApiError(false)}> <CloseIcon fontSize="inherit" /></IconButton>} sx={{ mb: 2 }}>
                                {apiErrorMessage}
                            </Alert>
                        </Collapse>
                    </Stack>
                }
                {apiSuccess &&
                    <Stack className="mt-2" sx={{ width: '100%' }} spacing={2}>
                        <Collapse in={apiSuccess}>
                            <Alert severity="success" variant="outlined" action={<IconButton onClick={()=>setApiSuccess(false)}> <CloseIcon fontSize="inherit" /></IconButton>}  iconMapping={{
                                success: <CheckCircleOutlineIcon fontSize="inherit" />,
                            }} sx={{ mb: 2 }}>
                                {apiSuccessMessage}
                            </Alert>
                        </Collapse>
                    </Stack>
                }

                {/*amount*/}
                <div className = "flex flex-col mt-4">
                    <label className = "font-semibold text-sm" htmlFor="amount-text-field">
                        Amount
                    </label>
                    <input id = "amount-text-field" type = "text" onChange={(e)=>setAmount(e.target.value)} placeholder = "Enter an amount" className = "py-3 px-4 border mt-2 rounded-md"/>
                </div>

                {/*pin*/}
                <div className = "flex flex-col mt-4">
                    <label className = "font-semibold text-sm" htmlFor="pin-text-field">
                        Pin
                    </label>
                    <input id = "pin-text-field" type = "text" placeholder = "Enter pin" onChange={(e)=>setPin(e.target.value)} className = "py-3 px-4 border mt-2 rounded-md"/>
                </div>

                {/*description*/}
                <div className = "flex flex-col mt-4">
                    <label className = "font-semibold text-sm" htmlFor="description-text-area">
                        Description
                    </label>
                    <textarea id = "pin-text-field" onChange={(e)=>setDescription(e.target.value)} placeholder = "Write a short description" className = "py-3 px-4 border mt-2 rounded-md">
                    </textarea>
                </div>
                {/*save as beneficiary*/}
                <div className = "flex mt-4 items-center justify-between">
                    <div>
                        <p className = "text-indigo-700 text-sm">Save as Beneficiary</p>
                    </div>
                    <div>
                        <Switch
                            checked={saveBeneficiary}
                            onChange={handleChange}
                            inputProps={{ 'aria-label': 'controlled' }}
                        />
                    </div>
                </div>
                <Backdrop
                    sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                    open={loading}
                >
                    <CircularProgress color="inherit" />
                </Backdrop>
                {apiLTError &&
                    <Stack className="mt-2" sx={{ width: '100%' }} spacing={2}>
                        <Collapse in={apiLTError}>
                            <Alert severity="error" action={<IconButton onClick={()=>setApiError(false)}> <CloseIcon fontSize="inherit" /></IconButton>} sx={{ mb: 2 }}>
                                {apiLTErrorMessage}
                            </Alert>
                        </Collapse>
                    </Stack>
                }
                {apiLTSuccess &&
                    <Stack className="mt-2" sx={{ width: '100%' }} spacing={2}>
                        <Collapse in={apiLTSuccess}>
                            <Alert severity="success" variant="outlined" action={<IconButton onClick={()=>setApiSuccess(false)}> <CloseIcon fontSize="inherit" /></IconButton>}  iconMapping={{
                                success: <CheckCircleOutlineIcon fontSize="inherit" />,
                            }} sx={{ mb: 2 }}>
                                {apiLTSuccessMessage}
                            </Alert>
                        </Collapse>
                    </Stack>
                }
                {/*continue button*/}
                <div className = "mt-10">
                    <button className = "text-center w-full py-3 px-4 rounded-md bg-indigo-700 text-sm font-semibold text-white" onClick={handleLocalTransfer}>Continue</button>
                </div>
            </form>
        </div>
    )
}

export default LocalTransfer;