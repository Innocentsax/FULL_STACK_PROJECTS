import React, { useState, useEffect } from "react";
import {
  Grid,
  Segment,
  Image,
  Dropdown,
  Container,
  GridColumn,
 Button
} from "semantic-ui-react";
import "../styles/Layout/_product_item_layout.scss";
import "../styles/Components/_button.scss";
import productApis from "../apis/ProductApi";
import { useCartUpdate } from "../utilities/CartContext";
import axios from 'axios';
import BaseUrl from "../apis/BaseUrl";

function ProductItemLayout(props) {
  const productId = props.productId;
  const [optionscolor, setColors] = useState([]);
  const [optionssize, setSizes] = useState([]);
  const [product, setProduct] = useState({});
  const [quantity, setQuantity] = useState(1);
  const [color, setColor] = useState("Select Color");
  const [size, setSize] = useState("Select Size");
  const [message, setMessage] = useState("");
  //use context for cart item on navigation bar
  const incrementCartItem = useCartUpdate();
  const token = localStorage.getItem('token');


  useEffect(async () => {
    const productData = await productApis.getProductById(productId);

    setProduct(productData);

    const arrColor = productData.colors;
  
    const newOptionColor = arrColor.map((list, index) => ({
      key: index + 2,
      text: list.color,
      value: index + 2,
    }));

    newOptionColor.unshift({ key: 1, text: "Select Color", value: 1 });

    setColors(newOptionColor);

    const arrSize = productData.sizes;

    const newOptionSize = arrSize.map((list, index) => ({
      key: index + 2,
      text: list.size,
      value: index + 2,
    }));

    newOptionSize.unshift({ key: 1, text: "Select Size", value: 1 });

    setSizes(newOptionSize);
  }, []);

  //increment quantity
  const incrementQuantity = () => {
    setQuantity((quantity) => quantity + 1);
  };

  //decrement quantity
  const decrementQuantity = () => {
    if (quantity !== 1) setQuantity((quantity) => quantity - 1);
  };

  //add products to cart
  const addToCart = async () => {
    //informations to be added to cart
    const orderItems = {
      productId,
      quantity,
      price: product.price,
      name: product.name,
      description: product.description,
      size: size === "Select Size" ? null : size,
      color: color === "Select Color" ? null : color,
      productImage: product.hasOwnProperty("productImages")
       ? product.productImages[0].image : "",
    };

    const response = await productApis.addProductToCart(orderItems);

    if (response.status === 201) {
      setMessage(response.message);
      incrementCartItem();
    } else setMessage("Error adding product to cart, please check back later");

    console.log("Ordered Items", orderItems);
  };

  //track the size picked from the lists of sizes
  const trackSizeInput = (e, result) => {
    const { value } = result;

    let textField = optionssize[value - 1].text;

    setSize(textField);
  };

  //track the color picked from the lists of colors
  const trackColorInput = (e, result) => {
    const { value } = result;

    let textField = optionscolor[value - 1].text;

    setColor(textField);
  };

  const addToFavorites = async ()=>{
   const endPoint = BaseUrl +  "/api/customer/favorite/" + productId;
   const headers = {
     headers:{
         Authorization:"Bearer " + token
        }
   }
   try{
     const response = await axios.put(endPoint, headers);
     console.log(response)
   }catch(responseError){
     console.error(responseError.response);
   }
    // alert(endPoint);
  }

  return (
    <Container fluid padded className="product-item-container container">
      <Grid>
        <GridColumn mobile={16} tablet={16} widescreen={8} computer={6}>
          <Segment>
            {product.hasOwnProperty("productImages") && (
              <Image
                src={product.productImages[0].image}
                alt="product-item-placeholder"
                height="500px"
                centered
              ></Image>
            )}
          </Segment>
          <Segment>
            <h5 className="product-item-description">Description</h5>
            <p className="product-item-description-p">{product.description}</p>
          </Segment>
        </GridColumn>
        <GridColumn mobile={16} tablet={16} widescreen={8} computer={10}>
          <Segment>
            <h3 className="product-item-title">{product.name}</h3>
            <p className="product-item-price">₦{product.price}</p>
            <p className="product-item-qty">
              <span>
                Qty
                <button onClick={decrementQuantity} className="qty qtypminu">
                  -
                </button>
                <input className="inputqty" value={quantity} />
                <button onClick={incrementQuantity} className="qty qtyplus">
                  +
                </button>
              </span>
            </p>
            <p className="product-item-size">Size</p>

            <Dropdown
              selection
              options={optionssize}
              onChange={trackSizeInput}
              placeholder={size}
              className="product-item-size-dropdown"
              value={size}
            />

            <p className="product-item-color">Color</p>
            <div className="product-item-categorycolor">
              {product.hasOwnProperty("productImages") && (
                <Dropdown
                  selection
                  // options={optionscolo}
                  options={optionscolor}
                  onChange={trackColorInput}
                  placeholder={color}
                  className="product-item-size-dropdown"
                  value={color}
                />
              )}
          </div>
          </Segment>
          <Grid>
            <GridColumn width="2">
            <Button basic color='red' size='huge'   onClick={addToFavorites} title='Add this item to my Favorites'>
              <i class="far fa-heart "></i> 
             </Button>
            </GridColumn>
            <GridColumn width="14">
              <button className="product-item-addtocart " onClick={addToCart}>
                ADD TO CART
              </button>
              <p className="addtocart__notice">{message}</p>
            </GridColumn>
          </Grid>
        </GridColumn>
      </Grid>
    </Container>
  );
}

export default ProductItemLayout;
