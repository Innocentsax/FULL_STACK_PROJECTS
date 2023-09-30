import { Routes, Route, Link } from "react-router-dom";
import "./assets/css/color.css";
import "./assets/css/style.css";
import Login from "./components/auth/Login";
import PasswordReset from "./components/auth/authenticationManager/PasswordReset";
import Header from "./components/BackendPages/layout/Header";
import Dashboard from "./components/BackendPages/Dashboard";
import Register from "./components/auth/Register/Register";
import Home from "./components/Pages/Home";
import Layout from "./components/BackendPages/layout/Layout";
import SendMoneyPartOne from "./components/BackendPages/SendMoneyPartOne";
import SendMoneyPartTwo from "./components/BackendPages/SendMoneyPartTwo";
import { MyContextProvider } from "./statemanagement/ComponentState";
import PasswordResetForm from "./components/auth/authenticationManager/PasswordResetForm";
import Payment from "./components/BackendPages/Payment";
import Welcome from "./components/Pages/Welcome";
import TransactionPin from "./components/BackendPages/TransactionPin";
import SettingsMenu from "./components/Settings/Settings";
import { Toaster } from "react-hot-toast";
import ConfirmFund from "./components/BackendPages/wallet/ConfirmFund";
import SendMoneyPartThree from "./components/BackendPages/SendMoneyPartThree";
import BuyAirtimePartOne from "./components/BackendPages/buyairtime/BuyAirtime";
import BuyDataPartOne from "./components/BackendPages/buy_data/BuyDataPartOne";
import BuyDataSuccessMessage from "./components/BackendPages/buy_data/BuyDataSuccessMessage"
import BuyAirtimeSuccessScreen from "./components/BackendPages/buyairtime/BuyAIrtimeSuccessScreen";
import BuyElectricity from "./components/BackendPages/BuyElectricity";
import BuyElectricitySuccessScreen from "./components/BackendPages/BuyElectricitySuccessScreen";
import AccountVerified from "./components/auth/Register/AccountVerified";


function App() {
  return (
    <div className="App">
      <Toaster />
      <MyContextProvider>
      <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/reset-password/:token" element={<PasswordResetForm />} />
              <Route path="/reset" element={<PasswordReset />} />
              <Route path="/accountverified" element={<AccountVerified />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/welcome" element={<Welcome />} />
              <Route path="verifyPayment/:reference" element={<ConfirmFund/>}/>

              <Route path = "pay-buddy"  element={<Layout />} >
                  <Route path="dashboard" element={<Dashboard />} />
                  <Route path="payment" element={ <Payment />} />

                  <Route path="create-transaction-pin" element={<TransactionPin />} />
                  <Route path="settings-menu" element={<SettingsMenu />} />

                  <Route path="send-money-1" element={<SendMoneyPartOne />}/>
                  <Route path="send-money-2" element={<SendMoneyPartTwo />}/>

                  <Route path="buy-electricity" element={<BuyElectricity />}/>


                  <Route path="buy-airtime-partone" element={<BuyAirtimePartOne />}/>
                  <Route path="buy-data-1" element={<BuyDataPartOne />}/>

              </Route>
              <Route path="buy-data-2" element={<BuyDataSuccessMessage />}/>
              <Route path="send-money-success" element={<SendMoneyPartThree />}/>
              <Route path="buy-airtime-success" element={<BuyAirtimeSuccessScreen />}/>
              <Route path="buy-electricity-success" element={<BuyElectricitySuccessScreen />}/>

          </Routes>
      </MyContextProvider>
          

    </div>
  );
}
export default App;