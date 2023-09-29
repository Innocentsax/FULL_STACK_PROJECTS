import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { BiEditAlt } from "react-icons/bi";
import { TbCurrencyNaira } from "react-icons/tb";
import DashboardCard from "../../components/DashboardComponents/DashboardCard";
import SideBar from "../../components/SideBar/SideBar";
import { useAuth } from "../../context/authcontext";
// import "./accountDashboard.css";
import { ProtectCustomerRoute } from "../../context/ProtectRoute";

export const MobileMenu = ({title, to}) => {
  return(
    <div className="flex border-2 justify-between items-center px-2 py-3 shadow-sm">
      <Link to={to}>{title}</Link>
      <p> &#62; </p>
    </div>
  )
}


const AccountDashboard = () => {
  const [screenSize, setScreenSize] = useState(window.innerWidth);

  const handleResize = () => {
    setScreenSize(window.innerWidth);
  };

  const { GetUser, getUser, setGetUser } = useAuth();

  //console.log(getUser);

  const { GetWallet, getWallet, setGetWallet } = useAuth();

  
  useEffect(() => {
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  useEffect(() => {
    GetUser();
  }, []);

 useEffect(() => {
   GetWallet();
 }, []);


  const onChange = (e) => {
    e.preventDefault();
    setGetUser({ ...getUser, [e.target.name]: e.target.value });
    setGetWallet({...getWallet, [e.target.name]: e.target.value});
  };

  return (
    <div className="md:px-[5%] lg:py-[5%] pb-0">
      {screenSize > 768 ? (
        <div className="row mx-auto">
          {/* Desktop layout */}
          <div className="col-md-4">
            <SideBar />
            <p className="text-20xl text-red-700"></p>
          </div>

          <div className="col-md-8 bg-white drop-shadow-md rounded-md pb-5 divide-y">
            <div className="border-gray-300 border-b-1 py-3">
              <h1 className="text-2xl font-bold-900 ">Account Overview</h1>
            </div>

            <div className="grid xl:grid-cols-2 lg:grid-cols-1 sm:grid-cols-1 gap-3 px-3 pt-4">
              <div className="">
                <DashboardCard
                  title="Account Details"
                  subtitle={getUser.firstName}
                  content={getUser.email}
                />
              </div>

              <div className="">
                <DashboardCard
                  title="Address Book"
                  subtitle="Your Default Shipping Address"
                  content={getUser.address}
                  icon={<BiEditAlt />}
                />
              </div>
              <div className="">
                <DashboardCard
                  title="Wallet Balance"
                  content={getWallet.walletBalance}
                />
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div className="px-2 py-5 m-h-[100%] flex flex-col">
          <div className="px-2 py-3 shadow-md">
            <h6>My Oakland Account</h6>
          </div>
          <MobileMenu title="Orders" />
          <MobileMenu title="Favourites" />
          <MobileMenu title="Edit Profile" to="/dashboard-acc-info" />
          <MobileMenu title="Address Book" to='/addressbook' />
          <MobileMenu title="Close Account" />
          <button type="button" className="bg-[#7e6a17] self-center text-[white] py-2 px-4 mt-4 rounded-md">Logout</button>
        </div>
      )}
    </div>
  );
};

export default AccountDashboard;
