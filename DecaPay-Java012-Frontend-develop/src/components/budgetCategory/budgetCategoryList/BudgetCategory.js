import React from "react";
import "./BudgetCategoryList.css";
import BudgetCategoryCard from "./BudgetCategoryCard";
import { useState, useEffect } from "react";
import {baseEndpoint} from "../../../globalresources/Config";
import BudgetCategoryList from "./BudgetCategoryList";
import CreateBudgetCategory from "../createBudgetCategory/CreateBudgetCategory";

function BudgetCategory() {

    const [list, setList] = useState([]);

    useEffect(() => {
        const getBudgetCategoryList = async() => {
            const taskFromServer = await fetchBudgetCategories();
            setList(taskFromServer);
        }

        getBudgetCategoryList()
    }, []);

    const fetchBudgetCategories = async ()=> {
        const token = localStorage.getItem("token");

        const res = await fetch(baseEndpoint+'/api/v1/budgets/category', {
            method: 'GET',
            headers: {
                Authorization: `Bearer ${token}`,
            }
        });

        const data = await res.json();
        return data;
    }

    return (
        <>
            {list.length>0 ?<BudgetCategoryList list={list} /> : <CreateBudgetCategory />}

        </>
    );
}

export default BudgetCategory;
