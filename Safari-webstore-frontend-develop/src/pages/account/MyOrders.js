import React, { useEffect, useState } from "react";
import { Grid, GridColumn, Segment, Container } from "semantic-ui-react";
import DashboardLayout from "../../components/userdashboard/DashboardLayout";
import "../../styles/Layout/_myorders_layout.scss";
import Footer from "../../components/Footer";
import OrderApi from "../../apis/OrderApi";

const MyOrders = (props) => {
  const [userOrders, setUserOrders] = useState([]);

  useEffect(() => {
    (async () => {
      const userOrdersList = await OrderApi.getOrdersByUser();

      setUserOrders(userOrdersList.content);
    })();
  }, []);

  return (
    <>
      <Container fluid className="myorders">
        <DashboardLayout>
          <Grid>
            <h3>My Orders</h3>
          </Grid>
          <Segment>
            <Grid>
              {userOrders.map((order) => (
                <>
                  <GridColumn mobile={16} tablet={8} computer={8}>
                    <Grid>
                      {console.log("PRINTING ORDER", order)}
                      <GridColumn mobile={8} tablet={8} computer={8}>
                        <img
                          // src="../images/Casual Flat Loafers 2.png"
                          src={order.orderedItems[0].productImage}
                          alt={order.orderedItems[0].name}
                          className="order-image"
                        />
                        <button className="order-status">Pending</button>
                      </GridColumn>
                      <GridColumn mobile={8} tablet={8} computer={8}>
                        <p className="order-title">
                          {order.orderedItems.productImage}
                        </p>
                        <span className="order-size">
                          {order.orderedItems.size}
                        </span>
                        <p className="order-ammount">
                          {order.orderedItems.price}
                        </p>
                        <span>Qty: {order.quantity}</span>
                        <p className="order-date">{order.dateOrdered}</p>
                      </GridColumn>
                    </Grid>
                  </GridColumn>
                  <GridColumn mobile={16} tablet={8} computer={4}>
                    <p className="order-payment">Payment Details</p>
                    <span className="payment-details">
                      Item’s total - ₦{order.totalCost}
                    </span>
                    <p className="payment-details">
                      Delivery fee - ₦{order.deliveryFee}{" "}
                    </p>
                    <span className="payment-details">
                      TOTAL - ₦ {order.totalCost}{" "}
                    </span>
                  </GridColumn>
                  <GridColumn mobile={16} tablet={8} computer={4}>
                    <p className="order-payment">Delivery Method</p>
                    <span className="payment-detail">Door delivery</span>
                    <p className="order-payment">Shipping address</p>
                    <span className="payment-detail">
                      {`${order.shippingAddress.address}, ${order.shippingAddress.city}`}
                    </span>
                  </GridColumn>
                </>
              ))}
              {/* <GridColumn mobile={16} tablet={8} computer={8}>
                <Grid>
                  <GridColumn mobile={8} tablet={8} computer={8}>
                    <img
                      src="../images/Casual Flat Loafers 2.png"
                      alt=""
                      className="order-image"
                    />
                    <button className="order-status">Delivered</button>
                  </GridColumn>
                  <GridColumn mobile={8} tablet={8} computer={8}>
                    <p className="order-title">Casual flat loafers</p>
                    <span className="order-size">Size - EU: 36 US: 5.5</span>
                    <p className="order-ammount">₦ 10,000</p>
                    <span>Qty: 1</span>
                    <p className="order-date">On 20/07/2020</p>
                  </GridColumn>
                </Grid>
              </GridColumn>
              <GridColumn mobile={16} tablet={8} computer={4}>
                <p className="order-payment">Payment Details</p>
                <span className="payment-details">
                  Item’s total - ₦ 10,000{" "}
                </span>
                <p className="payment-details">Delivery fee - ₦ 2,000 </p>
                <span className="payment-details">TOTAL - ₦ 12,000 </span>
              </GridColumn>
              <GridColumn mobile={16} tablet={8} computer={4}>
                <p className="order-payment">Delivery Method</p>
                <span className="payment-detail">Door delivery</span>
                <p className="order-payment">Shipping address</p>
                <span className="payment-detail">
                  Ayokunle Oriolowo 4, Barnawa Close, Barnawa Kaduna.
                </span>
              </GridColumn> */}
            </Grid>
            <Grid>
              <button className="order-btn order-again-btn">Order Again</button>
              <button className="order-btn request-return-btn">
                Request Return
              </button>
            </Grid>
          </Segment>
        </DashboardLayout>
      </Container>
    </>
  );
};
export default MyOrders;
