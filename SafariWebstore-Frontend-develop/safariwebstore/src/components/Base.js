import React from "react";
import Footer from "./Footer";
import SideBar from "./SideBar";
import NavBar from "./NavBar";
//import { BrowserRouter } from "react-router-dom";

export default function Base() {
  return (
    <>
      <div>
        {/* <NavBar /> */}
        <SideBar />
        {/* <Footer /> */}
      </div>
    </>
  );
}
