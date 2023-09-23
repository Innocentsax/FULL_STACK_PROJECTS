import AvatarDefault from "./AvatarDefault";
import "./CSS/Wallet.css";
import WithdrawModal from "../Component/WithdrawModal";
import React, { useState, useEffect } from "react";
import WalletService from "../service/WalletService";
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';


function Wallet(props) {
// get token from local storage to use in the header callback
  const token = localStorage.getItem("token");

  // Define walletBalance as a state variable
  const [walletBalance, setWalletBalance] = useState(0);
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

  //style for modal
  const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
  };

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  
    return (
      <div className="wallet-wallet" style={props.style}>
        <div className="wallet-frame-16452x">
          <div className="wallet-rectangle-153x">
            <div className="wallet-frame-16451x">
              <p className="wallet-hive">Hive</p>
              <div className="wallet-ellipse-1x" />
            </div>
            <div className="wallet-frame-16503x">
              <div className="wallet-frame-16502x">
                <div className="wallet-ellipse-2x" />
              </div>
              <div className="wallet-frame-16453x">
                <AvatarDefault className="wallet-avatar-default-1" />
                {/*<p className="wallet-hi-omotayo">Hi, Omotayo</p>*/}
              </div>
            </div>
          </div>
        </div>
        <div className="wallet-frame-16522x">
          <p className="wallet-my-wallet">My Wallet</p>
          <div className="wallet-frame-16504x">
            <p className="wallet-all-payments-receive">
              All payments receive will appear here.
            </p>
          </div>
        </div>
        <div className="wallet-frame-16529x">
          <div className="wallet-frame-16528x">
            <div className="wallet-frame-16527x">
              <div className="wallet-frame-16526x">
                <div className="wallet-frame-16524x">
                  <p className="wallet-text-5">Wallet Balance</p>
                  <p className="wallet-ngn-35800x">{walletBalance}</p>
                </div>
              </div>
              <div className="wallet-frame-16361x">
                <p className="wallet-text-2" onClick={handleOpen}>
                 Fund Wallet
                </p>
              <p className="wallet-text-2" onClick={handleOpen}>
                Withdraw to bank
              </p>
                <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                  <Box sx={style}>
                    <WithdrawModal/>
                  </Box>
                </Modal>
            </div>
            </div>
          </div>
          <div className="wallet-frame-16457x">
            <div className="wallet-frame-16456x">
              <p className="wallet-text">Activity</p>
              <p className="wallet-text-1">Clear all</p>
            </div>
            <div className="wallet-frame-16426x">
              <div className="wallet-frame-16425x">
                <p className="wallet-text-3">No transaction</p>
                <p className="wallet-text-4">
                  You have not made any transaction yet.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
     
    );
    
  }


Wallet.defaultProps = {
  className: "",
  style: {},
};

export default Wallet;
