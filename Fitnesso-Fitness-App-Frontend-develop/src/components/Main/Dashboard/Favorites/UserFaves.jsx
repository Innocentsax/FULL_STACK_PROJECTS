import React, { useCallback, useEffect, useState } from "react";
import "./Favorites.css";
import ReactPaginate from "react-paginate";
import ProductModel from "../../../shop/shopProduct/ProductModel";

const UserFaves = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoaing] = useState(false);
  const [offset, setOffset] = useState(0);
  const [total, setTotal] = useState(0);
  const [pageCount, setPageCount] = useState(0);
  const [pageLimit] = useState(5);

  useEffect(() => {
      const fetchData = async () => {
          const response = await fetch("https://fitnesso-app-new.herokuapp.com/products")
          const faveData = await response.json();
          setTotal(faveData.length);
          setPageCount(Math.ceil(faveData.length/pageLimit));
          setProducts(faveData.slice(offset, offset+pageLimit))
          setIsLoaing(true);
      }
    fetchData();
  }, [offset, pageLimit]);

  const handlePageClick = (e) => {
    const selectedPage = e.selected;  
    console.log(selectedPage);
    setOffset((selectedPage)*pageLimit);  
  }

  return (
    <div>
      <div className="fave-container">
        <header className="db-component-header">
          <h1>My Favourites ({total}) </h1>
        </header>
        {isLoading ? (
           <div className="productSection">
           {products.map((item) => (
             <div className="productContainer" key={item.id}>
               <ProductModel
                 backgroundImage={item.image}
                 time={item.duration_in_days}
                 cost={item.price}
                 product={item.productName}
                 id={item.id}
               />
             </div>
           ))}
         </div>
        ) : (
          <div>Pending...</div>
        )}
      </div>
      <ReactPaginate
              previousLabel={"prev"}
              nextLabel={"next"}
              breakLabel={"..."}
              breakClassName={"break-me"}
              pageCount={pageCount}
              marginPagesDisplayed={2}
              pageRangeDisplayed={5}
              onPageChange={handlePageClick}
              containerClassName={"pagination"}
              subContainerClassName={"pages pagination"}
              activeClassName={"active"}/>
    </div>
  );
};

export default UserFaves;
