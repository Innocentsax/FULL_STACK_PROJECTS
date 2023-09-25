import React from "react";
import { useNavigate } from "react-router-dom";
import "./transBtn.css";

export const TransactionBtn = () => {
  const navigate = useNavigate();

  return (
    <div className='TransactionBtn'>
      <button onClick={() => navigate("/fundsTransfer")} className='transfer'>
        Transfer
      </button>

      <button onClick={() => navigate("/profile")} className='profile'>
        {" "}
        Profile
      </button>
    </div>
  );
};
