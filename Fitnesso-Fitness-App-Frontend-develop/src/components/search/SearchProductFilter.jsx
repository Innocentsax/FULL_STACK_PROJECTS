import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Search.css";


function SearchProductFilter() {
  const [loading, setLoading] = useState(false);
  const [posts, setPosts] = useState([]);
  const [searchTitle, setSearchTitle] = useState("");
  useEffect(() => {
    const loadPosts = async () => {
      setLoading(true);
      const response = await axios.get("https://fitnesso-app-new.herokuapp.com/product/viewproducts");
      setPosts(response.data);
      console.log(response.data);
      setLoading(false);
    };
    
    loadPosts();
  }, []);
  return (
    <div className="search">
      <input className="search__input"
        type="text"
        placeholder="Search..."
        onChange={(e) => setSearchTitle(e.target.value)}
      />
      {loading ? (
        <h4> </h4>
      ) : (
        
           <div className="search__row">
     
          { 
            posts.filter((value) => {
              if (searchTitle === "") {
                return 
              } else if (value.productName.toUpperCase().includes(searchTitle.toUpperCase()) 
              || value.category.toUpperCase().includes(searchTitle.toUpperCase()) 
              || value.description.toUpperCase().includes(searchTitle.toUpperCase())) {
                return value;
              }
            })
            .map((item) => (
              <div key={item.id} className="search__box">
              <div className="search_products_wrapper">
                <a className="search__container" href={`/cart`}>
                  <span className="search__image">
                     <img src={item.image}/>

                  </span>
                  <span className="search__name">{item.productName}</span>
                  {/* <span className="search__description">{item.description}</span> */}
                  <span className="search__price">₦ {item.price}</span>
                </a>
              </div>
              </div>
            )) 
            }
        </div> 

      )
      }
    </div>
  );
}
export default SearchProductFilter;
