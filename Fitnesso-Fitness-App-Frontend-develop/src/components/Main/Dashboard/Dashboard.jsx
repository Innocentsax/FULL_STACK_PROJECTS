import React, { useEffect } from "react";
import { NavLink, Outlet, useLocation, useNavigate } from "react-router-dom";
import "./UserDashboard.css";
import axios from "axios";
import { useState } from "react";
import useFetch from "./useFetch";

const Dashboard = () => {
  const token = localStorage.getItem("token");
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [peopledata, setPeopleData] = useState({});
  const [successful, setSuccessful] = useState(false);
  const [address, setAddress] = useState({});
  const navigate = useNavigate();

  useEffect((e) => {
    getUserInfo(e);
  }, []);
  const getUserInfo = async (e) => {
    setSuccessful(true);


    const url = "https://fitnesso-app-new.herokuapp.com/person/profile";
    // const url = `http://localhost:9067/person/profile`;

    try {
      const personInfoResponse = await axios.get(url, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setPeopleData(personInfoResponse.data);
      setSuccessful(false);
      console.log(personInfoResponse.data);
      setAddress(personInfoResponse.data.address);
      localStorage.setItem(
        "peopleData",
        JSON.stringify(personInfoResponse.data)
      );
      setIsLoggedIn(true);
    } catch (e) {
      // localStorage.clear();
      navigate("/");
      console.log("User does not exist!");
    }
  };

  return (
    <>
      {isLoggedIn ? (
        <div id={peopledata.id}>
        <div className="users__dashboard__main__title">
          <img src={`${peopledata.image}`} alt='img' />
          <div className="users__dashboard__main__greeting">
            <h1>{peopledata.username}</h1>
            <p>Welcome {`${peopledata.firstName} ${peopledata.lastName}`}</p>
          </div>
        </div>
        <div className="users__dashboard__charts">
          <div className="users__dashboard__charts__left">
            <div className="users__dashboard__charts__left__title">
              <div>
                <h1>Personal Information</h1>
                <p>{peopledata.gender}</p>
              </div>
              <i className="fa fa-user" aria-hidden="true"></i>
            </div>
            <br />
            <div className="users__dashboard__charts__left__info">
              <div>
                <p>{`${peopledata.firstName} ${peopledata.lastName}`}</p>
                <br></br>
                <p>{peopledata.email}</p>
                <br></br>
                <p>{peopledata.phoneNumber}</p>
                <br></br>
                <p>{peopledata.dobText}</p>
              </div>
              <a href="/userdashboard/edit-user-info">
                <i className="fa fa-pencil" aria-hidden="true"></i>
              </a>
            </div>
          </div>
          <div className="users__dashboard__charts__left">
            <div className="users__dashboard__charts__left__title">
              <div>
                <h1>Address & shipping details</h1>
                <p>{address.state}</p>
              </div>
              <i className="fa fa-truck" aria-hidden="true"></i>
            </div>
            <br />
            <div className="users__dashboard__charts__left__info">
              <div>
                <p>{address.streetDetail}</p>
                <br></br>
                <p>{address.city}</p>
              </div>
              <a href="/userdashboard/edit-user-address">
                <i className="fa fa-pencil" aria-hidden="true"></i>
              </a>
            </div>
          </div>
          <div className="users__dashboard__charts__left">
            <div className="users__dashboard__charts__left__title">
              <div>
                <h1>Subscriptions</h1>
                <p></p>
              </div>
              <i className="fa fa-user" aria-hidden="true"></i>
            </div>
            <br />
            <div className="users__dashboard__charts__left__info">
              <div>
                <span className="users__dashboard__subscribe__checkbox">
                  subscribe to our newsletter{" "}
                </span>
                <input type="checkbox" />
              </div>
              <a href="#">
                <i className="fa fa-pencil" aria-hidden="true"></i>
              </a>
            </div>
          </div>
          <div className="users__dashboard__charts__right">
            <div className="users__dashboard__charts__right__title">
              <div>
                <h1>Password</h1>
              </div>
              <i className="fa fa-dollar-sign" aria-hidden="true"></i>
            </div>
            <div className="users__dashboard__charts__right__cards">
              <div className="users__dashboard__charts__right__title">
                <div>
                  <p>
                    Change password?{" "}
                    <a href="/userdashboard/edit-user-pass"> Click here</a>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      ) : (
        <div>
          {/* Something */}
        </div>
      )}
    </>
  );
};

export default Dashboard;
