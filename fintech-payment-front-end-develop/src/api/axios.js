import axios from "axios";
export default axios.create({
baseUrl: 'http://localhost:3333/api/v1/user'
});