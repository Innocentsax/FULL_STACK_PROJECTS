import { useState } from "react";
import styles from "./Logout.module.css";
import Button from "../../../UI/Button";
import BigCard from "../../../UI/BigCard";
import { useNavigate } from "react-router-dom";

const Logout = (props) => {
  //    const[logoutModal, setLogoutModal]= useState(true)
  const navigate = useNavigate();

  const imageClick = () => {
    props.setLogoutModal(null);
  };
  const logoutHandler = () => {
    localStorage.removeItem("token");
    navigate("/");
  };
  return (
    <>
      {props.logoutModal && (
        <div>
          <div className={styles.backdrop} onClick={imageClick}></div>
          <BigCard className={styles.lineitem}>
            <img
              src="/assets/close-gFb.png"
              alt="cancel icon "
              style={{
                width: "30px",
                height: "30px",
                position: "relative",
                left: "452px",
                top: "40px",
              }}
              onClick={imageClick}
            />

            <div>
              <p className={styles.title}>Logout Confirmation</p>
            </div>
            <p className={styles.para}>
              Are you sure you want to log out of your account?
            </p>

            <Button onClick={logoutHandler}>Yes, logout</Button>
            <button className={styles.btns} onClick={imageClick}>
              No
            </button>
          </BigCard>
        </div>
      )}
    </>
  );
};

export default Logout;
