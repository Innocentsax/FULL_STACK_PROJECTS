import React, { useCallback, useEffect, useState } from "react";
// import "./Favorites.css";
import ReactPaginate from "react-paginate";
import ProductModel from "../../shop/shopProduct/ProductModel";

const Products = () => {
    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoaing] = useState(false);
    const [offset, setOffset] = useState(0);
    const [total, setTotal] = useState(0);
    const [pageCount, setPageCount] = useState(0);
    const [pageLimit] = useState(5);
    const [pageNumber, setPageNumber] = useState(1);


    // TODO: Edit Url to be dynamic
   const serviceUrl = "https://fitnesso-app-new.herokuapp.com/product/view_products_np"
   // const serviceUrl = "https://http://localhost:9067/product/view_services_np"

  
    useEffect(() => {
      const fetchData = async () => {
          const response = await fetch(serviceUrl);
          const faveData = await response.json();
          console.log(faveData)
          setTotal(faveData.totalElements);
          setPageCount(Math.ceil(faveData.totalPages));
          setProducts(faveData)
          setIsLoaing(true);
      }
    fetchData();
  }, [pageNumber, pageLimit]);

  const handlePageClick = (e) => {
    const selectedPage = e.selected; 

    console.log(selectedPage)
    setPageNumber(selectedPage + 1) 
    console.log(pageNumber);
  }

  return (
    <div>
      <div className="fave-container">
        <div></div>
        <header className="db-component-header">
          <h1>All Products ({total}) </h1>
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
      {/* <ReactPaginate
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
              activeClassName={"active"}/> */}
    </div>
  );
}
export default Products;
