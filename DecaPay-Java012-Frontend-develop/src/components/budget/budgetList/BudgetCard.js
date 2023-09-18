import React, {useEffect, useState} from "react";
import "./BudgetList.css";
import {baseEndpoint} from "../../../globalresources/Config";
import BudgetEditModal from "./BudgetEditModal";
import Loader from "../../../globalresources/Loader";
import {Link} from "react-router-dom";

const BudgetCard = ({ item }) => {

    const [budgetName, setBudgetName]= useState("");
    const [budgetId, setBudgetId] = useState("");
    const [budgetAmount, setBudgetAmount]= useState(0);
    const [amountSpent, setAmountSpent] = useState(0);
    const [percentage, setPercentage] = useState(0);

    const [budgetDetails, setBudgetDetails] = useState({});

    const [responseMessage, setResponseMessage] = useState(null);
    const [isSpinning, setisSpinning] = useState(false);
    const [confirm, setConfirm] = useState(false);
    const [updateCount, setUpdateCount] = useState(0);
    const [count, setCount] = useState(0);

    const [open, setOpen] = React.useState(false);
    const handleOpen = (e) =>{
        setOpen(true);
        setBudgetName(e.target.dataset.name);
        setBudgetId(e.target.dataset.id);
        setBudgetAmount(e.target.dataset.amount);
        setAmountSpent(e.target.dataset.amount_spent);
        setPercentage(e.target.dataset.percentage);

        setBudgetDetails({
            budgetId: e.target.dataset.id,
            budgetName: e.target.dataset.name,
            budgetAmount: e.target.dataset.amount,
            amountSpent: e.target.dataset.amount_spent,
            percentage: e.target.dataset.percentage
        });

    }

    const handleClose = () => setOpen(false);

    function handleDelete(e){
        setBudgetId(e.target.dataset.id)

        setConfirm(window.confirm("Would you like to delete this item ?"));


    }

    useEffect(()=>{

        if(confirm){
            setisSpinning(true);
            deleteBudgetById(budgetId);
        }
    },[budgetId]);

    const deleteBudgetById = (id) => {
        const token = localStorage.getItem("token");
        fetch(baseEndpoint + "/api/v1/budgets/"+id, {
            method: "DELETE",
            headers: {
                "content-type": "application/json",
                Authorization: `Bearer ${token}`
            },
           // body: JSON.stringify(data)
        }).then(response => {
            console.log(response);
            setResponseMessage("Budget deleted");
            setisSpinning(false);
            window.location.reload();

        }).catch(error => {
            console.log(error.message);
            setResponseMessage("error : " + error.message + "- Budget not deleted");
            setisSpinning(false);
        });
    };


  return (
      <div className="frame-8722-XPf budgetList item-box">
        <div className="">
          <p className="transportation-YJm">Budget: #{item.budgetId} <Loader status={isSpinning}/></p>
            <p className="budget-amount-WzV">
                Budget amount: <span>{item.amount}</span>
            </p>
            <p className="budget-amount-WzV">
                Total amount spent: <span>{item.totalAmountSpent}</span>
            </p>
            <p className="budget-amount-WzV">
                Percentage: <span>{item.percentage} %</span>
            </p>
        </div>


          <div className="dropdown button-dropdown">
              <button className="btn btn-secondary dropdown-toggle dropdown-bg" type="button" id="dropdownMenuButton1"
                      data-bs-toggle="dropdown" aria-expanded="false">
                 {<img className="option-sM3 dropdown-image" src="/assets/option-uxu.png" />}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                  <li><Link className="dropdown-item" to={`/decapay/edit-budget/${item.budgetId}`}
                         data-name={item.name}
                         data-id={item.id}
                         data-amount={item.amount}
                         data-amount_spent={item.totalAmountSpent}
                         data-percentage = {item.percentage}>Edit</Link></li>
                  <li>
                      <a className="dropdown-item" data-id={item.budgetId} href="#"
                         onClick={handleDelete}>Delete
                        </a>
                  </li>

                  <li>
                      <Link
                          to={{
                              pathname:
                                  "/decapay/single-budget/" +
                                  item.budgetId
                          }}
                          className="dropdown-item"
                      >
                          View
                      </Link>

                  </li>
              </ul>
          </div>

          {/* <BudgetEditModal
              handleClose={handleClose}
              handleOpen={handleOpen}
              open={open}
              budgetId={budgetId}
              budgetName={budgetName}
              budgetAmount = {budgetAmount}
              amountSpent = {amountSpent}
              percentage = {percentage}
              setBudgetAmount = {setBudgetAmount}
              setAmountSpent = {amountSpent}
              setPercentage = {setAmountSpent}

          /> */}

    </div>
  );
};

export default BudgetCard;
