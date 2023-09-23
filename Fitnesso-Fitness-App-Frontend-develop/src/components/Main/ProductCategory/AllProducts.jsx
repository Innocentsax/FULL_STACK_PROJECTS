import React, { useCallback, useEffect, useState } from "react";
// import "./Favorites.css";
import ReactPaginate from "react-paginate";
import { FavoriteButton } from "../../services/FavoriteButton";
import Paginations from "../../services/Paginations";
import ProductModel from "../../shop/shopProduct/ProductModel";

const AllProduct = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoaing] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [offset, setOffset] = useState(0);
  const [pageCount, setPageCount] = useState(0);
  const [pageLimit] = useState(10);
  const [pageNumber, setPageNumber] = useState(1);
  const [total, setTotal] = useState(0);


    const productUrl = "https://fitnesso-app-new.herokuapp.com/product/viewproducts/"
    console.log(pageNumber);

  
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(productUrl + `${pageNumber}`)
            const faveData = await response.json();
            console.log("Products from the DB : " + faveData)
            setTotal(faveData.totalElements);
            setPageCount(Math.ceil(faveData.totalPages));
            setProducts(faveData.content)
            setIsLoaing(true);
        }
      fetchData();
    }, [pageNumber, pageLimit]);
  
  const onPageChanged = (event, page) => {
    event.preventDefault();
    setCurrentPage(page);
  };

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(productUrl);
      const productData = await response.json();
      console.log(productData);
      setTotal(productData.length);
      setPageCount(Math.ceil(total/pageLimit));
      setProducts(productData.slice(offset, offset+pageLimit));
      setIsLoaing(true);
    };
    fetchData();
  }, [offset]);

  // const [currentPage, setCurrentPage] = useState(1);
  let NUM_OF_RECORDS = products.length;
  let LIMIT = 10;

  const handlePageClick = (e) => {
     setCurrentPage(e.selected);
    setOffset((currentPage + 1)*perPage)
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
      <div className="pagination-wrapper">
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
          activeClassName={"active"} */}
        />
      </div>
    </div>
  );
};
export default AllProduct;
