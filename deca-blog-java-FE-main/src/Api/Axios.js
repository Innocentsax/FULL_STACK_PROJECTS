import axios from "axios";

let headers = {};
if (localStorage.token) {
  headers.Authorization = `Bearer ${localStorage.token}`;
}

export default axios.create({
    baseURL: process.env.REACT_APP_API_URL,
    headers
});
