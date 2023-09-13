import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
import customFetch from '../../utils/axios';

const initialFiltersState = {
  search: '',
  searchStatus: 'all',
  searchType: 'all',
  sort: 'latest',
  sortOptions: ['latest', 'oldest', 'a-z', 'z-a'],
};

const initialState = {
  isLoading: false,
  // customers: data,
  orders: [],
  totalOrders: 0,
  numOfPages: 1,
  page: 1,
  totalElements: 0,
  searchTerm: '',
};


export const getAllOrders = createAsyncThunk(
  'allOrders/getOrders',
  async (_, thunkAPI) => {
    let url = `/api/order/getAllOrders`;
    
    console.log("sending request");
    try {
      const resp = await customFetch.get(url);

      console.log(resp.data);
      return resp.data;
    } catch (error) {
      console.log(error.response);
    //   return thunkAPI.rejectWithValue(error.response.data.error);
    }
  }
);


const allOrdersSlice = createSlice({
  name: 'allOrders',
  initialState,
  reducers: {
    showLoading: (state) => {
      state.isLoading = true;
    },
    hideLoading: (state) => {
      state.isLoading = false;
    },
    changePage: (state, { payload }) => {
      state.page = payload;
    },
    clearAllOrdersState: () => initialState,
  },
  extraReducers: (builder) => {
    builder
    .addCase(getAllOrders.pending, (state) => {
      console.log("ispending");
      state.isLoading = true;
    })
    // .addCase(getAllOrders.fulfilled, (state, { payload : { data : { content }} }) => {
    .addCase(getAllOrders.fulfilled, (state, { payload : { data } }) => {
      state.isLoading = false;
      // console.log(content);
      console.log(data);
      // pageNo, pageSize, totalPages, totalElements
    //   console.log("fulfilled");
      state.orders = data.content;
      state.numOfPages = data.totalPages;
      state.page = data.pageNo;
      state.totalElements = data.totalElements;
    })
    .addCase(getAllOrders.rejected, (state, { payload }) => {
      state.isLoading = false;
      console.log(payload);
      // toast.error(payload);
    })
  }
});


export const { showLoading, hideLoading, clearAllOrdersState } = allOrdersSlice.actions

export default allOrdersSlice.reducer;