import React from "react";
import Hero from "../shop/Premium/Hero";
import ProductCard from "../shop/shopProduct/ProductCard";
import Navbar from "../Main/Navbar/Navbar";
import Footer from "../Main/Footer/Footer";
import SearchProductFilter from "../search/SearchProductFilter";



function Shop(){
  return (
    <div>
        <Navbar/>
        {/* <SearchProductFilter /> */}
        <Hero />
        <ProductCard />
        <Footer/>
    </div>
  )
}

export default Shop;
