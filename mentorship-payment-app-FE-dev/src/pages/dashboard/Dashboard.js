import React from "react";
import "./index.css";
import { TransactionDisplay } from "../../component/Dashboard/TransactionDisplay";
import { TransactionBtn } from "../../component/Dashboard/TransactionBtn/TransactionBtn";
import { TransactionInput } from "../../component/Dashboard/TransactionInput";
import { TransactionList } from "../../component/Dashboard/TransactionList";
import { useNavigate } from "react-router-dom";
import DashboardHeader from "../../component/DashboardHeader/DashboardHeader";

export default function Dashboard() {
  const exp = localStorage.getItem("expiry");

  const navigate = useNavigate();

  if (exp < Date.now() / 1000) {
    navigate("/login");
  }

  return (
    <div className='overlay-container'>
      <DashboardHeader />
      <TransactionDisplay />
      <TransactionBtn />
      <TransactionInput />
      <TransactionList />
    </div>
  );
}
