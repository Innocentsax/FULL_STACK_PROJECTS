import axios from "axios";
import { BASE_URL } from "../component/common/BaseService";


class AuthenticationService {
    register(user){
        return axios.post(BASE_URL + "/api/v1/users/", user)
    } 

    login(user){
        return axios.post(BASE_URL + "/api/v1/login", user)
    }
}

export default new AuthenticationService;