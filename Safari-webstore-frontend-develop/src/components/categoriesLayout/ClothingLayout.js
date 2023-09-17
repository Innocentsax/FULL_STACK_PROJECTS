import React, { useState, useEffect, useCallback } from "react";
import "../../styles/Layout/_product_layout.scss";
import CategoryCategory from "../CategoryCategory";
import CategorySize from "../CategorySize";
import CategoryPrice from "../CategoryPrice";
import CategoryColor from "../CategoryColor";
import ClothesPage from "../../pages/ClothesPage";
import productApis from "../../apis/ProductApi";
import { Grid, Container, GridColumn } from "semantic-ui-react";

const ProductsLayout = (props) => {
  const [loadedClothes, setLoadedClothes] = useState([]);
  const [filteredLoadedClothes, setFilteredLoadedClothes] = useState([]);
  const [checkedPrices, setCheckedPrices] = useState([]);
  const [activeFilter, setActiveFilter] = useState({
    clothes: "",
    color: "",
    size: "",
    price: [],
  });

  useEffect(async () => {
    const produc = await productApis.getAllProduct();
    const category = "Clothes";
    const products = produc.content;
    const allClothes = products.filter(product=>product.category[0].name === category)
    console.log("products", allClothes);

    setLoadedClothes(allClothes);
    setFilteredLoadedClothes(allClothes);
    console.log(loadedClothes);
  }, []);

  useCallback(() => {
    console.log("checkedPrices", checkedPrices);
  }, [checkedPrices]);

  // Function to filter clothes by subCategory
  const allFilters = () => {
    const { color, size, clothes, price } = activeFilter;
    let filteredClothes = loadedClothes;
    if (clothes)
      filteredClothes = filteredClothes.filter((cloth) =>
        cloth.subCategory.some((sub) => sub.name === clothes)
      );
    if (color)
      filteredClothes = filteredClothes.filter((cloth) =>
        cloth.colors.some((item) => item.color === color)
      );
    if (size)
      filteredClothes = filteredClothes.filter((cloth) =>
        cloth.sizes.some((item) => item.size === size)
      );
    if (price.length > 0)
      filteredClothes = filteredClothes.filter(
        (cloth) =>
          +cloth.price >= Number(price[0].split(",")[0]) &&
          +cloth.price <= Number(price[price.length - 1].split(",")[1])
      );

    setFilteredLoadedClothes(filteredClothes);
  };

  const filterByCategory = (category) => {
    if (category === "All") return setFilteredLoadedClothes(loadedClothes);
    setActiveFilter({ ...activeFilter, clothes: category });
    allFilters();
    // const filteredClothes = loadedClothes.filter((cloth) => cloth.subCategory.some(sub => sub.name === category));
    // setFilteredLoadedClothes(filteredClothes);
  };

  //Function to filter clothes by size
  const filterBySize = (size) => {
    // const filteredClothes = filteredLoadedClothes.filter((cloth) => cloth.sizes.some(item => item.size === size));
    // setFilteredLoadedClothes(filteredClothes);
    setActiveFilter({ ...activeFilter, size });
    allFilters();
  };

  //Function to filter clothes by color
  const filterByColor = (color) => {
    // const filteredClothes = filteredLoadedClothes.filter((cloth) => cloth.colors.some(item => item.color === color));
    // setFilteredLoadedClothes(filteredClothes);
    setActiveFilter({ ...activeFilter, color });
    allFilters(0);
  };

  const handleCheck = (e) => {
    // if(checkedPrices.length === 0) return setFilteredLoadedClothes(loadedClothes)
    console.log(e.target.checked);
    const { price } = activeFilter;
    if (!price.includes(e.target.value)) {
      price.push(e.target.value);
      setActiveFilter({ ...activeFilter, price });
    } else {
      setActiveFilter({
        ...activeFilter,
        price: price.filter((item) => item !== e.target.value),
      });
    }
    if (checkedPrices[0])
      console.log(
        "checkedPrices",
        checkedPrices.sort((a, b) => +a.split(",")[0] - b.split(",")[0])
      );

    //   const filteredClothes = loadedClothes.filter(cloth => +cloth.price >= Number(checkedPrices[0].split(",")[0]) && +cloth.price <= Number(checkedPrices[checkedPrices.length - 1].split(",")[1]))
    //   setFilteredLoadedClothes(filteredClothes);
    allFilters();
  };

  //This function clears all selected items according to categories
  const handleClear = (filter) => {
    if (filter === "price") setActiveFilter({ ...activeFilter, [filter]: [] });
    else setActiveFilter({ ...activeFilter, [filter]: "" });
    // activeFilter[filter] = "";
    console.log(activeFilter, "active");
    allFilters();
  };
  const { author, names, category } = props.value;
  return (
    <Container fluid className="product__container">
      <h1 className="category-title">{author}</h1>
      <Grid padded>
        <Grid.Column mobile={16} tablet={8} computer={4}>
          <h3 className="category">{category}</h3>
          <ul className="">
            <CategoryCategory
              click={() => filterByCategory("All")}
              name="All"
              path="localhost.com"
            />
            {names?.map((name, index) => (
              <CategoryCategory
                click={() => filterByCategory(name.name)}
                name={name.name}
                path={name.path}
                key={index}
              />
            ))}
          </ul>
          <Grid className="category">
            <GridColumn width="12">
              <h3 className="">SIZE</h3>
            </GridColumn>
            <GridColumn width="4">
              <p className="category-clear" onClick={() => handleClear("size")}>
                Clear<i class="fas fa-times times-clear"></i>
              </p>
            </GridColumn>
          </Grid>
          <CategorySize click={filterBySize} />
          <Grid className="category">
            <GridColumn width="12">
              <h3 className="">COLOR</h3>
            </GridColumn>
            <GridColumn width="4">
              <p
                className="category-clear"
                onClick={() => handleClear("color")}
              >
                Clear <i class="fas fa-times times-clear"></i>
              </p>
            </GridColumn>
          </Grid>
          <CategoryColor click={filterByColor} />
          <Grid className="category">
            <GridColumn width="12">
              <h3 className="">PRICE</h3>
            </GridColumn>
            <GridColumn width="4">
              <p
                className="category-clear"
                onClick={() => handleClear("price")}
              >
                Clear <i class="fas fa-times times-clear"></i>
              </p>
            </GridColumn>
          </Grid>
          <CategoryPrice click={handleCheck} activeFilter={activeFilter} />
        </Grid.Column>
        <GridColumn mobile={16} tablet={4} computer={12}>
          {filteredLoadedClothes.length === 0 ? (
            <h4>NO MATCH FOUND!!!</h4>
          ) : (
            <ClothesPage products={filteredLoadedClothes} />
          )}
        </GridColumn>
      </Grid>
    </Container>
  );
};

export default ProductsLayout;

{
  /* <div className="Pages">
      <div>
        <h1 className="category-title">{author}</h1>
      </div>
      <div className="productLayout">
        <div className="side">
          <h3 className="category">{category}</h3>
          <ul className="">
            <CategoryCategory
              click={() => filterByCategory("All")}
              name="All"
              path="localhost.com"
            />
            {names?.map((name, index) => (
              <CategoryCategory
                click={() => filterByCategory(name.name)}
                name={name.name}
                path={name.path}
                key={index}
              />
            ))}
          </ul>
          <div className="category-size-clear">
            <div className="">
              <h3 className="category-size">SIZE</h3>
            </div>
            <div className="category-clear" onClick={() => handleClear("size")}>
              <p>
                Clear <i class="fal fa-times"></i>
              </p>
            </div>
          </div>
          <CategorySize click={filterBySize} />
          <div className="category-size-clear">
            <div>
              <h3 className="category-color">COLOR</h3>
            </div>
            <div
              className="category-clear"
              onClick={() => handleClear("color")}
            >
              <p>
                Clear <i class="fal fa-times"></i>
              </p>
            </div>
          </div>
          <CategoryColor click={filterByColor} />
          <div className="category-size-clear">
            <div>
              <h3 className="category-color">PRICE</h3>
            </div>
            <div
              className="category-clear"
              onClick={() => handleClear("price")}
            >
              <p>
                Clear <i class="fal fa-times"></i>
              </p>
            </div>
          </div>
          <CategoryPrice click={handleCheck} activeFilter={activeFilter} />
        </div>
        {filteredLoadedClothes.length === 0 ? (
          <h4>NO MATCH FOUND!!!</h4>
        ) : (
          <ClothesPage products={filteredLoadedClothes} />
        )}
      </div>
    </div> */
}
