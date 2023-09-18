import React, {useEffect, useState} from "react";
import "./BudgetCategoryList.css";
import {baseEndpoint} from "../../../globalresources/Config";
import BudgetCategoryEditModal from "./BudgetCategoryEditModal";
import Loader from "../../../globalresources/Loader";

const BudgetCategoryCard = ({ item }) => {

    const [budgetCategoryName, setBudgetCategoryName]= useState("");
    const [budgetCategoryId, setBudgetCategoryId] = useState("");

    const [responseMessage, setResponseMessage] = useState(null);
    const [isSpinning, setisSpinning] = useState(false);
    const [confirm, setConfirm] = useState(false);
    const [updateCount, setUpdateCount] = useState(0);

    const [open, setOpen] = React.useState(false);
    const handleOpen = (e) =>{
        setOpen(true);

        setBudgetCategoryName(e.target.dataset.name);
        setBudgetCategoryId(e.target.dataset.id);

    }
    const handleClose = () => setOpen(false);

    function handleDelete(e){
        setBudgetCategoryId(e.target.dataset.id)

        setConfirm(window.confirm("Would you like to delete this item ?"));


    }

    useEffect(()=>{

        if(confirm){
            setisSpinning(true);
            deleteBudgetCategoryById(budgetCategoryId);
        }
    },[budgetCategoryId]);

    const deleteBudgetCategoryById = (id) => {
        const token = localStorage.getItem("token");
        fetch(baseEndpoint + "/api/v1/budgets/category/"+id, {
            method: "DELETE",
            headers: {
                "content-type": "application/json",
                Authorization: `Bearer ${token}`
            },
           // body: JSON.stringify(data)
        }).then(response => {
            console.log(response);
            setResponseMessage("Budget Category deleted");
            setisSpinning(false);
            window.location.reload();
           // alert("Budget Category deleted");
            //setUpdateCount(1);

        }).catch(error => {
            console.log(error.message);
            setResponseMessage("error : " + error.message + "- Budget category not deleted");
            setisSpinning(false);
        });
    };


  return (
      <div className="frame-8722-XPf budgetCategoryList">
        <div className="frame-8717-FaZ">
          <p className="transportation-YJm">{item.name} <Loader status={isSpinning}/></p>
        </div>


          <div className="dropdown button-dropdown">
              <button className="btn btn-secondary dropdown-toggle dropdown-bg" type="button" id="dropdownMenuButton1"
                      data-bs-toggle="dropdown" aria-expanded="false">
                 {<img className="option-sM3 dropdown-image" src="/assets/option-uxu.png" />}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                  <li><a className="dropdown-item" href="#" onClick={handleOpen}
                         data-name={item.name}  data-id={item.id}>Edit</a></li>
                  <li><a className="dropdown-item" data-id={item.id} href="#" onClick={handleDelete}>Delete</a></li>
              </ul>
          </div>

          <BudgetCategoryEditModal
              handleClose={handleClose}
              handleOpen={handleOpen}
              open={open}
              budgetCategoryId={budgetCategoryId}
              budgetCategoryName={budgetCategoryName}
              onChange={setBudgetCategoryName}

          />

    </div>
  );
};

export default BudgetCategoryCard;
