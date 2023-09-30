import axios from 'axios';

const endPoint = axios.create({
    baseURL: 'http://localhost:8080',
});

const token = localStorage.getItem("token");
endPoint.interceptors.request.use(config => {
    if (token) {
        config.headers["Authorization"] = `Bearer ${token}`;
    }
    config.headers['Content-Type'] = 'application/json';
    return config;
});

export default endPoint;

export const baseURL = 'http://localhost:8080';
export const baseURLFE ='http://localhost:3000';