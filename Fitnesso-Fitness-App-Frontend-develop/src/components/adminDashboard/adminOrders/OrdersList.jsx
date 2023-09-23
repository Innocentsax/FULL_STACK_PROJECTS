import React, {useState, useEffect} from 'react'
import ViewOrder from './ViewOrder'

const OrdersList = () => {
    const [orderData, setOrderData] = useState({});
    const orderHistoryUrl = 'https://fitnesso-app-new.herokuapp.com/product/';

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(orderHistoryUrl);
            const data = await response.json();
            setOrderData(orderData);
        }
    })

  return (
    <div className="admin-dashboard-board">
        <table width="100%">
            <thead>
                <tr>
                    <td>Order Id</td>
                    <td>User</td>
                    <td>Status</td>
                    <td>Amount</td>
                    <td>Order Date</td>
                    <td>Delivered date</td>
                    <td>Action</td>
                  
                </tr>
            </thead>
            <tbody>
                {/* {
                    orderData ? orderData.map(order=>(
                        <ViewOrder
                        id={order.id} 
                        status={order.status}
                        user={order.user}
                        amount={order.amount}
                        order_date={order.order_date} 
                        delivered_date={order.delivered_date} 
                        paymentMethod={order.paymentMethod} 
                        />
                    )) : (
                        <div><h1>This is order list section</h1></div>
                    )

                } */}
                <ViewOrder
                        id={`1`} 
                        status={`COMPLETED`}
                        user={`Francis Taiye`}
                        amount={`$27450`}
                        order_date={`27/11/2021`} 
                        delivered_date={`04/12/2021`} 
                        paymentMethod={`Free things o!`} 
                        />

            </tbody>

        </table>


    </div>
  )
}

export default OrdersList;