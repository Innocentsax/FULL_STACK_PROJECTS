import React, { useEffect, useState , useRef } from "react";
import "./CreateBudget.css";
import { Link } from "react-router-dom";
import axios from "axios";
import ResponseMessage from "../../../globalresources/modals/ResponseMessage";
import Loader from "../../../globalresources/Loader";
import {elementSelector} from "../../../globalresources/elementSelector";

function CreateBudget() {
  const [period, changePeriod] = useState(null);
  const [formData, setFormData] = useState({});

  const refreshPage = () => {
    window.location.reload(false);
  }

  const budgetForm = useRef(null);

  const [responseMessage, setResponseMessage] =useState(null);
  const [isSpinning, setisSpinning]=useState(false);

  const handleChange = (e) => {
    e.persist();
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    setResponseMessage(null);
    setisSpinning(true);

    const data = { ...formData, period };

    switch (period) {
      case "MONTHLY":
        data.month = formData.budgetStartDate.split("-")[1];
        data.year = formData.budgetStartDate.split("-")[0];
        break;
      case "ANNUAL":
        data.year = formData.budgetStartDate.split("-")[0];
        break;
      default:
        break;
    }

    createBudget(data);
  };

  const createBudget = async (data) => {
    const token = localStorage.getItem("token");

    try {
      const response = await axios.post(
        "http://localhost:8082/api/v1/budgets",
        data,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      console.log(response);
      setResponseMessage("Budget added");
      setisSpinning(false);
      budgetForm.current.reset();
     // elementSelector("form").reset();
    } catch (error) {
      console.log(error.message);
      setResponseMessage("error : "+ error.message + "- Budget not added");
      setisSpinning(false);
    }
  };

  const selector = (el) => document.querySelector(el);
  useEffect(() => {
    if (period === null) {
      selector(".start-date").style.display = "none";
      selector(".end-date").style.display = "none";
    } else if (period === "CUSTOM") {
      selector(".start-date").style.display = "block";
      selector(".end-date").style.display = "block";
    } else {
      selector(".start-date").style.display = "block";
      selector(".end-date").style.display = "none";
    }
  }, [period]);

  return (
    <div className="create-budget-decapay-8vu">
      <div className="frame-8812-s7o">
        <div className="frame-8811-b3o">
          <div className="frame-8780-iPK">
            <img className="back-arrow-f3f" src="/assets/back-arrow.png" />
            <Link to="/decapay/dashboard" className="back-zbj">
              Back
            </Link>
          </div>
          <p className="create-budget-th7">Create Budget</p>
        </div>
        <form onSubmit={handleSubmit} ref={budgetForm}>
          <div className="frame-8666-RS9">
            <div className="frame-6-yiZ">
              <div className="frame-4-vNu">
                <p className="title-gcy">Title</p>
                <input
                  className="frame-2-d2R"
                  placeholder="Enter title"
                  name="title"
                  type="text"
                  onChange={handleChange}
                />
              </div>
              <div className="frame-6-VqK">
                <p className="amount-ey7">Amount</p>
                <input
                  className="frame-2-Bi9"
                  placeholder="Enter amount"
                  name="amount"
                  type="number"
                  onChange={handleChange}
                />
              </div>
              <div className="frame-7-41F">
                <p className="budget-period-Qqo">Budget Period</p>
                <select
                  className="frame-2-LzM"
                  onChange={(e) => changePeriod(e.target.value)}
                  name="period"
                >
                  <option value="null"></option>
                  <option value="DAILY">DAILY</option>
                  <option value="WEEKLY">WEEKLY</option>
                  <option value="MONTHLY">MONTHLY</option>
                  <option value="ANNUAL">ANNUAL</option>
                  <option value="CUSTOM">CUSTOM</option>
                </select>
              </div>
              <div className="frame-7-41F dateInput">
                <div className="row">
                  <div className="col-6 start-date">
                    <p className="amount-ey7 date-label">Start Date</p>
                    <input
                      className="frame-2-Bi9"
                      type="date"
                      name="budgetStartDate"
                      onChange={handleChange}
                    />
                  </div>
                  <div className="col-6 end-date">
                    <p className="amount-ey7 date-label">End Date</p>
                    <input
                      className="frame-2-Bi9"
                      type="date"
                      name="budgetEndDate"
                      onChange={handleChange}
                    />
                  </div>
                </div>
              </div>
              <div className="frame-5-XZ3">
                <p className="description-tuP">Description</p>
                <input
                  className="frame-3-d6H"
                  placeholder="Add description here..."
                  type="text"
                  name="description"
                  onChange={handleChange}
                />
              </div>
            </div>
            {/* <Link to="/decapay/budget-created" className="frame-3-WA5">
              <input className="frame-3-WA5" value="Done" type="submit" />
            </Link> */}

            <button className="frame-3-WA5" type="submit"  onClick={refreshPage}>
             Done <Loader status={isSpinning}/>
            </button>
          </div>
        </form>
      </div>
      {responseMessage && <ResponseMessage message={responseMessage}  />}
    </div>
  );
}
export default CreateBudget;
