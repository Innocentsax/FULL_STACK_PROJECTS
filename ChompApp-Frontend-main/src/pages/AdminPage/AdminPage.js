import React, { useEffect, useRef, useState } from "react";
import CurrencyFormat from "react-currency-format";
import { ProductDelete } from "../../component/AdminProductImpl/ProductDelete";
import { ProductImageSave } from "../../component/AdminProductImpl/ProductImageSave";
import { ProductSave } from "../../component/AdminProductImpl/ProductSave";
import Product from "../../models/Product";
import ProductImage from "../../models/ProductImage";
import ProductService from "../../services/ProductService";
import "./AdminPage.css";
import { motion } from "framer-motion";

const AdminPage = () => {
  const [productList, setProductList] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(
    new Product("", "", "", "", "", "", 0)
  );
  const [selectedImage, setSelectedImage] = useState(new ProductImage(""));
  const [errorMessage, setErrorMessage] = useState("");

  const saveComponent = useRef();
  const deleteComponent = useRef();
  const saveImageComponent = useRef();

  useEffect(() => {
    ProductService.getAllProducts().then((response) => {
      setProductList(response.data.content);
      console.log(productList);
    });
  }, []);

  const createdProductRequest = () => {
    setSelectedProduct(new Product("", "", "", "", "", "", 0));
    saveComponent.current?.showProductModal();
  };

  const uploadProductImage = () => {
    setSelectedImage(new ProductImage(""));
    saveImageComponent.current?.showProductModal();
  };

  const editProductRequest = (item) => {
    setSelectedProduct(Object.assign({}, item));
    saveComponent.current?.showProductModal();
  };

  const deleteProductRequest = (product) => {
    setSelectedProduct(product);
    deleteComponent.current?.showDeleteModal();
  };

  const deleteProduct = () => {
    ProductService.deleteProduct(selectedProduct)
      .then((response) => {
        setProductList(
          productList.filter(
            (del) => del.productId !== selectedProduct.productId
          )
        );
      })
      .catch((error) => {
        setErrorMessage("Unexpected error occured.");
        console.log(error);
      });
  };
  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0, transition: { duration: 0.3 } }}
    >
      <div className="admin">
        <div className="admin__page-wrapper container pt-5">
          {errorMessage && (
            <div className="alert alert-danger">{errorMessage}</div>
          )}
          <div className="card">
            <div className="card-header">
              <div className="row">
                <div className="col-6">
                  <h3>All Products</h3>
                </div>
                <div className="col-6 text-end">
                  <button
                    className="btn btn-primary"
                    onClick={() => createdProductRequest()}
                  >
                    Create Product
                  </button>
                  <button
                    className="btn btn-warning text-dark mx-2"
                    onClick={() => uploadProductImage()}
                  >
                    Upload Image
                  </button>
                </div>
              </div>
            </div>
            <div className="card-body">
              <table className="table table-striped">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Product Name</th>
                    <th scope="col">Product Category</th>
                    <th scope="col">ProductId</th>
                    <th scope="col">Product Image</th>
                    <th scope="col">Product Price</th>
                    {/* <th scope="col">Quantity</th> */}
                    <th scope="col">Size</th>
                    <th scope="col">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {productList.map((item, index) => (
                    <tr key={item.productId}>
                      <th scope="row">{index + 1}</th>
                      <td>{item.productName}</td>
                      <td>{item.categoryName}</td>
                      <td>{item.productId}</td>
                      <td>
                        <img src={item.productImage} alt={item.productName} />
                      </td>
                      <td>
                        <CurrencyFormat
                          renderText={(value) => <p>{value}</p>}
                          decimalScale={2}
                          value={item.productPrice}
                          displayType={"text"}
                          thousandSeparator={true}
                          prefix={"₦"}
                        />
                      </td>
                      {/* <td>{item.quantity}</td> */}
                      <td>{item.size}</td>
                      <td className="">
                        <button
                          className="btn btn-primary"
                          onClick={() => editProductRequest(item)}
                        >
                          Edit
                        </button>

                        <button
                          className="btn btn-danger mx-2"
                          onClick={() => deleteProductRequest(item)}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <ProductSave product={selectedProduct} ref={saveComponent} />
        <ProductImageSave image={selectedImage} ref={saveImageComponent} />
        <ProductDelete
          ref={deleteComponent}
          onConfirmed={() => deleteProduct()}
        />
      </div>
    </motion.div>
  );
};

export default AdminPage;
