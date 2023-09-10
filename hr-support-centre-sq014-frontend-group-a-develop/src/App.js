import { Route, Routes, BrowserRouter } from "react-router-dom";
import './App.css';
import Landing from "./pages/Landing";
import Login from "./pages/Login";
import ForgetPassword from "./pages/ForgetPassword";
import ResetPassword from "./pages/ResetPassword";
import StaffDashboard from "./pages/Staff/StaffDashboard";
import StaffJobBoard from "./pages/Staff/StaffJobBoard";
import StaffProfile from "./pages/Staff/StaffProfile";
import StaffEmployees from "./pages/Staff/StaffEmployees";
import StaffChat from "./pages/Staff/StaffChat";
import StaffRewardRecognition from "./pages/Staff/StaffRewardRecognition";
import StaffSetting from "./pages/Staff/StaffSetting";
import Admin from "./pages/Admin";
import Staff from "./pages/Staff";
import AdminHrDashboard from "./pages/AdminHr/AdminHrDashboard";
import AdminHrJobBoard from "./pages/AdminHr/AdminHrJobBoard";
import AdminHrEmployeeInformation from "./pages/AdminHr/AdminHrEmployeeInformation";
import AdminHrRewardRecognition from "./pages/AdminHr/AdminHrRewardRecognition";
import AdminHrSettings from "./pages/AdminHr/AdminHrSettings";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Landing />} />
          <Route path="/login" element={<Login />} />
          <Route path="/forgot-passwword" element={<ForgetPassword />} />
          <Route path="/reset-password" element={<ResetPassword />}/>
          {/* Staff dashboard Route */}
          <Route path="/staff" element={<Staff />}>
            <Route index path="/staff/" element={<StaffDashboard />} />
            <Route path="/staff/job-board" element={<StaffJobBoard />} />
            <Route path="/staff/profile" element={<StaffProfile />} />
            <Route path="/staff/employees" element={<StaffEmployees />} />
            <Route path="/staff/chat" element={<StaffChat />} />
            <Route path="/staff/reward-recognition" element={<StaffRewardRecognition />} />
            <Route path="/staff/setting" element={<StaffSetting />} />
          </Route>
          {/* Hr - Admin dashboard Route */}
          <Route path="/admin" element={<Admin />}>
            <Route index path="/admin/" element={<AdminHrDashboard />} />
            <Route path="/admin/job-board" element={<AdminHrJobBoard />} />
            <Route path="/admin/employee-information" element={<AdminHrEmployeeInformation />} />
            <Route path="/admin/reward-recognition" element={<AdminHrRewardRecognition />} />
            <Route path="/admin/setting" element={<AdminHrSettings />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
