import {
  BrowserRouter as Router,
  Routes as RouterCover,
  Route,
} from "react-router-dom";
import Dashboard from "../pages/dashboard/Dashboard";
import Login from "../pages/login/login";
import VerifyEmailRegistration from "../pages/registration/VerifyEmailRegistration";
import SignUp from "../pages/registration/SignUp";
import LandingPage from "../pages/home/landingPage";
import EmailVerification from "../pages/emailVerification/EmailVerification";
import Profile from "../pages/profile/ProfilePage";
import LocalTransfer from "../pages/localtransfer/LocalTransferPage";
import AuthState from "../reducers/authState";
import ResetPassword from "../pages/resetPassword/ResetPassword";
import ForgotPassword from "../pages/forgotPassword";
import PasswordResetModal from "../pages/resetPassword/PasswordResetModal";
import FundsTransfer from "../pages/fundsTransfer/FundsTransfer";
// eslint-disable-next-line no-unused-vars

export const Routes = () => {
  //TODO protect the dashboard route.
  return (
    <Router>
      <AuthState>
        <RouterCover>
          <Route path='/' element={<LandingPage />} />
          <Route path='/login' element={<Login />} />
          <Route path='/dashboard' element={<Dashboard />} />
          <Route path='/verify-email' element={<VerifyEmailRegistration />} />
          <Route path='/signup' element={<SignUp />} />
          <Route path='/verifyRegistration' element={<EmailVerification />} />
          <Route path='/profile' element={<Profile />} />
          <Route path='/transfer' element={<LocalTransfer />} />
          <Route path='/forgotPassword' element={<ForgotPassword />} />
          <Route path='/resetPassword' element={<ResetPassword />} />
          <Route
            path='/resetPassword/PasswordResetModal'
            element={<PasswordResetModal />}
          />
          <Route path='/fundsTransfer' element={<FundsTransfer />} />
        </RouterCover>
      </AuthState>
    </Router>
  );
};
export default Routes;
