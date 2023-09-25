import axios from "axios";
import React, { useEffect, useState } from "react";
import "./ProfilePage.css";
import { useNavigate } from "react-router-dom";
import DashboardHeader from "../../component/DashboardHeader/DashboardHeader";

const ProfilePage = () => {
  const navigate = useNavigate();

  const token = localStorage.getItem("accessToken");

  var header = { Authorization: "Bearer " + token };

  const [user, setUser] = useState([]);
  // eslint-disable-next-line react-hooks/exhaustive-deps
  const fetchSingleUser = () => {
    axios
      .get(`${process.env.REACT_APP_BASE_URL}/api/v1/user`, {
        headers: header,
      })
      .then((resp) => {
        setUser(resp.data.result);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    fetchSingleUser();
  }, [fetchSingleUser,]);

  return (
    <>
    < DashboardHeader />
    <div className="profilePage">
      <div className="profilePage__wrapper">
        <div className="profilePage__side">
          <p>&larr;</p>
          <button className="side_link" onClick={() => navigate(-1)}>
            {" "}
            Go back
          </button>
        </div>
        <div className="profilePage__top">
          <p>PROFILE</p>
        </div>
        <div className="profilePage__body">
          <div className="profilePage__content">
            <label>FirstName</label>
            <input disabled type="text" value={user.firstName} />
          </div>

          <div className="profilePage__content">
            <label>LastName</label>
            <input disabled type="text" value={user.lastName} />
          </div>

          <div className="profilePage__content">
            <label>PhoneNumber</label>
            <input disabled type="text" value={user.phoneNumber} />
          </div>

          <div className="profilePage__content">
            <label>BVN</label>
            <input disabled type="text" value={user.bvn} />
          </div>

          <div className="profilePage__content">
            <label>Email</label>
            <input disabled type="email" value={user.email} />
          </div>
        </div>
      </div>
    </div>
    </>
  );
};

export default ProfilePage;
