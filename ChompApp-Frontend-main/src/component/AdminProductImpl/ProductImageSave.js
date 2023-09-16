import axios from 'axios';
import React, { forwardRef, useEffect, useImperativeHandle, useState } from 'react'
import { Modal } from 'react-bootstrap';
import { BASE_API_URL } from '../../common/Constants';
import ProductImage from '../../models/ProductImage';
import { authHeader } from '../../services/BaseService';
import ProductService from '../../services/ProductService';
const UPLOAD_IMAGE_API = `${BASE_API_URL}/api/admin/upload-image`

const ProductImageSave = forwardRef((props, ref) => {

        useImperativeHandle(ref, () => ({
          showProductModal() {
            setTimeout(() => {
              setShow(true);
            }, 0);
          },
        }));

        const [productId, setProductId] = useState("");
        const [errorMessage, setErrorMessage] = useState("");
        const [infoMessage, setInfoMessage] = useState(false);
        const [submitted, setSubmitted] = useState(false);
        const [productList, setProductList] = useState([]);
        const [imageFormData, setImageFormData] = useState();

        const [show, setShow] = useState(false);
        
        useEffect(() => {
            ProductService.getAllProducts()
            .then((response) => {
                setProductList(response.data.content);
            })
            .catch((error) => {
                console.log(error);
            });
        }, []);
        
        console.log(productList);

        const handleUploadImage = (e) => {
            e.preventDefault()
            const formData = new FormData();
            formData.append("image", imageFormData, imageFormData.name);
            const config = {
              headers: {
                "content-type": "multipart/form-data",
                headers: authHeader(),
              },
            };

            const url = UPLOAD_IMAGE_API + "/" + productId;
          axios.post(url, formData, config).then((response) => {
              setInfoMessage(true);
              console.log(response);
              setSubmitted(true);
              setProductId("");
          }) 
            .catch((error) => {
                setErrorMessage("Unexpected error occurred.");
                console.error(error)
            })
        }

        
        useEffect(() => {
          setTimeout(() => {
            if (infoMessage) {
            setInfoMessage(false);
            setShow(false);
            window.location.reload();
            }
        }, 3000);
    }, [infoMessage]);

    
  return (
    <Modal show={show}>
      <form>
        <div className="modal-header">
          <h5 className="modal-title">Product Image</h5>
          <button
            className="btn-close"
            type="button"
            onClick={() => setShow(false)}
          ></button>
        </div>
        <div className="modal-body">
          {errorMessage && (
            <div className="alert alert-danger">{errorMessage}</div>
          )}
          {infoMessage && (
            <div
              className="alert alert-success alert-dismissible fade show d-flex justify-content-between text-dark"
              role="alert"
            >
              Product image added successfully
            </div>
          )}

          <div className="form-group">
            <label htmlFor="name">Product Image:</label>
            <input
              type="text"
              name="productId"
              className="form-control mb-3"
              placeholder="Product Id"
              required
              value={productId}
              onChange={(e) => setProductId(e.target.value)}
            />
            <div className="invalid-feedback">Name is required.</div>
          </div>
          <div className="form-group">
            <input
              type="file"
              className="form-control"
              accept="image/*"
              name="image"
              onChange={(e) => setImageFormData(e.target.files[0])}
            />
          </div>
          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={() => setShow(false)}
            >
              Close
            </button>
            <button
              onClick={handleUploadImage}
              type="submit"
              className="btn btn-primary"
            >
              Save Changes
            </button>
          </div>
        </div>
      </form>
    </Modal>
  );
})

export  {ProductImageSave};