import React, { useState, useEffect, useCallback } from "react";
import "../../styles/Layout/_product_layout.scss";
import CategoryCategory from "../CategoryCategory";
import CategorySize from "../CategorySize";
import CategoryPrice from "../CategoryPrice";
import CategoryColor from "../CategoryColor";
import AccessoriesPage from "../../pages/AccessoriesPage";
import productApis from "../../apis/ProductApi";
import { Grid, Container, GridColumn } from "semantic-ui-react";

const ProductsLayout = (props) => {
  const [loadedAccessories, setLoadedAccessories] = useState([]);
  const [filteredLoadedAccessories, setFilteredLoadedAccessories] = useState(
    []
  );
  const [checkedPrices, setCheckedPrices] = useState([]);
  const [activeFilter, setActiveFilter] = useState({
    accessories: "",
    color: "",
    size: "",
    price: [],
  });

  useEffect(async () => {
    const produc = await productApis.getAllProduct();
    const category = "Accessories";
    const products = produc.content;
    const allAccessories = products.filter(product=>product.category[0].name === category)
    console.log("products", allAccessories);

    setLoadedAccessories(allAccessories);
    setFilteredLoadedAccessories(allAccessories);
    console.log(loadedAccessories);
  }, []);

  useCallback(() => {
    console.log("checkedPrices", checkedPrices);
  }, [checkedPrices]);

  // Function to filter Accessories by subCategory
  const allFilters = () => {
    const { color, size, accessories, price } = activeFilter;
    let filteredAccessories = loadedAccessories;
    if (accessories)
      filteredAccessories = filteredAccessories.filter((accessory) =>
        accessory.subCategory.some((sub) => sub.name === accessories)
      );
    if (color)
      filteredAccessories = filteredAccessories.filter((accessory) =>
        accessory.colors.some((item) => item.color === color)
      );
    if (size)
      filteredAccessories = filteredAccessories.filter((accessory) =>
        accessory.sizes.some((item) => item.size === size)
      );
    if (price.length > 0)
      filteredAccessories = filteredAccessories.filter(
        (accessory) =>
          +accessory.price >= Number(price[0].split(",")[0]) &&
          +accessory.price <= Number(price[price.length - 1].split(",")[1])
      );

    setFilteredLoadedAccessories(filteredAccessories);
  };

  const filterByCategory = (category) => {
    if (category === "All")
      return setFilteredLoadedAccessories(loadedAccessories);
    setActiveFilter({ ...activeFilter, accessories: category });
    allFilters();
  };

  //Function to filter Accessories by size
  const filterBySize = (size) => {
    setActiveFilter({ ...activeFilter, size });
    allFilters();
  };

  //Function to filter Accessories by color
  const filterByColor = (color) => {
    setActiveFilter({ ...activeFilter, color });
    allFilters(0);
  };

  const handleCheck = (e) => {
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
          {filteredLoadedAccessories.length === 0 ? (
            <h4>NO MATCH FOUND!!!</h4>
          ) : (
            <AccessoriesPage products={filteredLoadedAccessories} />
          )}
        </GridColumn>
      </Grid>
    </Container>
  );
};

export default ProductsLayout;
