import React, {useContext} from "react";
import { NavLink, withRouter } from "react-router-dom";
import "./SideBar.css";
import "../../styles/Components/_button.scss";
import { UserContext } from "../../context/UserContext";
const SideBar = ({ menuItems }) => {
  
  const userCtx = useContext(UserContext);
  
  return (
    // <div>
    <div className="sidebar-wrapper">
      <h1 className="title" style={{ textAlign: "center", marginTop: "30px" }}>
        Dashboard
      </h1>
      <div className="menu-wrapper">
        {menuItems.map((menuItem, index) => (
          <NavLink
            className={`nav-link sidebar-links`}
            to={menuItem.path}
            key={index}
            exact={menuItem.exact}
            activeStyle={{
              color: "#FFFFFF",
              background: "#ED165F",
              borderRadius: "0 50px 50px 0",
            }}
          >
            <i className={`${menuItem.icon} nav-link-icon`}></i>
            <span className="nav-link-text">{menuItem.name}</span>
          </NavLink>
        ))}
      </div>
      <button className="sign-out-button" onClick = {userCtx.doLogout}>
        <span className="sign-out-icon">
          <img src="../images/sign-out-icon.svg" alt="sign-out-icon" />
        </span>
        <span className="sign-out-text">Sign Out </span>
      </button>
    </div>
    //   </div>
  );
};
export default withRouter(SideBar);
