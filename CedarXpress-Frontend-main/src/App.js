import { BrowserRouter as Router, Route, Routes} from "react-router-dom";
import React, { Fragment } from 'react';
import axios from "axios";
import Navbar from './component/home/Navs';
import Home from './component/home/Home';
import HomeCss from './component/home/Home.module.css';
import ErrorPage from './component/errorPage/ErrorPage';
import Admin from './component/admin/Admin';
import Aboutus from './component/about/About-us';
import UploadImage from "./component/image/UploadImage"
import Signup from "./component/signup/Signup";
import Login from "./component/login/Login";
import ProductArchive from "./component/product-archive/ProductArchive";
import Cart from "./component/cart/Cart"

function App() {
    const [sideNav, setSideNav] = React.useState(false);
    const [data, setDate] = React.useState([]);


    //  const fetchdata = () => {   
    //       axios.get("http://localhost:8080/products").then((resp) => {
    //         console.log(resp.data);
    //         setDate(resp)
    //       }).catch((error) => {
    //         console.log(error);
    //       })
    //   }

    //   console.log(data);

    React.useEffect(() => {
      // fetchdata()
    }, [])
      const showSideNav = () => {
      setSideNav(prevSide => !prevSide);
    }

  return (
    <Fragment>
        <Router> 
          <Navbar className={`${HomeCss.fixedPosition}`} HomeCss={HomeCss} showSideNav={showSideNav} />
        <Routes>
          <Route path='/about' element={<Aboutus />} />
          <Route path="/admin/dashbord/upload-image" element={<UploadImage />}/>
          <Route path='/signup' element={<Signup />} />
          <Route path='/login' element={<Login />} />
          <Route path="/admin/dashbord" element={<Admin HomeCss={HomeCss} />}/>
          <Route path="*" element={<ErrorPage />} />
          <Route path="/product-archive" element={<ProductArchive />} />
          <Route path='/' element={< Home sideNav={sideNav} showSideNav={showSideNav} />} />
          <Route path="/cart" element={<Cart />} />
        </Routes>
      </Router>
    </Fragment>  
  );
  }
export default App;
