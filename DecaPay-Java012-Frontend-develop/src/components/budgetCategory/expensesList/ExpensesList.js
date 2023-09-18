import "./ExpensesList.css";
import React, { useState, useEffect } from "react";
import axios from "axios";
import moment from "moment";
import { Params, useParams } from "react-router-dom";
import { baseEndpoint } from "../../../globalresources/Config";
import { Link } from "react-router-dom";

import Pagination from "react-paginate";

function ExpensesList() {
  const token = localStorage.getItem("token");
  const [expenses, setExpenses] = useState([]);
  const { id, category } = useParams();

  const [currentPage, setCurrentPage] = useState(0);
  const [itemsPerPage, setItemsPerPage] = useState(3);

  useEffect(() => {
    if (token !== null) {
      getExpenses();
    }
  }, []);

  const getExpenses = async () => {
    try {
      const response = await axios.get(
        baseEndpoint + "/api/v1/expenses/" + id + "/expenses",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      const expenseLists = response.data;
      console.log(expenseLists);
      setExpenses(expenseLists.content);
    } catch (error) {
      console.log(error.message);
    }
  };

  return (
    <div className="expenses-decapay-du3">
      <img className="ellipse-4-yCD" src="/assets/ellipse-4-NUq.png" />
      <div className="frame-8782-VRT">
      
            <div className="frame-8797-XMj">
                <div className="frame-8780-TWH">
                <Link to="/decapay/dashboard" className="back-zbj">
                <img className="back-arrow-f3f" src="/assets/back-arrow.png"/>
            </Link>
           </div>
          {/* <p className="transportation-expenses-7r9">{category}</p> */}
          
        </div>

        
        {expenses
          .slice(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage)
          .map((expense) => (
            <div className="frame-8779-3jo" key={expense.id}>
              <div className="frame-8775-Br1">
                <div className="frame-8772-Vrh">
                  <p className="amount-qff">Amount</p>
                  <p className="n5000-AC9">{expense.amount}</p>
                </div>
                <div className="frame-8773-HXf">
                  <p className="description-Eho">Description</p>
                  <p className="lorem-ipsum-dolor-sit-amet-rus-consectetur-adipiscing-elit-aWm">
                    {expense.description}.{" "}
                  </p>
                </div>
                <div className="frame-8774-5yK">
                  <p className="time-logged-qxV">Time Logged</p>
                  <p className="pm-z4h">
                    {moment(expense.createdAt).format("h:mm A")}
                  </p>
                </div>
              </div>
            </div>
          ))}
        <Pagination
          pageCount={Math.ceil(expenses.length / itemsPerPage)}
          onPageChange={({ selected }) => setCurrentPage(selected)}
        />
      </div>
    </div>
  );
}

export default ExpensesList;
