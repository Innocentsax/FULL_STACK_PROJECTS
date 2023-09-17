import React from "react";
import Pagination from "../../components/Pagination";
import Footer from "../../components/Footer";
import AccessoriesLayout from "../../components/categoriesLayout/AccessoriesLayout";

function Accessories() {
  return (
    <>
      <AccessoriesLayout
        value={{
          author: "Accessories",
          names: [
            { name: "Facemask", path: "localhost/" },
            { name: "Jewelry", path: "localhost" },
            { name: "Watches", path: "localhost" },
            { name: "Hair accessories", path: "localhost" },
            { name: "Belts", path: "localhost" },
            { name: "Backpacks", path: "localhost" },
            { name: "Handbags", path: "localhost" },
            { name: "Fragrances", path: "localhost" },
            { name: "Sunglasses and eyewears", path: "localhost" },
            { name: "Socks", path: "localhost" },
            { name: "Hats and beanies", path: "localhost" },
            { name: "Gloves", path: "localhost" },
          ],
          category: "Category",
        }}
      />
      <Pagination />
      <Footer />
    </>
  );
}

export default Accessories;
