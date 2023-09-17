import axios from "axios";

const instance = axios.create({
  baseURL: "localhost:8045",
});
export default instance;
