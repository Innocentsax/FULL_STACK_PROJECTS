import axios from 'axios';
// import { clearStore } from '../features/user/userSlice';
import { getUserFromLocalStorage } from './localStorage';

const customFetch = axios.create({
  // baseURL: 'https://adire-web-service.onrender.com',
  baseURL: 'http://localhost:9090',
});


customFetch.interceptors.request.use((config) => {
  const user = getUserFromLocalStorage();
  if (user) {
    console.log(`Bearer ${user.token}`);
    config.headers['Authorization'] = `Bearer ${user.token}`;
    // config.headers['Authorization'] = `Bearer ${user}`;
  }
  return config;
});

export const errorResponse = (error, thunkAPI) => {
  if (error.response.status === 401) {
    // thunkAPI.dispatch(clearStore());
    return thunkAPI.rejectWithValue('Unauthorized! Logging Out...');
  }
  if (error.response.status === 500) {
    return thunkAPI.rejectWithValue('Something went wrong. Try again');
  }
  return thunkAPI.rejectWithValue(error.response.data.message);
};


export default customFetch;