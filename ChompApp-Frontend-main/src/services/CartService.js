import axios from "axios";
import { BASE_API_URL } from "../common/Constants";
import { authHeader } from "./BaseService";




class CartService {
  
  addToCart(cart, productId) {
        const response = axios.post(
      `${BASE_API_URL}/cart/add-to-cart/${productId}`, cart,
      {
        headers: authHeader(),
      }
    );

    return response;
  }

  viewUsersDetails() {
    const response = axios.get(
     `${BASE_API_URL}/api/v1/auth/users/display-user-details`,
      {
        headers: authHeader(),
      }
    );

    return response
  }

  getAllCartItem() {
    return axios.get(`${BASE_API_URL}/cart/view-cart`, {
      headers: authHeader(),
    });
  }

  clearCart(){
    return axios.delete(`${BASE_API_URL}/cart/clear-cart`, {
      headers: authHeader(),
    });
  }
}

export default new CartService();