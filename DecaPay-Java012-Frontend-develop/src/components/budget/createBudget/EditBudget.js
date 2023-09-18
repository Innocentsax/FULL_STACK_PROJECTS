import React, { useEffect, useState , useRef } from "react";
import "./CreateBudget.css";
import {Link, useParams} from "react-router-dom";
import axios from "axios";
import ResponseMessage from "../../../globalresources/modals/ResponseMessage";
import Loader from "../../../globalresources/Loader";
import {elementSelector} from "../../../globalresources/elementSelector";
import {baseEndpoint} from "../../../globalresources/Config";

function EditBudget() {
  const [budgetPeriod, changePeriod] = useState(null);
  const [formData, setFormData] = useState({});

  // const [budgetTitle, setBudgetTitle] = useState("");
  // const [budgetAmount, setBudgetAmount] = useState("");
  // const [budgetDescription, setBudgetDescription] = useState("");



  const {id} = useParams();
  const token = localStorage.getItem("token");
  const refreshPage = () => {
    window.location.reload(false);
  }

 // const budgetForm = useRef(null);

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


    const data = { ...formData, budgetPeriod};

    switch (budgetPeriod) {
      case "MONTHLY":
        data.month = formData.startDate.split("-")[1];
        data.year = formData.startDate.split("-")[0];
        break;
      case "ANNUAL":
        data.year = formData.startDate.split("-")[0];
        break;
      default:
        break;
    }

    updateBudget(data);
    console.log(data)
  };

  const updateBudget = async (data) => {
    try {
      const response = await axios.put(
        `${baseEndpoint}/api/v1/budgets/${id}`,
        data,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      console.log(response);
      setResponseMessage("Budget updated");
      setisSpinning(false);
     // budgetForm.current.reset();
     // elementSelector("form").reset();
    } catch (error) {
      console.log(error.message);
      setResponseMessage("error : "+ error.message + "- Budget not updated");
      setisSpinning(false);
    }
  };
  // const title = localStorage.getItem("title")
  // const amount = localStorage.getItem("amount")
  // const bperiod = localStorage.getItem("period")
  // const description = localStorage.getItem("description")
  //
  // setBudgetTitle(title)
  // setBudgetAmount(amount)
  // setBudgetPeriod(bperiod)
  // setBudgetDescription(description)

  const selector = (el) => document.querySelector(el);
  useEffect(() => {



    if (budgetPeriod === null) {
      selector(".start-date").style.display = "none";
      selector(".end-date").style.display = "none";
    } else if (budgetPeriod === "CUSTOM") {
      selector(".start-date").style.display = "block";
      selector(".end-date").style.display = "block";
    } else {
      selector(".start-date").style.display = "block";
      selector(".end-date").style.display = "none";
    }

    getBudgetItem(id);
  }, [budgetPeriod]);


  const getBudgetItem = async (id) => {
    try {
      const response = await axios.get(baseEndpoint + "/api/v1/budgets/"+id, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const item = response.data;
      // setBudgetTitle(item.title)
      // setBudgetAmount(item.amount)

      //setBudgetDescription(item.description)


      setFormData({
        title: item.title,
        amount : item.amount,
        description : item.description,
        startDate : item.startDate,
        endDate : item.endDate,
        year : 2100,
        month : 12
      })

    //   changePeriod(item.budgetPeriod)

      console.log(item);

      //setBudgetItem(item);
      //setBudgetLineItemList(item.lineItemRests);
    } catch (error) {
      console.log(error.message);
    }
  };

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
          <p className="create-budget-th7">Update Budget</p>
        </div>
        <form onSubmit={handleSubmit} >
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
                  value={formData.title}
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
                  value={formData.amount}
                />
              </div>
              <div className="frame-7-41F">
                <p className="budget-period-Qqo">Budget Period</p>
                <select
                  className="frame-2-LzM"
                  onChange={(e) => changePeriod(e.target.value)}
                  name="budgetPeriod"
                >
                {/* <option value={budgetPeriod} selected>{budgetPeriod}</option> */}
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
                      name="startDate"
                      onChange={handleChange}
                    />
                  </div>
                  <div className="col-6 end-date">
                    <p className="amount-ey7 date-label">End Date</p>
                    <input
                      className="frame-2-Bi9"
                      type="date"
                      name="endDate"
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
                  value= {formData.description}
                />
              </div>
            </div>
            {/* <Link to="/decapay/budget-created" className="frame-3-WA5">
              <input className="frame-3-WA5" value="Done" type="submit" />
            </Link> */}

            <button className="frame-3-WA5" type="submit">
             Done <Loader status={isSpinning}/>
            </button>
          </div>
        </form>
      </div>
      {responseMessage && <ResponseMessage message={responseMessage}  />}
    </div>
  );
}
export default EditBudget;