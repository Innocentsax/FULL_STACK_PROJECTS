import HeaderBar from "../Component/HeaderBar";
import SearchBar from "../Component/Dashboard/searchBar"
import Newpage from "../Component/Dashboard/DashboardJob";
import '../Component/Dashboard/dashboard.css'
import {useNavigate} from "react-router-dom";


const Dashboard = () => {

    const role = localStorage.getItem("userRole");
    const navigate = useNavigate();

    if (role === "TASKER") {
        navigate("/tasker");
    }
    return <div className="dashboard-container">
        {/*<HeaderBar />*/}
        <SearchBar />
        <Newpage />
        
    </div>
}
 
export default Dashboard;