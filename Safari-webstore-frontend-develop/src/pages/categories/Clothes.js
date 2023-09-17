import React from "react";
import Pagination from "../../components/Pagination";
import Footer from "../../components/Footer";
import ClothingLayout from "../../components/categoriesLayout/ClothingLayout";

function Clothes() {
  return (
    <>
      <ClothingLayout
        value={{
          author: "Clothes",
          names: [
            { name: "Dresses", path: "localhost/" },
            { name: "Denim", path: "localhost" },
            { name: "Jeans", path: "localhost" },
            { name: "Jumpsuits", path: "localhost" },
            { name: "Top", path: "localhost" },
            { name: "Jacket and coats", path: "localhost" },
            { name: "Pants", path: "localhost" },
            { name: "Shorts", path: "localhost" },
            { name: "Skirts", path: "localhost" },
            { name: "Loungerie & underwear", path: "localhost" },
            { name: "Leather", path: "localhost" },
            { name: "Sweater and knits", path: "localhost" },
          ],
          category: "Category",
        }}
      />
      <Pagination />
      <Footer />
    </>
  );
}

export default Clothes;
