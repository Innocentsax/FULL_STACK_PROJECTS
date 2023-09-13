import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
import {
  addUserToLocalStorage,
  getUserFromLocalStorage,
  removeUserFromLocalStorage,
} from '../../utils/localStorage';
import {
  loginUserThunk,
  registerUserThunk,
  updateUserThunk,
  getUserDetailsThunk,
  changePasswordThunk,
  forgotPasswordThunk,
  googleRegisterThunk,
  resetPasswordThunk,
  clearStoreThunk,
} from './userThunk';

const initialState = {
  isLoading: false,
  isSidebarOpen: false,
  user: getUserFromLocalStorage(),
  userData: null,
  forgotPasswordMail: '',
  isMember: true,
};

export const registerUser = createAsyncThunk(
  'user/registerUser',
  async (user, thunkAPI) => {
    console.log("here");
    return registerUserThunk('/api/auth/register', user, thunkAPI);
  }
);
export const googleRegister = createAsyncThunk(
  'user/googleRegister',
  async (token, thunkAPI) => {
    console.log({token});
    return googleRegisterThunk('/api/auth/login/google', token, thunkAPI);
  }
);

export const loginUser = createAsyncThunk(
  'user/loginUser',
  async (user, thunkAPI) => {
    return loginUserThunk('/api/auth/login', user, thunkAPI);
  }
);

export const updateUser = createAsyncThunk(
  'user/updateUser',
  async (user, thunkAPI) => {
    return updateUserThunk('/api/designer/updateDesigner', user, thunkAPI);
  }
);

export const getUserDetails = createAsyncThunk(
  'user/getUserDetails',
  async (_, thunkAPI) => {
    return getUserDetailsThunk('/api/designer/getDesignerInfo', thunkAPI);
  }
);

export const changePassword = createAsyncThunk(
  'user/changePassword',
  async (passwordDetails, thunkAPI) => {
    return changePasswordThunk('/api/auth/update-password', passwordDetails, thunkAPI);
  }
);

export const forgotPassword = createAsyncThunk(
  'user/forgotPassword',
  async (userEmail, thunkAPI) => {
    console.log('email sent for forgot password');
    return forgotPasswordThunk('/api/auth/forgot-password-request', userEmail, thunkAPI);
  }
);

export const resetPassword = createAsyncThunk(
  'user/resetPassword',
  async (resetPasswordDetails, thunkAPI) => {
    console.log('reset password successful');
    return resetPasswordThunk('/api/auth/reset-password', resetPasswordDetails, thunkAPI);
  }
);
export const clearStore = createAsyncThunk('user/clearStore', clearStoreThunk);
const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    toggleSidebar: (state) => {
      state.isSidebarOpen = !state.isSidebarOpen;
    },
    logoutUser: (state, { payload }) => {
      state.user = null;
      state.isSidebarOpen = false;
      removeUserFromLocalStorage();
      if (payload) {
        toast.success(payload);
      }
    },
    toggleIsMember: (state) => {
      state.isMember = !state.isMember
    },
    handleChange: (state, { payload: { name, value } }) => {
      state[name] = value;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(registerUser.pending, (state) => {
        console.log("isPending");
        state.isLoading = true;
      })
      .addCase(registerUser.fulfilled, (state, { payload }) => {
        console.log("isFulfilled");
        console.log(payload);
        const { data : user } = payload;
        console.log(user);
        state.isLoading = false;
        state.isMember = true;
        toast.success(`Welcome ${user.firstName}, Please login`);
        // state.user = user;
        // addUserToLocalStorage(user);
        // toast.success(`Hello There ${user.name}`);
      })
      .addCase(registerUser.rejected, (state, { payload }) => {
        console.log("isRejected");
        state.isLoading = false;
        toast.error(payload);
      })
      .addCase(googleRegister.pending, (state) => {
        console.log("isPending");
        state.isLoading = true;
      })
      .addCase(googleRegister.fulfilled, (state, { payload }) => {
        console.log("isFulfilled");
        state.isLoading = false;
        console.log(payload);
        const { data : user } = payload;
        console.log(user);
        state.user = user;
        addUserToLocalStorage(user);
        toast.success(`Welcome ${user.firstName}`);
      })
      .addCase(googleRegister.rejected, (state, { payload }) => {
        console.log("isRejected");
        state.isLoading = false;
        console.log(payload);
        // toast.error(payload);
      })
      .addCase(loginUser.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(loginUser.fulfilled, (state, { payload }) => {
        const { data : user } = payload;
        state.isLoading = false;
        state.user = user;
        addUserToLocalStorage(user);
        toast.success(`Login successful`);
      })
      .addCase(loginUser.rejected, (state, { payload }) => {
        state.isLoading = false;
        // toast.error(payload);
      })
      .addCase(updateUser.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(updateUser.fulfilled, (state, { payload }) => {
        const { data : user } = payload;
        state.isLoading = false;
        const updatedUser = {...getUserFromLocalStorage(), ...user};
        state.user = updatedUser;
        addUserToLocalStorage(updatedUser);
        toast.success(`User Updated!`);
      })
      .addCase(updateUser.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error(payload);
      })
      .addCase(getUserDetails.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getUserDetails.fulfilled, (state, { payload : { data } }) => {
        state.isLoading = false;
        state.userData = data;
      })
      .addCase(getUserDetails.rejected, (state, { payload }) => {
        state.isLoading = false;
        console.log(payload);
        // toast.error(payload);
      })
      .addCase(changePassword.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(changePassword.fulfilled, (state, { payload }) => {
        state.isLoading = false;
        state.user = null;
        state.isSidebarOpen = false;
        removeUserFromLocalStorage();
        toast.success("Logout successful")
        // addUserToLocalStorage(user);

        toast.success('password updated successfully');
      })
      .addCase(changePassword.rejected, (state, { payload }) => {
        state.isLoading = false;
        console.log(payload);
        // toast.error(payload);
      })
      .addCase(forgotPassword.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(forgotPassword.fulfilled, (state, { payload }) => {
        state.isLoading = false;
        state.forgotPasswordMail = '';
        console.log('forgot pass successful');
        console.log(payload);
        toast.success('check your email for reset token');
      })
      .addCase(forgotPassword.rejected, (state, { payload }) => {
        state.isLoading = false;
        console.log(payload);
        // toast.error(payload);
      })
      .addCase(resetPassword.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(resetPassword.fulfilled, (state, { payload }) => {
        state.isLoading = false;
        console.log('reset pass successful');
        console.log(payload);
        toast.success('password reset successfully');
      })
      .addCase(resetPassword.rejected, (state, { payload }) => {
        state.isLoading = false;
        console.log(payload);
        // toast.error(payload);
      })
      .addCase(clearStore.rejected, () => {
        toast.error('There was an error..');
      });
  },
});

export const { toggleSidebar, logoutUser, handleChange, toggleIsMember } = userSlice.actions;
export default userSlice.reducer;