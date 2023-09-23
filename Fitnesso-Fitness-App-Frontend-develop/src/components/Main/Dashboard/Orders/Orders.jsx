import React, { useCallback, useEffect, useState } from "react";
// import "../UserDashboard.css";
import './Orders.css'

const Orders = () => {
  const [orders, setOrders] = useState([]);
  const [isLoading, setIsLoaing] = useState(false);
  const [offset, setOffset] = useState(0);
  const [total, setTotal] = useState(0);
  const [pageCount, setPageCount] = useState(0);
  const [pageLimit] = useState(5);

  return (
    <div className="order-container">
      <header className="db-component-header">
          <h1>My Orders ({total}) </h1>
        </header>
      <article className="order-article">
        <div className="order-details">
            <img src="#" alt="Product Image Loading" />
            <div className="order-info">
                <h3>Order No:</h3>
                <p>Order Date:</p>
                <p>Order Total Cost:</p>
                <div className="order-status">
                    Order Status:
                </div>
            </div>
            <a href="#" className="order-see-more">See Details</a>
        </div>
      </article>
    </div>
  );
};

export default Orders;
