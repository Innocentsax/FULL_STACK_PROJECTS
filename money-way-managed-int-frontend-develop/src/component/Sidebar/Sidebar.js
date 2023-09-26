import React from "react";
import { LuLayoutDashboard } from "react-icons/lu";
import { MdLogout } from "react-icons/md";
import { BiWallet } from "react-icons/bi";
import {useNavigate} from "react-router-dom";
import { PiArrowCircleUpRight, PiMoney } from "react-icons/pi";
import { SideCard, SideNavLink, SideLine, SideNavDiv, LogoutBTN } from "../Styled/Styled";
import axios from "axios";
import {useStateContext} from "../Context/ContextProvider";


const Sidebar = () => {
    const navigate = useNavigate();

    const context = useStateContext()

    const handleLogout = async () =>{
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer '+context.token
        };
        await axios.get("http://localhost:9000/api/v1/auth/logout", { headers })
            .then(
                response=>{
                    context.setToken("")
                    navigate("/login")
                }
            )
            .catch(error => {console.log(error)})
    }

  return (
    <SideCard>
      <SideNavDiv>
          <SideNavLink
            className={({ isActive, isPending }) =>
              isPending ? "pending" : isActive ? "active" : ""
            }
            to={"/user/"}
          >
            <LuLayoutDashboard style={{ marginRight: '8px', fontSize: '22px' }} />
            Dashboard
          </SideNavLink>

          <SideNavLink
            className={({ isActive, isPending }) =>
              isPending ? "pending" : isActive ? "active" : ""
            }
            to={"/user/payment"}
          >
            <PiMoney style={{ marginRight: '8px', fontSize: '22px' }} />
            Payments
          </SideNavLink>

          <SideNavLink
            className={({ isActive, isPending }) =>
              isPending ? "pending" : isActive ? "active" : ""
            }
            to={"/user/fundwallet"}
          >
            <BiWallet style={{ marginRight: '8px', fontSize: '22px' }} />
            Fund Wallet
          </SideNavLink>

          <SideNavLink
            className={({ isActive, isPending }) =>
              isPending ? "pending" : isActive ? "active" : ""
            }
            to={"/user/transfer"}
          >
            <PiArrowCircleUpRight style={{ marginRight: '8px', fontSize: '22px' }} />
            Transfer
          </SideNavLink>
      </SideNavDiv>

      <SideLine />

      <SideNavDiv>
              <LogoutBTN onClick={handleLogout}>
                  <MdLogout style={{ marginRight: '8px', fontSize: '22px' }} />
                  Logout
              </LogoutBTN>
      </SideNavDiv>
    </SideCard>
  );
};

export default Sidebar;
