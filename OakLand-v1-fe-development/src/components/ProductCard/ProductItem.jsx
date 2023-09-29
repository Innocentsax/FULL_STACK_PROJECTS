import { Rate } from 'antd'
import { useState } from 'react';
import useProduct from '../../hooks/useProduct';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useCart } from 'react-use-cart';
import { useAuth } from '../../context/authcontext';
import { BiHeart } from "react-icons/bi";


const desc = ['Terrible', 'Bad', 'Normal', 'Good', 'Wonderful'];

const ProductItem = ({ product, isEditable, url }) => {
  const navigate = useNavigate()
  const location = useLocation()
  let from = location.state?.from?.pathname || "/product"

  const desc = ['Terrible', 'Bad', 'Normal', 'Good', 'Wonderful'];

  const{ id, name, price, imageUrl } = product
  const[value, setValue] = useState(3);
  const[isHover, setIsHover] = useState(false)
    
  const { products, setProducts, addToFavorites } = useProduct()
  const handleHover = () => isEditable && setIsHover(true)
  const handleHoverLeave = () => isEditable && setIsHover(false)

  const addProductToFavorites = () => {
    addToFavorites(id)
  }

  const deleteProduct = (name) => 
    setProducts([...products.filter(x  => name !== x.name)])
  
    const showSingleProduct = () => navigate(from, { replace: true, state: product })

    /*** Cart ***/
    const { AddToCartConfig } = useAuth();

    const addItemToCartHandler = () => {
      console.log(`"Product Item: ${id}`)
      AddToCartConfig(id);
    }

    return ( 
      <div className="product-container bordered-cont" onMouseOver={handleHover} onMouseLeave={handleHoverLeave}>
          <div className="add-icon" onClick={ addProductToFavorites }>
            { !isEditable && <BiHeart className="item-icon"/> }
          </div>
          <Link to={`${url}/${product.id}`} className="link-container">
            <img style={{ height: "100%" }} src={ imageUrl } alt={ name } />
            <h6 style={{ fontSize: "14px" }} className="product-name">{ name }</h6>
            <span>
                <Rate tooltips={desc} onChange={setValue} value={value}  />
                <p className="product-price">{ price }</p>
            </span>
          </Link>
          <p className="add-tocart-btn btn" onClick={addItemToCartHandler}>ADD TO CART</p>
          { isHover && <button className='delete-btn' 
              onClick={() => deleteProduct(name)}>X</button> }
      </div>
    );
  }

export default ProductItem
