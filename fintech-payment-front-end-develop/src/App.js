import Signup from './pages/signup/Signup';
import Login from './pages/login/Login';
// import Topbar from './topbar/Topbar';
import Home from './pages/dashboard/home/Home';
import ProfilePage from './pages/profile page/Profilepage';
import ReactDOM from "react-dom/client";
import EmailVerification from "./pages/EmailVerification/EmailVerified";
import LandingPage from './pages/landing page/Landingpage';
import LocalTransfer from "./pages/localtransfer/LocalTransfer";
import OtherBankTransfer from './pages/otherBankTransfer/OtherBankTransfer';
import {Toaster}  from "react-hot-toast"



import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";

function App() {
  return (
    <div className="App">
      <Toaster/>
      <BrowserRouter>
        <Routes>
          <Route path="/signup" element={<Signup />}> </Route>
          <Route path="/login" element={<Login />}> </Route>
          <Route path="/dashboard" element={<Home />}> </Route>
          <Route path="/profile" element={<ProfilePage />}> </Route>
          <Route path="/email-verification" element={<EmailVerification/>}></Route>
          <Route path="/" element={<LandingPage />}> </Route>
          <Route path="/local" element={<LocalTransfer/>}> </Route>
          <Route path="/other-transfer" element={<OtherBankTransfer />}> </Route>

        </Routes>
      </BrowserRouter>
      
    </div>
  );
}

export default App;
