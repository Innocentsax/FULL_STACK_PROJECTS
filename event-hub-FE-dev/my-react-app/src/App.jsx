import './App.css'
import {BrowserRouter as Router,Routes ,Route} from 'react-router-dom'
import HomePage from './assets/components/HomePage/HomePage'
import Register from './assets/components/CreateAccount/Register'
import Creator from './assets/components/CreateAccount/Creator'
import Login from './assets/components/Login/Login'
import VerifyUser from './assets/components/Login/Verify/VerifyUser'
import Token from './assets/components/Login/Token/Token'
import ForgetPassword from './assets/components/Login/Forget-Password/ForgetPassword'
import AuthenticatePassword from './assets/components/Login/Forget-Password/AuthenticatePassword'
import EventDetails from './assets/components/EventDetails/EventDetails'

import UpdateInfo from './assets/components/Dashboard/Account/UpdateInfo'
import Checkout from './assets/components/Checkout/Checkout'
import Information from './assets/components/Dashboard/Account/Information'
import PaymentSetup from './assets/components/Dashboard/Account/PaymentSetup'
import TicketModel from './assets/components/TicketModel/TicketModel'

import TicketSummary from './assets/components/TicketSummary/TicketSummary'
import TicketPage from './assets/components/TicketPage/TicketPage'
import FundWalletPage from './assets/components/FundWallet/FundWalletPage'
import UserProfileModal from './assets/components/Profile-modal/UserProfileModal'

import Logout from './assets/components/Dashboard/Logout'
import Event from './assets/components/Dashboard/Event'

import SearchBar from './assets/components/Dashboard/CreateEvent/EventSearch/SearchBar'
import Confirmation from "./assets/components/ConfirmationDetails/Confirmation.jsx";
import CreateEvent from "./assets/components/Dashboard/CreateEvent/CreateEvent.jsx";
import AllEventCreated from "./assets/components/AllEventsCreated/AllEventCreated.jsx";
import RegistrationAlert from "./assets/components/Alert-Modals/RegistrationAlert.jsx";





function App() {


  return (
    <>
    <Router>
    <Routes>
    <Route path='/' element={<HomePage />} />
    <Route path='/login' element={<Login />} />
    <Route path='/createvent' element={<CreateEvent />}/>
    <Route path='/signup' element={<Register />}/>
    <Route path='/signup-creator' element={<Creator />}/>
    <Route path='/user/forgetpassword/authenticate/' element={<ForgetPassword />} />
    <Route path='/verify-user/' element={<VerifyUser />} />
    <Route path="/token/:token" element={<Token />} />
    <Route path="/app/event" element={<Event />} />
    <Route path="/tokens" element={<AuthenticatePassword />} />

    <Route path='/ticket' element={<TicketPage />}/>
    <Route path='/checkout' element={<Checkout />} />
    <Route path='/ticket-summary' element={<TicketSummary />}/>
    <Route path='/fund-wallet' element={<FundWalletPage />}/>
    


    <Route path="/event/confirmation/:id" element={<Confirmation />} />
    <Route path="/app/setup-account" element={<PaymentSetup />} />
    <Route path="/app/account" element={<Information />} />
    <Route path="/ticket-modal" element={<TicketModel />} />
    <Route path="/app/event-details/:id" element={<EventDetails />} />
    <Route path="/user-profile-modal" element={<UserProfileModal />} />
    <Route path="/search-bar" element={<SearchBar />} />
    <Route path="/user-event-created/:id" element={<AllEventCreated />} />
    <Route path="/user-event-created" element={<AllEventCreated />} />
    <Route path="/registration-modal" element={<RegistrationAlert />} />
    <Route path="/logout" element={<Logout />} />
    <Route path='/app/update' element={<UpdateInfo />} />

    </Routes>
    </Router>

     
    </>
  )
}

export default App
