import React, {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import Logo from "../Assets/logo.svg";
import {HiOutlineBars3} from "react-icons/hi2";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import HomeIcon from "@mui/icons-material/Home";
import InfoIcon from "@mui/icons-material/Info";
import LoginIcon from "@mui/icons-material/Login";
import LogoutIcon from "@mui/icons-material/Logout";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faBell} from "@fortawesome/free-regular-svg-icons";
import axios from 'axios';
import {useNavigation} from "react-router-dom";
import NotificationPopover from "./NotificationPopover"
import NotificationService from "../service/NotificationService";
import jwt_decode from "jwt-decode";
// import {useHistory} from 'react-router-dom'


const HeaderBar = () => {

    const [open, setOpenMenu] = useState(false);
    const [isLogin, setIsLogin] = useState(false);
    const [user, setUser] = useState("");
    const [userEmail, setUserEmail] = useState("");


    const role = localStorage.getItem('userRole');


    const [renderKey, setRenderKey] = useState(0);

    useEffect(() => {
        setRenderKey(Math.random());
    }, []);



    let linkPath = '/dashboard'; // default link path

    if (role === 'TASKER') {
        linkPath = '/tasker';
    }

    useEffect(() => {
        const currentUser = localStorage.getItem("token");
        if (currentUser) {
            const decodedToken = jwt_decode(currentUser);
            setUser(decodedToken.fullName)
            setUserEmail(decodedToken.email)
            const verifiedStatus = decodedToken.verifiedStatus;
            if (verifiedStatus ===true) {
                setIsLogin(true);
                console.log(decodedToken);
            }
            console.log( "test");
        }
    })
        // }

    const [notifications, setNotifications] = useState([]);
    const userDto = JSON.parse(localStorage.getItem("userDto"));
    // setUser(localStorage.getItem("fullName"))
    const token = localStorage.getItem("token");

    let isLoggedIn = false;
    if (localStorage.getItem("isLoggedIn") ==="true")
    { isLoggedIn = true; }
    console.log(localStorage.getItem("isLoggedIn") +"test");
    useEffect(() => {
        NotificationService.getAllNotifications({}, token)
            .then((response) => {
                setNotifications(response.data.result.slice(0, 3));
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);


    const menuOptions = [
        {
            name: "Home",
            icon: <HomeIcon/>,
            link: "/",
        },
        {
            name: "About",
            icon: <InfoIcon/>,
            link: "/about",
        },
        {
            name: "Sign In",
            icon: <LoginIcon/>,
            link: "/login",
        },
        {
            name: "Sign Up",
            icon: <LogoutIcon/>,
            link: "/register",
        },
    ];
    const navigate = useNavigate();

    const handleLogout = () => {
        // Remove the token from local storagese
        navigate('/login');
        isLoggedIn=false;

        localStorage.removeItem('token');
        // Send a logout request to the server
        // axios.post('/logout')
        //     .then(() => {
        //         // Redirect to the login page
        //         navigate('/login');
        //     })
        //     .catch((error) => {
        //         console.log(error);
        //     });
    };

    // useEffect(() => {
    //     const currentUser = localStorage.getItem("token");
    //     // if (currentUser) {
    //     //     const decodedToken = jwt_decode(currentUser);
    //     //     setUser(decodedToken.fullName)
    //     //     setUserEmail(decodedToken.email)
    //     //     setIsLogin(true);
    //     // }
    //
    //     // const userStatus = localStorage.getItem("isLoggedIn");
    //     // if (userDto.isLoggedIn) {
    //     //     // const decodedToken = jwt_decode(currentUser);
    //     //     // const decodedToken = jwt_decode(currentUser);
    //     //     // setUser(decodedToken.fullName)
    //     //     // setUserEmail(decodedToken.email)
    //     //     setIsLogin(true);
    //
    //         NotificationService.getAllNotifications({}, localStorage.getItem("token"))
    //             .then((response) => {
    //                 setNotifications(response.data.result.slice(0, 3));
    //             })
    //             .catch((error) => {
    //                 console.log("I was called with")
    //                 console.log(error);
    //             });
    //     }
    // }, [])

    return (
        <nav key={renderKey}>
            <div className="nav-logo-container">
                <Link to={"/"}>
                <img src={Logo} alt="Hive logo navebar"/>
                </Link>
            </div>

            {isLogin? (
                <div className="userloginMenu">
                    <Link style={{textDecoration:"none",color:"black",fontSize:"18px"}} to={linkPath} className="item task">
                        <p style={{color:"black",fontSize:"18px"}} className="viewAllTask">Tasks</p>
                    </Link>

                    <Link style={{textDecoration:"none",color:"black",fontSize:"18px"}} to="/wallet" className="item wallet" >
                        <p style={{color:"black",fontSize:"18px"}} className="viewAllTask">Wallet</p>
                    </Link>

                    <div className="item userDetails_notification">
                        <div className="notificationIcon">
                            <NotificationPopover notify={notifications}/>
                            {/*<FontAwesomeIcon icon={faBell}/>*/}

                        </div>
                        <div className="userDetails">
                            <div className="user-image">
                            </div>
                            <p>{user.split(' ')[0]}</p>
                        </div>
                        <Link style={{textDecoration:"none"}} onClick={handleLogout} to="/login" className="logout"><h6>Logout</h6></Link>

                    </div>

                </div>
            ) : (
                <div className="navbar-links-container">

                    <Link to={"/register"}>
                        <button className="primary-button" style={{backgroundColor: "#1570EF"}}>Register</button>
                    </Link>
                    <Link onClick={handleLogout} to={"/login"}>
                        <button className="primary-button" style={{backgroundColor: "#1570EF"}}>Login</button>
                    </Link>
                </div>
            )}

            <div className="navbar-menu-container">
                <HiOutlineBars3 onClick={() => setOpenMenu(true)}/>
            </div>
            <Drawer open={open} onClose={() => setOpenMenu(false)} anchor="right">
                <Box
                    sx={{width: 250}}
                    role="presentation"
                    onClick={() => setOpenMenu(false)}
                    onKeyDown={() => setOpenMenu(false)}
                >
                    <List>
                        {menuOptions.map((item) => (
                            <ListItem key={item.text} disablePadding>
                                <ListItemButton>
                                    <ListItemIcon>{item.icon}</ListItemIcon>
                                    <ListItemText primary={item.text}/>
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                    <Divider/>
                </Box>
            </Drawer>
        </nav>
    );
};

export default HeaderBar;
