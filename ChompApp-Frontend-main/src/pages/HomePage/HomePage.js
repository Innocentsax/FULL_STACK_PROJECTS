import React, { useEffect, useState } from "react";
import img1 from "../../asset/trustpilot.svg";
import banner_image from "../../asset/banner_image.jpeg";
import step_2 from "../../asset/step_2.png";
import img2 from "../../asset/Pattern_01.svg";
import img3 from "../../asset/pexels-photo-315755.jpeg";
import "./HomePage.css";
import img4 from "../../asset/step_2.png";
import img5 from "../../asset/Step_01.png";
import img6 from "../../asset/Step_03.png";
import ShoppingSection from "../../component/ShoppingSection/ShoppingSection";
import Footer from "../../component/footer/Footer";
import { useSelector } from "react-redux";
import ProductService from "../../services/ProductService";
import Cart from "../../models/Cart";
import CartService from "../../services/CartService";
import { Link } from "react-router-dom";
import { motion } from "framer-motion";

const HomePage = () => {
  const [productList, setProductList] = useState([]);
  const [errorMessage, setErrorMessage] = useState(false);
  const [infoMessage, setInfoMessage] = useState(false);
  const [burgerState, setBurgerState] = useState(true);
  const [sideState, setSideState] = useState(false);
  const [drinksState, setDrinksState] = useState(false);

  const currentUser = useSelector((state) => state.user);

  const burgerHandleState = (e) => {
    e.preventDefault();
    setBurgerState(true);
    setSideState(false);
    setDrinksState(false);
  };

  const sidesHandleState = (e) => {
    e.preventDefault();
    setSideState(true);
    setBurgerState(false);
    setDrinksState(false);
  };

  const drinksHandleState = (e) => {
    e.preventDefault();
    setDrinksState(true);
    setSideState(false);
    setBurgerState(false);
  };

  useEffect(() => {
    ProductService.getAllProducts()
      .then((response) => {
        setProductList(response.data.content);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const cart = (product, productId) => {
    if (!currentUser?.userId) {
      setErrorMessage("You should login to buy a product");
      return;
    }

    const cart = new Cart(
      currentUser.userId,
      product.productId,
      product.productName,
      product.productImage,
      product.size,
      product.quantity,
      product.unitPrice,
      product.subTotal,
      product.cartId
    );

    CartService.addToCart(cart, productId)
      .then((response) => {
        console.log(cart);
        console.log(response);
        setInfoMessage(true);
      })
      .catch((error) => {
        setErrorMessage(true);
        console.log(error);
      });
  };

  useEffect(() => {
    setTimeout(() => {
      if (infoMessage || errorMessage) {
        setInfoMessage(false);
        setErrorMessage(false);
      }
    }, 2000);
  }, [infoMessage, errorMessage]);
  return (
    <div>
      <section className="container homepage">
        <div className="homepage__wrapper">
          <div className="homepage__banner-left">
            <h1>
              Beautiful food & <br /> takeaway, <br /> <span>delivered</span> to
              your <br /> door.
            </h1>
            <p>
              We deliver nation wide in 45mins. <br /> teste mouth watering food
              and dessert.
              <br /> all food are made in industry standard and more since the
              2018.
            </p>
            <Link to="/fullMenu">
              <button type="button" class="btn btn-success">
                Place an Order
              </button>
            </Link>
            <div className="homepage__banner-left_bottom">
              <img src={img1} alt="" />
              <p>
                <span>4.8 out of 5 </span>based on 2000+ reviews
              </p>
            </div>
          </div>
          <div className="homepage__banner-right">
            <div className="banner-right_main-img">
              <img className="" src={banner_image} alt="" />
            </div>
            <div className="banner-right_step2">
              <img src={img2} alt="" />
              <img src={step_2} alt="" />
            </div>
          </div>
        </div>
      </section>
      <section>
        <div className="container section__two">
          <div className="section__two-right">
            <img src={img3} alt="" />
          </div>
          <div className="section__two-left">
            <h1>
              The home of <br /> fresh products
            </h1>
            <p>
              Food so yummy and delicious, it reminds you of home and happiness
            </p>
            <button type="button" class="btn btn-success">
              Learn about us
            </button>
          </div>
        </div>
      </section>
      <section>
        <div className="container section__three">
          <h1>How it works.</h1>
          <div className="section__three-flex">
            <div className="flex-one">
              <img src={img4} alt="" />
              <h5>Adapt your menu items</h5>
              <p>
                Easily adapt your menu using the webflow CMS and allow customers
                to browse your items.
              </p>
            </div>
            <div className="flex-one">
              <img src={img5} alt="" />
              <h5>Accept online orders & takeout</h5>
              <p>
                Let your customers order and pay via the powerful ecommerce
                system, or simple let them call your store.
              </p>
            </div>
            <div className="flex-one">
              <img src={img6} alt="" />
              <h5>Manage delivery or takeout</h5>
              <p>
                Manage your own logistics and take orders simply through the
                ecommerce system.
              </p>
            </div>
          </div>
        </div>
      </section>
      <section>
        <div className="section__four container">
          <h2>Browse our menu</h2>
          <p>
            Use our menu to <span>place</span> an order online, or phone our
            store to <br /> place a pickup order. Fast and fresh food.
          </p>
          <div className="button__section">
            <button className="btn btn-success" onClick={burgerHandleState}>
              Burgers
            </button>
            <button className="btn btn-success" onClick={sidesHandleState}>
              Sides
            </button>
            <button className="btn btn-success" onClick={drinksHandleState}>
              Drinks
            </button>
          </div>

          {errorMessage && (
            <div
              className="alert alert-danger alert-dismissible fade show d-flex justify-content-between"
              role="alert"
            >
              Unexpected error occured
            </div>
          )}

          {infoMessage && (
            <div
              className="alert alert-success alert-dismissible fade show d-flex justify-content-between"
              role="alert"
            >
              Added successfully
            </div>
          )}
          <div className="section__four-product">
            {burgerState && (
              <div className="content__wrapper">
                {productList.map(
                  ({
                    productImage,
                    productName,
                    productPrice,
                    size,
                    categoryName,
                    productId,
                    productDescription,
                  }) => (
                    <div className="section__four-menu">
                      {burgerState && categoryName === "Burger" && (
                        <ShoppingSection
                          img={productImage}
                          title={productName}
                          price={productPrice}
                          size={size}
                          cart={cart}
                          productId={productId}
                          productDescription={productDescription}
                          key={productId}
                        />
                      )}
                    </div>
                  )
                )}
              </div>
            )}

            {sideState && (
              <div className="content__wrapper">
                {productList.map(
                  ({
                    productImage,
                    productName,
                    productPrice,
                    size,
                    categoryName,
                    productId,
                    productDescription,
                  }) => (
                    <div className="section__four-menu">
                      {sideState && categoryName === "Sides" && (
                        <ShoppingSection
                          img={productImage}
                          title={productName}
                          price={productPrice}
                          size={size}
                          cart={cart}
                          productId={productId}
                          productDescription={productDescription}
                          key={productId}
                        />
                      )}
                    </div>
                  )
                )}
              </div>
            )}

            {drinksState && (
              <div className="content__wrapper">
                {productList.map(
                  ({
                    productImage,
                    productName,
                    productPrice,
                    size,
                    categoryName,
                    productId,
                    productDescription,
                  }) => (
                    <div className="section__four-menu">
                      {drinksState && categoryName === "Drinks" && (
                        <ShoppingSection
                          img={productImage}
                          title={productName}
                          price={productPrice}
                          size={size}
                          cart={cart}
                          productId={productId}
                          productDescription={productDescription}
                          key={productId}
                        />
                      )}
                    </div>
                  )
                )}
              </div>
            )}
          </div>
          <div className="button">
            <Link to="/fullMenu">
              <button className="btn full-menu btn-success">
                See Full Menu
              </button>
            </Link>
          </div>
        </div>
      </section>
      <section>
        <div className="container section__two">
          <div className="section__two-right">
            <img
              src="https://tmbidigitalassetsazure.blob.core.windows.net/rms3-prod/attachments/37/1200x1200/Pizza-from-Scratch_EXPS_FT20_8621_F_0505_1_home.jpg"
              alt=""
            />
          </div>
          <div className="section__two-left">
            <h1>Order online with our simple checkout.</h1>
            <p>
              Let your customers order and pay via the powerful ecommerce
              system, or simple let them call your store. Easily adapt your menu
              using the webflow CMS and allow customers to browse your items.
            </p>
            <button type="button" class="btn btn-success">
              See our FAQ
            </button>
          </div>
        </div>
      </section>

      <section className="container homepage">
        <div className="homepage__wrapper">
          <div className="homepage__banner-left">
            <h2>Call our store and takeaway when it suits you best.</h2>
            <p>
              Manage your own logistics and take orders simply through the
              ecommerce system.
            </p>
            <button type="button" class="btn btn-success">
              Ph. +61 233 2333
            </button>
          </div>
          <div className="homepage__banner-right">
            <div className="banner-right_main-img-1">
              <img
                className=""
                src="https://images.pexels.com/photos/2253643/pexels-photo-2253643.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                alt=""
              />
            </div>
          </div>
        </div>
      </section>
      <Footer />
    </div>
  );
};

export default HomePage;
