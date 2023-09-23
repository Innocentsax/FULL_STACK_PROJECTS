import React from "react";
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { useNavigate } from "react-router-dom";
import { FavoriteButton } from "../../services/FavoriteButton";


const ProductModel = ({ backgroundImage,
    time,
    cost,
    product, id}) => {
      const navigate = useNavigate();

      const viewItem = () => {
        console.log("This is id in Model: " + id);
        localStorage.setItem("productId", id);
        navigate("/product/item");
      }
  return (
    
    <div className="Products">
      <div className="imgWrapper">
        <img style={{ minWidth:"100%", height:'inherit'}} src={backgroundImage} className="img"/>
      </div>
        

      <div className="product_titleContainer">
        <div className="product_titleWrapper">
          
        {/* <FavoriteButton itemId={id} /> */}
          <span className="colour-free"> {cost}</span>
        </div>

      <div className="productName">
        <h5 className="nameProduct">{product}</h5>
      </div>

      <div className="productTrain">
        <a href='' onClick={viewItem}>View</a>
      </div>
      </div>

    </div>
  );
};

export default ProductModel;
