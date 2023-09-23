import React, { useState, useEffect } from "react";
import useSWR from "swr";
import Tada from "react-reveal/Tada";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart, faHeartBroken } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import axios from "axios";

function FavoriteButton(props) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const loggedInUser = localStorage.getItem("peopleData");
    if (loggedInUser) {
      setUser(JSON.parse(loggedInUser));
    }
  }, []);

  // IF USER IS NOT IN LOCAL STORAGE, ASKED TO LOG IN INSTEAD
  if (user === null) {
    return (
      <p className="text_small">
        <span>
          <Link to="/login">Log in to favorite this item!</Link>
        </span>
      </p>
    );
  }
  return <FindInitialState username={user.userName} itemId={props.itemId} />;
}

function FindInitialState(props) {
  const [data, setData] = useState();
  const token = localStorage.getItem("token");
  const config = {
    headers: { Authorization: `Bearer ${token}` },
  };

  let favBool;

  const url = `http://localhost:9067/person/check_fave_default/${props.username}/${props.itemId}`;
  useEffect(() => {
    const fetchData = async () => {
      const response = await axios.post(url, {
        headers: { Authorization: `Bearer ${token}` },
      });
      const value = response.data;
      setData(value);
    };
    fetchData();
  });

  if (data === undefined) {
    return <div>loading...</div>;
  } else if (data || !data) {
    favBool = data;
    return (
      <SetStateAndToggle
        favBool={favBool}
        username={props.username}
        itemId={props.itemId}
      />
    );
  } else {
    return <div>failed to load</div>;
  }
}

function SetStateAndToggle(props) {
  const currentlyAFavorite = <FontAwesomeIcon icon={faHeart} />;
  const notCurrentlyAFavorite = <FontAwesomeIcon icon={faHeartBroken} />;

  const [favorite, setFavorite] = useState(props.favBool);

  const url = `http://localhost:9067/person/handle_favourite/${props.username}/${props.itemId}`;
  const toggleFavorite = async (itemId) => {
      const response = await axios.post(url);
      const data = response.data;
      setFavorite(data);
      console.log("This is data: " +data);
   
    
    console.log(favorite);
  }

  return (
    <Tada spy={favorite}>
      <button
        className="favorite-button"
        onClick={() => toggleFavorite(props.itemId)}
        key={props.itemId}
      >
        {favorite === true ? currentlyAFavorite : notCurrentlyAFavorite}
      </button>
    </Tada>
  );
}

export { FavoriteButton };
