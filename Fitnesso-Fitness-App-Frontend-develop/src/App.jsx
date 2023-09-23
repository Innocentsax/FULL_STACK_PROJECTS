import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./app.css";
import HomePage from "../src/HomePage";
import Login from "../src/components/Login/Login"
import Signup from "./components/Signup/Signup";
import BlogPost from './components/Main/BlogPost/BlogPost.js'
import FullBlogPost from './components/Main/BlogPost/FullBlogPost'
import Layout from "./components/Layout/Layout"
import UserDashboard from "./components/Main/Dashboard/UserDashboard";
import Dashboard from "./components/Main/Dashboard/Dashboard";
import Orders from "./components/Main/Dashboard/Orders/Orders";
import UserFaves from "./components/Main/Dashboard/Favorites/UserFaves";
import UserInfo from "./components/Main/Dashboard/EditForms/UserInfo";
import UserPassword from "./components/Main/Dashboard/EditForms/UserPassword";
import ProductDashboard from "./components/Main/ProductCategory/ProductDashboard";
import AllProduct from "./components/Main/ProductCategory/AllProducts";
import Shop from "./components/ShopPage/ShopPage";
import CartPage from "./components/Cart/CartPage/CartPage";
import SearchProductFilter from "./components/search/SearchProductFilter";
import Contact from "./components/Contact/pages/Contact.jsx"
import Navbar from "./components/Main/Navbar/Navbar"
import Footer from "./components/Main/Footer/Footer";
import Products from "./components/Main/ProductCategory/Products";
import Services from "./components/Main/ProductCategory/Services";

import ViewProduct from "./components/Main/ProductCategory/ViewProduct";
import UserAddress from "./components/Main/Dashboard/EditForms/UserAddress";

import CheckingOut from "./components/CheckOut/CheckingOut";
import EmailCheck from "./components/EmailConfirmation/EmailCheck";
import ShopApp from './shopCart/ShopApp'
import AdminDashboard from "./components/adminDashboard/AdminDashboard";
import OrdersList from "./components/adminDashboard/adminOrders/OrdersList";
import UsersList from "./components/adminDashboard/viewAllUsers/UsersList";
import AddProduct from "./components/adminDashboard/addProductForm/AddProduct";
import ProductsList from "./components/adminDashboard/productRow/ProductsList";
import CreateAddress from "./components/CreateAddress/CreateAddress";

import ThankYouPage from "./components/Thankyou/ThankYouPage";
import Youtube from "./components/YoutubePlayer";
import Youtube2 from "./components/YoutubePlayer/index1";
import Youtube3 from "./components/YoutubePlayer/index2";
import AddTrainer from "./components/adminDashboard/addTrainer/AddTrainer";



function App() {

  return (
    <div className='app'>
      <div className='section'>
        <Navbar />
        <Router>
          <Routes>
            <Route exact path="/" element={<HomePage />} />
            <Route exact path="/login" element={<Login />} />

            <Route path="/product" element={<ProductDashboard/>}>
              <Route path="/product/item" element={<ViewProduct/>}/>
            </Route>
            <Route exact path="/signup" element={ <Signup/>} />

            <Route exact path="/v1" element={ <Youtube/>} />
            <Route exact path="/v2" element={ <Youtube2/>} />
            <Route exact path="/v3" element={ <Youtube3/>} />
            <Route exact path="/blog" element={ <Layout />} >
                <Route path="" element={<BlogPost/>} />
                <Route  path=":id" element={<FullBlogPost/>} />
            </Route >
            <Route exact path="/createaddress/:uname" element={ <CreateAddress/>} />
            <Route path="/product" element={<ProductDashboard/>}/>

            <Route exact path="/userdashboard" element={<UserDashboard />}>
              <Route index element={<Dashboard />} />
              <Route path="/userdashboard/edit-user-info" element={<UserInfo/>}/>
              <Route path="/userdashboard/orders" element={<Orders/>}/>
              <Route path="/userdashboard/user-faves" element={<UserFaves/>}/>
              <Route path="/userdashboard/edit-user-pass" element={<UserPassword/>}/>
              <Route path="/userdashboard/edit-user-address" element={<UserAddress/>}/>
            </Route>
            <Route exact path="/product" element={<ProductDashboard />}>
              <Route index element={<ShopApp/>} />
              <Route path="/product/products" element={<Products/>}/>
              <Route path="/product/services" element={<Services/>}/>
            </Route>
            <Route exact path="/search" element={<SearchProductFilter/>}/>
            <Route exact path="/cart" element={<ShopApp/>}/>
            <Route exact path="/shop" element={<Shop/>}/>
            <Route
              exact
              path='/contact'
              element={
                <div>
                  <Navbar />
                  <Contact />
                  <Footer />
                </div>
              }
            />
            <Route exact path="/checkout/*" element={<CheckingOut/>} />
            <Route exact path="/verify-email/*" element={<EmailCheck/>} />
            <Route exact path="/admindashboard" element={<AdminDashboard />}>
              <Route index element={<OrdersList/>} />
              <Route path="/admindashboard/view-users" element={<UsersList/>}/>
              <Route path="/admindashboard/add-products" element={<AddProduct/>}/>
              <Route path="/admindashboard/add-trainer" element={<AddTrainer/>}/>
              <Route path="/admindashboard/view-products" element={<ProductsList/>}/>
              <Route path="/admindashboard/edit-admin-pass" element={<UserPassword/>}/>
            </Route>
              <Route exact path="/thank-you-page" element={<ThankYouPage/>}/>
          </Routes>
        </Router>
      </div>
    </div>
  )
}

export default App;
