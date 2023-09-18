import styles from "./LineItemModal.module.css";
import { useEffect, useState } from "react";
import BigCard from "../../UI/BigCard";
import Button from "../../UI/Button";
import axios from "axios";
import { useNavigate } from "react-router-dom";
// import cancel from '../../UI/icons/close-cKT.png'

const LineItemModal = (props) => {
  // const[itemModal, setItemModal]= useState(true)
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const [form, setForm] = useState({
    projectedAmount: "",
    budgetCategoryId: "",
  });

  const [budgetCategoryList, setBudgetCategoryList] = useState([]);

  useEffect(() => {
    const fetchBudgetCategory = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8082/api/v1/budgets/category",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        setBudgetCategoryList(response.data);
      } catch (error) {
        console.log(error.message);
      }
    };
    fetchBudgetCategory();
  }, []);

  const handleChange = (event) => {
    const { value, name } = event.target;
    setForm((prevForm) => ({
      ...prevForm,
      [name]: value,
    }));
  };

  const handleClick = () => {
    form.budgetId = props.budgetId;
    console.log(form);

    const createLineItem = async () => {
      try {
        const response = await axios.post(
          "http://localhost:8082/api/v1/user/line-items",
          form,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        console.log(response);
      } catch (error) {
        console.log(error.message);
      }
    };
    createLineItem();
    props.setItemModal(null);
    navigate("/decapay/dashboard");
  };

  const removeModal = () => {
    props.setExpenseModal(null);
  };

  return (
    <>
      {props.expenseModal && (
        <div>
          <div className={styles.backdrop} onClick={removeModal}></div>
          <BigCard className={styles.lineitem}>
            <img
              src="/assets/close-gFb.png"
              alt="cancel icon "
              style={{
                width: "30px",
                height: "30px",
                position: "relative",
                left: "452px",
                top: "40px",
              }}
              onClick={removeModal}
            />

            <div>
              <p className={styles.title}>Create Budget Line Item</p>
              {props.expenses.map((expense) => (
                <p key={expense.id}>{expense.description}</p>
              ))}
            </div>
            
          </BigCard>
        </div>
      )}
    </>
  );
};

export default LineItemModal;
