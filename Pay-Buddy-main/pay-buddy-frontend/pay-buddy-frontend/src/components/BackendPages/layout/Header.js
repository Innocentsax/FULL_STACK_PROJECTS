import React from 'react';
import logo from "../../../assets/images/logo.svg";
import {useNavigate} from "react-router-dom";

function Header() {
    let fullName="";
    let  profileImage ="";
    let token = localStorage.getItem("token");

    const navigate= useNavigate();
    const userProfile = localStorage.getItem("userProfile");
    if(token || userProfile){
        const user= JSON.parse(userProfile);
        fullName= user.name;
        profileImage = user.picture;
    }
    const logout = () => {
        localStorage.removeItem("userProfile");
        localStorage.removeItem("token");
        localStorage.removeItem("count");
        localStorage.removeItem("bankDetails");  
        navigate("/login");
    }
    if(token || userProfile) {
        return (
            <>
            
            <nav className="navbar navbar-expand-lg navbar-light">
                <div className="container-fluid">
                    <a className="navbar-brand" href="#"><img src={logo} alt="Logo" width="30" height="30"/></a>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"/>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0 nav-bar-items">
                            <li className="nav-item">
                                <a className="nav-link active" aria-current="page" href="#">Home</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="#">Link</a>
                            </li>

                        </ul>
                        <form className="d-flex">
                            <ul>
                                <li className="nav-item dropdown">
                                    <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                       data-bs-toggle="dropdown" aria-expanded="false">
                                        <i className="fa fa-bell" aria-hidden="true"></i>&nbsp; &nbsp; &nbsp;<img
                                        src={profileImage} alt="Logo" width="30" height="30"/>
                                    </a>
                                    <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                                        <li><a className="dropdown-item" href="#" onClick={logout}>Logout</a></li>
                                    </ul>
                                </li>
                            </ul>

                        </form>
                    </div>
                </div>
            </nav>
            </>
        );
    }
}

export default Header;