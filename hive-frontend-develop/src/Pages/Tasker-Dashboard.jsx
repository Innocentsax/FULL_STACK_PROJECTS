import React from 'react'

import HeaderBar from "../Component/HeaderBar";
import UserDashboard from '../Component/Tasker/UserDashboard';
import Newpage from "../Component/Dashboard/DashboardJob";
import { useNavigate } from "react-router-dom";
// import TaskerView from '../Component/Tasker/TakserView';
import '../Component/Dashboard/dashboard.css'



function useTaskerRedirect() {

}

function Tasker() {

    const role = localStorage.getItem("userRole");
    const navigate = useNavigate();

    if (role === "DOER") {
        navigate("/dashboard");
    }
    useTaskerRedirect();
    // const  navigate = useNavigate();
    //
    // const role = localStorage.getItem("userRole");
    // if (role !== "TASKER") {
    //     navigate("/fund-wallet");
    // }
  return (
    <div className="dashboard-container">
        <HeaderBar />
        <UserDashboard />
        <Newpage />
        {/* <TaskerView /> */}
    </div>
  )
}

export default Tasker