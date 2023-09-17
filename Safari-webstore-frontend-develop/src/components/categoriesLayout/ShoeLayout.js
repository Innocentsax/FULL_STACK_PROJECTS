import React, { useState, useEffect, useCallback } from "react";
import "../../styles/Layout/_product_layout.scss";
import CategoryCategory from "../CategoryCategory";
import CategorySize from "../CategorySize";
import CategoryPrice from "../CategoryPrice";
import CategoryColor from "../CategoryColor";
import ShoesPage from "../../pages/ShoesPage";
import productApis from "../../apis/ProductApi";
import { Grid, Container, GridColumn } from "semantic-ui-react";

const ProductsLayout = (props) => {
  const [loadedShoes, setLoadedShoes] = useState([]);
  const [filteredLoadedShoes, setFilteredLoadedShoes] = useState([]);
  const [checkedPrices, setCheckedPrices] = useState([]);
  const [activeFilter, setActiveFilter] = useState({
    shoes: "",
    color: "",
    size: "",
    price: [],
  });

  useEffect(async () => {
    const produc = await productApis.getAllProduct();
    const category = "Shoes";
    const products = produc.content;
    const allShoes = products.filter(product=>product.category[0].name === category)
    console.log("products", allShoes);

    setLoadedShoes(allShoes);
    setFilteredLoadedShoes(allShoes);
    console.log(loadedShoes);
  }, []);

  useCallback(() => {
    console.log("checkedPrices", checkedPrices);
  }, [checkedPrices]);

  // Function to filter shoes by subCategory
  const allFilters = () => {
    const { color, size, shoes, price } = activeFilter;
    let filteredShoes = loadedShoes;
    if (shoes)
      filteredShoes = filteredShoes.filter((shoe) =>
        shoe.subCategory.some((sub) => sub.name === shoes)
      );
    if (color)
      filteredShoes = filteredShoes.filter((shoe) =>
        shoe.colors.some((item) => item.color === color)
      );
    if (size)
      filteredShoes = filteredShoes.filter((shoe) =>
        shoe.sizes.some((item) => item.size === size)
      );
    if (price.length > 0)
      filteredShoes = filteredShoes.filter(
        (shoe) =>
          +shoe.price >= Number(price[0].split(",")[0]) &&
          +shoe.price <= Number(price[price.length - 1].split(",")[1])
      );

    setFilteredLoadedShoes(filteredShoes);
  };

  const filterByCategory = (category) => {
    if (category === "All") return setFilteredLoadedShoes(loadedShoes);
    setActiveFilter({ ...activeFilter, shoes: category });
    allFilters();
  };

  //Function to filter shoes by size
  const filterBySize = (size) => {
    setActiveFilter({ ...activeFilter, size });
    allFilters();
  };

  //Function to filter shoes by color
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
          {filteredLoadedShoes.length === 0 ? (
            <h4>NO MATCH FOUND!!!</h4>
          ) : (
            <ShoesPage products={filteredLoadedShoes} />
          )}
        </GridColumn>
      </Grid>
    </Container>
  );
};

export default ProductsLayout;

{
}
