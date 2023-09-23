import React, {useEffect, useState} from "react";
import "./css/WithdrawalModal.css";
import WalletService from "../service/WalletService";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import SuccessPopModal from "./SuccessPopModal";
import ErrorPopModal from "./ErrorPopModal";



function WithdrawModal(props) {
  const [beneficiaryBankCode, setBeneficiaryBankCode] = useState("");
  const [beneficiaryAccountNumber, setBeneficiaryAccountNumber] = useState("");
  const [amount, setAmount] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [apiResponse, setApiResponse] = useState("");
  const [banks, setBanks] = useState([]);

  const handleBankChange = (event) => {
    setBeneficiaryBankCode(event.target.value);
  };

  const handleAccountNumberChange = (event) => {
    setBeneficiaryAccountNumber(event.target.value);
  };

  const handleAmountChange = (event) => {
    setAmount(event.target.value);
  };

  // get token from local storage to use in the header callback
  const token = localStorage.getItem("token");

  // Define walletBalance as a state variable
  const [walletBalance, setWalletBalance] = useState(0);


  const handleModalClose = () => {
    // Call the onClose callback function passed from parent
    props.onClose();
  }

  useEffect(() => {
    // Fetch wallet balance from API and update the state
    WalletService.getWalletBalance(token)
        .then((response) => {
          setWalletBalance(response.data.result.accountBalance);
        })
        .catch((error) => {
          console.log(error);
        });
  }, [token]);

  useEffect(() => {
    // Fetch banks from API and update the state
    WalletService.getBankList(token)
        .then((response) => {
          console.log(response.data);
          setBanks(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
  }, [token]);

  const handleWithdrawal = () => {
    // Check if amount to withdraw exceeds wallet balance
    if (parseInt(amount) > walletBalance) {
      setErrorMessage("Withdrawal amount exceeds wallet balance.");
      handleOpenErrorDialog();
      return;
    }
    // make the requestdata
    const requestData = {
      amount,
      beneficiaryAccountNumber,
      beneficiaryBankCode,
      "currencyCode": "NGN",
      // "reason": "Wallet withdrawal",
      "narration": "Wallet withdrawal",
    };
    // Perform axios post call with withdrawal data
    setIsLoading(true);

    WalletService.withdrawFromWallet(requestData, token)
        .then((response) => {
          setErrorMessage(response.data.error)
          console.log(response.data);
          if (response.data.status === "true") {
            setTimeout(() => {
              // Simulating successful withdrawal after 10 seconds
              setIsLoading(false);

            }, 2000);
            setApiResponse(response.data.message)
            handleOpen()

          }
          else {
            setIsLoading(false);
            setErrorMessage("Transaction failed.\n" +
                " Please check your details and try again later.");
            handleOpenErrorDialog();
          }
          console.log(response.data.result);
          // Handle successful withdrawal
        })
        .catch((error) => {
          setIsLoading(false);
          console.log(error);
          setErrorMessage("Transaction failed.\n" +
              " Please check your details and try again later.");
          handleOpenErrorDialog();
        });


  }

  //style for response modal
  const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 4,
    display: 'inline-flex',
  };

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {

    setOpen(true);
  }
  const handleClose = () => setOpen(false);

  const [openErrorDialog, setOpenErrorDialog] = React.useState(false);
  const handleOpenErrorDialog= () => setOpenErrorDialog(true);
  const handleCloseErrorDialog = () => setOpenErrorDialog(false);


  const [isLoading, setIsLoading] = useState(false); // state to track loading status


  // Styles as variables
  const loadingContainerStyle = {
    position: "absolute",
    top: 0,
    right: 0,
    bottom: 0,
    display: "flex",
    alignItems: "center",
    paddingRight: "8px", // Add some spacing to the right for the loading icon
  };

  const loadingSvgStyle = {
    marginRight: "8px", // Add margin to the right for spacing
  };

  const loadingTextStyle = {
    // Add styles for your loading text
  };
    return (
        <div className="modal-default-modal-default" style={props.style}>
          <div className="modal-default-rectangle-1x">
            <div className="modal-default-frame-16421x">
              <p className="modal-default-withdraw-funds">Withdraw funds</p>
              <Modal
                  open={open}
                  onClose={handleClose}
                  aria-labelledby="modal-modal-title"
                  aria-describedby="modal-modal-description"
              >
                <Box sx={style}>
                  <SuccessPopModal myProp={apiResponse}/>
                </Box>
              </Modal>
              <Modal
                  open={openErrorDialog}
                  onClose={handleCloseErrorDialog}
                  aria-labelledby="modal-modal-title"
                  aria-describedby="modal-modal-description"
              >
                <Box sx={style}>
                  <ErrorPopModal myProp={errorMessage} />
                </Box>
              </Modal>
            </div>

            <div className="modal-default-frame-16355x">
              <div
                  className="modal-default-button-default"
                  onClick={handleWithdrawal}
                  style={{ position: "relative" }} // Add relative position to parent div
              >
                {isLoading && ( // Render loading animation and text when isLoading is true
                    <div style={loadingContainerStyle}>
                      <div className="modal-default-loading-svg" style={loadingSvgStyle}>
                        {/* Replace with your SVG icon */}
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 24 24"
                            width="24"
                            height="24"
                        >
                          <circle cx="12" cy="12" r="10" stroke="white" strokeWidth="1" />
                        </svg>
                      </div>
                      <p className="modal-default-loading-text" style={loadingTextStyle}>
                        Processing...
                      </p>
                    </div>
                )}
                <p className="modal-default-text">Withdraw</p>
              </div>
            </div>
            <div className="modal-default-containing_box_2x">
              <div className="modal-default-frame-16424x">
                <div className="modal-default-frame-16353x">
                  <p className="modal-default-bank">Bank</p>
                  <select className="modal-default-bank-select" value={beneficiaryBankCode} onChange={handleBankChange}>
                    {banks.map((bank) => (
                        <option key={bank.code} value={bank.code}>
                          {bank.bankName}
                        </option>
                    ))}
                  </select>
                </div>
                <div className="modal-default-frame-16423x">
                  <div className="modal-default-frame-16350x">
                    <p className="modal-default-account-number">Account Number</p>
                    <input
                        className="modal-default-button-default-2"
                        value={beneficiaryAccountNumber}
                        onChange={handleAccountNumberChange}
                    />
                  </div>
                  <div className="modal-default-frame-16423x-1">
                    <p className="modal-default-amount">Amount</p>
                    <input
                        className="modal-default-button-default-2"
                        value={amount}
                        onChange={handleAmountChange}
                    />
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
    );
  }


WithdrawModal.defaultProps = {
  className: "",
  style: {},
};

export default WithdrawModal;
