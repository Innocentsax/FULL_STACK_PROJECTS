import React,{useState} from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LandingPage from './Component/LandingPage';
import Login from './Pages/Login';
import EmailVerification from './Pages/EmailVerfication'
import EmailVerificationPage from './Pages/EmailVerificatonPage';
import ResendEmailVerification from './Pages/ResendEmailVerificaton';
import EmailVerificationSuccessContainer from './Pages/EmailVerificationSuccessContainer';
import view from './Pages/View';
import Reset from './Pages/Resetpassword';
import ForgetPassword from './Pages/ForgetPassword';
import wallet from './Pages/Wallet';
import dashboard from './Pages/Dashboard';
import "./App.css";
import "./Pages/CSS/login.css"
import "./Pages/CSS/password_reset.css";
import Tasker from "./Pages/Tasker-Dashboard";
import HeaderBar from "./Component/HeaderBar";
import CreateJob from "./Pages/Tasker/createJob/CreateJob";
import FundWalletModal from "./Pages/FundWalletModal";

import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import About from "./Pages/About";
import Register from "./Pages/Register";
import WalletView from "./Pages/WalletView";

function App() {
  const [openNotification, setOpenNotification] = useState(false);
  const [isLoggin, setIslogin] = useState(false)

  const user = localStorage.getItem("token");

  function toggleBoolean(booleanValue) {
    return !booleanValue;
  }

  const toggleNotification = () => {
    setOpenNotification(!openNotification);
  };

  return (
    <div>
      <BrowserRouter>
        {/*{*/}
        {/*  user !== null ? (<HeaderBar*/}
        {/*      toggleNotification={toggleNotification}*/}
        {/*      openNotification={openNotification}*/}
        {/*  /> ):*/}
        {/*  (*/}
        {/*      ""*/}
        {/*  )*/}
        {/*}*/}
    <HeaderBar/>

        <Routes>
          <Route path="/" Component={LandingPage}></Route>
          <Route path="/home" Component={LandingPage}></Route>
          <Route
              path="/verifyRegistration"
              Component={EmailVerification}
          ></Route>
          <Route
              path='/resendEmailVerification'
              Component={ResendEmailVerification}
          ></Route>
          <Route path="/login" Component={Login}></Route>
          <Route
              path="/confirmed-email"
              Component={EmailVerificationSuccessContainer}
          ></Route>
          <Route path="/view" Component={view}></Route>
          <Route path="/wallet2" Component={wallet}></Route>
          <Route path="/reset-password/:token" Component={Reset}></Route>
          <Route path="/forget-password" Component={ForgetPassword}></Route>
          <Route path="/wallet" Component={WalletView}></Route>
          <Route path="/fund-wallet" Component={FundWalletModal}></Route>
          <Route path="/dashboard" Component={dashboard}></Route>
          <Route path="/tasker/create-job" Component={CreateJob} />
          <Route path='/tasker' Component={Tasker}></Route>
          <Route path='/about' Component={About}></Route>
          <Route path='/register' Component={Register}></Route>

        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App
