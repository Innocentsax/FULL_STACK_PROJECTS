import "./sidebar.scss";
import { Link } from "react-router-dom";
import { useRef } from "react";
import DashboardIcon from "@mui/icons-material/Dashboard";
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";
import LocalShippingIcon from "@mui/icons-material/LocalShipping";
import CreditCardIcon from "@mui/icons-material/CreditCard";
import StoreIcon from "@mui/icons-material/Store";
import InsertChartIcon from "@mui/icons-material/InsertChart";
import SettingsApplicationsIcon from "@mui/icons-material/SettingsApplications";
import ExitToAppIcon from "@mui/icons-material/ExitToApp";
import NotificationsNoneIcon from "@mui/icons-material/NotificationsNone";
import SettingsSystemDaydreamOutlinedIcon from "@mui/icons-material/SettingsSystemDaydreamOutlined";
import PsychologyOutlinedIcon from "@mui/icons-material/PsychologyOutlined";
import AccountCircleOutlinedIcon from "@mui/icons-material/AccountCircleOutlined";
import Menu from "@mui/icons-material/Menu";
import { WarehouseSharp } from "@mui/icons-material";

const Sidebar = () => {
  const sidebarRef = useRef()

  const handleNavDisplay = () => {
    sidebarRef.current.classList.toggle("sidebar-hidden")
  }

  window.onresize = () => {
    if(window.outerWidth <= 889) 
      sidebarRef.current.classList.add("sidebar-hidden")
  }

  return (
    <div className="outer-div">
    <div className="sidebar sidebar-hidden" ref={ sidebarRef }>
        <div className="top ">
          <Link to="/" style={{ textDecoration: "none" }}> <span className="logo">Oakland</span> </Link>
            <Menu className="icon" onClick={ handleNavDisplay }/>
        </div>
      <hr />
      <div className="center">
        <ul>
          <p className="title">MAIN</p>
          <Link to="/" style={{ textDecoration: "none" }}>
            <li className="options hover-text">
              <DashboardIcon className="icon" />
              <p>Dashboard</p>
              <span className="tooltip-text" id="right">Dashboard</span>
            </li>
          </Link>

            <p className="title">LISTS</p>

          <Link to="/admin/users" style={{ textDecoration: "none" }}>
            <li className="options hover-text">
            <PersonOutlineIcon className="icon" />
            <p>Admins</p>
            <span className="tooltip-text" id="right">Users</span>
            </li>
          </Link>

          <Link to="/admin" style={{ textDecoration: "none" }}>
            <li className="options hover-text">
                <StoreIcon className="icon" />
                <p>Products</p>
                <span className="tooltip-text" id="right">Products</span>
              </li>
          </Link>

          <Link to="/admin/orders" style={{ textDecoration: "none" }}>
            <li className="options hover-text">
              <CreditCardIcon className="icon" />
              <p>Orders</p>
                <span className="tooltip-text" id="right">Orders</span>
            </li>
          </Link>
          <Link to="/admin/delivery" style={{ textDecoration: "none" }}>
            <li className="options hover-text">
            <LocalShippingIcon className="icon" />
            <p>Delivery</p>
            <span className="tooltip-text" id="right">Delivery</span>
            </li>
          </Link>
          
          <Link to="/admin/states" style={{ textDecoration: "none" }}>
            <li className="options hover-text">
            <WarehouseSharp className="icon" />
            <p>States</p>
            <span className="tooltip-text" id="right">States </span>
            </li>
          </Link>
          
          <Link to="/admin/pickupCenter" style={{ textDecoration: "none" }}>
            <li className="options hover-text">
            <WarehouseSharp className="icon" />
            <p>Pickup Center</p>
            <span className="tooltip-text" id="right">Pickup Center</span>
            </li>
          </Link>

          <Link to="/admin/category" style={{ textDecoration: "none" }}>
            <li className="options hover-text">
            <InsertChartIcon className="icon" />
            <p>Category/Subcategory</p>
            <span className="tooltip-text" id="right">Stats</span>
            </li>
          </Link>

          <p className="title">SERVICE</p>
          <li className="options hover-text">
            <SettingsSystemDaydreamOutlinedIcon className="icon" />
            <p>System Health</p>
            <span className="tooltip-text" id="right">System Health</span>
          </li>

          <li className="options hover-text">
            <PsychologyOutlinedIcon className="icon" />
            <p>Logs</p>
            <span className="tooltip-text" id="right">Logs</span>
          </li>

          <li className="options hover-text">
            <SettingsApplicationsIcon className="icon" />
            <p>Settings</p>
            <span className="tooltip-text" id="right">Settings</span>
          </li>

          <p className="title">USER</p>
          <li className="options hover-text">
            <AccountCircleOutlinedIcon className="icon" />
            <p>Profile</p>
            <span className="tooltip-text" id="right">Profile</span>
          </li>

          <li className="options hover-text">
            <ExitToAppIcon className="icon" />
            <p>Logout</p>
            <span className="tooltip-text" id="right">Logout</span>
          </li>

        </ul>

      </div>
    </div>
    </div>
  );
};

export default Sidebar;
