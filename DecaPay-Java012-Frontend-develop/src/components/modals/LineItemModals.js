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
        window.location.reload();
      } catch (error) {
        console.log(error.message);
      }
    };
    createLineItem();
    props.setItemModal(null);
    navigate("/decapay/dashboard");
  };
  const imageClick = () => {
    props.setItemModal(null);
  };
  return (
    <>
      {" "}
      {props.itemModal && (
        <div>
          {" "}
          <div className={styles.backdrop} onClick={imageClick}></div>{" "}
          <BigCard className={styles.lineitem}>
            {" "}
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
              onClick={imageClick}
            />{" "}
            <div>
              {" "}
              <p className={styles.title}>Create Budget Line Item</p>{" "}
            </div>{" "}
            <form>
              {" "}
              <label className={styles.label} htmlFor="category">
                {" "}
                Category
              </label>{" "}
              {" "}
              <div>
                {" "}
                <select
                  id="category"
                  value={form.budgetCategoryId}
                  name="budgetCategoryId"
                  onChange={handleChange}
                  className={styles.category}
                >
                  {" "}
                  <option value="">--Select--</option>{" "}
                  {budgetCategoryList.map((category) => (
                    <option key={category.id} value={category.id}>
                      {" "}
                      {category.name}
                    </option>
                  ))}
                  {/* <option value="Transportation">Transportation</option>                  <option value="Feeding">Feeding</option> */}
                </select>{" "}
              </div>{" "}
              <br /> 
              <label className={styles.label} htmlFor="amount">
                {" "}
                Amount
              </label>{" "}
              
              <div className={styles.amount}>
               {" "}
                <input
                  id="amount"
                  type="text"
                  name="projectedAmount"
                  onChange={handleChange}
                  value={form.projectedAmount}
                  placeholder="N0.00"
                />{" "}
              </div>
              <br /> <Button onClick={handleClick}>Create</Button>{" "}
            </form>{" "}
          </BigCard>{" "}
        </div>
      )}
    </>
  );
};
export default LineItemModal;
