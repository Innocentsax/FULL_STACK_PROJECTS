import React, { useEffect, useState } from "react";
import { Container } from "semantic-ui-react";
import productApis from "../apis/ProductApi";
import CardProductItem from "./CardProductItem";

function SearchLayout() {
  const [searchedProducts, setSearchedProduct] = useState([]);
  const [loading, setLoading] = useState(true);
  let keyword = new URLSearchParams(window.location.search).get("keyword");

  useEffect(() => {
    setTimeout(() => setLoading(false), 1000);

    (async () => {
      const products = await productApis.searchProductByParams(keyword);
      console.log("This one is good",products.content);
      setSearchedProduct(products.content);
    })();
  }, []);

  return (
    <div className="cards">
      <h2>Search for: {keyword}</h2>

      <div className="cards__container">
        <div className="cards__wrapper">
          <ul className="cards__items">
            {loading ? (
              <p>Loading...</p>
            ) : searchedProducts.length != 0 ? (
              searchedProducts.map((product, index) => (
                <CardProductItem
                  key={index}
                  id={product.id}
                  src={product.productImages[0].image}
                  name={product.name}
                  price={"₦" + product.price}
                  path={`/cart/${product.id}`}
                  // favourtie="/favourite"
                  // favourtie={`/api/customer/favourite/${product.id}`}
                />
              ))
            ) : (
              <p>No product exist by that keyword</p>
            )}
          </ul>
        </div>
      </div>
    </div>
  );
}


export default SearchLayout;
