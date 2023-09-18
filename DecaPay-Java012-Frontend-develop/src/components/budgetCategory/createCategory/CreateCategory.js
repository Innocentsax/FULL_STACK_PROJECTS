import React, {useState} from "react";
import "./CreateCategory.css";
import ResponseMessage from "../../../globalresources/modals/ResponseMessage";
import Loader from "../../../globalresources/Loader";
import {baseEndpoint} from "../../../globalresources/Config";
import {Link} from "react-router-dom";
function CreateCategory() {
  const [budgetCategoryName, setBudgetCategoryName]= useState("");

  const [responseMessage, setResponseMessage] =useState(null);
  const [isSpinning, setisSpinning]=useState(false);

  const handleBudgetCategorySubmit = (e) =>{

    e.preventDefault();

    setResponseMessage(null);
    setisSpinning(true);
    const categoryName = {name :budgetCategoryName };

    createBudgetCategory(categoryName);

  }
  const createBudgetCategory = (data) => {
    const token = localStorage.getItem("token");
    fetch(baseEndpoint+"/api/v1/budgets/category/create",{
      method:"POST",
      headers:{
        "content-type":"application/json",
        Authorization: `Bearer ${token}`
      },
      body:JSON.stringify(data)
    }).then(response=>{
      console.log(response);
      setResponseMessage("Budget Category Added");
      setisSpinning(false);

      setBudgetCategoryName("");
    }).catch(error=>{
      console.log(error.message);
      setResponseMessage("error : "+ error.message + "- Budget category not added");
      setisSpinning(false);
    });
  };

  return (
    <div className="create-category-decapay-Da9">
      <img className="ellipse-4-XKw" src="/assets/ellipse-4-f3X.png" />
      <div className="frame-8794-Sho">
        <div className="frame-8780-ySq">
          <Link to="/decapay/budget-category" className="back-zbj">
          <img className="back-arrow-f3f" src="/assets/back-arrow.png" />
          </Link>
        </div>
        <div className="frame-8793-h1T">
          <p className="what-do-you-usually-spend-on-qNZ">
            What do you usually spend on?
          </p>

          <div className="frame-8792-wRb">
            <div className="frame-4-uNR">
              <form onSubmit={handleBudgetCategorySubmit}>
                <p className="name-of-category-Tuj" >Name of Category</p>
                <input name="categoryName"
                  className="frame-2-PYV" value={budgetCategoryName}
                  placeholder="Enter name of item"
                       onChange={(e)=>setBudgetCategoryName(e.target.value)}
                /> 
                <br/><br/>
                  <button className="frame-8754-GcH" type="submit">Add
                    <Loader status={isSpinning}/>
                  </button>

              </form>
            </div>
          </div>
        </div>
      </div>

      {responseMessage && <ResponseMessage message={responseMessage}  />}
    </div>
  );
}

export default CreateCategory;
