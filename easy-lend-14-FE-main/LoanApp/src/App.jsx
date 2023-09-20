import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './assets/components/Homepage/HomePage';
import Login from './assets/components/Login/Login';
import Register from './assets/components/Register/Register';
import './App.css'
import ResetPassword from './assets/components/Password/ResetPassword';
import Password from './assets/components/Password/Password';
import Confirm from './assets/components/Dashboard/RegisterDashboard/Proccess/Confirm';
import Contact from './assets/components/Dashboard/RegisterDashboard/KYC/Contact';
import Dashboard from './assets/components/Dashboard/UserDashboard/Dashboard';
import Token from './assets/components/Token/Token';
import VerifyMessage from './assets/components/Token/VerifyMessage'
import Logout from './assets/components/Dashboard/Logout/Logout'
import Lending from './assets/components/Dashboard/UserDashboard/Lending/Lending';
import Profile from './assets/components/Dashboard/UserDashboard/Profile/Profile';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Register />} />
        <Route path="/reset" element={<ResetPassword />} />
        <Route path="/reset-password" element={<Password />} />
        <Route path="/confirm" element={<Confirm />} />
        <Route path="/kyc/:id" element = {<Contact />} />
        <Route path="/user-auth" element = {<Token />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/verify-message" element={<VerifyMessage />} />
        <Route path="/logout" element={<Logout />} />
        <Route path='/leading' element = {<Lending />} />
        <Route path='/profile' element = {<Profile />} />
      </Routes>
    </Router>
  );
}

export default App;
