import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Footer from "../../HomePage/Footer/Footer";
import Header from "../../HomePage/Header/Header";
import preloader from "../../CreateAccount/image/preloader.gif";
import {
  UpcomingEvents,
  EvnetsBody,
  Setup,
  FormAccount,
  MessageResponse,
  Details,
  ButtonForm,
  DiviNFO,
  AccountNFO,
  Button,
  Loader,
} from "../../Styled/Styled";

const Information = () => {
  const TOKEN = localStorage.getItem("TOKEN");
  if (TOKEN == null) {
    window.location.replace("/login");
  }

  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);

    axios
      .get("http://localhost:8999/api/v1/bank/account", {
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
          Authorization: `Bearer ${TOKEN}`,
        },
      })
      .then((data) => {
        console.log(data);
        setAccounts(data.data.data);
        console.log(accounts.length)
        setLoading(false);
      })
      .catch((e) => {
        setLoading(false);
        console.log(e);
      });
  }, []);

  return (
    <>
      <Header />

      <UpcomingEvents>
        <EvnetsBody>
          <Setup>
            <h1></h1>
            {loading ? (
              <Loader src={preloader} alt="preloader" >
              </Loader>
            ) : (
              <FormAccount>
              

                  {
                
                accounts.length > 0 &&
                  accounts.map((val, index) => (
                    <React.Fragment key={index}>
                      <Details>
                        <DiviNFO>
                          <p>Account Name</p>
                          <h3>{val.accountName}</h3>
                        </DiviNFO>
                        <AccountNFO>
                          <Link to="http://localhost:5173/app/update">
                            <Button>Edit</Button>
                          </Link>
                        </AccountNFO>
                      </Details>
                      <Details>
                        <DiviNFO>
                          <p>Account Number</p>
                          <h3>{val.accountNumber}</h3>
                        </DiviNFO>
                        <AccountNFO></AccountNFO>
                      </Details>
                      <Details>
                        <DiviNFO>
                          <p>Bank</p>
                          <h3>{val.bankName}</h3>
                        </DiviNFO>
                        <AccountNFO></AccountNFO>
                      </Details>
                    </React.Fragment>
                  ))}
              </FormAccount>
            )}
          </Setup>
        </EvnetsBody>
      </UpcomingEvents>

      <Footer />
    </>
  );
};

export default Information;
