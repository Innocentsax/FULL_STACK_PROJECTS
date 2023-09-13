import customFetch from '../../utils/axios';
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
// import { createCustomerThunk, deleteCustomerThunk, editCustomerThunk } from './customerThunk';


const initialState = {
    isLoading: false,
    numOfOrders: 0,
    numOfCustomers: 0,
    totalRevenue: 0,
    monthlyChart: [],
};



export const fetchDashboardInfo = createAsyncThunk(
    'dashboard/fetchDashboardInfo',
    async (_, thunkAPI) => {
        try {
            const [countCustomers, monthlyChartResp, orderReportResp] = await Promise.all([
            // const [monthlyChartResp, orderReportResp] = await Promise.all([
                customFetch.get('/api/customer/countCustomers'),
                customFetch.get('/api/order/monthly-Chart-report'),
                customFetch.get('/api/order/report'),
            ])
            // console.log({ monthlyChartResponse: monthlyChartResponse.data, orderReportResponse: orderReportResponse.data, countCustomers: countCustomers.data });
            // console.log({ monthlyChartResponse, orderReportResponse });
            // return { monthlyChartResponse: monthlyChartResponse.data, orderReportResponse: orderReportResponse.data, countCustomers: countCustomers }
            console.log(countCustomers);
            return { monthlyChartResp: monthlyChartResp.data, orderReportResp: orderReportResp.data, countCustomers: countCustomers.data }
        } catch (error) {
            console.log(error.response.data);
            // return thunkAPI.rejectWithValue(error.response.data.msg);
        }
        
    }
)


const dashboardSlice = createSlice({
    name: 'dashboard',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchDashboardInfo.pending, (state) => {
                console.log("isPending");
                state.isLoading = true;
            })
            .addCase(fetchDashboardInfo.fulfilled, (state, { payload }) => {
                console.log("isFulfilled");
                const { monthlyChartResp, orderReportResp, countCustomers } = payload;
                state.numOfOrders = orderReportResp.numberOfOrders;
                state.numOfCustomers = countCustomers;
                state.totalRevenue = orderReportResp.totalRevenue;
                console.log(payload);
                state.isLoading = false;
            })
            .addCase(fetchDashboardInfo.rejected, (state, { payload }) => {
                console.log("isRejected");
                // console.log(payload);
                state.isLoading = false;
                // toast.error(payload);
            })
    }
});


// export const {  } = dashboardSlice.actions;


export default dashboardSlice.reducer;