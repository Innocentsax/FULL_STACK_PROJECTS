// import { store } from '../../store';
import customFetch, { errorResponse } from '../../utils/axios';
import { clearAllOrdersState } from '../allOrders/allOrdersSlice';
import { clearAllCustomersState } from '../allCustomers/allCustomersSlice';
import { clearOrderValues } from '../order/orderSlice';
import { clearCustomerValues } from '../customer/customerSlice';
import { logoutUser } from './userSlice';

export const registerUserThunk = async (url, user, thunkAPI) => {
  try {
    const resp = await customFetch.post(url, user);
    return resp.data;
  } catch (error) {
    console.log(error.response);
    return thunkAPI.rejectWithValue(error.response.data.msg);
  }
};

export const googleRegisterThunk = async (url, token, thunkAPI) => {
  try {
    const resp = await customFetch.post(url, token);
    console.log(resp.data);
    return resp.data;
  } catch (error) {
    console.log(error.response);
    // return thunkAPI.rejectWithValue(error.response.data.msg);
  }
};

export const loginUserThunk = async (url, user, thunkAPI) => {
  try {
    const resp = await customFetch.post(url, user);
    return resp.data;
  } catch (error) {
    console.log(error.response);
    return errorResponse(error, thunkAPI);
  }
};

export const getUserDetailsThunk = async (url, thunkAPI) => {
  try {
    const resp = await customFetch.get(url);
    return resp.data;
  } catch (error) {
    console.log(error.response);
    // return thunkAPI.rejectWithValue(error.response.data.msg);
  }
};

export const changePasswordThunk = async (url, passwordDetails, thunkAPI) => {
  try {
    const resp = await customFetch.put(url, passwordDetails);
    // if(resp.data.status) thunkAPI.dispatch(logoutUser());
    return resp.data;
  } catch (error) {
    console.log(error.response.data);
    // return thunkAPI.rejectWithValue(error.response.data.msg);
  }
};

export const updateUserThunk = async (url, user, thunkAPI) => {
  try {
    const resp = await customFetch.put(url, user);
    return resp.data;
  } catch (error) {
    console.log(error.response.data);
    // return checkForUnauthorizedResponse(error, thunkAPI);
  }
};
export const forgotPasswordThunk = async (url, userEmail, thunkAPI) => {
  try {
    const resp = await customFetch.post(url, userEmail);
    console.log('forgotPasswordThunk');
    return resp.data;
  } catch (error) {
    console.log(error.response.data);
    // return checkForUnauthorizedResponse(error, thunkAPI);
  }
};

export const resetPasswordThunk = async (url, resetPasswordDetails, thunkAPI) => {
  try {
    const resp = await customFetch.post(url, resetPasswordDetails);
    console.log('resetPasswordThunk');
    return resp.data;
  } catch (error) {
    console.log(error.response.data);
    // return checkForUnauthorizedResponse(error, thunkAPI);
  }
};

export const clearStoreThunk = async (message, thunkAPI) => {
  try {
    thunkAPI.dispatch(logoutUser(message));
    thunkAPI.dispatch(clearAllCustomersState());
    thunkAPI.dispatch(clearCustomerValues());
    thunkAPI.dispatch(clearAllOrdersState());
    thunkAPI.dispatch(clearOrderValues());
    return Promise.resolve();
  } catch (error) {
    return Promise.reject();
  }
};