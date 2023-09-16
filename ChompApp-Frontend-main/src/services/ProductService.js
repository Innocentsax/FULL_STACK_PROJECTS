import axios from 'axios'
import { BASE_API_URL } from '../common/Constants'
import { authHeader } from './BaseService';

class ProductService {
  saveProduct(product) {
    return axios.post(`${BASE_API_URL}/api/admin/create-product`, product, {
      headers: authHeader(),
    });
  }

  deleteProduct(product) {
    return axios.delete(
      `${BASE_API_URL}/api/admin/delete-product/${product.id}`,
      { headers: authHeader() }
    );
  }

  saveProductImage() {
    return axios.post(`${BASE_API_URL}/api/admin/upload-image`, {
      headers: authHeader(),
    });
  }

  getAllProducts() {
    return axios.get(`${BASE_API_URL}/api/v1/auth/users/getAllProducts`);
  }

  updateProduct(product) {
    return axios.put(
      `${BASE_API_URL}/api/admin/update-product/${product.productId}`,
      {
        headers: authHeader(),
      }
    );
  }

  getSingleProduct(productId) {
    return axios.get(
      `${BASE_API_URL}/api/v1/auth/users/fetch-single-product/${productId}`,
      {
        headers: authHeader(),
      }
    );
  }

  deleteProduct(product){
    return axios.delete(
      `${BASE_API_URL}/api/admin/delete-product/${product.productId}`,
      { headers: authHeader()}
    );
  }
}

export default new ProductService();