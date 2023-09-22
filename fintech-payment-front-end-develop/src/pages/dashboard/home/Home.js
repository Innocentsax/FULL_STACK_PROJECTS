
import Topbar from '../components/topbar/Topbar'
import ViewWallet from '../components/Viewwallet'
import React from 'react'
import "./home.css"
import Transaction from '../components/transaction/transaction'
const Home = () => {
  return (
    <div>
        <Topbar />
        <ViewWallet />
        <Transaction />
    </div>
  );
}

export default Home