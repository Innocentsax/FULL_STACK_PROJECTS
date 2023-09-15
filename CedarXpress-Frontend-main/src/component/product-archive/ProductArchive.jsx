import React, {useState, useLayoutEffect} from 'react'
import ArchiveCss from "./Product-archive.module.css";
import axios from "axios";
import APIS from "../../data/urls";


const ProductArchive = () => {
    const API = APIS.FETCH_PRODUCTS_API;

    const [products, setProducts] = useState([]);

    const fetchProductsFromAPI = async () => {
        console.log("-------" + API + "----------")
        axios.get(API).then(res => {
            const data = res.data;
            setProducts(data);
        })
        console.log(products)
    }

    useLayoutEffect(()=>fetchProductsFromAPI, []);


    // const data = [
    //   {
    //     id: 1,
    //     img: "https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/J7ZW2XK@2x-300x300.jpg",
    //     title: "Coffe Table",
    //     price: 910.0,
    //     btn: "Add to cart",
    //   },
    //   {
    //     id: 2,
    //     img: "  https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/XFSNSK@2x-300x300.jpg",
    //     title: "Table wood",
    //     price: 126.0,
    //     btn: "Add to cart",
    //   },
    //   {
    //     id: 3,
    //     img: "https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-RBSJBF@2x-300x300.jpg",
    //     title: "Deco Lamp",
    //     price: 126.0,
    //     btn: "Add to cart",
    //   },
    //   {
    //     id: 4,
    //     img: "https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-U5BW8PS@2x-300x300.jpg",
    //     title: "End Table",
    //     price: 180.0,
    //     btn: "Add to cart",
    //   },
    //   {
    //     id: 5,
    //     img: "https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/3N8FQJ@2x-300x300.jpg",
    //     title: "Lounge Sofa",
    //     price: 435.0,
    //     btn: "Add to cart",
    //   },
    //   {
    //     id: 6,
    //     img: "https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-WAX664T@2x-300x300.jpg",
    //     title: "Modern Chair",
    //     price: 115.0,
    //     btn: "Add to cart",
    //   },
    //   {
    //     id: 7,
    //     img: "https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/J7ZW2XK@2x-300x300.jpg",
    //     title: "Modern Table",
    //     price: 89.0,
    //     btn: "Add to cart",
    //   },
    //   {
    //     id: 8,
    //     img: "https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/3N8FQJ@2x-300x300.jpg",
    //     title: "Scandinavia Dresser",
    //     price: 115.0,
    //     btn: "Add to cart",
    //   },
    //   {
    //     id: 6,
    //     img: "https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-WAX664T@2x-300x300.jpg",
    //     title: "Modern Chair",
    //     price: 115.0,
    //     btn: "Add to cart",
    //   },
    // ];
  return (
    <div className={ArchiveCss.product__archive}>
      <div className={ArchiveCss.product__archive_container}>
        <h1 className={ArchiveCss.container}>Product Archive</h1>
      </div>
      <div className={`${ArchiveCss.product__archive_header} ${ArchiveCss.container}`}>
        <p>Showing 1–9 of 12 results</p>
        <form class={ArchiveCss.woocommerce_ordering} method="get">
          <select name="orderby" class="orderby" aria-label="Shop order">
            <option value="menu_order" selected="selected">
              Default sorting
            </option>
            <option value="popularity">Sort by popularity</option>
            <option value="rating">Sort by average rating</option>
            <option value="date">Sort by latest</option>
            <option value="price">Sort by price: low to high</option>
            <option value="price-desc">Sort by price: high to low</option>
          </select>
          <input type="hidden" name="paged" value="1" />
        </form>
      </div>
      <ul className="container">
        {/* {data.map((item, index) => (
          <li key={index}>
            <img src={item.img} alt="" />
            <h2>{item.title}</h2>
            <p>$ {item.price}</p>
            <button>{item.btn}</button>
          </li>
        ))} */}

        {products.map((item, index) => (
          <li key={index}>
            <img src={item.imges[0]} alt="" />
            <h2>{item.productName}</h2>
            <p>$ {item.price}</p>
            <button>{item.btn}</button>
          </li>
        ))}     
      </ul>
    </div>
  );
}
export default ProductArchive