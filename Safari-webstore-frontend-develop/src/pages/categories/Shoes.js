import React from "react";
import Pagination from "../../components/Pagination";
import Footer from "../../components/Footer";
import ShoeLayout from "../../components/categoriesLayout/ShoeLayout";

function Shoes() {
  return (
    <>
      <ShoeLayout
        value={{
          author: "Shoes",
          names: [
            { name: "Booties", path: "localhost/" },
            { name: "Flats", path: "localhost" },
            { name: "Boots", path: "localhost" },
            { name: "Sandals", path: "localhost" },
            { name: "Sneakers", path: "localhost" },
            { name: "Slides and Slippers", path: "localhost" },
            { name: "Heels", path: "localhost" },
            { name: "Wedges", path: "localhost" },
            { name: "Mules", path: "localhost" },
            { name: "Party Shoes", path: "localhost" },
            { name: "Vegan Shoes", path: "localhost" },
            { name: "Oxfords", path: "localhost" },
          ],
          category: "Category",
        }}
      />
      <Pagination />
      <Footer />
    </>
  );
}

export default Shoes;
