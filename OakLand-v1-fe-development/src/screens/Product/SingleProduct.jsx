import { useEffect, useState } from "react";
import "./singleProduct.css";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import {
  FacebookFilled,
  TwitterCircleFilled,
  InstagramFilled,
} from "@ant-design/icons";
import { message } from "antd";
import { useAuth } from "../../context/authcontext";
import './singleProduct.css'
import { setInputSelection } from "rc-mentions/lib/util";
import { apiGet } from "../../utils/api/axios";


const SingleProduct = () => {
    const params = useParams()
    const navigate = useNavigate()
    const location = useLocation()
    const from = location?.state?.from?.pathname || "/product-not-found" 

    const [messageApi, contextHolder] = message.useMessage();
    const [singleProduct, setSingleProduct]  = useState([])
    const{ imageUrl, description, price, name, availableQty } = singleProduct
    const[numOfItems, setNumOfItems] = useState(1)

    const onQuantityInputChange = (e) => setNumOfItems(e.target.value)
    
    const notification = (type, content) => {
    messageApi.open({
      type: type,
      content: content,
    });
  };

  useEffect(() => {
    apiGet(`products/view/${params.id}`)
    .then(res => {
         setSingleProduct(res.data)
        //  notification('success', res.data)
    })
    .catch(err => {
         console.log(err)
         navigate(from, { replace: true })
        //  notification('error', err.response.data.errorMessage)
    }) 
 
   return () => {}

 }, [navigate, from, params.id])



    //*** Cart ***/
    const { AddToCartConfig } = useAuth();

    const addItemToCartHandler = () => {
      AddToCartConfig(params.id);
    }

  return (
    <section className="single-product-section">
        {contextHolder}
        <div className="product-preview">
            <div className="img-div">
                <img src={imageUrl} alt="" />
            </div>
            <div className="info-div">

            </div>
        </div>

        <div className="description-tab">
            <div className="head">
                <h1 className="title">{ name }</h1>
                <p className="price-tag">₦{ price }</p>
            </div>
        
            <p className="short-info">This is a sample product</p>

            <p className="description">{ description } </p>

            <div className="btn-div">
              <input
                type="number"
                max={availableQty}
                value={numOfItems}
                onChange={onQuantityInputChange}
                className="item-count-btn qty-input"
              />
              <button className="btn home-btn" onClick={addItemToCartHandler}>ADD TO CART</button>
              <h4>Category: <span>Table, Wooden</span></h4>
            </div>
            <hr />
            <div className="links-div">
                <p>Share This Item: </p>
                <FacebookFilled />
                <TwitterCircleFilled />
                <InstagramFilled />
            </div>
        </div>

        <div className="related-products-div">

        </div>
    </section>
  )
}

export default SingleProduct