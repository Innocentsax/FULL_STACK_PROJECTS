import axios from "axios";
import { notifyUser } from "../../Contact/utils";
import "./AddProduct.css";
import React, { useState } from 'react';



const AddProduct = () => {
  
  const [productName, setProductName] = useState("");
  const [price, setPrice] = useState("");
  const [description, setDescription] = useState("");
  const [productType, setProductType] = useState("");
  const [quantity, setQuantity] = useState("");
  const [stock, setStock] = useState("");
  const [category, setCategory] = useState("");
  const [monthlySubscription, setMonthlySubscription] = useState("");
  const [image, setImage] = useState("");
  const handleImage = (e) => {
      console.log(e);
      try{
          const formData = new FormData();
          formData.append("file", e.target.files[0])
          formData.append("upload_preset", "fitnessoapp");
          formData.append("cloud_name","fitnesso")
          console.log(formData);
          axios.post(
              "https://api.cloudinary.com/v1_1/fitnesso/image/upload",
              formData
          ).then((response) => {
              setImage(response.data.secure_url)})
      }catch(e){
          console.log(e.message);
      }
  }

  async function sendAddProductRequest(e) {
    setDisabledButton(true)
    e.preventDefault();
    const reqBody = {
      productName: productName,
      price: price,
      description: description,
      productType: productType,
      quantity: quantity,
      stock: stock,
      category: category,
      monthlySubscription: monthlySubscription,
      image: image
    };

    console.log(reqBody)

    const url = 'https://fitnesso-app-new.herokuapp.com/product/add';
  //  const url = "http://localhost:9067/product/add";
  
    try {
        const token = localStorage.getItem("token");
        
        const response = await axios.post(url, reqBody, {
          headers: { Authorization: `Bearer ${token}` },
          
        });
        const res = response.data;

        console.log(res)
        alert("Product added successfully");
        setProductName(""); setCategory(""); setDescription(""); setImage(""); setMonthlySubscription(""); setPrice(""); setProductType(""); setStock(""); setQuantity("");
  
    } catch (e) {
        console.log("Ensure all fields are filled correctly");
        alert("Ensure all fields are filled correctly");
       // console.log(e)
    }
  
  }
  const [disabledButton, setDisabledButton] = React.useState(false);
 
  return (
    <div className="admin-dashboard-board">
      <div className="add-product-body">
        <form className="add-product-form">
          <div className="add-product-title">Products</div>
          <div className="add-product-subtitle">Add a Product</div>

          <div className="add-product-input-container add-product-ic1">
            <input
              name="productName"
              className="add-product-input"
              type="text"
              placeholder="Product Name"
              value={productName}
              onChange={(event) => setProductName(event.target.value)}
              required
            />
          </div>
          <div className="add-product-input-container add-product-ic2">
            <input
              name="price"
              className="add-product-input"
              type="text"
              placeholder="Product Price"
              value={price}
              onChange={(event) => setPrice(event.target.value)}
              required
            />
          </div>
          <div className="add-product-textarea-container add-product-ic2">
            <textarea
              name="description"
              className="add-product-textarea"
              type="text"
              placeholder="Product Description "
              value={description}
              onChange={(event) => setDescription(event.target.value)}
              required
            />
          </div>
          {/* <div className="add-product-input-container add-product-ic2">
            <input
              name="category"
              className="add-product-input"
              type="text"
              placeholder="Product Category"
              value={category}
              onChange={(event) => setCategory(event.target.value)}
            />
          </div> */}
          <div className="add-product-input-container add-product-ic2">
            <select className="select-box" name="category" value={category} onChange={(event) => setCategory(event.target.value)}>
              <option className="select-box1" value="select Product Type" onChange={(event) => setCategory(event.target.value)}>
                Select Product Category
              </option>
              <option className="select-box1" value="EQUIPMENT">
                EQUIPMENT
              </option>
              <option className="select-box1" value="WEARS & ACCESSORIES">
                WEARS & ACCESSORIES
              </option>
              <option className="select-box1" value="SUPPLEMENTS">
                SUPPLEMENTS
              </option>
              <option className="select-box1" value="WORKOUT">
                WORKOUT
              </option>
              <option className="select-box1" value="MEAL PLAN">
                MEAL PLAN
              </option>
            </select>
            {/* <input
              name="category"
              className="add-product-input"
              type="text"
              placeholder="Product Type"
              value={productType}
              onChange={(event) => setProductType(event.target.value)}
            /> */}
          </div>
          <div className="add-product-input-container add-product-ic2">
            <input
              name="quantity"
              className="add-product-input"
              type="text"
              placeholder="Quantity / Trainers"
              value={quantity}
              onChange={(event) => setQuantity(event.target.value)}
            />
          </div>
          <div className="add-product-input-container add-product-ic2">
            <input
              name="stock"
              className="add-product-input"
              type="text"
              placeholder="Remaining Stock"
              value={stock}
              onChange={(event) => setStock(event.target.value)}
            />
          </div>
          <div className="add-product-input-container add-product-ic2">
            <select className="select-box" name="category" value={productType} onChange={(event) => setProductType(event.target.value)}>
              <option className="select-box1" value="select Product Type" onChange={(event) => setProductType(event.target.value)}>
                Select Product Type
              </option>
              <option className="select-box1" value="PRODUCT">
                PRODUCT
              </option>
              <option className="select-box1" value="SERVICE">
                SERVICE
              </option>
            </select>
          </div>
          <div className="add-product-iput-container add-product-ic2">
          <input
              name="image"
              className="add-product-input"
              type="file"
              placeholder="Image Url"
              onChange={(event) => handleImage(event)}
            />
            <div className="add-product-cut cut-short"></div>
          </div>
          <div className="add-product-iput-container add-product-ic2">
          <input
              name="monthlySubscription"
              className="add-product-input"
              type="text"
              placeholder="Monthly Subscription"
              value={monthlySubscription}
              onChange={(event) => setMonthlySubscription(event.target.value)}
            />
            <div className="add-product-cut cut-short"></div>
          </div>

          <button type="submit" className="add-product-submit" onClick={sendAddProductRequest} >
            SUBMIT
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddProduct;
