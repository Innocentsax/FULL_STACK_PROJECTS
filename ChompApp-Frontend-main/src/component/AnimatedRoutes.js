import React from 'react'
import HomePage from "../pages/HomePage/HomePage";
import SignUp from "../pages/Signup/SignUp";
import Login from "../pages/Login/Login";
import { AuthGuard } from "../guards/AuthGuard";
import { Role } from "../models/Role";
import AdminPage from "../pages/AdminPage/AdminPage";
import UnauthorizedPage from "../pages/Unauthorized/UnauthorizedPage";
import NotFoundPage from "../pages/Not-Found/NotFoundPage";
import ViewUsersDetails from "../pages/UserDetails/UserDetails";
import CartPage from "../pages/Cart/CartPage";
import SingleProductPage from "../pages/SingleProduct/SingleProductPage";
import FullMenu from "../pages/FullMenu/FullMenu";
import { Routes, Route, useLocation } from "react-router-dom";
import { AnimatePresence } from "framer-motion";


const AnimatedRoutes = () => {
    const location = useLocation();
  return (
    <AnimatePresence>
      <Routes location={location} key={location.pathname}>
        <Route path="/" element={<HomePage />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<Login />} />
        <Route
          path="/admin"
          element={
            <AuthGuard roles={[Role.ROLE_ADMIN]}>
              <AdminPage />
            </AuthGuard>
          }
        />
        <Route path="/userDetails" element={<ViewUsersDetails />} />
        <Route path="/cart" element={<CartPage />} />
        <Route path="/fullMenu" element={<FullMenu />} />
        <Route
          path="/singleProduct/:productId"
          element={<SingleProductPage />}
        />
        <Route path="/404" element={<NotFoundPage />} />
        <Route path="/401" element={<UnauthorizedPage />} />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </AnimatePresence>
  );
}

export default AnimatedRoutes