import React, { useEffect, useState } from "react";
import "./index.css";
// import  Pagination  from "./Pagination";
import axios from "axios";
import { ReactComponent as FirstBank } from "./images/newfirstb.svg";
import { useNavigate } from "react-router-dom";

export const TransactionList = () => {
  const navigate = useNavigate();

  const [values, setValues] = useState([]);
  const token = localStorage.getItem("accessToken");
  // const [ currentPage, setCurrentPage] = useState(0);
  if (!token) {
    navigate("/login");
  }

  const fetchData = async () => {
    try {
      const response = await axios.get(
        "https://mentorship-payment-app.herokuapp.com/api/v1/user/view-transaction-history",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      const result = response.data.result.content;
      console.log("our result", result);
      setValues(result);
    } catch (error) {
      console.log(error.message);
    }
  };

  useEffect(() => {
    fetchData();

    // eslint-disable-next-line
  }, []);
  // const indexOfLastPost = currentPage * postsPerPage;
  // const indexOfFirstPost = indexOfLastPost - postsPerPage;
  // const currentPost = values.slice(indexOfFirstPost, indexOfLastPost);

  return (
    <>
      <div className='note-tag'>
        <p>Transaction history</p>
      </div>
      {values.map((item, index) => {
        return (
          <div className='mother' key={index}>
            <div className='left'>
              <FirstBank />
              <div className='transaction-history-middle'>
                <p>{item.name}</p>
                <div className='transaction-history-bottom'>
                  <p>{item.bank}</p>
                  <p>{item.transactionTime}</p>
                </div>
              </div>
            </div>
            <div className='transaction-history-right'>
              <p>{item.amount}</p>
            </div>
          </div>
        );
        // <Pagination />
      })}
    </>
  );
};
