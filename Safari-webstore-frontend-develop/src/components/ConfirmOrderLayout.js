import React, { useEffect, useState } from "react";
import {
  Grid,
  Container,
  Segment,
  Button,
  Checkbox,
  Form,
} from "semantic-ui-react";
import OrderApi from "../apis/OrderApi";
import productApis from "../apis/ProductApi";

import "../styles/Layout/_confirm_order_layout.scss";
import { monetize } from "../utilities/helperFunction";

function ConfirmOrderLayout() {
  const data = localStorage.getItem("order");
  const parsedData = JSON.parse(data);

  const address = parsedData.data.shippingAddress.address;

  const phone = parsedData.data.shippingAddress.phone;

  const fullName = parsedData.data.shippingAddress.fullName;

  const orderedDate = parsedData.data.dateOrdered;

  const paymentType = parsedData.data.paymentType;

  const orderedItems = parsedData.data.orderedItems;

  const subtotal = parsedData.data.costOfProducts;

  const total = parsedData.data.totalCost;

  const deliveryFee = parsedData.data.deliveryFee;

  const handlePayement = async () => {
    const orderData = JSON.parse(localStorage.getItem("order"));

    const response = await OrderApi.placePaymentOrder(orderData.data.orderId);

    window.location.href = response.data;
  };

  useEffect(() => {
    const buttonPayment = document.getElementById("btnMakePayment");

    const confirmMessage = document.getElementById("confirmationMessage");

    if (paymentType === "Pay on delivery") {
      buttonPayment.style.display = "none";

      confirmMessage.style.display = "block";
    }
    if (paymentType === "Pay with card") {
      buttonPayment.style.display = "block";

      confirmMessage.style.display = "none";
    }
  }, []);

  return (
    <Container className="confirm-order">
      <Grid className="confirm-order-inner">
        <Grid.Column width="10">
          <h1>Confirm Order</h1>
          <Segment>
            <h3>Address Details</h3>
            <Segment>
              <h4>{fullName}</h4>
              <p>{`${address}` + " " + `${phone}`} </p>
            </Segment>
          </Segment>
          <Segment>
            <h3>Delivery Details</h3>
            <Segment>
              <p>
                <strong>Payment Method:</strong> {paymentType}
              </p>
            </Segment>
            <Segment>
              <strong>Order Date:</strong> {orderedDate}
            </Segment>
            {/* <Segment>
                <strong>Payment Type: </strong> Card
              </Segment> */}
          </Segment>
        </Grid.Column>
        <Grid.Column width="6">
          <h2>Order Summary</h2>
          <Segment>
            <h3>Your Order({orderedItems.length})</h3>
            {orderedItems.map((each) => {
              {
                console.log("llllllll", each);
              }
              return (
                <Grid className="order">
                  <Grid.Column width="5">
                    <img
                      style={{ width: "50px" }}
                      src={each.productImage}
                      alt={each.name}
                    />
                  </Grid.Column>
                  <Grid.Column width="11">
                    <p>{each.name}</p>
                    <p>₦{monetize(each.price)}</p>
                    <p>QTY: {parsedData.data.quantity}</p>
                  </Grid.Column>
                </Grid>
              );
            })}
          </Segment>

          <Segment className="confirm-totals">
            <p>
              <strong>Subtotal:</strong> ₦{monetize(subtotal)}
            </p>
            <br />
            <p>
              <strong>Delivery Fee: ₦{monetize(deliveryFee)}</strong>
            </p>
            <br />

            <p>
              <strong>Total: </strong>₦{monetize(total)}
            </p>
          </Segment>

          <button
            id="btnMakePayment"
            onClick={handlePayement}
            className="payment-button"
          >
            Make Payment
          </button>
        </Grid.Column>
      </Grid>
      <Grid>
        <Container textAlign="center" id="confirmationMessage">
          <Segment>
            <h1>Thank You for the Order</h1>
            <h3>Order Confirmation</h3>
            <p>Order Number: {parsedData.data.orderId}</p>
          </Segment>
        </Container>
      </Grid>
    </Container>
  );
}

export default ConfirmOrderLayout;
