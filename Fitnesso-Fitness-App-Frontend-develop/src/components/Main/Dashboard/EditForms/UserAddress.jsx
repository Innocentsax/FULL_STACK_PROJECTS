import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const UserAddress = () => {
  const userInfo = JSON.parse(localStorage.getItem("peopleData"));
  const username = localStorage.getItem("username");
  const userAdd = userInfo.address;
  const navigate = useNavigate();

  const [streetDetail, setStreetDetail] = useState(userAdd.streetDetail);
  const [city, setCity] = useState(userAdd.city);
  const [state, setState] = useState(userAdd.state);
  const [country, setCountry] = useState(userAdd.country);
  const [zipCode, setZipCode] = useState(userAdd.zipCode);
  const [id] = useState(userAdd.id);

  async function deleteAddress(e) {
      e.preventDefault();
      const url = `http://localhost:9067/address/delete_address/${id}`;

      try {
          confirm("Delete account");
          await axios.delete(url);
          navigate("/userdashboard");
      } catch (err) {
          console.log(err);
      }
  }

  async function sendAddUpdate(e) {
    e.preventDefault();

    const addressBody = {
      zipCode: zipCode,
      streetDetail: streetDetail,
      country: country,
      state: state,
      city: city,
      username: username,
      id: id
    };

    const url = `http://localhost:9067/address/update_address/`;

    try {
      await axios.put(url, addressBody);

      alert("Address edited!");
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
            value={streetDetail}
            onChange={(event) => setStreetDetail(event.target.value)}d
          />

          <input
            type="text"
            className="username"
            align="center"
            value={city}
            onChange={(event) => setCity(event.target.value)}
          />

          <input
            type="text"
            className="username"
            align="center"
            value={state}
            onChange={(event) => setState(event.target.value)}
          />

          <input
            type="text"
            className="username"
            align="center"
            value={country}
            onChange={(event) => setCountry(event.target.value)}
          />

          <input
            type="text"
            className="username"
            align="center"
            value={zipCode}
            onChange={(event) => setZipCode(event.target.value)}
          />
        </div>

        <div className="user-edit-btns">
          <button type="submit" className="submit" onClick={sendAddUpdate}>
            Edit Address
          </button>

          <p className="forgot" align="center">
            <a href="#" onClick={deleteAddress}>
              Delete Address?
            </a>{" "}
          </p>
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

export default UserAddress;
