import React, { useState, useEffect } from "react";
import "./BudgetCreatedDash.css";
import { Calendar } from "react-calendar";
import LineItemModal from "../../modals/LineItemModals";
import axios from "axios";
import {baseEndpoint, currencySymbol} from "../../../globalresources/Config";
import LogModal from "../../modals/LogModal";
import {Link, useParams} from "react-router-dom";

function SingleBudget() {
    const token = localStorage.getItem("token");
    const [value, onChange] = useState(new Date());
    const [itemModal, setItemModal] = useState(false);
    const [logModal, setLogModal] = useState(false);
    const [lineId, setLineId] = useState(null);
    const [budgetItem, setBudgetItem] = useState({});
    const [budgetLineItemList, setBudgetLineItemList] = useState([]);

    const  {id} = useParams();
    useEffect(() => {
        if (token !== null) {
            getBudgetItem(id);
        }
    }, []);
    const getBudgetItem = async (id) => {
        try {
            const response = await axios.get(baseEndpoint + "/api/v1/budgets/"+id, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            const item = response.data;
            console.log(item);
            setBudgetItem(item);
            setBudgetLineItemList(item.lineItemRests);
        } catch (error) {
            console.log(error.message);
        }
    };
    const createBudgetHandler = () => {
        setItemModal(true);
    };
    const handleLogExpense = (id) => {
        setLogModal(true);
        setLineId(id);
        console.log(id);
    };
    return (
        <main>
            {" "}
            <div className="dashboard-decapay-qhb">
                {" "}
                <img className="ellipse-4-9iH" src="/assets/ellipse-4-c4h.png" />{" "}
                <div className="frame-8768-seH">
                    {" "}
                    <div className="frame-8770-mzZ">
                        {" "}
                        <div className="auto-group-e345-jAh">
                            {" "}
                            <div className="frame-8767-Fuj">
                                {" "}
                                <div className="frame-8766-Cpy">
                                    {" "}
                                    <div className="frame-8757-m7P">
                                        {" "}
                                        <div className="frame-8756-uUV">
                                            {" "}
                                            <div className="rectangle-2-GK3"></div>{" "}
                                            <div className="frame-8625-Qg9">
                                                {" "}
                                                <div className="my-budget-Y1f">My Budget</div>{" "}
                                                <div className="n3000000-TPX"><span dangerouslySetInnerHTML={ { __html: currencySymbol.naira}}></span> {budgetItem.amount}</div>{" "}
                                            </div>{" "}
                                            <img
                                                className="ellipse-3-BaR"
                                                src="/assets/ellipse-3-5YD.png"
                                            />{" "}
                                        </div>{" "}
                                    </div>{" "}
                                    <div className="frame-8765-Wch">
                                        {" "}
                                        <div className="frame-8761-rAm">
                                            {" "}
                                            <div className="frame-8760-acZ">
                                                {" "}
                                                <div className="frame-8758-Y3b">
                                                    {" "}
                                                    <div className="total-amount-spent-Hms">
                                                        {" "}
                                                        Total Amount
                                                        <br /> spent
                                                    </div>{" "}
                                                    <div className="n20000-1Sy">
                                                        {" "}
                                                        <span dangerouslySetInnerHTML={ { __html: currencySymbol.naira}}></span> {budgetItem.totalAmountSpent}
                                                    </div>{" "}
                                                </div>{" "}
                                                <img
                                                    className="frame-8759-wrR"
                                                    src="/assets/frame-8759-5kM.png"
                                                />{" "}
                                            </div>{" "}
                                        </div>{" "}
                                        <div className="frame-8764-Tpm">
                                            {" "}
                                            <div className="frame-8763-1LV">
                                                {" "}
                                                <div className="percentage-xmX">Percentage</div>{" "}
                                                <div className="item-375-Hoo">
                                                    {" "}
                                                    {budgetItem.percentage}%
                                                </div>{" "}
                                            </div>{" "}
                                            <img
                                                className="frame-8762-2Fb"
                                                src="/assets/frame-8762-fWh.png"
                                            />{" "}
                                        </div>{" "}
                                    </div>{" "}
                                </div>{" "}
                                <div className="calendar-2022-839-2022-month-05-may-LXB">
                                    {" "}
                                    <div className="header-fJZ">
                                        {" "}
                                        {/* <div className="calendar-2022-839-atoms-head-mcV">May</div> */}
                                    </div>{" "}
                                    <div>
                                        {" "}
                                        <Calendar onChange={onChange} value={value} />{" "}
                                        <p style={{ fontSize: "11px" }}>{value.toString()}</p>{" "}
                                    </div>{" "}
                                </div>{" "}
                            </div>{" "}
                            {budgetLineItemList.map((item) => (
                                <div key={item.lineItemId} className="frame-8771-Kww">
                                    {" "}
                                    <div className="frame-8747-sTf">
                                        {" "}
                                        <div className="frame-8745-nad">
                                            {" "}
                                            <div className="groceries-KaZ">{item.category}</div>{" "}
                                            <div className="projected-amount-n5500-TRs">
                                                {" "}
                                                <span className="projected-amount-n5500-TRs-sub-0">
                          {" "}
                                                    Projected amount:&nbsp;&nbsp;
                        </span>{" "}
                                                <span className="projected-amount-n5500-TRs-sub-1">
                          {" "}
                                                    N{item.projectedAmount}
                        </span>{" "}
                                            </div>{" "}
                                            <div className="frame-8751-Y5w">
                                                {" "}
                                                <div className="amount-so-far-n2500-hUd">
                                                    {" "}
                                                    <span className="amount-so-far-n2500-hUd-sub-0">
                            {" "}
                                                        Amount Spent:&nbsp;&nbsp;
                          </span>{" "}
                                                    <span className="amount-so-far-n2500-hUd-sub-1">
                            {" "}
                                                        N{item.amountSpentSoFar}
                          </span>{" "}
                                                </div>{" "}
                                                <Link
                                                    to={{
                                                        pathname:
                                                            "/decapay/expenses-list/" +
                                                            item.lineItemId +
                                                            "/" +
                                                            item.category,
                                                    }}
                                                >
                                                    <div className="view-expenses-fZs">View expenses</div>
                                                </Link>{" "}
                                            </div>{" "}
                                        </div>{" "}
                                        <div className="frame-8746-nPb">
                                            {" "}
                                            <div
                                                className="frame-8639-XMB"
                                                onClick={() => handleLogExpense(item.lineItemId)}
                                            >
                                                {" "}
                                                <div className="log-ewb">Log</div>{" "}
                                                <img
                                                    className="arrow-up-right-xSV"
                                                    src="./assets/arrow-up-right-PXP.png"
                                                />{" "}
                                            </div>{" "}
                                            <div className="item-375-s3f">
                                                {" "}
                                                {item.percentageSpentSoFar}%
                                            </div>{" "}
                                        </div>{" "}
                                    </div>{" "}
                                </div>
                            ))}
                        </div>{" "}
                        <div className="frame-8754-Jhw">
                            {" "}
                            <img className="plus-dVK" src="./assets/plus-fJR.png" />{" "}
                            <div className="create-budget-B13" onClick={createBudgetHandler}>
                                {" "}
                                Create Budget Line Item
                            </div>{" "}
                        </div>{" "}
                    </div>{" "}
                </div>{" "}
            </div>{" "}
            <div>
                {" "}
                {itemModal && (
                    <LineItemModal
                        itemModal={itemModal}
                        setItemModal={setItemModal}
                        budgetId={budgetItem.budgetId}
                    />
                )}
            </div>{" "}
            <div>
                {" "}
                {logModal && (
                    <LogModal
                        logModal={logModal}
                        setLogModal={setLogModal}
                        lineId={lineId}
                    />
                )}
            </div>{" "}
        </main>
    );
}
export default SingleBudget;
