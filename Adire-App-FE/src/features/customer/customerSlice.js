import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
import { createCustomerThunk, deleteCustomerThunk, editCustomerThunk } from './customerThunk';


const initialState = {
    isLoading: false,
    address: '',
    email: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    isEditing: false,
    editCustomerId: '',
};


export const createCustomer = createAsyncThunk('customer/createCustomer', createCustomerThunk);

export const deleteCustomer = createAsyncThunk('customer/deleteCustomer', deleteCustomerThunk)

export const editCustomer = createAsyncThunk('customer/editCustomer', editCustomerThunk)


const customerSlice = createSlice({
    name: 'customer',
    initialState,
    reducers: {
        handleChange: (state, { payload: { name, value } }) => {
            state[name] = value;
        },
        clearCustomerValues: () => {
            return initialState
        },
        setEditCustomer: (state, { payload }) => {
            return { ...state, isEditing: true, ...payload }
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(createCustomer.pending, (state) => {
                console.log("isPending");
                state.isLoading = true;
            })
            .addCase(createCustomer.fulfilled, (state, action) => {
                console.log("isFulfilled");
                console.log(action.payload);
                state.isLoading = false;
                toast.success(`Customer created successfully`);
            })
            .addCase(createCustomer.rejected, (state, { payload }) => {
                console.log("isRejected");
                console.log(payload);
                state.isLoading = false;
                toast.error(payload);
            })
            .addCase(deleteCustomer.pending, (state, { payload }) => {
                console.log("isRejected");
                console.log(payload);
                state.isLoading = true;
                toast.error(payload);
            })
            .addCase(deleteCustomer.fulfilled, (state, { payload }) => {
                console.log("isFulfilled");
                state.isLoading = false;
                // console.log(payload);
                toast.success('Customer deleted successfully');
            })
            .addCase(deleteCustomer.rejected, (state, { payload }) => {
                console.log("isRejected");
                state.isLoading = false;
                console.log(payload);
                // toast.error(payload);
            })
            .addCase(editCustomer.pending, (state) => {
                console.log("isPending");
                state.isLoading = true;
            })
            .addCase(editCustomer.fulfilled, (state, action) => {
                console.log("isFulfilled");
                console.log(action.payload);
                state.isLoading = false;
                toast.success(`Customer modified...`);
            })
            .addCase(editCustomer.rejected, (state, { payload }) => {
                console.log("isRejected");
                console.log(payload);
                state.isLoading = false;
                // toast.error(payload);
            })
    }
});


export const { handleChange, clearCustomerValues, setEditCustomer } = customerSlice.actions;


export default customerSlice.reducer;