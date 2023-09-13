const authHeader = (thunkAPI) => {
    return {
      headers: {
        // authorization: `Bearer ${thunkAPI.getState().user.user.token}`,
        authorization: `Bearer ${thunkAPI.getState().user.user}`,
      },
    };
  };
  
  export default authHeader;