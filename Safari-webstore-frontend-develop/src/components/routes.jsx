import React from 'react';
import { Switch, Route } from 'react-router-dom';
import Home from "../pages/HomePage";
import About from "../pages/AboutPage";
import Contact from "../pages/ContactPage";
import Cart from "../pages/CartPage";
import SignInSignUp from "../pages/SignInSignUp";
import TermsConditions from "../pages/TermsConditionsPage";
import Clothes from "../pages/categories/Clothes";
import Checkout from "../pages/CheckoutPage";
import Shoes from '../pages/categories/Shoes';
import Accessories from "../pages/categories/Accessories";
import AccountInfo from "../pages/account/Account";
import AccountAddressBook from "../pages/account/addressbook/AddressBook";
import AccountMyOrders from "../pages/account/MyOrders";
import AccountMyFavourites from "../pages/account/MyFavourites";
import AdminDashboard from "../pages/admindashboard/AdminDashboard";
import AdminOrders from "../pages/adminorders/AdminOrders";
import AdminProducts from "../pages/adminproducts/AdminProducts";
import AdminProductList from "../pages/adminproductlist/AdminProductList";
import ProductPage from "../pages/ProductPage";
import SearchPage from "../pages/SearchPage";
import FavouritesPage from "../pages/FavouritesPage";
import ConfirmOrderPage from "../pages/ConfirmOrderPage";
import ProtectedRoute from "./ProtectedRoute";
import SuccessPayment from "../pages/SuccessPayment";



const Routes = () => (
  <Switch>
    <Route path="/" exact component={Home} />
    <Route path="/about" component={About} />
    <Route path="/contact" component={Contact} />
    <Route path="/terms-conditions" component={TermsConditions} />
    <Route path="/cart" component={Cart} />
    <Route path="/signin-signup" component={SignInSignUp} />
    <Route path="/categories/clothes/" component={Clothes} />
    <Route path="/categories/shoes" component={Shoes} />
    <Route path="/categories/accessories" component={Accessories} />
    <ProtectedRoute path="/account/information" component={AccountInfo} />
    <Route path="/account/addressbook" component={AccountAddressBook} />
    <Route path="/account/myorders" component={AccountMyOrders} />
    <Route path="/account/myfavourites" component={AccountMyFavourites} />
    <ProtectedRoute path="/admin/dashboard" component={AdminDashboard} />
    <Route path="/favourites" component={FavouritesPage} />
    <ProtectedRoute path="/admin/orders" component={AdminOrders} />
    <ProtectedRoute path="/admin/products" component={AdminProducts} />
    <ProtectedRoute path="/admin/productList" component={AdminProductList} />
    <Route path="/search?keyword=data" component={SearchPage} />
    <Route path="/confirm-order" component={ConfirmOrderPage} />
    <Route path="/product/:id" component={ProductPage} />
    <Route path="/product" component={ProductPage} />
    <Route path="/search" component={SearchPage} />
    <Route path="/checkout" component={Checkout} />
    <Route path="/success-payment" component={SuccessPayment} />
  
  </Switch>
);

export default Routes;