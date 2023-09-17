import React from "react";
import ReactDOM from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
// import $ from "jquery";
// import Popper from "popper.js";
import "bootstrap/dist/js/bootstrap.bundle.min";
import "bootstrap-icons/font/bootstrap-icons.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "./index.css";
import App from "./App";
import {CookiesProvider} from "react-cookie"
ReactDOM.render(
  <React.StrictMode>
    <CookiesProvider>
    <App />
    </CookiesProvider>
   
  </React.StrictMode>,
  document.getElementById("root")
);
