import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
import { createOrderThunk, deleteOrderThunk, editOrderThunk } from './orderThunk';
import { measurements } from '../../utils/enumData'


const initialState = {
    isLoading: false,
    customerEmail: '',
    designImage: '',
    designImageUrl: '',
    dueDate: '',
    duration: '',
    materialType: '',
    measurementList: Object.fromEntries(Object.keys(measurements).map(key => [key, ''])),
    numberOfOrders: 0,
    orderPrice: '',
    orderStatus: '',
    isEditing: false,
    editOrderId: '',
};


export const createOrder = createAsyncThunk('order/createOrder', createOrderThunk);

export const deleteOrder = createAsyncThunk('order/deleteOrder', deleteOrderThunk)

export const editOrder = createAsyncThunk('order/editOrder', editOrderThunk)


const orderSlice = createSlice({
    name: 'order',
    initialState,
    reducers: {
        handleChange: (state, { payload: { name, value } }) => {
            console.log(name, value);
            state[name] = value;
        },
        handleMeasurementsChange: (state, { payload: { name, value } }) => {
            state.measurementList[name] = value;
        },
        clearOrderValues: () => {
            return initialState
        },
        setEditOrder: (state, { payload }) => {
            console.log(payload);
            return { ...state, isEditing: true, ...payload }
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(createOrder.pending, (state) => {
                console.log("isPending");
                state.isLoading = true;
            })
            .addCase(createOrder.fulfilled, (state, { payload }) => {
                console.log("isFulfilled");
                console.log(payload);
                state.isLoading = false;
                toast.success(`Order created successfully`);
            })
            .addCase(createOrder.rejected, (state, { payload }) => {
                console.log("isRejected");
                console.log(payload);
                state.isLoading = false;
                toast.error(payload);
            })
            .addCase(deleteOrder.fulfilled, (state, { payload }) => {
                console.log("isFulfilled");
                // console.log(payload);
                toast.success('Order deleted successfully');
            })
            .addCase(deleteOrder.rejected, (state, { payload }) => {
                console.log("isRejected");
                console.log(payload);
                // toast.error(payload);
            })
            .addCase(editOrder.pending, (state) => {
                console.log("isPending");
                state.isLoading = true;
            })
            .addCase(editOrder.fulfilled, (state, action) => {
                console.log("isFulfilled");
                console.log(action.payload);
                state.isLoading = false;
                toast.success(`Order modified...`);
            })
            .addCase(editOrder.rejected, (state, { payload }) => {
                console.log("isRejected");
                console.log(payload);
                state.isLoading = false;
                // toast.error(payload);
            })
    }
});


export const { handleChange, handleMeasurementsChange, clearOrderValues, setEditOrder } = orderSlice.actions;


export default orderSlice.reducer;