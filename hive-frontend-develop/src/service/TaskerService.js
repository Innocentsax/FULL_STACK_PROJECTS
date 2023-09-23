import axios from "axios";

const GET_ALL_NEW_TASKS_CREATED = "http://localhost:8080/tasks/fetchTasks/?status=NEW";
const GET_ONGOING_TASKS = "http://localhost:8080/tasks/fetchTasks/?status=ONGOING";
const GET_COMPLETED_TASKS = "http://localhost:8080/tasks/fetchTasks/?status=COMPLETED";
const GET_PENDING_APPROVAL = "http://localhost:8080/tasks/fetchTasks/?status=PENDING_APPROVAL";
const GET_ALL_TASKS = "http://localhost:8080/transaction/history";
const JWT_TOKEN = localStorage.getItem("token");

class TaskerService {
    getAllNewTasksCreated() {
        return axios.get(GET_ALL_NEW_TASKS_CREATED, {
            headers: {
                Authorization: `Bearer ${JWT_TOKEN}`,// Add the Bearer token to the headers
                "Content-Type": "application/json"
            }
        });
    }

    getAllOngoingTasksCreated() {
        return axios.get(GET_ONGOING_TASKS, {
            headers: {
                Authorization: `Bearer ${JWT_TOKEN}`,// Add the Bearer token to the headers
                "Content-Type": "application/json"
            }
        });
    }

    getAllCompletedTasksCreated() {
        return axios.get(GET_COMPLETED_TASKS, {
            headers: {
                Authorization: `Bearer ${JWT_TOKEN}`,// Add the Bearer token to the headers
                "Content-Type": "application/json"
            }
        });
    }

    getAllToBeApprovedTasksCreated() {
        return axios.get(GET_PENDING_APPROVAL, {
            headers: {
                Authorization: `Bearer ${JWT_TOKEN}`,// Add the Bearer token to the headers
                "Content-Type": "application/json"
            }
        });
    }
    approveTask( taskId) {
        return axios.post(`http://localhost:8080/tasks/${taskId}/approve`, "", {
            headers: {
                Authorization: `Bearer ${JWT_TOKEN}`,// Add the Bearer token to the headers
            }
        });
    }
    cancelTask(token, taskId) {
        return axios.post(`http://localhost:8080/tasks/${taskId}/cancel`, "", {
            headers: {
                Authorization: `Bearer ${token}`,// Add the Bearer token to the headers
            }
        });
    }


}

export default new TaskerService();