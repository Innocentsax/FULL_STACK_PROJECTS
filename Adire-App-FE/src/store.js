import { configureStore } from '@reduxjs/toolkit';

import userSlice from './features/user/userSlice';
import modalSlice from './features/modal/modalSlice';
import customerSlice from './features/customer/customerSlice';
import allCustomersSlice from './features/allCustomers/allCustomersSlice';
import dashboardSlice from './features/dashboard/dashboardSlice';
import orderSlice from './features/order/orderSlice';
import allOrdersSlice from './features/allOrders/allOrdersSlice';

export const store = configureStore({
  reducer: {
    user: userSlice,
    modal: modalSlice,
    customer: customerSlice,
    allCustomers: allCustomersSlice,
    order: orderSlice,
    allOrders: allOrdersSlice,
    dashboard: dashboardSlice,
  },
});