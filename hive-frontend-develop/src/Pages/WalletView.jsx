import "../Pages/CSS/Walletview.css";
import React, {useEffect, useState} from "react";
import WalletService from "../service/WalletService";
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import WithdrawModal from "../Component/WithdrawModal";
import FundWalletModal from "./FundWalletModal";

function WalletView(props) {
    // get token from local storage to use in the header callback
    const token = localStorage.getItem("token");

    const [count, setCount] = useState(0);


    const handleRefresh = () => {
        // Add a timeout of 2 seconds (2000 milliseconds)
            // Call window.location.reload() after 2 seconds
        setTimeout(() => {
            // Call window.location.reload() after 2 seconds
            window.location.reload();
        }, 5000);
    };

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

    const [walletHistory, setWalletHistory] = useState([]);

    useEffect(() => {
        // Fetch wallet balance from API and update the state
        WalletService.getWalletHistory(token)
            .then((response) => {
               // map the response list to the history div
                setWalletHistory(response.data.result);
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
        width: 450,
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        p: 4,
    };

    // state for withdraw modal
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    //  state for deposit modal
    const [openDeposit, setOpenDeposit] = React.useState(false);
    const handleOpenDeposit = () => {
        console.log("opendeposit is clicked")
        setOpenDeposit(true);}

    const handleCloseDeposit = () => {
        setOpenDeposit(false);}


    //closing modals
// Callback function to close fundWalletModal
    const handleCloseFundWalletModal = () => {
        setOpenDeposit(false);
    }
// Callback function to close withrawlaModal
    const handleWithdrawFundWalletModal = () => {
        setOpen(false);
    }

    return (
        <div className="wallet-view-wallet-view" style={props.style}>
            <div className="wallet-view-history-container">
                {walletHistory.map((item, index) => (
                    <div key={index} className="wallet-view-frame-16530x">
                        <p className="wallet-view-deposit">{item.transactionType}</p>
                        <p className="wallet-view-date">{item.transactionDate}</p>
                        <p className="wallet-view-amount">{item.amount}</p>
                    </div>
                ))}
            </div>
            <div className="wallet-view-containing_box_4x">
                <div className="wallet-view-rectangle-153x">
                    <div className="wallet-view-containing_box_6x">
                        <div className="wallet-view-frame-16451x">
                            <p className="wallet-view-hive"></p>
                            <div className="wallet-view-ellipse-1x" />
                        </div>
                        <div className="wallet-view-frame-16503x">
                            <div className="wallet-view-frame-16502x">
                                <div className="wallet-view-ellipse-2x" />
                            </div>
                            <div className="wallet-view-frame-16453x">
                                <p className="wallet-view-hi-omotayo"></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="wallet-view-containing_box_2x">
                    <div className="wallet-view-frame-16522x">
                        <p className="wallet-view-my-wallet">My Wallet</p>
                    </div>
                    <div className="wallet-view-frame-16529x">
                        <div className="wallet-view-frame-16528x">
                            <div className="wallet-view-frame-16527x">
                                <div className="wallet-view-containing_box_10x">
                                    <div className="wallet-view-frame-16526x">
                                        <div className="wallet-view-frame-16524x">
                                            <p className="wallet-view-text-2">{"Total amount "}</p>
                                            <p className="wallet-view-ngn-35800x">NGN {walletBalance}</p>
                                        </div>
                                    </div>
                                    <div className="wallet-view-containing_box_8x">
                                        <div className="wallet-view-witdrawbutton">
                                            <p className="wallet-view-text-3" onClick={handleOpenDeposit}>Fund wallet</p>
                                            <Modal
                                                open={openDeposit}
                                                onClose={handleCloseDeposit}
                                                aria-labelledby="modal-modal-title"
                                                aria-describedby="modal-modal-description"
                                            >
                                                <Box sx={style}>
                                                    <FundWalletModal onClose={handleCloseFundWalletModal}/>
                                                </Box>
                                            </Modal>
                                        </div>
                                        <div className="wallet-view-witdrawbutton-1">
                                            <p className="wallet-view-text-4" onClick={handleOpen}>
                                                Withdraw to bank
                                            </p>
                                            <Modal
                                                open={open}
                                                onClose={handleClose}
                                                aria-labelledby="modal-modal-title"
                                                aria-describedby="modal-modal-description"
                                            >
                                                <Box sx={style}>
                                                    <WithdrawModal onClose={handleWithdrawFundWalletModal} onSuccess={handleRefresh}/>
                                                </Box>
                                            </Modal>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="wallet-view-frame-16457x">
                            <div className="wallet-view-frame-16456x">
                                <p className="wallet-view-text">Activity</p>
                                <p className="wallet-view-text-1">Clear all</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

WalletView.defaultProps = {
    className: "",
    style: {},
};

export default WalletView;