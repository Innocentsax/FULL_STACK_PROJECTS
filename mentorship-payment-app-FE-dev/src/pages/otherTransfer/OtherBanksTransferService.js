import { apiPost } from "../../utils/apiHelper";

class OtherTransferService {
  async saveTransaction(payload, header) {
    const response = await apiPost(
      "/api/v1/transfers/otherBank",
      payload,
      header,
      true
    );
    console.log(response.data);
    return response.data;
  }
}
export default new OtherTransferService();
