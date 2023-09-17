import React, { useEffect, useState } from "react";
import {
  Grid,
  Container,
  Segment,
  Button,
  Checkbox,
  Form,
} from "semantic-ui-react";
import "../styles/Layout/_success-layout.scss";
const handleBackToHome = (e) => {
  e.preventDefault();
  window.location.href = "/";
};

function SuccessPaymentLayout() {
  return (
    <Container textAlign="center" className="success-container">
      <Grid className="">
        <Grid.Column width="4"></Grid.Column>
        <Grid.Column width="8">
          <Segment>
            <img src="../images/success.png" />
            <h1>Payment Successful!!! </h1>
            <button
              id="success"
              onClick={handleBackToHome}
              className="success-class"
            >
              Home
            </button>
          </Segment>
        </Grid.Column>
        <Grid.Column width="4"></Grid.Column>
      </Grid>
    </Container>
  );
}

export default SuccessPaymentLayout;
