import React, { useState, useEffect } from "react";
import axios from "axios";
import "./cart.css";
import { AiTwotoneDelete, AiTwotoneEdit } from "react-icons/ai";

const USER_CART = "http://localhost:8080/api/v1/customer/carts"; 
const ALTER_CART_ITEM_QUANTITY = "http://localhost:8080/api/v1/customer/carts/";
const DELET_CART_ITEM = "http://localhost:8080/api/v1/customer/carts/";


const Cart = () => {
  // const data = cartObject.cartItemDto;
  const [values, setValues] = useState([]);
  const [quantity, setQuantity] = useState(1);
  const getJWT = () =>  localStorage.getItem("currentUser");


  const getCartInfo = () => {
   axios.get(USER_CART, {
      headers: { Authorization: getJWT() },
    }).then(res=>{
        const data = res.data;
        const todo = data.cartItemDto;
        setValues(todo);
        console.log(values)
    }).catch(err=>
        console.log(err)
    );
  };

  function handleChange(e) {
    setQuantity(e.target.value);
  }

  function handleDelete(cartItemId) {
    axios.delete(DELET_CART_ITEM + cartItemId, {
      headers: { Authorization: getJWT() },
    });

    const data = values.filter((item) => {
      return item.id !== cartItemId;
    });

    setValues(data);
  }

  const handleIncrement = (id) => {
    const newValue = values.map((each) => {
      if (each.id === id) {
        let newTotal = each.price * quantity;
        each.total = newTotal;
      }
      return each;
    });
    setValues(newValue);
  };

  const sumAll = values.reduce((acc, curr) => {
    return acc + curr.total;
  }, 0);

  const handleAlter = async (id) => {
    // console.log(getAuth)
    console.log(id);
    console.log(quantity);
    try {
      const response = await axios.put(
        ALTER_CART_ITEM_QUANTITY,
        { cartItemId: id, quantity: quantity },
        { headers: { Authorization: getJWT() } }
      );
      // console.log(response)
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getCartInfo();
  }, []);

  useEffect(() => {
    handleIncrement();
  }, [quantity]);

  return (
    <div className="cart">
      <div className="cart-container">
        <h1 className="container">Cart</h1>
      </div>
      <table className="table table-striped container">
        <thead>
          <tr>
            <th scope="col">UPDATE</th>
            <th scope="col">DELETE</th>
            {/* <th scope="col">IMAGE</th> */}
            <th scope="col">PRODUCT</th>
            <th scope="col">PRICE</th>
            <th scope="col">QUANTITY</th>
            <th scope="col">SUBTOTAL</th>
          </tr>
        </thead>
        <tbody>
          {values.length === 0 ? (
            <tr>
              <td colSpan={7}>No product in cart</td>{" "}
            </tr>
          ) : (
            values.map((item, index) => (
              <tr key={index}>
                <td>
                  <button
                    className="custome--btn"
                    onClick={() => handleAlter(item.id)}
                  >
                    <AiTwotoneEdit />
                  </button>
                </td>

                <td>
                  <button
                    className="custome--btn"
                    onClick={() => handleDelete(item.id)}
                  >
                    <AiTwotoneDelete />
                  </button>
                </td>
                <td>
                  <img src={item.img} alt="" />
                </td>
                <td>{item.product}</td>
                <td>{item.price}</td>
                <td>
                  <input
                    type="number"
                    name="quantity"
                    onChange={(e) => handleChange(e)}
                    defaultValue={item.quantity}
                    min="1"
                    onClick={() => handleIncrement(item.id)}
                  />
                </td>
                <td> &#x20A6; {item.total.toFixed(2)}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>

      <div className="container cart--footer">
        <div>
          <h1>Cart Total</h1>
          <table className="table table-striped" style={{ width: "300px" }}>
            <tbody>
              <tr>
                <td>Cart total</td>
                <td>&#x20A6; {sumAll.toFixed(2)}</td>
              </tr>
            </tbody>
          </table>
          <button className="proceed-btn">Proceed to checkout</button>
        </div>
      </div>
    </div>
  );
};
export default Cart;
