import axios from "axios";

const GET_ALL_NOTIFICATIONS = "http://localhost:8080/api/notifications/user-notifications";
const JWT_TOKEN = localStorage.getItem("token");

class NotificationService {
    getAllNotifications(data) {
        return axios.get(GET_ALL_NOTIFICATIONS, {
            headers: {
                Authorization: `Bearer ${JWT_TOKEN}`,// Add the Bearer token to the headers
                "Content-Type": "application/json"
            }
        });
    }

}

export default new NotificationService();