import React from "react";
import { LuLayoutDashboard, LuShieldAlert } from "react-icons/lu";
import { FaRegUser } from "react-icons/fa";
import { MdLogout, MdPhoneIphone } from "react-icons/md";
import { BiWallet } from "react-icons/bi";

import { SlBell } from "react-icons/sl";
import { PiArrowCircleUpRight, PiMoney } from "react-icons/pi";

import {
  SideNavLink,
  SideLine,
  LogoutBTN,
  MenuDiv,
  LogDiv,
  PGraphM,
} from "../Styled/Styled";

const Mobilemenu = () => {
  return (
    <>
      <MenuDiv>
        <SideNavLink
          className={({ isActive, isPending }) =>
            isPending ? "pending" : isActive ? "active" : ""
          }
          to={"/user/"}
        >
          <LuLayoutDashboard style={{ marginRight: "8px", fontSize: "22px" }} />
          Dashboard
        </SideNavLink>

        <SideNavLink
          className={({ isActive, isPending }) =>
            isPending ? "pending" : isActive ? "active" : ""
          }
          to={"/user/payment"}
        >
          <PiMoney style={{ marginRight: "8px", fontSize: "22px" }} />
          Payments
        </SideNavLink>

        <SideNavLink
          className={({ isActive, isPending }) =>
            isPending ? "pending" : isActive ? "active" : ""
          }
          to={"/user/fundwallet"}
        >
          <BiWallet style={{ marginRight: "8px", fontSize: "22px" }} />
          Fund Wallet
        </SideNavLink>

        <SideNavLink
          className={({ isActive, isPending }) =>
            isPending ? "pending" : isActive ? "active" : ""
          }
          to={"/user/transfer"}
        >
          <PiArrowCircleUpRight
            style={{ marginRight: "8px", fontSize: "22px" }}
          />
          Transfer
        </SideNavLink>

        <SideNavLink
          className={({ isActive, isPending }) =>
            isPending ? "pending" : isActive ? "active" : ""
          }
          to={"/user/buyairtime"}
        >
          <MdPhoneIphone style={{ marginRight: "8px", fontSize: "22px" }} />
          Buy Airtime
        </SideNavLink>

        <SideNavLink
          className={({ isActive, isPending }) =>
            isPending ? "pending" : isActive ? "active" : ""
          }
          to={"/user/notification"}
        >
          <SlBell style={{ marginRight: "8px", fontSize: "20px" }} />
          Notification
        </SideNavLink>

        <PGraphM>OTHERS</PGraphM>

        <SideNavLink
          className={({ isActive, isPending }) =>
            isPending ? "pending" : isActive ? "active" : ""
          }
          to={"/user/profile"}
        >
          <FaRegUser style={{ marginRight: "8px", fontSize: "20px" }} />
          Account
        </SideNavLink>

        <SideNavLink
          className={({ isActive, isPending }) =>
            isPending ? "pending" : isActive ? "active" : ""
          }
          to={"/user/security"}
        >
          <LuShieldAlert style={{ marginRight: "8px", fontSize: "22px" }} />
          Security
        </SideNavLink>
      </MenuDiv>

      <SideLine />

      <LogDiv>
        <LogoutBTN>
          <MdLogout style={{ marginRight: "8px", fontSize: "22px" }} />
          Logout
        </LogoutBTN>
      </LogDiv>
    </>
  );
};

export default Mobilemenu;
