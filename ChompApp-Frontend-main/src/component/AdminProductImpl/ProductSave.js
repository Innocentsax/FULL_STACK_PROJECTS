import React, { forwardRef, useEffect, useImperativeHandle, useState } from 'react'
import Product from '../../models/Product'
import { Modal } from "react-bootstrap";
import ProductService from '../../services/ProductService';


const ProductSave = forwardRef((props, ref) => {
  

   useImperativeHandle(ref, () => ({
     showProductModal() {
       setTimeout(() => {
         setShow(true);
       }, 0);
     },
   }));

    useEffect(() => {
        setProduct(props.product)
    }, [props.product])

    const [product, setProduct] = useState(new Product("","","","","","",0,""))
    const [errorMessage, setErrorMessage] = useState("");
    const [submitted, setSubmitted] = useState(false);
    const [show, setShow] = useState(false);

    const saveProduct = (e) => {
        e.preventDefault();
        setSubmitted(true);

        if (
          !product.productName ||
          !product.categoryName ||
          !product.productPrice ||
          !product.size ||
          !product.productDescription
        ) {
          return;
        }

        ProductService.saveProduct(product).then((response) => {
            setShow(false)
            setSubmitted(false)
            window.location.reload();

        }).catch((error) => {
             setErrorMessage("Unexpected error occurred.");
             console.log(error);
        })

    }


    const handleChange = (e) => {
        const {name, value} = e.target;
        setProduct((prevState) => {
               return {
                 ...prevState,
                 [name]: value,
               }; 
        })
    }
  return (
    <Modal show={show}>
      <form
        onSubmit={(e) => saveProduct(e)}
        noValidate
        className={submitted ? "was-validated" : ""}
      >
        <div className="modal-header">
          <h5 className="modal-title">Product Details</h5>
          <button
            type="button"
            className="btn-close"
            onClick={() => setShow(false)}
          >
            {" "}
          </button>
        </div>
        <div className="modal-body">
          {errorMessage && (
            <div className="alert alert-danger">{errorMessage}</div>
          )}
          <div className="form-group mb-3">
            <input
              type="text"
              name="productName"
              placeholder="Product Name"
              className="form-control"
              required
              value={product.productName}
              onChange={(e) => handleChange(e)}
            />
            <div className="invalid-feedback">Product name is required.</div>
          </div>

          <div className="form-group mb-3">
            <select
              class="form-select form-select-md mb-3"
              aria-label=".form-select-lg example"
              required
              value={product.categoryName}
              name="categoryName"
              onChange={(e) => handleChange(e)}
            >
              <option selected>Category</option>
              <option value="Burger">Burger</option>
              <option value="Sides">Sides</option>
              <option value="Drinks">Drinks</option>
            </select>
            <div className="invalid-feedback">
              Product category is required.
            </div>
          </div>

          <div className="form-group mb-3">
            <input
              type="number"
              name="productPrice"
              placeholder="Product Price"
              className="form-control"
              required
              value={product.productPrice}
              onChange={(e) => handleChange(e)}
            />
            <div className="invalid-feedback">Product price is required.</div>
          </div>

          <div className="form-group mb-3">
            <select
              class="form-select form-select-md mb-3"
              aria-label=".form-select-lg example"
              required
              value={product.size}
              name="size"
              onChange={(e) => handleChange(e)}
            >
              <option selected>Size</option>
              <option value="Small">Small</option>
              <option value="Medium">Medium</option>
              <option value="Large">Large</option>
            </select>
            <div className="invalid-feedback">Product size is required.</div>
          </div>

          <div className="form-group mb-3">
            <textarea
              name="productDescription"
              placeholder="Product Description"
              className="form-control"
              required
              rows="4"
              min="5"
              max="255"
              value={product.productDescription}
              onChange={(e) => handleChange(e)}
            />
            <div className="invalid-feedback">
              Product description is required.
            </div>
          </div>
        </div>

        <div className="modal-footer">
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => setShow(false)}
          >
            Close
          </button>
          <button type="submit" className="btn btn-primary">
            Save Changes
          </button>
        </div>
      </form>
    </Modal>
  );
})

export {ProductSave};