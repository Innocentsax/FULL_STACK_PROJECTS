import React, { useState, useEffect } from "react";
import Homepage from "./homepage/Homepage";
import Login from "./login/login";
import SignUp from "./signup/SignUp";
import NoBudgetCreated from "./budget/noBudgetCreated/NoBudgetCreated";
import BudgetCreated from "./dashboard/budgetCreated/BudgetCreated";
import CreateBudget from "./budget/createBudget/CreateBudget";
import CreateBudgetCategory from "./budgetCategory/createBudgetCategory/CreateBudgetCategory";
import CreateCategory from "./budgetCategory/createCategory/CreateCategory";
import BudgetCategory from "./budgetCategory/budgetCategoryList/BudgetCategory";
import BudgetList from "./budget/budgetList/BudgetList";
import ExpensesList from "./budgetCategory/expensesList/ExpensesList";
import BudgetCreatedDash from "./dashboard/budgetCreatedDashboard/BudgetCreatedDash";
import { Routes, Route } from "react-router-dom";

import axios from "axios";

import InternalLayout from "./Layout/internal_layout/InternalLayout";
import ResetPassword from "../passwordreset/ResetPassword";
import EditProfile from "./editProfile/EditProfile";
import UploadPhoto from "./editProfile/UploadPhoto";
import SingleBudget from "./dashboard/budgetCreatedDashboard/SingleBudget";
import EditBudget from "./budget/createBudget/EditBudget";

function App() {
  const token = localStorage.getItem("token");
  const [budgetList, setBudgetList] = useState([]);
  const [lineitems, setLineItems] = useState(false);

  console.log(lineitems);

  useEffect(() => {
    if (token != null) {
      getBudgetList();
    }
  }, []);

  const getBudgetList = async () => {
    try {
      const response = await axios.get("http://localhost:8082/api/v1/budgets", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setBudgetList(response.data);
      setLineItems(
        response.data.length > 0
          ? response.data.reverse()[0].lineItemRests.length > 0
            ? true
            : false
          : false
      );
      console.log(response.data);
    } catch (error) {
      console.log(error.message);
    }
  };

  return (
    <Routes>
      <Route path="/" element={<Homepage />} />
      <Route path="login" element={<Login />} />
      <Route path="signup" element={<SignUp />} />
      <Route path="/reset-password/:token" element={<ResetPassword />} />

      <Route path="decapay" element={<InternalLayout />}>
        <Route
          path="dashboard"
          element={
            budgetList.length === 0 ? (
              <NoBudgetCreated />
            ) : lineitems ? (
              <BudgetCreatedDash />
            ) : (
              <BudgetCreated />
            )
          }
        />

        <Route path="single-budget/:id" element={<SingleBudget />} />

        <Route
          path="budget"
          element={
            budgetList.length === 0 ? <NoBudgetCreated /> : <BudgetList />
          }
        />

        <Route path="create-budget" element={<CreateBudget />} />
        <Route path="edit-budget/:id" element={<EditBudget />} />
        <Route path="budget-created" element={<BudgetCreated />} />
        <Route
          path="create-budget-category"
          element={<CreateBudgetCategory />}
        />
        <Route path="create-category" element={<CreateCategory />} />
        <Route path="budget-category" element={<BudgetCategory />} />
        <Route path="budget-list" element={<BudgetList />} />

        <Route
          path="dashboard-budget-created"
          element={<BudgetCreatedDash />}
        />

        <Route path="expenses-list/:id/:category" element={<ExpensesList />} />
        {/* <Route path='logout' element={<BudgetCreated/>}/> */}
        <Route path="profile" element={<EditProfile />} />
        <Route path= "upload" element={<UploadPhoto/>}/>
      </Route>
    </Routes>
  );
}

export default App;
