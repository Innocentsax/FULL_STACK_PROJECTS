import customFetch from '../../utils/axios';
import { showLoading, hideLoading, getAllCustomers } from '../allCustomers/allCustomersSlice'
import { clearCustomerValues } from './customerSlice';

export const createCustomerThunk = async (customer, thunkAPI) => {
    try {
        const resp = await customFetch.post('/api/customer/register', customer);
        thunkAPI.dispatch(clearCustomerValues());
        console.log(resp.data);
        console.log("customer : " + customer);
        return resp.data;
    } catch (error) {
        console.log(error.response);
        // basic setup
        // return thunkAPI.rejectWithValue(error.response.data.msg);
        // logout user
        // if (error.response.status === 401) {
        //     thunkAPI.dispatch(logoutUser());
        //     return thunkAPI.rejectWithValue('Unauthorized! Logging Out...');
        // }
        // return thunkAPI.rejectWithValue(error.response.data.msg);
        return thunkAPI.rejectWithValue(error.response.data);
    }
}

export const deleteCustomerThunk = async (customerId, thunkAPI) => {
    thunkAPI.dispatch(showLoading());
    try {
        const resp = await customFetch.delete(`/api/customer/deleteCustomer/${customerId}`);
        thunkAPI.dispatch(getAllCustomers());
        console.log(resp.data);
        return resp.data;
    } catch (error) {
        thunkAPI.dispatch(hideLoading());
        console.log(error.response);
        // return thunkAPI.rejectWithValue(error.response.data);


        // basic setup
        // return thunkAPI.rejectWithValue(error.response.data.msg);
        // logout user
        // if (error.response.status === 401) {
        //     thunkAPI.dispatch(logoutUser());
        //     return thunkAPI.rejectWithValue('Unauthorized! Logging Out...');
        // }
        // return thunkAPI.rejectWithValue(error.response.data.msg);
        
    }
}

export const editCustomerThunk = async ({ customerId, customer }, thunkAPI) => {
    console.log(customer);
    try {
        const resp = await customFetch.put(`/api/customer/editCustomer/${customerId}`, customer);
        thunkAPI.dispatch(clearCustomerValues());
        console.log(resp.data);
        return resp.data;
    } catch (error) {
        // thunkAPI.dispatch(hideLoading());
        console.log(error.response);
        // return thunkAPI.rejectWithValue(error.response.data);

        // basic setup
        // return thunkAPI.rejectWithValue(error.response.data.msg);
        // logout user
        // if (error.response.status === 401) {
        //     thunkAPI.dispatch(logoutUser());
        //     return thunkAPI.rejectWithValue('Unauthorized! Logging Out...');
        // }
        // return thunkAPI.rejectWithValue(error.response.data.msg);
        
    }
}