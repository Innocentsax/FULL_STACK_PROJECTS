import axios from "axios";
import { BASE_API_URL } from "../common/Constants";


class AuthenticationService{

    login(user){
        return axios.post(BASE_API_URL + "/api/v1/auth" + "/login", user);
    }

    register(user){
        return axios.post(BASE_API_URL + "/signup", user);
    }
}

export default new AuthenticationService;