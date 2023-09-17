import React from "react";
import CartContext from "../store/Cart-Context";
import { useContext, useEffect, useState } from "react";
import ProductApis from "../apis/ProductApi";
import { Link } from "react-router-dom";
import FavouriteContext from "../store/Favourite-Context";

function CardProductItem(props) {
  const [cartItemsDB, setCartItemsDB] = useState([]);

  useEffect(async () => {
    // const cart = await productApis.getAllProductsInCart;
    //  console.log(`CART ITEMS ARE ${cart}`)
    //  const cats = cart;
    //  setCartItemsDB(cats);
  }, []);

  const cartCtx = useContext(CartContext);
  const favoriteCtx = useContext(FavouriteContext);

  const itemIsInCart = cartCtx.itemIsInCart(props.id);
  const itemIsFavorite = favoriteCtx.isInFavourite(props.id);

  function toggleAddToCartHandler() {
    if (itemIsInCart) {
      cartCtx.removeCartItem(props.id);
      ProductApis.deleteProductFromCart(props.id);
    } else {
      cartCtx.addToCart({
        id: props.id,
        price: props.price,
        name: props.name,
        image: props.image,
      });

      ProductApis.addProductToCart(props.id);
    }
  }

  function toggleAddToFavoriteHandler() {
    if (itemIsFavorite) {
      favoriteCtx.removeFavourite(props.id);
      // productApis.deleteProductFromCart(props.id)
    } else {
      favoriteCtx.addToFavourite({
        id: props.id,
        price: props.price,
        name: props.name,
        image: props.image,
      });
      ProductApis.addProductToFavorite(props.id);
    }
  }

  return (
    <>
      {" "}
      <Link to={"/product/" + props.id}>
        <li className="cards__item">
          <div>
            <figure className="cards_item_pic_wrap">
              <img
                src={props.src}
                alt="ProductPhoto"
                className="cards_item_img"
                width="297px"
                height="457px"
              />
            </figure>
            <div className="cards__item__info">
              <h5 className="cards__item__text">{props.name}</h5>
              <p className="cards__item__price">{props.price}</p>
              <div className="product-item-hover">
                {/* <div className="favourite">
                  <i class="far fa-heart"></i>
                </div> */}

                {/* <button className="addtocart">
                  ADD TO CART
                  <i class="cart-icon fas fa-shopping-cart"></i>
                </button> */}
              </div>
              {/* <div>{props.id}</div> */}
            </div>
          </div>
        </li>
      </Link>
    </>
  );
}

export default CardProductItem;
