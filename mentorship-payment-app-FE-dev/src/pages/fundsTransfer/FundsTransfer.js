import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import DashboardHeader from "../../component/DashboardHeader/DashboardHeader";
import LocalTransferPage from "../localtransfer/LocalTransferPage";
import OtherBankTransfer from "../otherTransfer/OtherBankTransfer";
import "./FundsTransfer.css";

export default function FundsTransfer() {
  const [local, setLocal] = useState(true);
  const [other, setOther] = useState(false);
  const [toggle, setToggle] = useState(true);

  const navigate = useNavigate();

  function switchLocal() {
    setLocal(true);
    setOther(false);
    setToggle(!toggle);
  }
  function switchOther() {
    setLocal(false);
    setOther(true);
    setToggle(!toggle);
  }
  return (
    <div>
      <DashboardHeader />
      <div className='top_section'>
        <h3 onClick={() => navigate(-1)} type='submit' className='side_link'>
          <span>&larr;</span>Go back
        </h3>

        <div className='transfer_btn'>TRANSFER</div>
        <div className='tx_type'>
          <div className={`line ${toggle ? "active" : ""} `}>
            <p className={toggle ? "active" : ""} onClick={switchLocal}>
              Local Transfer
            </p>
          </div>
          <div className={`line ${toggle ? "" : "active"} `}>
            <p className={toggle ? "" : "active"} onClick={switchOther}>
              Other Bank Transfer
            </p>
          </div>
        </div>
      </div>
      {local && <LocalTransferPage />}
      {other && <OtherBankTransfer />}
    </div>
  );
}
