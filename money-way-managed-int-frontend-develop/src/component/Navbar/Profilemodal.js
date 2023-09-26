import React from "react";
import { LuShieldAlert } from "react-icons/lu";
import { FaRegUser } from "react-icons/fa";
import { MdLogout } from "react-icons/md";
import {
  LPdiv,
  LabelPM,
  LogoutBTNpm,
  ParaPM,
  PmHdiv,
  PmNavDiv,
  PmNavLink,
  SideLine,
} from "../Styled/Styled";
import {useStateContext} from "../Context/ContextProvider";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const Profilemodal = () => {
    const context=useStateContext();

    const navigate = useNavigate();
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
      <>
        <PmHdiv to={"/user/profile"}>
          <img
            src="https://res.cloudinary.com/dafxzu462/image/upload/v1687701303/Ellipse_21user-avatar_k5vu1n.png"
            alt="user profile avatar"
            width={48}
            height={48}
            style={{ clipPath: "circle(50% at 50% 50%)", marginRight: "4px" }}
          />
          <LPdiv>
            <LabelPM>{context.firstName+" "+context.lastName}</LabelPM>
            <ParaPM>{context.email}</ParaPM>
          </LPdiv>
        </PmHdiv>

        <SideLine />

        <PmNavDiv>

        <PmNavLink
          className={({ isActive, isPending }) =>
            isPending ? "pending" : isActive ? "active" : ""
          }
          to={"/user/profile"}
        >
          <FaRegUser style={{ marginRight: "8px", fontSize: "20px" }} />
          Account
        </PmNavLink>

        <PmNavLink
          className={({ isActive, isPending }) =>
            isPending ? "pending" : isActive ? "active" : ""
          }
          to={"/user/security"}
        >
          <LuShieldAlert style={{ marginRight: "8px", fontSize: "22px" }} />
          Security
        </PmNavLink>
        <LogoutBTNpm onClick={handleLogout}>
          <MdLogout style={{ marginRight: "8px", fontSize: "22px" }} />
          Logout
        </LogoutBTNpm>
        </PmNavDiv>
      </>
  );
};

export default Profilemodal;
