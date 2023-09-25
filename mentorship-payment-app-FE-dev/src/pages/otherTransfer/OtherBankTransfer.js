import React, { useState, useEffect } from "react";
import "./OtherTransfer.css";
import axios from "axios";
import { useToasts } from "react-toast-notifications";
import SelectDropdown from "../../component/SelectDropdown/SelectDropdown";
import OtherBanksTransferService from "./OtherBanksTransferService";

function OtherBankTransfer() {
  const [banks, setBanks] = useState([]);
  const [bankCode, setBankCode] = useState("");
  const [bankName, setBankName] = useState("Select Bank");
  var code = "";
  const [accountNumber, setAccountNumber] = useState([""]);
  const [userName, setUserName] = useState("");
  const [pin, setPin] = useState("");
  const [amount, setAmount] = useState("");
  const [narration, setNarration] = useState("");

  const { addToast } = useToasts();

  //call to get all banks
  const resolveOtherBanks = (code) => {
    axios({
      method: "POST",
      url: "https://mentorship-payment-app.herokuapp.com/api/v1/transfers/resolveOtherBank",
      data: {
        account_bank: code,
        account_number: accountNumber,
      },
    })
      .then((response) => {
        console.log(response.data);
        setUserName(response.data.result.data.account_name);
      })
      .catch((error) => {
        addToast("Account does not exist, confirm account details", {
          appearance: "error",
        });
        setUserName("");
      });
  };

  useEffect(() => {
    async function getOtherBanks() {
      const response = await axios.get(
        "https://mentorship-payment-app.herokuapp.com/api/v1/transfers/banks"
      );
      return response;
    }

    getOtherBanks().then((resp) => {
      setBanks([...resp.data]);
    });
  }, []);

  const bankSelectHandle = (e) => {
    code = e.target.value;
    setBankCode(e.target.value);
    let bank = "";
    for (let i of banks) {
      if (i.code === code) {
        bank = i.name;
        break;
      }
    }
    console.log(bank);
    setBankName(bank);
    console.log("acc", accountNumber, "code", code, "code selected");
    resolveOtherBanks(code);
  };

  const token = localStorage.getItem("accessToken");
  const header = { Authorization: token };
  const payload = {
    accountNumber: accountNumber,
    accountName: userName,
    amount: amount,
    pin: pin,
    narration: narration,
    bankCode: bankCode,
  };

  const submitTransfer = (e) => {
    e.preventDefault();

    console.log(bankCode);
    OtherBanksTransferService.saveTransaction(payload, header)
      .then((response) => {
        console.log(response);
        if (response.result !== null) {
          addToast("Transfer successfull!!", { appearance: "success" });
          setAccountNumber("");
          setUserName("");
          setPin("");
          setAmount("");
          setBankName("Select Bank");
          setAccountNumber("");
          setNarration("");
        } else {
          addToast(response.message, "error");
        }
      })
      .catch((error) => {
        console.log(error);
        addToast("An error occured please try again later!!", {
          appearance: "error",
        });
      });
  };

  return (
    <div className='container'>
      <p className='tx-label'>Account Number</p>
      <input
        title='Account Number'
        className='tx-input'
        placeholder='Account Number'
        onChange={(e) => setAccountNumber(e.target.value)}
        value={accountNumber}
        required
      />

      <p className='tx-label'>Select Banks</p>
      <SelectDropdown
        id='payment-select-helper'
        value={code}
        onChange={bankSelectHandle}
        name={bankName}
        dropValues={banks}
        required
      />

      <p className='tx-label'>Recipient Name</p>
      <input className='tx-input' value={userName} required disabled />

      <p className='tx-label'>Amount</p>
      <input
        title='Amount'
        type='number'
        className='tx-input'
        placeholder='Amount'
        onChange={(e) => setAmount(e.target.value)}
        value={amount}
        required
      />

      <p className='tx-label'>Pin</p>
      <input
        title='Pin'
        className='tx-input'
        type='password'
        placeholder='Pin'
        onChange={(e) => setPin(e.target.value)}
        value={pin}
        required
      />

      <p className='tx-label'>Narration</p>
      <textarea
        title='Narration'
        className='tx-input'
        placeholder='Message'
        onChange={(e) => setNarration(e.target.value)}
        value={narration}
        required
      />

      <input
        className='submit_btn'
        name='Send Money'
        value='Send Money'
        type='button'
        onClick={submitTransfer}
      />
    </div>
  );
}

export default OtherBankTransfer;
