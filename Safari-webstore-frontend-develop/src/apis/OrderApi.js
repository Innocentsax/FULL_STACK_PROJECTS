import axios from "axios";
import BaseUrl from "./BaseUrl";
import setHeader from "../utilities/Header";

const OrderApi = {
  //getOrderByUser: async () => {
    //const { data: userOrders } = await axios.get(
      //`${BaseUrl}/orders/user`,
      //setHeader()
    //);


    getOrdersByUser: async () => {
      const { data: userOrders } = await axios.get(
        `${BaseUrl}/api/orders/user`, 
       setHeader()
      );
      
      return userOrders;
    },
   

  adminGetAllOrders: async () => {

        const { data: allOrders } = await axios.get(
          `${BaseUrl}/api/orders/admin`,
          setHeader()
        );
        
        return allOrders;
      },


  placeUserOrders: async (data) => {
    console.log(setHeader());
    return await axios.post(`${BaseUrl}/api/check-out`, data, setHeader());
  },

  placePaymentOrder: async (data) => {
    return await axios.get(`${BaseUrl}/api/payment/pay/${data}`, setHeader());
  },
};

export default OrderApi;
