import React, {useEffect, useState} from 'react';
import {baseEndpoint} from "../../../globalresources/Config";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import Loader from "../../../globalresources/Loader";



    const BudgetEditModal = (props)  => {
        // props.persist();

        const [budgetName, setBudgetName]= useState(props.budgetName);
        const [responseMessage, setResponseMessage] = useState(null);
        const [isSpinning, setisSpinning] = useState(false);

        //setBudgetCatName(props.budgetCategoryName);
            //alert(budgetCatName)
        //
        // useEffect(()=>{
        //     if(props.budgetCategoryName!==""){
        //         setBudgetCatName(props.budgetCategoryName);
        //     }
        //
        // },[budgetCatName],)

        const handleBudgetSubmit = (e) => {
            e.preventDefault();

            setResponseMessage(null);
            setisSpinning(true);
            const categoryName = {name: budgetName};

            editBudget(categoryName);

        }
        const editBudget = (data) => {
            const token = localStorage.getItem("token");
            fetch(baseEndpoint + "/api/v1/budgets/category/update/"+props.budgetCategoryId, {
                method: "PUT",
                headers: {
                    "content-type": "application/json",
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify(data)
            }).then(response => {
                console.log(response);
                setResponseMessage("Budget Category updated");
                setisSpinning(false);
                window.location.reload();

            }).catch(error => {
                console.log(error.message);
                setResponseMessage("error : " + error.message + "- Budget category not updated");
                setisSpinning(false);
            });
        };

        {
            return (
                <div>
                    <Modal
                        open={props.open}
                        onClose={props.handleClose}
                        aria-labelledby="modal-modal-title"
                        aria-describedby="modal-modal-description"
                    >
                        <Box className="modal-box">
                            <div><span class="modal-title">Budget Update</span>
                                <button className="btnClose" onClick={props.handleClose}>X</button>
                            </div>
                            <br/>
                            <form onSubmit={handleBudgetSubmit}>
                                <span>Budget Amount</span>
                                <input name="categoryName"
                                       className="frame-2-PYV form-input" value={props.budgetAmount}
                                       placeholder={props.budgetAmount}
                                       onChange={(e) => props.setBudgetAmount(e.target.value)}
                                       readOnly={false} required type="number"
                                />
                                <span >Total Amount spent</span>
                                <input name="amountSpent"
                                       className="frame-2-PYV form-input" value={props.amountSpent}
                                       placeholder={props.amountSpent}
                                       onChange={(e) => setBudgetName(e.target.value)}
                                       readOnly={false} required type="number"
                                />
                                <span >Percentage</span>
                                <input name="budgetName"
                                       className="frame-2-PYV form-input" value={props.percentage}
                                       placeholder={props.percentage}
                                       onChange={(e) => setBudgetName(e.target.value)}
                                       readOnly={false} required type="number"
                                />
                                <br/><br/>
                                {responseMessage && <span className="text-success responseStatus">{responseMessage}</span>}
                                <button className="frame-8754-GcH form-button" type="submit">Update
                                    <Loader status={isSpinning}/>
                                </button>
                            </form>

                        </Box>
                    </Modal>
                </div>
            );
        }
    }


export default BudgetEditModal;