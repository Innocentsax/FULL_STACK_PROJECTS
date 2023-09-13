export const addUserToLocalStorage = (user) => {
    localStorage.setItem('user', JSON.stringify(user));
  };
  
  export const removeUserFromLocalStorage = () => {
    localStorage.removeItem('user');
  };
  
  export const getUserFromLocalStorage = () => {
    const result = localStorage.getItem('user');
    console.log(result);
    const user = result ? JSON.parse(result) : null;
    return user;
  };