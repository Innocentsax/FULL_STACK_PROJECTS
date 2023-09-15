import React, { useCallback, useState, useEffect} from "react";
import axios from "axios";
import APIS from "../../data/urls";

const FETCH_PRODUCT_API = "http://localhost:8080/api/v1/products/";
const UPLOAD_IMAGE_API = "http://localhost:8080/api/v1/admin/products";

function ImageUpload(){
    const[products, setProducts] = useState([]);
    const[selectedProduct, setSelectedProduct] = useState();
    const[imageFormData, setImageFormData] = useState();
    const configureJWT = () =>  localStorage.getItem("currentUser");    


     const fetchProducts =  () => {
        axios.get(FETCH_PRODUCT_API).then(response => {
          const data = response.data;
          setProducts(data);
          setSelectedProduct(data[0].productName);
        });
    }

    useEffect(()=> fetchProducts(), []);

    const handleUploadImage = async () => {
      const SELECTED_PRODUCT_ID = products.filter(item => item.productName === selectedProduct)[0].id;
    
        try {
          console.log("Upload Image", imageFormData);
          const formData = new FormData();
          formData.append("image", imageFormData);
          formData.append("destination", "images");
          formData.append("create_thumbnail", true);

          const config = {
            headers: {
              "content-type": "multipart/form-data",
              "Authorization" : configureJWT()
            }
          };

          const url = UPLOAD_IMAGE_API + "/" + SELECTED_PRODUCT_ID 
          const result = await axios.patch(url, formData, config);
          console.log("REsult: ", result);
        } catch (error) {
          console.error(error);
        }
      };

    return (
      <div>
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
         <select value={selectedProduct} onChange={(e)=>setSelectedProduct(e.target.value)}>
          {products.map((product, index) => {
        return <option key = {index} >{product.productName}</option>
        })}
        </select>
        <input type="button" placeholder ="Upload Image" />
        <input type="file"  accept="image/*" name="image" onChange={(e)=> setImageFormData(e.target.files[0])} id="file"  />
        <input type="submit" onClick={handleUploadImage} placeholder="UploadImage" disabled={!imageFormData && true} />
      </div>
       
    )
}

  export default ImageUpload;