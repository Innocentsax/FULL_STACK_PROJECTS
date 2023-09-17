import React from "react";
import CardProductItem from "../components/CardProductItem";
import CartContext from "../store/Cart-Context";
import { useContext } from "react";
import { useState, useEffect } from "react";
import productApis from "../apis/ProductApi";
import Pagination from '../components/Pagination';


function ShoesPage(props) {
  const cartCtx = useContext(CartContext);

  const { products } = props;
  console.log("THE CONTENT ISSSSSSS", products);
  const [currentPage, setCurrentPage] = useState(1);
  const [productsPerPage] = useState(12);
  const indexOfLastProducts = currentPage * productsPerPage;
  const indexOfFirstProducts = indexOfLastProducts - productsPerPage;
  const currentProducts = products.slice(indexOfFirstProducts, indexOfLastProducts);
  const paginate = (pageNumber) => setCurrentPage(pageNumber);
  return (
    <div>
      <div className="productmain">
        <div className="cards__wrapper">
          <ul className="cards__items clothes__items">
            {currentProducts ? (
              currentProducts.map((product, index) => {
                return (
                
                  <CardProductItem
                    key={index}
                    id={product.id}
                    src={product.productImages[0].image}
                    price={product.price}
                    name={product.name}
                    favourtie={`/api/customer/favourite/${product.id}`}
                  />
                );
              })
            ) : (
              <div></div>
            )}
          </ul>
          <Pagination itemsPerPage = {productsPerPage} totalPages={products.length} paginate={paginate} currentPage={currentPage} />
        </div>
      </div>
    </div>
  );
}

export default ShoesPage;
