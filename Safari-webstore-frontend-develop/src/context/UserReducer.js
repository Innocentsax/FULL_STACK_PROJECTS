export const UserReducer = (state, action) => {
  const { type, payload } = action;
  switch (type) {
    case "SET_USER_DETAILS_FROM_LOGIN":
      const {roles} = payload;
      localStorage.setItem(
        "isLoggedIn",
        JSON.stringify({ isLoggedIn: true, role: roles[0].name })
      );
      return { ...state, ...payload, isLoggedIn: true };
    case "AUTO_SIGN_IN":
        handleAutoLogin();
        break;
    case "LOG_OUT":
      localStorage.removeItem("token");
      localStorage.setItem("isLoggedIn", JSON.stringify({isLoggedIn:false}));
      return {...state,...payload, isLoggedIn:false}
    default:
      return state;
  }
};


function handleAutoLogin(){
    if(_getLocalStorageState() ) localStorage.setItem('isLoggedIn', JSON.stringify({ isLoggedIn: true }))
}

// there need to be isLoggedIn && token keys for auto login
function _getLocalStorageState (){
    const loginState = JSON.parse(localStorage.getItem('isLoggedIn'));
    const authToken = localStorage.getItem('token');
    return (loginState !== undefined || loginState.isLoggedIn !== false) && (authToken !== null && authToken.length >= 100)
}

export {_getLocalStorageState as authKeysExist}