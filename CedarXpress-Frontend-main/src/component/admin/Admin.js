import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";



const  Admin = (props) => {
    const initialState = {productName: "", description: "", price: 0, category: "", subCategory: "subCategory"};
    const [image, setImage] = useState(initialState);
    const {HomeCss} = props;
    const handleProductName = (e) => setImage({...image , productName: e.target.value })
    const handleProductDescription = (e) => setImage({...image , description: e.target.value })
    const handleProductPrice = (e) => setImage({...image , price: e.target.value })
    const handleCategory = (e) => setImage({...image , category: e.target.value })
    const configureJWT = () =>  localStorage.getItem("currentUser");

    function handleSave(e){
        e.preventDefault();
        uploadToDatabase();
        setImage(initialState);
    }

    function uploadToDatabase(){
        axios.post("http://localhost:8080/api/v1/admin/products", image, {headers:{Authorization:configureJWT()}});
        
    }


    return (
        <article className={HomeCss.adminWrapper}>
            <div className={HomeCss.adminContainer}>
            <h2>ADMIN DASHBOARD PAGE</h2>
        <form>
            <input type="text" value = {image.productName} onChange={handleProductName} placeholder="Product Name"  required/>
            <input type="text" value= {image.description} onChange={handleProductDescription} placeholder="Product Description" required/>
            <input type="number" value={image.price}  onChange={handleProductPrice} min="1" max="1000000"placeholder="Product Price"  />
            <input type="text" value={image.category}onChange={handleCategory} placeholder="Product Category" required />
            <input type="submit" onClick={handleSave} value="upload Product" />
        </form>
        </div>
    </article>
    )
    
}

  export default Admin;