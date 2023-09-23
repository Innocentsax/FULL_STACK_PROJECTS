import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const UserPassword = () => {
  const userName = localStorage.getItem("username");
  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  async function sendPassRequest(e) {
      e.preventDefault();

      const updatePasswordReq = {
          currentPassword: currentPassword,
          newPassword: newPassword,
          confirmPassword: confirmPassword,
          userName: userName,
      };

      const url = `https://fitnesso-app-new.herokuapp.com/person/profile/edit/password`;

      try {
          const response = await axios.put(url, updatePasswordReq, {
            headers: { Authorization: `Bearer ${token}`},
          })

          alert(`${response.data.message}`);
          if(response.data.message !== `password successfully changed`) {
            setCurrentPassword("");
            setNewPassword("");
            setConfirmPassword("");
              return navigate('/userdashboard/edit-user-pass');
          }
          
          return navigate('/userdashboard');
      } catch (err) {
          console.log(err);
      }
  }

  return (
    <div>
      <form className="edit-password-container">
        <div className="edit-pass-fields">
          <div>
            <input
              type="password"
              placeholder="Enter Current Password"
              align="center"
              className="username"
              value={currentPassword}
              onChange={(event) => setCurrentPassword(event.target.value)}
            />
          </div>
          <div>
            <input
              type="password"
              align="center"
              placeholder="Enter New Password"
              className="username"
              value={newPassword}
              onChange={(event) => setNewPassword(event.target.value)}
            />
          </div>
          <div>
            <input
              type="password"
              placeholder="Confirm New Password"
              align="center"
              className="username"
              value={confirmPassword}
              onChange={(event) => setConfirmPassword(event.target.value)}
            />
          </div>
        </div>
        <div>
          <button type="submit" className="submit" onClick={sendPassRequest}>
            Update Password
          </button>
        </div>
      </form>
    </div>
  );
};

export default UserPassword;
