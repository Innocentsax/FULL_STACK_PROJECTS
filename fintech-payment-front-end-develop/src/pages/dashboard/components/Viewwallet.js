import React, { useState, useEffect} from "react";
import './viewwallet.css';
import { TbWallet } from 'react-icons/tb';
import { MdContentCopy } from 'react-icons/md';
import { AiOutlineEye } from 'react-icons/ai';
import { AiOutlineEyeInvisible } from 'react-icons/ai';
import { BiSearch } from 'react-icons/bi';
import axios from 'axios';






function ViewWallet() {
  const [show, setShow] = useState(true);
  const [balance, setBalance] = useState('N2,000,000');

  const hideBalance = () => {
    setShow((prevState) => {
      return !prevState;
    })
    setBalance('****');
    
  }

  const showBalance = () => {
    setShow((prevState) => {
      return !prevState;
    })
    setBalance('N2,000,000');
    
  }

  const [userWallet, setUserWallet] = useState([]);

  useEffect(() => {
    const token = JSON.parse(localStorage.getItem("token"));

    const getWallets = async () => {
      try {
        const response = await axios.get("http://localhost:3333/api/v1/user/viewWallet", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setUserWallet(response.data);
        console.log(response.data);
      } catch (error) {
        console.log(error);
      }
    };

  getWallets();
  }, []);
  
    return (
      <>
      <section className="wallet-container">
        <div className="wallet-card">
            <div className="wallet-content">
              <div className="wallet-icon">
                <span>
                  <TbWallet className="icon"/>
                </span>
              </div>
              <div className="wallet-details">
                <p className="balance-text">account balance</p>
                <p className="balance">{userWallet.balance}</p>
                <p id="bank-name">{userWallet.bankName}</p>
                <p>
                  <button className="copyBtn"><MdContentCopy/></button>
                  {userWallet.acctNumber}
                </p>
              </div>
            </div>
            <div className="balance-visibility">
              {
                show ? (
                  <button className="show" onClick={hideBalance}>
                    <AiOutlineEye  className="show-icon "/>
                  </button>
                ) : (
                  <button className="hide" onClick={showBalance}>
                    <AiOutlineEyeInvisible className="hide-icon"/>
                  </button>
                )
              }
            </div>
        </div>
        <div className="transfer-profile-container">
          <div className="transfer-tag-div">
            <a href="/other-transfer" className="transfer-tag">transfer</a>
          </div>
          <div className="profile-tag-div">
            <a href="/profile" className="profile-tag">profile</a>
          </div>
        </div>
      </section>

      </>
    )
}

export default ViewWallet
