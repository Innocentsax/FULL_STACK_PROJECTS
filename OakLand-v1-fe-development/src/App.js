import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import BackToTop from "./components/BackToTopButton";
import Footer from "./components/Footer";
import NavBar from "./components/NavBar/NavBar";
import HomePage from "./screens/Home/Hompage";
import ContactUs from "./screens/ContactUs/ContactUs";
import Subcategory from "./screens/Subcategory/Subcategory";
import Page404 from "./screens/404Page/Page404";
import Product from "./screens/Product/Product";
import SingleProduct from "./screens/Product/SingleProduct";
import AccountDashboard from "./screens/AccountDashboard/AccountDashboard";
import UserInformation from "./components/UserInformation/UserInformation";
import FormSignUp from "./screens/Signup/FormSignUp";
import DashboardInfo from "./screens/AccountDashboard/DasboardInfo";
import FormLogin from "./screens/Login/FormLogin";
import AddressbookDashboard from "./screens/AccountDashboard/AddressbookDashboard";
import NewAddress from "./components/AddressBook/NewAddress";
import ResetPassword from './screens/ResetPassword/ResetPassword';
import ForgottenPassword from './screens/ForgottenPassword/ForgottenPaassword';
import AboutUs from './screens/AboutUs/AboutUs';
import ShoppingCart from "./screens/ShoppingCart/ShoppingCart";
import VerifyRegistration from "./screens/Signup/VerifySignup";
import Checkout from "./screens/Checkout/Checkout";
import { IsAuthenticated, ProtectCustomerRoute, RequireAdminAuth, } from "./context/ProtectRoute";
import WalletDashboard from "./screens/WalletPage/WalletDashoard";
import VerifyPayment from "./screens/WalletPage/VerifyPayment";
import Layout from './Admin/components/Layout/Layout';
import TableView from './Admin/components/Product/TableView';
import PersonTableView from "./Admin/components/Person/PersonTableView";
import OrdersTableView from './Admin/components/Order/OrdersTableView'
import OpenOrders from "./screens/Orders/OpenOrders";
import ClosedOrders from "./screens/Orders/ClosedOrders";
import ProcessPayment from "./screens/ProcessPayment/ProcessPayment";
import Checkout2 from "./screens/Checkout2/Checkout2";
import CheckMail from "./screens/Signup/CheckMail";
import PickupTableView from "./Admin/components/PickupCenter/PickupTableView";
// import SuperAdminDashBoard from "./SuperAdmin/Dashboard";
import StatesTableView from "./Admin/components/States/StatesTableView";
import Dashboard from "./Admin/components/Dashboard/Dashboard";
import SubcategoryTableView from "./Admin/components/Cat_SubCat/SubcategoryTableView";
import Home from "./Admin/components/Dashboard/Home";
import { useAuth } from "./context/authcontext";
import { EditAddress } from "./components/AddressBook/AddressBookCard";

function App() {
  const { showNavbar } = useAuth()
  return (
    <React.Fragment>
      <Router>

        { showNavbar && <NavBar /> }

        <Routes>
          <Route index element={<HomePage />} />
          <Route path="/" element={<HomePage />} />
          <Route path="/contactus" element={<ContactUs />} />
          <Route path="/dashboard" element={<AccountDashboard />} />
          <Route path="/product" element={<SingleProduct />} />
          <Route path="/dashboard" element={
            <ProtectCustomerRoute>
              <AccountDashboard />
            </ ProtectCustomerRoute>
          } />
          <Route path="/signup" element={<FormSignUp />} />
          <Route path="/dashboard-acc-info" element={
          <ProtectCustomerRoute>
          <DashboardInfo />
        </ ProtectCustomerRoute>} />
          <Route path="/addressbook" element={
            <ProtectCustomerRoute>
            <AddressbookDashboard />
          </ ProtectCustomerRoute>} />
          
          <Route path="/login" element={
          <IsAuthenticated>
          <FormLogin />
          </IsAuthenticated>
          } />
          <Route path="/new-address" element={
          <ProtectCustomerRoute>
          <NewAddress />
        </ ProtectCustomerRoute>} />
          <Route path="/forgotpassword" element={<ForgottenPassword />} />
          <Route path="/resetpassword" element={<ResetPassword />} />
          <Route path="/aboutus" element={<AboutUs />} />
          <Route path="/shopping-cart" element={<ShoppingCart />}/>
          <Route path="/verifyRegistration" element={<VerifyRegistration />} />
          <Route path="/check-mail" element={<CheckMail />} />
          <Route path="/process-payment" element={<ProcessPayment />} />
          <Route path="/checkout" element={<Checkout2 />} />

          <Route
            path="/shop"
            element={
              <Product
                title={"Products"}
                url={"products"}
                productUrlProp={`products/paginated-all`}
                isEditable={false}
                isId={false} showFavorites={false}
              />
            }
          />
          <Route path="/shop/products/:id" element={<SingleProduct />} />
          <Route
            path="favorites"
            element={
              <Product
                title={"Favorites"}
                url={"favorites"}
                productUrlProp={`customer/products/favorites/viewAllFavorites`}
                displayCategories={false}
                isEditable={true}
                isId={false}
                showFavorites={true}
              />
            }
          />
          <Route path="favorites/:id" element={<SingleProduct />} />

          <Route path="*" element={<Page404 />} />
          
          <Route path="/accountInfo" element={
          <ProtectCustomerRoute>
            <UserInformation />
        </ ProtectCustomerRoute>} />

          <Route
            path="categories/viewByCategory/:id"
            element={
              <Subcategory
                title={"Subcategories"}
                url={`subcategory/viewByCategory`}
              />
            }
          />

          <Route path="categories/subcategories/:id/shop" element={<Product 
                  title={"Products"}
                  url={"products"}
                  productUrlProp={`products/subcategory`}
                  isEditable={false}
                  isId={true}
              />}
          />
          <Route path="categories/subcategories/:id/shop/products/:id" 
                  element={<SingleProduct /> } />
          
          <Route element={<RequireAdminAuth /> } >
            <Route path='/admin' element={<Layout />}>
                <Route index element={<Home />} />
                <Route path="/admin/products" element={<TableView tableTitle={ "PRODUCTS" }/>} />
                <Route path="/admin/users" element={<PersonTableView tableTitle={"ALL USERS"}/>} />
                <Route path="/admin/orders" element={<OrdersTableView tableTitle={"ALL ORDERS"}/>} />
                <Route path="/admin/category" element={<SubcategoryTableView tableTitle={"CATEGORIES/SUBCATEGORIES"}/>} />
                <Route path="/admin/pickupCenter" element={<PickupTableView tableTitle={"ALL PICKUP CENTERS"}/>} />
                <Route path="/admin/delivery" element={<PickupTableView tableTitle={"ALL PICKUP CENTERS"}/>} />
                <Route path="/admin/states" element={<StatesTableView tableTitle={"ALL STATES"}/>} />
            </Route>
          </Route>
          
          <Route path="/wallet" element={
            <ProtectCustomerRoute>
              <WalletDashboard />
            </ ProtectCustomerRoute>
          } />
          <Route path="/confirm-payment" element={<VerifyPayment />} />
          <Route path="/open-orders" element={<OpenOrders />} />
          <Route path="/closed-orders" element={<ClosedOrders />} />
          <Route path="super/dashboard" element={< Layout />} />
        </Routes>
        <BackToTop />
        <Footer />
      </Router>
    </React.Fragment>
  );
}

export default App;
