import React from "react";
import "./BudgetList.css";
import BudgetCard from "./BudgetCard";
import { useState, useEffect } from "react";
import {baseEndpoint, pageLimit} from "../../../globalresources/Config";

import BudgetEditModal from "./BudgetEditModal";
import {Link, useNavigate} from "react-router-dom";
import ReactPaginate from "react-paginate";
import {Pagination} from "@mui/material";


function BudgetList() {
    const [list, setList] = useState([]);
    const [currentPage , setCurrentPage] = useState(0);
    const [pageTotal, setPageTotal] = useState(0);
    const navigate = useNavigate();
  const token = localStorage.getItem("token");


  useEffect(() => {
    const getBudgetList = async () => {
      const taskFromServer = await fetchBudgets();

       let totalPages=  Math.ceil(taskFromServer[0]["totalBudgets"]/pageLimit);
       setPageTotal(totalPages);
       setList(taskFromServer);
    };

    getBudgetList();
  }, [currentPage]);

  const pageHandler = (event , value) => {
      setCurrentPage(value)
    }

  const fetchBudgets = async () => {
    const res = await fetch(baseEndpoint + "/api/v1/budgets?page="+currentPage+"&limit="+pageLimit, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    const data = await res.json();
    return data;
  };



    return (
        <div className="budget-category-list-decapay-sY5">

            <img className="back-arrow-f3f" src="/assets/back-arrow.png"/>
            <div className="frame-8797-XMj">
                <div className="frame-8780-TWH">
                <Link to="/decapay/dashboard" className="back-zbj">
              Back
            </Link>
                </div>

                <div className="frame-8796-dZB">
                    <div className="frame-8795-Bqb">
                        {list.length > 0 ? list.map((item,index) => (<BudgetCard key={item.id} item={item} index={index+1}/>)) : ""}
                    </div>
                    <Pagination
                        className="my-3"
                        count={pageTotal}
                        page={currentPage}
                        siblingCount={1}
                        boundaryCount={1}
                        variant="outlined"
                        shape="rounded"
                        onChange={pageHandler}

                    />
                    <div className="frame-8754-bER">
                        <img className="plus-iZw" src="/assets/plus-zeD.png" />
                        <div className="create-budget-TnR">
                            <Link to="/decapay/create-budget" className="text-white">Create Budget </Link></div>
                    </div>
                </div>

            </div>

        </div>
    );
}

export default BudgetList;
