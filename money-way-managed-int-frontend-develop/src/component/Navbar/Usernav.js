import React, { useState, useEffect, useRef } from "react";

import {
  BellIcon,
  CloseIcon,
  IconFlex,
  MIconFlex,
  MenuCard,
  MenuIcon,
  NavBarCon,
  NavLogoDiv,
  NavRightDiv,
  NotiAlert,
  NotiCard,
  NotiDiv,
  PMCard,
  UserAvDiv,
} from "../Styled/Styled";
import Mobilemenu from "../Sidebar/Mobilemenu";
import Profilemodal from "./Profilemodal";
import Notificationmodal from "./Notificationmodal";
import {useStateContext} from "../Context/ContextProvider";

const Usernav = () => {
    const {firstName} = useStateContext();
  const [showMenu, setShowMenu] = useState(false);
  const [showProfile, setShowProfile] = useState(false);
  const [showNoti, setShowNoti] = useState(false);

  const dropdownRef = useRef(null);
  const dropdownRef2 = useRef(null);

  const toggleMenuVisibility = () => {
    setShowMenu(!showMenu);
  };

  const toggleProfile = () => {
    setShowProfile(!showProfile);
  };

  const toggleNoti = () => {
    setShowNoti(!showNoti);
  };

  

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setShowProfile(false);
      }
      if (dropdownRef2.current && !dropdownRef2.current.contains(event.target)) {
        setShowNoti(false);
      }
    };

    document.addEventListener("click", handleClickOutside);

    return () => {
      document.removeEventListener("click", handleClickOutside);
    };
  }, []);
  

  return (
    <NavBarCon>
      <NavLogoDiv to={"/"}>
        <img
          src="https://res.cloudinary.com/dafxzu462/image/upload/v1687697811/Frame_8402Logo_-_MoneyWay_an1xnp.svg"
          alt="user profile avatar"
          width={30}
          height={30}
          style={{ marginRight: "10px" }}
        />
        MoneyWay
      </NavLogoDiv>

      <NavRightDiv>
        <NotiDiv
          className={showNoti ? "visible" : ""}
          onClick={toggleNoti}
          ref={dropdownRef2}
        >
          <BellIcon />
          <NotiAlert />
        </NotiDiv>

        <UserAvDiv
          className={showProfile ? "visible" : ""}
          onClick={toggleProfile}
          ref={dropdownRef}
        >
          <img
            src="https://res.cloudinary.com/dafxzu462/image/upload/v1687701303/Ellipse_21user-avatar_k5vu1n.png"
            alt="user profile avatar"
            width={42}
            height={42}
            style={{ clipPath: "circle(50% at 50% 50%)", marginRight: "10px" }}
          />
            {firstName}
        </UserAvDiv>
      </NavRightDiv>

{/* ----------------------------------------------------------------- */}

      <NotiCard
        className={showNoti ? "active" : ""}
        onClick={toggleNoti}
      >
        <Notificationmodal />
      </NotiCard>


{/* ----------------------------------------------------------------- */}
      <PMCard
        className={showProfile ? "active" : ""}
        onClick={toggleProfile}
      >
        <Profilemodal />
      </PMCard>

      <MIconFlex
        className={`show-menu-icon ${showMenu ? "visible" : ""}`}
        onClick={toggleMenuVisibility} 
      >
            <IconFlex>
              {showMenu ? <CloseIcon /> : <MenuIcon />}
            </IconFlex>
      </MIconFlex>

      <MenuCard
        className={showMenu ? "active" : ""}
        onClick={toggleMenuVisibility}
      >
        <Mobilemenu />
      </MenuCard>
    </NavBarCon>
  );
};

export default Usernav;
