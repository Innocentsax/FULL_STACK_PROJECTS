import React, { useEffect, useState } from "react";
import { Calendar } from "react-calendar";
import "./BudgetCreated.css";
import axios from "axios";
import LineItemModal from "../../modals/LineItemModals";
import Loader from "../../../globalresources/Loader";
import {baseEndpoint, currencySymbol} from "../../../globalresources/Config";

function BudgetCreated() {
  const token = localStorage.getItem("token");

  const [value, onChange] = useState(new Date());
  const [itemModal, setItemModal] = useState(false);

  const [budgetItem, setBudgetItem] = useState({});
  const [budgetLineItemList, setBudgetLineItemList] = useState([]);

  const refreshPage = () => {
    //window.location.reload(false);
  }

  useEffect(() => {
    if (token !== null) {
      getBudgetItem();
    }
  }, []);

  const getBudgetItem = async () => {
    try {
      const response = await axios.get(baseEndpoint+"/api/v1/budgets", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const item = response.data.reverse()[0];
      setBudgetItem(item);
      setBudgetLineItemList(item.lineItemRests);
    } catch (error) {
      console.log(error.message);
    }
  };

  const createBudgetHandler = () => {
    setItemModal(true);
    refreshPage();
  };

  return (
      <main>
        <div className="row mt-5 ">
          <div className="  item-wrapper">
            <div className="item-wrapper-border">
              <div className="">
                <div className="row">
                  <div className="col-12 mt-b-5 budget-header">
                    <span>My Budget</span><br/><br/>
                    <h1><span dangerouslySetInnerHTML={ { __html: currencySymbol.naira}}></span>{budgetItem.amount}</h1>
                  </div>

                  <div className="col-6 mt-b-5">
                    <table border="2">
                      <tr><td>Total Amount</td></tr>
                      <tr><td><h1> &#8358;{budgetItem.totalAmountSpent}</h1></td><td>
                        <img
                            className="frame-8759-fQm"
                            src="/assets/frame-8759.png"
                        />
                      </td>
                      </tr>
                    </table>
                  </div>

                  <div className="col-6 mt-b-5">
                    <table border="2">
                      <tr><td>Percentage</td></tr>
                      <tr><td><h1>{budgetItem.percentage}%</h1></td><td>
                        <img
                            className="frame-8759-fQm"
                            src="/assets/frame-8762.png"
                        /></td>
                      </tr>
                    </table>
                  </div>
                </div>
                <br/>
                <div className="">
                  <div className="calender-box">
                    <Calendar onChange={onChange} value={value} />
                    <h5>{value.toString()}</h5>

                    <div className="">
                      <p className="text-info">
                        No line item found in this Budget{" "}
                      </p>
                    </div>
                    <div>
                      <button className="action-button" onClick={createBudgetHandler}>+ Create Budget Line Item
                      </button>
                      <div>
                        {itemModal && (
                            <LineItemModal
                                itemModal={itemModal}
                                setItemModal={setItemModal}
                                budgetId={budgetItem.budgetId}
                            />
                        )}
                      </div>
                      <div>
                      </div>

                    </div>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </main>
  );
}

export default BudgetCreated;