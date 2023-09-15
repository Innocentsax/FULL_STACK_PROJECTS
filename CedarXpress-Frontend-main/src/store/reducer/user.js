import { CLEAR_CURRENT_USER, SET_CURRENT_USER } from "../types";


const userReducer = (state = {}, action) => {
    switch(action?.type){
        case SET_CURRENT_USER:
            localStorage.setItem("currentUser", action?.payload);
            return action?.payload
        case CLEAR_CURRENT_USER:
            localStorage.removeItem("currentUser")
            return null
        default:
            return localStorage.getItem("currentUser")
    }
}

export default userReducer