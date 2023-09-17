import React from "react";
import { Link } from "react-router-dom";
import "../styles/Components/_category_category.scss";

function CategoryCategory(props) {
  return (
    <>
      <li className="categoriesNavlinks">
        {/* <Link to ={props.path}> */}
        <p className="category-name" onClick={props.click}>
          {props.name}
        </p>
        {/* </Link> */}
      </li>
    </>
  );
}

export default CategoryCategory;
