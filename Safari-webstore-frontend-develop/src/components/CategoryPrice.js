import React from "react";
import "../styles/Components/_category_price.scss";
import {
  Table,
  Grid,
  Dropdown,
  Segment,
  Container,
  Menu,
  GridColumn,
} from "semantic-ui-react";

function CategoryPrice({ click, activeFilter }) {
  return (
    <>
      <div className="Category-Price">
        <form>
          <input
            type="checkbox"
            checked={activeFilter.price.includes("0,10000")}
            className="prizecheckbox"
            value={[0, 10000]}
            onChange={click}
          />{" "}
          <label>₦0 - ₦10,000 </label>
          <br />
          <br />
          <input
            type="checkbox"
            checked={activeFilter.price.includes("10000,20000")}
            className="prizecheckbox"
            value={[10000, 20000]}
            onChange={click}
          />{" "}
          <label>₦10,000 - ₦20,000</label>
          <br />
          <br />
          <input
            type="checkbox"
            checked={activeFilter.price.includes("20000,50000")}
            className="prizecheckbox"
            value={[20000, 50000]}
            onChange={click}
          />{" "}
          <label>₦20,000 - ₦50,000</label>
          <br />
          <br />
          <input
            type="checkbox"
            checked={activeFilter.price.includes("50000,100000")}
            className="prizecheckbox"
            value={[50000, 100000]}
            onChange={click}
          />{" "}
          <label>₦50,000 - ₦100,000</label>
          <br />
          <br />
          <input
            type="checkbox"
            checked={activeFilter.price.includes("100000,200000")}
            className="prizecheckbox"
            value={[100000, 200000]}
            onChange={click}
          />{" "}
          <label>₦100,000 - ₦200,000</label>
          <br />
          <br />
          <div className="price-filter">
            <Grid>
              <GridColumn width="4">
                <input type="text" className="prizetext" placeholder="₦" />
              </GridColumn>
              <GridColumn width="1">
                <p className="filter-between">To</p>
              </GridColumn>
              <GridColumn width="4">
                <input type="text" className="prizetext" placeholder="₦" />
              </GridColumn>
              <GridColumn width="6">
                <button className="pricefilterapply">Apply</button>
              </GridColumn>
            </Grid>
          </div>
        </form>
      </div>
    </>
  );
}

export default CategoryPrice;
