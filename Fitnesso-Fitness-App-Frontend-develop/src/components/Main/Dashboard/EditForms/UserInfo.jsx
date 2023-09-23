import React, { useState } from "react";
import axios from "axios";
import {useNavigate} from 'react-router-dom';

const UserInfo = () => {
  const userInfo = JSON.parse(localStorage.getItem("peopleData"));
  const token = localStorage.getItem("token");
  console.log(userInfo);
  const navigate = useNavigate();

  const [firstName, setFirstName] = useState(userInfo.firstName);
  const [lastName, setLastName] = useState(userInfo.lastName);
  const [phoneNumber, setPhoneNumber] = useState(userInfo.phoneNumber);
  const [gender, setGender] = useState(userInfo.gender);
  const [dateOfBirth, setDateOfBirth] = useState(userInfo.dateOfBirth);
  
  async function sendEditRequest(e) {
      e.preventDefault();

      const editRequestBody = {
          firstName: firstName,
          lastName: lastName,
          phoneNumber: phoneNumber,
          gender: gender,
          userName: userInfo.userName,
          dateOfBirth: dateOfBirth,
      };

      
      const url = `https://fitnesso-app-new.herokuapp.com/person/profile/edit/personinfo/`;

      try {
          await axios.put(url, editRequestBody, {
            headers: { Authorization: `Bearer ${token}`},
          });

          alert("Details changed successfully!");
          return navigate("/userdashboard");
      } catch (err) {
          console.log(err);
      }
  }

  return (
    <div>
      <form className="user-edit-form">
        <div className="user-edit-input-fields">

          <input
            type="text"
            className="username"
            align="center"
            value={firstName}
            onChange={(event) => setFirstName(event.target.value)}
          />

          <input
            type="text"
            className="username"
            align="center"
            value={lastName}
            onChange={(event) => setLastName(event.target.value)}
          />

          <input
            type="number"
            className="username"
            align="center"
            value={phoneNumber}
            pattern={/^(?:(?:\\+|0{0,2})91(\s*[\\-]\s*)?|[0]?)?[789]\d{9}$/}
            onChange={(event) => setPhoneNumber(event.target.value)}
          />
        </div>
        <div>
          <select value={gender} className="username" onChange={(event) => setGender(event.target.value)}>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Prefer not to say">Rather not say</option>
          </select>

          <div className="username">
              <i class="fas fa-calendar"></i>
              <input className="username" placeholder="Enter BirthDay" type="date" value={dateOfBirth} pattern=" enter BirthDay \d{4}-\d{2}-\d{2}" onChange={(event) => setDateOfBirth(event.target.value)}/>
            </div>

        </div>

        <div className="user-edit-btns">
          <button
            type="submit"
            className="submit"
            onClick={sendEditRequest}
            // href="/userdashboard"
          >
            Edit Info
          </button>
          <br></br>
          <br></br>
          <p className="cancel" align="center">
            <a href="/userdashboard">Cancel</a>{" "}
          </p>
          <br></br>
        </div>
      </form>
    </div>
  );
};

export default UserInfo;
