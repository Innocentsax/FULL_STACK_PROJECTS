import React from "react";
import "./CreateBudgetCategory.css";
import { Link } from "react-router-dom";

function CreateBudgetCategory() {

  
  return (
    <div className="budget-created-decapay-hsF">
      <img className="ellipse-4-28q" src="/assets/ellipse-4-vW5.png" />
      <div className="frame-8791-jow">
        <div className="frame-8790-3Jq">
          <div className="frame-8785-xgh">
            <p className="plan-ahead-your-expenses-and-take-total-control-7Jh">
              Plan ahead your expenses and take total control
            </p>
            <p className="take-control-of-your-money-by-tracking-expenses-Cqw">
              Take control of your money by tracking expenses
            </p>
          </div>
          <img className="budget-iZP" src="/assets/budget-Cws.png" />
        </div>
        <div className="frame-8754-eT3">
          <img className="plus-ykD" src="/assets/plus-FSq.png" />

          <Link to="/decapay/create-category" >
          <div className="create-budget-vfT">Create Budget Category</div>
          </Link>
        </div>
      </div>
    </div>
  );
}

export default CreateBudgetCategory;
