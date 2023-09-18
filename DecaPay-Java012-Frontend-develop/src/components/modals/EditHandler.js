import { logDOM } from "@testing-library/react";
import { useState } from "react";
import { baseEndpoint } from "../../globalresources/Config";
import BigCard from "../../UI/BigCard";
import styles from "./EditHandler.module.css";

const EditHandler = (props) => {

  const token = localStorage.getItem("token");
  const [editBtn, setEditBtn] = useState();
  const [form, setForm] = useState({
    budgetAmount: "",
  });
  // const[editModal, setEditModal]= useState(true)

  const editHandler = () => {
    const requestEdit = async () => {
      const req = await fetch(`${baseEndpoint}/api/v1/budgets/${props.id}`, {
        method: "PUT",
        body: props.editValue,
        headers: {
          contentType: "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      const data = await req.json();
      console.log(data);
    };

    requestEdit()
      .then(() => {
        console.log("saved");
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const cancelHandler = () => {
    props.setCallEdit(null);
  };
  const imageClick = () => {
    props.setCallEdit(null);
  };
  const values = props.editValue.find((value) => value.budgetId === props.id);
  console.log(values);
  return (
    <div>
      <div className={styles.backdrop} onClick={imageClick}></div>

      <BigCard className={styles.edit}>
        <form className="container form-container">
          <br />

          <center>
            <label htmlFor="amount">Budget Amount</label>
            <br />

            <input
              className={styles.amount}
              type="number"
              name="budgetAmount"
              placeholder={values.amount}
              id="amount"
            />

            <br />

            <label htmlFor="total-amount">Total Amount spent</label>
            <br />
            <input
              className={styles.amount}
              type="number"
              name="totalAmount"
              placeholder={values.totalAmountSpent}
              id="total-amount"
            />
            <br />
            <label htmlFor="percent">Percentage</label>
            <br />
            <input
              className={styles.amount}
              type="number"
              name="percentage"
              placeholder={values.percentage * 100 + "%"}
              id="percent"
            />
            <br />
            <button className={styles.btns} onClick={editHandler}>
              Save
            </button>
            <button className={styles.btns} onClick={cancelHandler}>
              Cancel
            </button>
          </center>
        </form>
      </BigCard>
    </div>
  );
};
export default EditHandler;
