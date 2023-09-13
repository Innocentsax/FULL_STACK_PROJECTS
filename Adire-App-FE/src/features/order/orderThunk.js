import customFetch from '../../utils/axios';
import { showLoading, hideLoading, getAllOrders } from '../allOrders/allOrdersSlice'
import { clearOrderValues } from './orderSlice';



export const createOrderThunk = async (order, thunkAPI) => {
    try {
        const resp = await customFetch.post('/api/order/create-order', order);
        thunkAPI.dispatch(clearOrderValues());
        console.log(resp.data);
        console.log("order : " + order);
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

export const deleteOrderThunk = async (orderId, thunkAPI) => {
    thunkAPI.dispatch(showLoading());
    try {
        const resp = await customFetch.delete(`/api/order/deleteOrder/${orderId}`);
        thunkAPI.dispatch(getAllOrders());
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
    
export const editOrderThunk = async ({ orderId, order }, thunkAPI) => {
    console.log(order);
    try {
        const resp = await customFetch.put(`/api/order/updateOrder/${orderId}`, order);
        thunkAPI.dispatch(clearOrderValues());
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





