import { ShoppingCart } from "@mui/icons-material";
import { Badge } from "@mui/material";
import { AiOutlineClose, AiOutlineMail, AiOutlineMenu } from "react-icons/ai";
import { React, useState, useEffect, useRef } from "react";
import "./Navbar.css";
import { ImFacebook2 } from "react-icons/im";
import { BsClock, BsInstagram, BsTelephone, BsTwitter } from "react-icons/bs";
import { Link } from "react-router-dom";
import { useAuth } from "../../context/authcontext";
import { FiLogOut } from "react-icons/fi";
import { RiArrowDropDownLine } from "react-icons/ri";

export const UserMenuDropdown = ({ closeMenu }) => {
  const ref = useRef(null);


  const handleClickOutside = (e) => {
    if (ref.current && !ref.current.contains(e.target)) {
      closeMenu();
    }
  };
  useEffect(() => {
    document.addEventListener("click", handleClickOutside, true);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [handleClickOutside]);

  return (
    <div
      ref={ref}
      className="flex flex-col gap-2 w-[140px] p-[0.5rem] bg-[white] absolute top-[100%] overflow-hidden border-[2px]"
    >
      <Link to="/dashboard" className="text-[black] border-b text-[1rem]">
        Account
      </Link>
      <Link to="/wallet" className="text-[black] text-[1rem]">
        Wallet
      </Link>
    </div>
  );
};

const Navbar = () => {
  const { Logout, localStorageValue, cartItems, GetAllCartItems, itemCount } = useAuth();

  const [nav, setNav] = useState(false);
  const [key, setKey] = useState(false);
  const [sideBar, setSideBar] = useState(false);
  const { getUser, GetUser } = useAuth();
  const [userMenu, setUserMenu] = useState(false);

  const handleNav = () => {
    setNav(!nav);
  };

  const handleSideBar = () => {
    setSideBar(!sideBar);
  };

  const [data, setData] = useState( localStorage.getItem("signature"));

  useEffect(() => {
    const localStorageValue = localStorage.getItem("signature");
    if (localStorageValue === null || localStorageValue.length <= 4 || localStorage.length === 0) {
      setKey(false);
    } else {
      setKey(true);
      setData(localStorageValue)
    }
  }, [localStorageValue]);

  useEffect(() => {
    GetUser();
  }, []);

  useEffect(() => {
    GetAllCartItems()
  }, [GetAllCartItems])
  
  // cartItems.items.length === 0 ? itemCount = 0 : itemCount = cartItems.items.length

  return (
    <div className="text-black items-center w-[100%] top-0 mb-[3rem]">
      {/* =========================LARGE SCREEN============================================ */}

      <div className="fixed shadow-sm z-50 top-0  bg-white justify-center items-center text-[0.7rem] hidden lg:flex w-[100%] p-2">
        <div
          onClick={handleSideBar}
          className="hidden md:block self-center w-[170px]"
        >
          {sideBar ? <AiOutlineClose size={30} /> : <AiOutlineMenu size={30} />}
        </div>
        <ul className="hidden lg:flex w-[380px] justify-center">
          <li className="">
            <Link to="/">HOME</Link>
          </li>
          <li className="">
            <Link to="/aboutus">ABOUT US</Link>
          </li>
          <li className="">
            <Link to="/shop">SHOP</Link>
          </li>
        </ul>
        <ul className="items-center hidden lg:block">
          <li className="w-full text-4xl font-bold text-[#403414]">OAKLAND</li>
        </ul>
        <ul className="hidden lg:flex w-[200px] justify-center">
          <li className="">
            <Link to="/favorites">FAVOURITES</Link>
          </li>
          <li className="">
            <Link to="/contactus">CONTACT</Link>
          </li>
        </ul>
        <ul className="hidden lg:flex w-[200px]">
          <li className="">
            <span style={{ color: "rgb(81, 81, 81)" }}>&nbsp;&nbsp;</span>
            {data && 
            (<Link to="/shopping-cart">
              <Badge color="secondary" badgeContent={itemCount}>
                <ShoppingCart className="text-[#403414]" />
              </Badge>
            </Link>)
            }
          </li>
        </ul>
        {!key ? (
          <ul className="hidden lg:flex w-[200px]">
            <li className="">
              <Link to="/signup">SIGNUP</Link>
            </li>
            <li className="">
              <Link to="/login">SIGNIN</Link>
            </li>
          </ul>
        ) : (
          <ul className="hidden lg:flex w-[250px] items-center">
            <li className="flex items-center text-[0.9rem]">Hi,&nbsp;
              {getUser.firstName}{" "}
              <RiArrowDropDownLine
                size={40} className='text-[#7e6a17] cursor-pointer'
                onClick={() => setUserMenu(!userMenu)}
              />
            </li>
            <li className="">
              <button onClick={Logout}>
                {/* <FiLogOut className="text-[1.3rem]" /> */}
                LOGOUT
              </button>
            </li>
            {userMenu && (
              <UserMenuDropdown closeMenu={() => setUserMenu(false)} />
            )}
          </ul>
        )}

        <div
          className={
            sideBar
              ? "fixed w-[600px] left-0 top-0 pr-[6rem] hidden rounded-b-sm lg:block bg-gray-800 shadow-sm ease-in-out duration-500 text-white z-10"
              : "fixed left-[-1000%] ease-in-out duration-500"
          }
        >
          <div
            onClick={handleSideBar}
            className="hidden ml-[100%] lg:block self-center w-[115px] pl-4 pt-4"
          >
            {sideBar ? (
              <AiOutlineClose size={30} />
            ) : (
              <AiOutlineMenu size={30} />
            )}
          </div>
          <div>
            <div className="flex flex-col gap-[4rem] h-[100vh] items-center px-[2rem] pt-[8rem] pb-[23rem] overflow-y-scroll">
              <div className="text-center flex flex-col gap-4">
                <h3 className="text-3xl font-bold">OAKLAND</h3>
                <p className="text-[1.2rem] text-gray-500">
                  Far far away, behind the world mountains, far from the
                  countires vokalia and consonatia there live the blind texts
                </p>
              </div>
              <div className=" flex flex-wrap gap-4">
                <img
                  className="w-[130px] bg-white"
                  src="../images/chair.jpeg"
                  alt=""
                />
                <img
                  className="w-[130px] bg-white"
                  src="../images/lamp3.jpeg"
                  alt=""
                />
                <img
                  className="w-[130px] bg-white"
                  src="../images/sofa.jpeg"
                  alt=""
                />
                <img
                  className="w-[130px] bg-white"
                  src="../images/glass-top-table.jpeg"
                  alt=""
                />
                <img
                  className="w-[130px] bg-white"
                  src="../images/end_table.png"
                  alt=""
                />
                <img
                  className="w-[130px] bg-white"
                  src="../images/wooden.png"
                  alt=""
                />
              </div>
              <div className="flex flex-col gap-3 text-[1.2rem] items-center font-light ">
                <div className="flex gap-2 items-center">
                  <span className="">
                    <BsClock />
                  </span>
                  <span>Sun - Sat: 9:00 AM - 17:00 PM</span>
                </div>
                <div className="flex gap-2 items-center">
                  <span className="">
                    <AiOutlineMail />
                  </span>
                  <span>oakland@gmail.com</span>
                </div>
                <div className="flex gap-2 items-center">
                  <span className="">
                    <BsTelephone />
                  </span>
                  <span>(+234)8166386376</span>
                </div>
                <div className="flex gap-8 text-black mt-[2.5rem] ">
                  <Link className="text-white" to="/">
                    <ImFacebook2 />
                  </Link>
                  <Link className="text-white" to="/">
                    <BsTwitter />
                  </Link>
                  <Link className="text-white" to="/">
                    <BsInstagram />
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* =========================MEDIUM SCREEN=========================== */}

      <div className="flex justify-between w-[100%] px-4 py-2 top-0 bg-white sm:hidden md:flex lg:hidden">
        <div
          onClick={handleNav}
          className="hidden md:block lg:hidden self-center"
        >
          {nav ? <AiOutlineClose size={30} /> : <AiOutlineMenu size={30} />}
        </div>

        <div className="text-center hidden md:block">
          <h1 className="w-full text-3xl m-2 font-bold text-[#403414]">
            OAKLAND
          </h1>
        </div>
        <div className="self-center hidden md:block lg:hidden">
          <span style={{ color: "rgb(81, 81, 81)" }}>₦0.00&nbsp;&nbsp;</span>
          {data && 
            (<Link to="/shopping-cart">
              <Badge color="secondary" badgeContent={itemCount}>
                <ShoppingCart className="text-[#403414]" />
              </Badge>
            </Link>)
          }
        </div>

        <div
          className={
            nav
              ? "fixed z-50 left-0 top-0 pr-[6rem] hidden rounded-b-sm md:block lg:hidden shadow-sm bg-[white] ease-in-out duration-500"
              : "fixed left-[-100%] ease-in-out duration-500"
          }
        >
          <div
            onClick={handleNav}
            className="hidden md:block self-center w-[115px] pl-4 pt-4"
          >
            {nav ? <AiOutlineClose size={30} /> : <AiOutlineMenu size={30} />}
          </div>
          {!key ? (
            <ul className="py-4 pl-8">

              <li className="p-2">
                <Link to="/">HOME</Link>
              </li>
              <li className="p-2">
                <Link to="/">ABOUT US</Link>
              </li>
              <li className="p-2">
                <Link to="/shop">SHOP</Link>
              </li>
              <li className="p-2">
              <Link to="/favorites">FAVOURITES</Link>
              </li>
              <li className="p-2">
                <Link to="/contactus">CONTACT</Link>
              </li>
              <li className="p-2">
                <Link to="/signup">SIGNUP</Link>
              </li>
              <li className="p-2">
                <Link to="/login">SIGNIN</Link>
              </li>
            </ul>
          ) : (
            <ul className="py-4 pl-8">
              <li className="p-2">
                <Link to="/">HOME</Link>
              </li>
              <li className="p-2">
                <Link to="/">ABOUT US</Link>
              </li>
              <li className="p-2">
                <Link to="/shop">SHOP</Link>
              </li>
              <li className="p-2">
              <Link to="/favorites">FAVOURITES</Link>
              </li>
              <li className="p-2">
                <Link to="/contactus">CONTACT</Link>
              </li>
              <li className="flex items-center text-[0.7rem] p-2"><Link to='/dashboard'>DASHBOARD</Link></li>
              <li className="p-2">
                <button onClick={Logout}>
                  <FiLogOut className="text-[1.3rem]" />
                </button>
              </li>
            </ul>
          )}
        </div>
      </div>

      {/* ==============================SMALL SCREEN====================================================== */}

      {/* flex justify-between w-[100%] px-4 py-2 top-0 bg-white sm:hidden md:flex lg:hidden */}
      <div className="fixed shadow-sm z-50 flex justify-between w-[100%] md:hidden items-center top-0  px-4 py-1 bg-[white]">
        <div onClick={handleNav} className="md:hidden">
          {nav ? <AiOutlineClose size={30} /> : <AiOutlineMenu size={30} />}
        </div>
        <div className="text-center md:hidden">
          <h1 className="w-full text-3xl m-2 font-bold text-[#403414]">
            OAKLAND
          </h1>
        </div>

        <div
          className={
            nav
              ? "fixed left-0 top-0 pr-[4rem] rounded-sm md:hidden shadow-sm bg-[white] ease-in-out duration-500 z-10"
              : "fixed left-[-100%] ease-in-out duration-500"
          }
        >
          <div onClick={handleNav} className="md:hidden pt-3 pl-3">
            {nav ? <AiOutlineClose size={30} /> : <AiOutlineMenu size={30} />}
          </div>
          {!key ? (
            <ul className="py-4 pl-8">

              <li className="p-2">
                <Link to="/">HOME</Link>
              </li>
              <li className="p-2">
                <Link to="/">ABOUT US</Link>
              </li>
              <li className="p-2">
                <Link to="/shop">SHOP</Link>
              </li>
              <li className="p-2">
              <Link to="/favorites">FAVOURITES</Link>
              </li>
              <li className="p-2">
                <Link to="/contactus">CONTACT</Link>
              </li>
              <li className="p-2">
                <Link to="/signup">SIGNUP</Link>
              </li>
              <li className="p-2">
                <Link to="/login">SIGNIN</Link>
              </li>
            </ul>
          ) : (
            <ul className="py-4 pl-8">
              <li className="p-2">
                <Link to="/">HOME</Link>
              </li>
              <li className="p-2">
                <Link to="/">ABOUT US</Link>
              </li>
              <li className="p-2">
                <Link to="/shop">SHOP</Link>
              </li>
              <li className="p-2">
              <Link to="/favorites">FAVOURITES</Link>
              </li>
              <li className="p-2">
                <Link to="/contactus">CONTACT</Link>
              </li>
              <li className="flex items-center text-[0.7rem] p-2"><Link to='/dashboard'>DASHBOARD</Link></li>
              <li className="p-2">
                <button onClick={Logout}>
                  <FiLogOut className="text-[1.3rem]" />
                </button>
              </li>
            </ul>
          )}
        </div>
      </div>
    </div>
  );
};

export default Navbar;