import React, { useState } from "react";
import "./App.css";
import Home from "./components/Home";
import Clothes from "./components/Clothes";
import Shoes from "./components/Shoes";
import Accessories from "./components/Accessories";
import Cart from "./components/Cart";
import Account from "./components/Account";
import Favourites from "./components/Favourites";
import NavBar from "./components/NavBar";
import Sidebar from "./components/SideBar";
import Footer from "./components/Footer";
import Checkout from "./components/Checkout"
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Logo from "./logo.svg";
import SignInSignOutPage from "./components/SignInSignOutPage";
function App() {

  const [sidebarState, setSideBarState] = useState(null)
  const [toggleSidebar, setToggleSideBar] = useState(null)


  return (
    <>
      <main>
        <Router>
          <NavBar
            sidebarState={sidebarState}
            setSideBarState={setSideBarState}
            toggleSidebar={toggleSidebar}
          />
          <Sidebar
            sidebarState={sidebarState}
            setSideBarState={setSideBarState}
            toggleSidebar={toggleSidebar}
          />

          <Footer />
          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/clothes" component={Clothes} />
            <Route path="/shoes" component={Shoes} />
            <Route path="/accessories" component={Accessories} />
            <Route path="/logo">
              <img svg={Logo} alt="logo" />
            </Route>
            <Route path="/cart" component={Cart} />
            <Route path="/account" component={SignInSignOutPage} />
            <Account />
            <Route path="/favourite" component={Favourites} />
            <Route path="/cart/checkout" component={Checkout} />
            {/* <Route exact path="/"></Route> */}
            <Route path="/blogs"></Route>
            <Route path="/contact"></Route>
          </Switch>
        </Router>
      </main>
    </>
  );
}

export default App;