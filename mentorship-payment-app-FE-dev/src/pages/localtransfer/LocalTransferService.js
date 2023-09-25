import { apiPost } from "../../utils/apiHelper"

class LocalTransferService {
  async saveTransaction(user,header) {
    const response = await apiPost("/api/v1/transfers/local", user, header, true)
    console.log(response.data);
    return response.data;
  }

}
export default new LocalTransferService();