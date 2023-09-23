import axios from "axios";
import React, { useState, useEffect } from "react";
import fisherShuffle from "../ProductDisplay/Shuffler";
import "./Trainers.css";

const Trainers = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [trainers, setTrainers] = useState([]);
  const url = `http://localhost:9067/person/view_trainers`;

  useEffect((e) => {
    getTrainers(e);
  }, []);

  const getTrainers = async (e) => {
    const response = await axios.get(url);
    const newData = fisherShuffle(response.data).slice(0, 3);
    setTrainers(newData);
    setIsLoading(true);
  };
  return (
    <div className="trainers">
      <div className="trainers_text">
        <span className="colour-free"> See Our Trainers</span>
      </div>
      <div className="trainers_section">
        {trainers.map((item) => (
          <div className="trainers_details" key={item.id}>
            <div className="big-img">
              <img className='trainer_img' src={item.trainer.image} alt="" />
            </div>

            <div className="box">
              <div className="row">
                <h2>{`${item.trainer.firstName} ${item.trainer.lastName}`}</h2>
              </div>
              {/* <Colors colors={item.colors} /> */}

              <p>{item.description}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Trainers;
