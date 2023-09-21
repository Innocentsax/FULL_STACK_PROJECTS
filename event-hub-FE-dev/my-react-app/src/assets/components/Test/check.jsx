import styled from "styled-components";
import Footer from "../HomePage/Footer/Footer";
import Header from "../HomePage/Header/Header";
import { UpcomingEvents, EvnetsBody, Fieldset, Select, Setup, FormAccount, MessageResponse, Details, ButtonForm, DiviNFO, AccountNFO, Preloader, Loader, Button } from "../Styled/Styled";
import { Link } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import swal from "sweetalert";
import preloader from '../CreateAccount/image/preloader.gif'

const PaymentSetup = () => {
  const TOKEN = localStorage.getItem("TOKEN");

  if (TOKEN == null) {
    window.location.replace("/login");
  }
  
  const [banks, setBanks] = useState([]);
  const [bankName, setBankName] = useState("");
  const [accountNumber, setAccountNumber] = useState("");
  const [accountName,setAccountName] = useState("")
  const [loading,setLoading] = useState(false)
  
  useEffect(() => {
    axios.get("https://api.paystack.co/bank")
      .then(response => {
        console.log(response.data.data);
        setBanks(response.data.data);
      });
  }, []);
 


  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(TOKEN);
    try {
      setLoading(true);
      const url = 'http://localhost:8999/api/v1/bank/getName';
      axios.get(url, {
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Authorization': `Bearer ${TOKEN}`
        },
        params: {
          bankName: bankName,
          accountNumber: accountNumber
        }
      })
      .then(response => {
        const data = response.data;
        console.log(data);
        setLoading(false);
        setAccountName(data.data[0].data.account_name);
      })
      .catch(error => {
        setLoading(false);
        console.log(error);
      });
    } catch(e) {
      swal("ALERT", "Try Again Later", "error");
      setLoading(false);
    }
  }  
  const saved = () => {
    alert(1);
    try {
      setLoading(true);
      const obj = {
        bankName: bankName,
        accountNumber: accountNumber,
        accountName: accountName
      };
      axios.post("http://localhost:8999/api/v1/bank/create-account", obj, {
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Authorization': `Bearer ${TOKEN}`
        }
      })
      .then(response => {
        const data = response.data;
        console.log(data);
        swal("ALERT",data.message,"success")
        setLoading(false);
      })
      .catch(error => {
        setLoading(false);
        swal("ALERT","Try Again","error")
        console.log(error);
      });
    } catch (error) {
      console.log(error);
    }
  };
  
  
    
  return (
    <>
    {(loading) ?
      <Preloader>
        <Loader src={preloader}></Loader>
      </Preloader>
      
      : null}
      <Header />

      <UpcomingEvents>
        <EvnetsBody>
          <Setup>
            <Details>
              <DiviNFO>
                <h1>Payment information</h1>
                <p>Please input all information correctly</p>
              </DiviNFO>
              <AccountNFO>
                <Link to="/app/account">View Account Information</Link>
              </AccountNFO>
            </Details>
            <FormAccount onSubmit={handleSubmit}>
              <Fieldset>
                <legend>Bank</legend>
                <Select name="bank" onChange={(e) => setBankName(e.target.value)} value={bankName}>
                  {banks.map((val, index) => (
                    <option key={index} value={val.name}>{val.name}</option>
                  ))}
                </Select>
              </Fieldset>
              <Fieldset>
                <legend>Account Number</legend>
                <input
                  type="text"
                  name="accountnumber"
                  onChange={(e) => setAccountNumber(e.target.value)}
                  value={accountNumber}
                />
              </Fieldset>
             {(accountName!="") ? <MessageResponse>{accountName}
             <input type="hidden"   onChange={(e) => setAccountName(e.target.value)} value={accountName} />
             
             </MessageResponse> :  <ButtonForm style={{ background: "#003366", cursor: "pointer" }}>
                Confirm
              </ButtonForm>} 
              
            
              {(accountName!="") ? <Button onClick={saved}>Saved</Button> :""}
            </FormAccount>
          </Setup>
        </EvnetsBody>
      </UpcomingEvents>

      <Footer />
    </>
  );
};

export default PaymentSetup;